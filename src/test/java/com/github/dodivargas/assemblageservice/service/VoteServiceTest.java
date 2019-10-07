package com.github.dodivargas.assemblageservice.service;

import com.github.dodivargas.assemblageservice.exception.ClosedRulingException;
import com.github.dodivargas.assemblageservice.exception.DuplicateVoteException;
import com.github.dodivargas.assemblageservice.exception.RulingNotOpenForVoteException;
import com.github.dodivargas.assemblageservice.repository.RulingStatusRepository;
import com.github.dodivargas.assemblageservice.repository.VoteRepository;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static com.github.dodivargas.assemblageservice.stubs.RulingStubs.*;
import static com.github.dodivargas.assemblageservice.stubs.VoteStubs.buildVote;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VoteServiceTest {

    private VoteService voteService;

    private RulingStatusRepository rulingStatusRepository;
    private VoteRepository voteRepository;
    private RulingService rulingService;

    public VoteServiceTest() {
        this.rulingStatusRepository = Mockito.mock(RulingStatusRepository.class);
        this.voteRepository = Mockito.mock(VoteRepository.class);
        this.rulingService = Mockito.mock(RulingService.class);
        this.voteService = new VoteService(rulingStatusRepository, voteRepository, rulingService);
    }

    @Test
    public void createVoteForRulingOpenForVoteThenReturnSuccess() {
        when(rulingStatusRepository.findByRulingId(any())).thenReturn(Optional.of(buildRulingStatusEntityWithExpirationDateInFuture()));

        voteService.createVote(buildVote());

        verify(rulingStatusRepository, times(0)).save(any());
        verify(voteRepository, times(1)).save(any());
    }

    @Test(expected = RulingNotOpenForVoteException.class)
    public void createVoteForRulingNotOpenForVoteThenThrowRulingNotOpenException() {
        when(rulingStatusRepository.findByRulingId(any())).thenReturn(Optional.empty());

        voteService.createVote(buildVote());
    }

    @Test(expected = ClosedRulingException.class)
    public void createVoteForRulingNotOpenForVoteThenThrowClosedRulingException() {
        when(rulingStatusRepository.findByRulingId(any())).thenReturn(Optional.of(buildRulingStatusEntityWithOpenForVoteFalse()));

        voteService.createVote(buildVote());
    }

    @Test(expected = ClosedRulingException.class)
    public void createVoteForRulingWithDateExpiredThenThrowRulingNotOpenException() {
        when(rulingStatusRepository.findByRulingId(any())).thenReturn(Optional.of(buildRulingStatusEntityWithExpirationDateInPast()));
        when(rulingService.expirationDateIsExceeded(any())).thenReturn(true);

        voteService.createVote(buildVote());
    }

    @Test(expected = DuplicateVoteException.class)
    public void createVoteForSameUserTaxIdThenThrowDuplicateVoteException() {
        when(rulingStatusRepository.findByRulingId(any())).thenReturn(Optional.of(buildRulingStatusEntityWithExpirationDateInFuture()));
        when(voteRepository.save(any())).thenThrow(new DataIntegrityViolationException(""));

        voteService.createVote(buildVote());
    }


}