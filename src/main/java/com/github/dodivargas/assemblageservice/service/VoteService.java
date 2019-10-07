package com.github.dodivargas.assemblageservice.service;

import com.github.dodivargas.assemblageservice.dto.Vote;
import com.github.dodivargas.assemblageservice.entity.RulingEntity;
import com.github.dodivargas.assemblageservice.entity.RulingStatusEntity;
import com.github.dodivargas.assemblageservice.entity.VoteEntity;
import com.github.dodivargas.assemblageservice.exception.ClosedRulingException;
import com.github.dodivargas.assemblageservice.exception.DuplicateVoteException;
import com.github.dodivargas.assemblageservice.exception.RulingNotOpenForVoteException;
import com.github.dodivargas.assemblageservice.repository.RulingStatusRepository;
import com.github.dodivargas.assemblageservice.repository.VoteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private RulingStatusRepository rulingStatusRepository;
    private VoteRepository voteRepository;
    private RulingService rulingService;


    public VoteService(RulingStatusRepository rulingStatusRepository, VoteRepository voteRepository, RulingService rulingService) {
        this.rulingStatusRepository = rulingStatusRepository;
        this.voteRepository = voteRepository;
        this.rulingService = rulingService;
    }

    public void createVote(Vote vote) {
        RulingStatusEntity rulingStatusEntity = rulingStatusRepository.findByRulingId(RulingEntity.RulingEntityBuilder.of().withId(vote.getRulingId()).build())
                .orElseThrow(RulingNotOpenForVoteException::new);
        validateRulingIsOpenForVote(rulingStatusEntity);
        if (rulingStatusEntity.getOpenForVote()) {
            saveVote(vote, rulingStatusEntity);
        }
    }

    private void validateRulingIsOpenForVote(RulingStatusEntity rulingStatusEntity) {
        if (!rulingStatusEntity.getOpenForVote()) {
            throw new ClosedRulingException();
        }
        if (rulingService.expirationDateIsExceeded(rulingStatusEntity)) {
            rulingStatusEntity.setOpenForVote(false);
            rulingStatusRepository.save(rulingStatusEntity);
            throw new ClosedRulingException();
        }
    }

    private void saveVote(Vote vote, RulingStatusEntity rulingStatusEntity) {
        try {
            VoteEntity voteEntity = buildVoteEntity(vote, rulingStatusEntity);
            voteRepository.save(voteEntity);
        } catch (DataIntegrityViolationException e) {
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
