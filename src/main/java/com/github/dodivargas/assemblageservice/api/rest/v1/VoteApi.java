package com.github.dodivargas.assemblageservice.api.rest.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dodivargas.assemblageservice.api.rest.v1.request.VoteRequest;
import com.github.dodivargas.assemblageservice.dto.Vote;
import com.github.dodivargas.assemblageservice.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/vote")
public class VoteApi {

    private ObjectMapper objectMapper;
    private VoteService voteService;

    public VoteApi(ObjectMapper objectMapper, VoteService voteService) {
        this.objectMapper = objectMapper;
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid VoteRequest voteRequest) {
        voteService.createVote(objectMapper.convertValue(voteRequest, Vote.class));
        return ResponseEntity
                .status(HttpStatus.CREATED).build();
    }
}
