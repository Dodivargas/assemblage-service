package com.github.dodivargas.assemblageservice.stubs;

import com.github.dodivargas.assemblageservice.dto.Vote;
import com.github.dodivargas.assemblageservice.entity.RulingStatusEntity;
import com.github.dodivargas.assemblageservice.entity.VoteEntity;

import java.util.ArrayList;
import java.util.List;

import static com.github.dodivargas.assemblageservice.stubs.RulingStubs.buildRulingStatusEntityWithExpirationDateInFuture;

public class VoteStubs {

    public static List<VoteEntity> buildAprovedVoteList() {
        RulingStatusEntity rulingStatusEntity = buildRulingStatusEntityWithExpirationDateInFuture();
        List<VoteEntity> voteEntityList = new ArrayList<>();
        voteEntityList.add(VoteEntity.VoteEntityBuilder.of().withTaxId("12345678910").withInFavor(true).withRulingId(rulingStatusEntity).build());
        voteEntityList.add(VoteEntity.VoteEntityBuilder.of().withTaxId("12345678911").withInFavor(false).withRulingId(rulingStatusEntity).build());
        voteEntityList.add(VoteEntity.VoteEntityBuilder.of().withTaxId("12345678912").withInFavor(true).withRulingId(rulingStatusEntity).build());
        return voteEntityList;
    }

    public static List<VoteEntity> buildUnaprovedVoteList() {
        RulingStatusEntity rulingStatusEntity = buildRulingStatusEntityWithExpirationDateInFuture();
        List<VoteEntity> voteEntityList = new ArrayList<>();
        voteEntityList.add(VoteEntity.VoteEntityBuilder.of().withTaxId("12345678910").withInFavor(false).withRulingId(rulingStatusEntity).build());
        voteEntityList.add(VoteEntity.VoteEntityBuilder.of().withTaxId("12345678911").withInFavor(true).withRulingId(rulingStatusEntity).build());
        voteEntityList.add(VoteEntity.VoteEntityBuilder.of().withTaxId("12345678912").withInFavor(false).withRulingId(rulingStatusEntity).build());
        return voteEntityList;
    }

    public static Vote buildVote() {
        Vote vote = new Vote();
        vote.setInFavor(true);
        vote.setRulingId(1);
        vote.setTaxId("12345678910");
        return vote;
    }
}