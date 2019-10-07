package com.github.dodivargas.assemblageservice.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dodivargas.assemblageservice.dto.RuleResult;
import com.github.dodivargas.assemblageservice.dto.Ruling;
import com.github.dodivargas.assemblageservice.entity.RulingEntity;
import com.github.dodivargas.assemblageservice.exception.RulingNeverOpenForVoteException;
import com.github.dodivargas.assemblageservice.exception.RulingNotFoundException;
import com.github.dodivargas.assemblageservice.repository.RulingRepository;
import com.github.dodivargas.assemblageservice.repository.RulingStatusRepository;
import com.github.dodivargas.assemblageservice.repository.VoteRepository;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static com.github.dodivargas.assemblageservice.stubs.RulingStubs.*;
import static com.github.dodivargas.assemblageservice.stubs.VoteStubs.buildAprovedVoteList;
import static com.github.dodivargas.assemblageservice.stubs.VoteStubs.buildUnaprovedVoteList;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RulingServiceTest {

    private RulingService rulingService;

    private RulingRepository rulingRepository;
    private RulingStatusRepository rulingStatusRepository;
    private VoteRepository voteRepository;
    private Integer openedTimeInMinutes;


    public RulingServiceTest() {
        this.rulingRepository = Mockito.mock(RulingRepository.class);
        this.rulingStatusRepository = Mockito.mock(RulingStatusRepository.class);
        this.voteRepository = Mockito.mock(VoteRepository.class);
        this.rulingRepository = Mockito.mock(RulingRepository.class);
        this.openedTimeInMinutes = 1;
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.rulingService = new RulingService(rulingRepository, rulingStatusRepository, voteRepository, objectMapper, openedTimeInMinutes);
    }

    @Test
    public void createValidRuleThenReturnSucces() {
        when(rulingRepository.save(any())).thenReturn(RulingEntity.RulingEntityBuilder.of().withId(1).withName("Teste").build());

        Ruling ruling = rulingService.create("Teste");

        assertEquals(Integer.valueOf(1), ruling.getId());
        assertEquals("Teste", ruling.getName());
    }

    @Test
    public void openRuleForVotingWithValidRuleAndOpenedTimeNullThenReturnSuccess() {
        when(rulingRepository.findById(any())).thenReturn(Optional.of(buildRulingEntity()));

        rulingService.openRuleForVoting(1, null);

        verify(rulingStatusRepository, times(1)).save(any());
    }

    @Test
    public void openRuleForVotingWithValidRuleThenReturnSuccess() {
        when(rulingRepository.findById(any())).thenReturn(Optional.of(buildRulingEntity()));

        rulingService.openRuleForVoting(1, 2);

        verify(rulingStatusRepository, times(1)).save(any());
    }


    @Test
    public void getRuleResultForRuleOpenForVoteAndRuleAprovedThenReturnSuccess() {
        RulingEntity rulingEntity = buildRulingEntity();
        when(rulingRepository.findById(1)).thenReturn(Optional.of(rulingEntity));
        when(rulingStatusRepository.findByRulingId(rulingEntity)).thenReturn(Optional.of(buildRulingStatusEntityWithExpirationDateInFuture()));
        when(voteRepository.findAllByRulingStatusId(1)).thenReturn(buildAprovedVoteList());

        RuleResult ruleResult = rulingService.getRuleResult(1);

        assertEquals(true, ruleResult.getAproved());
        assertEquals(true, ruleResult.getOpenedForVotes());
        assertEquals("Teste", ruleResult.getName());
        verify(rulingStatusRepository, times(0)).save(any());
    }

    @Test(expected = RulingNotFoundException.class)
    public void openRuleForVotingForRulingNotExistsThenThrowRulingNotFoundException() {
        when(rulingRepository.findById(1)).thenReturn(Optional.empty());

        rulingService.openRuleForVoting(1, 2);
    }

    @Test
    public void getRuleResultForRuleNotOpenForVoteAndRuleAprovedThenReturnSuccess() {
        RulingEntity rulingEntity = buildRulingEntity();
        when(rulingRepository.findById(1)).thenReturn(Optional.of(rulingEntity));
        when(rulingStatusRepository.findByRulingId(rulingEntity)).thenReturn(Optional.of(buildRulingStatusEntityWithExpirationDateInPast()));
        when(voteRepository.findAllByRulingStatusId(1)).thenReturn(buildAprovedVoteList());

        RuleResult ruleResult = rulingService.getRuleResult(1);

        assertEquals(true, ruleResult.getAproved());
        assertEquals(false, ruleResult.getOpenedForVotes());
        assertEquals("Teste", ruleResult.getName());
        verify(rulingStatusRepository, times(1)).save(any());
    }

    @Test
    public void getRuleResultForRuleNotOpenForVoteAndRuleUnAprovedThenReturnSuccess() {
        RulingEntity rulingEntity = buildRulingEntity();
        when(rulingRepository.findById(1)).thenReturn(Optional.of(rulingEntity));
        when(rulingStatusRepository.findByRulingId(rulingEntity)).thenReturn(Optional.of(buildRulingStatusEntityWithExpirationDateInPast()));
        when(voteRepository.findAllByRulingStatusId(1)).thenReturn(buildUnaprovedVoteList());

        RuleResult ruleResult = rulingService.getRuleResult(1);

        assertEquals(false, ruleResult.getAproved());
        assertEquals(false, ruleResult.getOpenedForVotes());
        assertEquals("Teste", ruleResult.getName());
        verify(rulingStatusRepository, times(1)).save(any());
    }


    @Test(expected = RulingNotFoundException.class)
    public void getRuleResulWithRuleNotExistsThenThrowRulingNotFoundException() {
        when(rulingRepository.findById(1)).thenReturn(Optional.empty());

        rulingService.getRuleResult(1);
    }

    @Test(expected = RulingNeverOpenForVoteException.class)
    public void getRuleResulWithRuleNotOpenForVoteThenThrowRulingNotFoundException() {
        RulingEntity rulingEntity = buildRulingEntity();
        when(rulingRepository.findById(1)).thenReturn(Optional.of(rulingEntity));
        when(rulingStatusRepository.findByRulingId(rulingEntity)).thenReturn(Optional.empty());

        rulingService.getRuleResult(1);
    }
}