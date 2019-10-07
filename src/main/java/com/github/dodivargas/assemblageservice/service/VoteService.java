package com.github.dodivargas.assemblageservice.service;

import com.github.dodivargas.assemblageservice.client.CpfValidatorClient;
import com.github.dodivargas.assemblageservice.client.response.CpfValidationResponse;
import com.github.dodivargas.assemblageservice.dto.Vote;
import com.github.dodivargas.assemblageservice.entity.RulingEntity;
import com.github.dodivargas.assemblageservice.entity.RulingStatusEntity;
import com.github.dodivargas.assemblageservice.entity.VoteEntity;
import com.github.dodivargas.assemblageservice.exception.ClosedRulingException;
import com.github.dodivargas.assemblageservice.exception.DuplicateVoteException;
import com.github.dodivargas.assemblageservice.exception.RulingNotOpenForVoteException;
import com.github.dodivargas.assemblageservice.exception.UserNotAbleToVoteException;
import com.github.dodivargas.assemblageservice.repository.RulingStatusRepository;
import com.github.dodivargas.assemblageservice.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private Logger logger = LoggerFactory.getLogger(VoteService.class);

    private RulingStatusRepository rulingStatusRepository;
    private VoteRepository voteRepository;
    private RulingService rulingService;
    private CpfValidatorClient cpfValidatorClient;
    private static final String USER_IS_UNABLE_TO_VOTE = "UNABLE_TO_VOTE";

    public VoteService(RulingStatusRepository rulingStatusRepository, VoteRepository voteRepository, RulingService rulingService, CpfValidatorClient cpfValidatorClient) {
        this.rulingStatusRepository = rulingStatusRepository;
        this.voteRepository = voteRepository;
        this.rulingService = rulingService;
        this.cpfValidatorClient = cpfValidatorClient;
    }

    public void createVote(Vote vote) {
        CpfValidationResponse cpfValidationResponse = cpfValidatorClient.checkIfCpfIsAbleToVote(vote.getTaxId());
        if (cpfValidationResponse.getStatus().equals(USER_IS_UNABLE_TO_VOTE)) {
            logger.error("User is not able to vote");
            throw new UserNotAbleToVoteException();
        }
        logger.info("Get ruling status in dataBase");
        RulingStatusEntity rulingStatusEntity = rulingStatusRepository.findByRulingId(RulingEntity.RulingEntityBuilder.of().withId(vote.getRulingId()).build())
                .orElseThrow(RulingNotOpenForVoteException::new);
        validateRulingIsOpenForVote(rulingStatusEntity);
        if (rulingStatusEntity.getOpenForVote()) {
            saveVote(vote, rulingStatusEntity);
        }
    }

    private void validateRulingIsOpenForVote(RulingStatusEntity rulingStatusEntity) {
        logger.info("Stating ruling validation.");
        if (!rulingStatusEntity.getOpenForVote()) {
            logger.error("Ruling close for votes.");
            throw new ClosedRulingException();
        }
        if (rulingService.expirationDateIsExceeded(rulingStatusEntity)) {
            rulingStatusEntity.setOpenForVote(false);
            rulingStatusRepository.save(rulingStatusEntity);
            logger.error("Ruling close for votes because of expirationDate.");
            throw new ClosedRulingException();
        }
    }

    private void saveVote(Vote vote, RulingStatusEntity rulingStatusEntity) {
        try {
            VoteEntity voteEntity = buildVoteEntity(vote, rulingStatusEntity);
            logger.info("Saving vote in database for taxId: {}.", voteEntity.getTaxId());
            voteRepository.save(voteEntity);
        } catch (DataIntegrityViolationException e) {
            logger.error("This user trying vote twice in the same ruling");
            throw new DuplicateVoteException();
        }
    }

    private VoteEntity buildVoteEntity(Vote vote, RulingStatusEntity rulingStatusEntity) {
        return VoteEntity.VoteEntityBuilder.of()
                .withRulingId(rulingStatusEntity)
                .withTaxId(vote.getTaxId())
                .withInFavor(vote.getInFavor()).build();
    }
}
