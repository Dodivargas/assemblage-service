package com.github.dodivargas.assemblageservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dodivargas.assemblageservice.dto.RuleResult;
import com.github.dodivargas.assemblageservice.dto.Ruling;
import com.github.dodivargas.assemblageservice.dto.Vote;
import com.github.dodivargas.assemblageservice.entity.RulingEntity;
import com.github.dodivargas.assemblageservice.entity.RulingStatusEntity;
import com.github.dodivargas.assemblageservice.exception.RulingNeverOpenForVoteException;
import com.github.dodivargas.assemblageservice.exception.RulingNotFoundException;
import com.github.dodivargas.assemblageservice.repository.RulingRepository;
import com.github.dodivargas.assemblageservice.repository.RulingStatusRepository;
import com.github.dodivargas.assemblageservice.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RulingService {

    private Logger logger = LoggerFactory.getLogger(VoteService.class);

    private RulingRepository rulingRepository;
    private RulingStatusRepository rulingStatusRepository;
    private VoteRepository voteRepository;
    private ObjectMapper objectMapper;
    private Integer openedTimeInMinutes;

    public RulingService(RulingRepository rulingRepository, RulingStatusRepository rulingStatusRepository, VoteRepository voteRepository, ObjectMapper objectMapper, @Value("${ruling.openedTimeInMinutes}") Integer openedTimeInMinutes) {
        this.rulingRepository = rulingRepository;
        this.rulingStatusRepository = rulingStatusRepository;
        this.voteRepository = voteRepository;
        this.objectMapper = objectMapper;
        this.openedTimeInMinutes = openedTimeInMinutes;
    }

    public Ruling create(String name) {
        logger.info("Starting creating rule with name {}.", name);
        return objectMapper.convertValue(rulingRepository.save(new RulingEntity(name)), Ruling.class);
    }

    public void openRuleForVoting(Integer rulingId, Integer openedTime) {
        logger.info("get ruling in database and Checking if ruling exists");
        RulingEntity rulingEntity = rulingRepository.findById(rulingId)
                .orElseThrow(RulingNotFoundException::new);
        RulingStatusEntity rulingStatusEntity = buildRulingStatusEntity(openedTime, rulingEntity);
        logger.info("saving ruling open in database {}", rulingStatusEntity);
        rulingStatusRepository.save(rulingStatusEntity);
    }


    public RuleResult getRuleResult(Integer ruleId) {
        logger.info("get ruling in database and Checking if ruling exists");
        RulingEntity rulingEntity = rulingRepository.findById(ruleId)
                .orElseThrow(RulingNotFoundException::new);
        logger.info("get open ruling in database and Checking if is opened");
        RulingStatusEntity rulingStatusEntity = rulingStatusRepository.findByRulingId(rulingEntity)
                .orElseThrow(RulingNeverOpenForVoteException::new);
        logger.info("calculating voting result for ruleid {}", ruleId);
        Boolean result = calculateVotesInRuling(ruleId);
        if (expirationDateIsExceeded(rulingStatusEntity)) {
            rulingStatusEntity.setOpenForVote(false);
            rulingStatusEntity.setResult(result);
            logger.error("Ruling close for votes because of expirationDate.");
            rulingStatusRepository.save(rulingStatusEntity);
        }
        return buildRuleResult(rulingEntity, rulingStatusEntity, result);
    }

    Boolean expirationDateIsExceeded(RulingStatusEntity rulingStatusEntity) {
        LocalDateTime expirationDate = LocalDateTime.ofInstant(rulingStatusEntity.getExpirationDate().toInstant(), ZoneId.systemDefault());
        return expirationDate.isBefore(LocalDateTime.now());
    }

    private Date getExpirationDate(Integer openedTime) {
        LocalDateTime fiveMinutesLater = LocalDateTime.now().plusMinutes(openedTime != null ? openedTime : openedTimeInMinutes);
        return Date.from(fiveMinutesLater.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Boolean calculateVotesInRuling(Integer ruleId) {
        List<Vote> votes = getRuleAllVotesInRule(ruleId);
        Long inFavor = votes.stream().filter(Vote::getInFavor).count();
        Long against = votes.stream().filter(vote -> !vote.getInFavor()).count();
        return against <= inFavor;
    }

    private List<Vote> getRuleAllVotesInRule(Integer rulingId) {
        return voteRepository.findAllByRulingStatusId(rulingId)
                .stream()
                .map(voteEntity -> objectMapper.convertValue(voteEntity, Vote.class))
                .collect(Collectors.toList());
    }

    private RulingStatusEntity buildRulingStatusEntity(Integer openedTime, RulingEntity rulingEntity) {
        return RulingStatusEntity.RulingStatusEntityBuilder.of()
                .withExpirationDate(getExpirationDate(openedTime))
                .withRulingId(rulingEntity)
                .withOpenForVote(true)
                .build();
    }

    private RuleResult buildRuleResult(RulingEntity rulingEntity, RulingStatusEntity rulingStatusEntity, Boolean result) {
        return RuleResult.RuleResultBuilder.of()
                .withRulingId(rulingEntity.getId())
                .withName(rulingEntity.getName())
                .withAproved(result)
                .withOpenedForVotes(rulingStatusEntity.getOpenForVote())
                .build();
    }
}
