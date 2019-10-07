package com.github.dodivargas.assemblageservice.rabbitmq;

import com.github.dodivargas.assemblageservice.dto.RuleResult;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
public class RulingProducer {

    public static final String EXCHANGE = "ruling";

    private AmqpTemplate rabbitTemplate;

    public RulingProducer(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void rulingResultProducer(String ruleResultAsString) {
        rabbitTemplate.convertAndSend(EXCHANGE, "ruling.result", ruleResultAsString);
    }
}