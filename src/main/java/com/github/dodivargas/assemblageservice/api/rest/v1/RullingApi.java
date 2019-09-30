package com.github.dodivargas.assemblageservice.api.rest.v1;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dodivargas.assemblageservice.api.rest.v1.request.RulingRequest;
import com.github.dodivargas.assemblageservice.api.rest.v1.response.RulingResponse;
import com.github.dodivargas.assemblageservice.service.RulingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/ruling")
public class RullingApi {

    private RulingService rulingService;
    private ObjectMapper objectMapper;

    private static final int OK = 200;
    private static final String OK_MESSAGE = "Operação realizada com sucesso.";
    private static final int CREATED = 201;
    private static final String CREATED_MESSAGE = "Operação foi realizada e resultou em um novo recurso.";


    public RullingApi(RulingService rulingService, ObjectMapper objectMapper) {
        this.rulingService = rulingService;
        this.objectMapper = objectMapper;
    }

    @ApiOperation(value = "Create a ruling")
    @ApiResponses(@ApiResponse(code = OK, message = OK_MESSAGE))
    @PostMapping
    public ResponseEntity<?> create(@RequestBody RulingRequest rulingRequest) {
        return ResponseEntity
                .ok(objectMapper.convertValue(rulingService.create(rulingRequest.getName()), RulingResponse.class));
    }


    @ApiOperation(value = "Open ruling for voting")
    @ApiResponses(@ApiResponse(code = CREATED, message = CREATED_MESSAGE))
    @PostMapping(value = "{id}/openForVote")
    public ResponseEntity<?> openForVote(@PathVariable("id") Integer rulingId,
                                         @RequestParam(required = false) Integer openedTime) {
        rulingService.openRuleForVoting(rulingId, openedTime);
        return ResponseEntity
                .status(HttpStatus.CREATED).build();
    }


}
