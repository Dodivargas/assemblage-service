package com.github.dodivargas.assemblageservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dodivargas.assemblageservice.dto.Ruling;
import com.github.dodivargas.assemblageservice.entity.RulingEntity;
import com.github.dodivargas.assemblageservice.repository.RulingRepository;
import org.springframework.stereotype.Service;

@Service
public class RulingService {

    private RulingRepository rulingRepository;
    private ObjectMapper objectMapper;

    public RulingService(RulingRepository rulingRepository, ObjectMapper objectMapper) {
        this.rulingRepository = rulingRepository;
        this.objectMapper = objectMapper;
    }

    public Ruling create(String name) {
        return objectMapper.convertValue(rulingRepository.save(new RulingEntity(name)), Ruling.class);
    }
}
