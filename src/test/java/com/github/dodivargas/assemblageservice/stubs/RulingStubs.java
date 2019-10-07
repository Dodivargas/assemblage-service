package com.github.dodivargas.assemblageservice.stubs;

import com.github.dodivargas.assemblageservice.entity.RulingEntity;
import com.github.dodivargas.assemblageservice.entity.RulingStatusEntity;
import com.github.dodivargas.assemblageservice.entity.VoteEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RulingStubs {


    public static RulingStatusEntity buildRulingStatusEntityWithExpirationDateInFuture() {
        Date expirationDate = Date.from(LocalDateTime.now().plusMinutes(500).atZone(ZoneId.systemDefault()).toInstant());
        return RulingStatusEntity.RulingStatusEntityBuilder.of().withId(1).withRulingId(buildRulingEntity()).withOpenForVote(true).withExpirationDate(expirationDate).build();
    }

    public static RulingStatusEntity buildRulingStatusEntityWithExpirationDateInPast() {
        Date expirationDate = Date.from(LocalDateTime.now().minusMinutes(500).atZone(ZoneId.systemDefault()).toInstant());
        return RulingStatusEntity.RulingStatusEntityBuilder.of().withId(1).withRulingId(buildRulingEntity()).withOpenForVote(true).withExpirationDate(expirationDate).build();
    }

    public static RulingEntity buildRulingEntity() {
        return RulingEntity.RulingEntityBuilder.of().withName("Teste").withId(1).build();
    }
}
