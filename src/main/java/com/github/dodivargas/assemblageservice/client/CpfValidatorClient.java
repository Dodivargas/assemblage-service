package com.github.dodivargas.assemblageservice.client;

import com.github.dodivargas.assemblageservice.client.response.CpfValidationResponse;
import com.github.dodivargas.assemblageservice.exception.InvalidTaxIdException;
import com.github.dodivargas.assemblageservice.exception.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;



@Component
public class CpfValidatorClient {

    private Logger logger = LoggerFactory.getLogger(CpfValidatorClient.class);

    private RestTemplate restTemplate;
    private final String BASE_URL = "https://user-info.herokuapp.com/users/";

    public CpfValidatorClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CpfValidationResponse checkIfCpfIsAbleToVote(String taxId) {
        try {
            return restTemplate.getForObject(BASE_URL + taxId, CpfValidationResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new InvalidTaxIdException();
            }
            throw  new UnexpectedException();
        }
    }
}
