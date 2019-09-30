package com.github.dodivargas.assemblageservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dodivargas.assemblageservice.dto.Ruling;
import com.github.dodivargas.assemblageservice.entity.RulingEntity;
import com.github.dodivargas.assemblageservice.entity.RulingStatusEntity;
import com.github.dodivargas.assemblageservice.repository.RulingRepository;
import com.github.dodivargas.assemblageservice.repository.RulingStatusRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class RulingService {

    private RulingRepository rulingRepository;
    private RulingStatusRepository rulingStatusRepository;
    private ObjectMapper objectMapper;
    @Value("${ruling.openedTimeInMinutes}")
    private Integer openedTimeInMinutes;

    public RulingService(RulingRepository rulingRepository, RulingStatusRepository rulingStatusRepository, ObjectMapper objectMapper) {
        this.rulingRepository = rulingRepository;
        this.rulingStatusRepository = rulingStatusRepository;
        this.objectMapper = objectMapper;
    }

    public Ruling create(String name) {
        return objectMapper.convertValue(rulingRepository.save(new RulingEntity(name)), Ruling.class);
    }

    public void openRuleForVoting(Integer rulingId, Integer openedTime) {
        RulingEntity rulingEntity = rulingRepository.findById(rulingId)
                .orElseThrow(RuntimeException::new);
        RulingStatusEntity rulingStatusEntity = RulingStatusEntity.RulingStatusEntityBuilder.of()
                .withExpirationDate(getExpirationDate(openedTime))
                .withRulingId(rulingEntity)
                .withOpenForVote(true)
                .build();
        rulingStatusRepository.save(rulingStatusEntity);
    }

    private Date getExpirationDate(Integer openedTime) {
        LocalDateTime fiveMinutesLater = LocalDateTime.now().plusMinutes(openedTime != null ? openedTime : openedTimeInMinutes);
        return Date.from(fiveMinutesLater.atZone(ZoneId.systemDefault()).toInstant());
    }
}
