package no.mebu.multiplication.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class AMQPConfig {

    @Bean
    public TopicExchange multiplicationTopicExchange(@Value("exchange.multiplication") final String exchangeName) {
        return ExchangeBuilder.topicExchange("exchange.multiplication").durable(true).build();
    }

    @Bean
    public Queue multiplicationQueue(@Value("queue.multiplication") final String queueName) {
        return QueueBuilder.durable("queue.multiplication").build();
    }

    @Bean
    public Binding multiplicationBinding(final Queue multiplicationQueue, TopicExchange multiplicationTopicExchange) {
        return BindingBuilder.bind(multiplicationQueue).to(multiplicationTopicExchange).with("key");
    }

}
