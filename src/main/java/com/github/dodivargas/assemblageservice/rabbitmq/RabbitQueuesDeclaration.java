package com.github.dodivargas.assemblageservice.rabbitmq;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Configuration
public class RabbitQueuesDeclaration {

    private final ConnectionFactory connectionFactory;

    public RabbitQueuesDeclaration(@Qualifier("rabbitConnectionFactory") ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @PostConstruct
    public void init() {
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
        declaration(admin);
    }

    private void declaration(AmqpAdmin amqpAdmin) {
        amqpAdmin.declareExchange(new TopicExchange("ruling", true, false));
    }
}
