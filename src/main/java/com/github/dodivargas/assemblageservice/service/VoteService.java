package com.github.dodivargas.assemblageservice.service;

import com.github.dodivargas.assemblageservice.dto.Vote;
import com.github.dodivargas.assemblageservice.entity.RulingEntity;
import com.github.dodivargas.assemblageservice.entity.RulingStatusEntity;
import com.github.dodivargas.assemblageservice.entity.VoteEntity;
import com.github.dodivargas.assemblageservice.exception.ClosedRulingException;
import com.github.dodivargas.assemblageservice.exception.DuplicateVoteException;
import com.github.dodivargas.assemblageservice.exception.RuleNotOpenForVoteException;
import com.github.dodivargas.assemblageservice.repository.RulingStatusRepository;
import com.github.dodivargas.assemblageservice.repository.VoteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class VoteService {

    private RulingStatusRepository rulingStatusRepository;
    private VoteRepository voteRepository;

    public VoteService(RulingStatusRepository rulingStatusRepository, VoteRepository voteRepository) {
        this.rulingStatusRepository = rulingStatusRepository;
        this.voteRepository = voteRepository;
    }

    public void createVote(Vote vote) {
        RulingStatusEntity rulingStatusEntity = rulingStatusRepository.findByRulingId(RulingEntity.RulingEntityBuilder.of().withId(vote.getRulingId()).build())
                .orElseThrow(RuleNotOpenForVoteException::new);
        validateRuling(rulingStatusEntity);
        if (rulingStatusEntity.getOpenForVote()) {
            saveVote(vote, rulingStatusEntity);
        }
    }

    private void validateRuling(RulingStatusEntity rulingStatusEntity) {
        if (!rulingStatusEntity.getOpenForVote()) {
            throw new ClosedRulingException();
        }
        LocalDateTime expirationDate = LocalDateTime.ofInstant(rulingStatusEntity.getExpirationDate().toInstant(), ZoneId.systemDefault());
        if (expirationDate.isBefore(LocalDateTime.now())) {
            rulingStatusEntity.setOpenForVote(false);
            rulingStatusRepository.save(rulingStatusEntity);
            throw new ClosedRulingException();
        }
    }

    private void saveVote(Vote vote, RulingStatusEntity rulingStatusEntity) {
        try {
            VoteEntity voteEntity = VoteEntity.VoteEntityBuilder.of()
                    .withRulingId(rulingStatusEntity)
                    .withTaxId(vote.getTaxId())
                    .withInFavor(vote.getInFavor()).build();
            voteRepository.save(voteEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateVoteException();
        }
    }


}
