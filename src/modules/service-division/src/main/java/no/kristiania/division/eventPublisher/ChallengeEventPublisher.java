package no.kristiania.division.eventPublisher;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChallengeEventPublisher {
    private final AmqpTemplate amqpTemplate;
    private final String exchange;

    public ChallengeEventPublisher(AmqpTemplate amqpTemplate,
                                   @Value("${amqp.exchange.analytics}")
                                   final String exchange) {
        this.amqpTemplate = amqpTemplate;
        this.exchange = exchange;
    }

    public void challengeCreated(Long id){
        String key = "challenge.created";
        amqpTemplate.convertAndSend(exchange, key, String.format("Challenge created with id %d", id));
    }
}
