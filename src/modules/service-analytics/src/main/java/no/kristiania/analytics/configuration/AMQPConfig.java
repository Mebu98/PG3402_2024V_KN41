package no.kristiania.analytics.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class AMQPConfig {

    @Bean
    public TopicExchange analyticsTopicExchange(@Value("${amqp.exchange.analytics}") final String exchangeName) {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Queue challengeCreatedQueue(@Value("${amqp.queue.analytics.challenge.created}") final String queueName) {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding challengeCreatedBinding(final Queue challengeCreatedQueue, final TopicExchange analyticsTopicExchange,
                                           @Value("${amqp.routingKey.challenge.created}") final String routingKey) {
        return BindingBuilder.bind(challengeCreatedQueue).to(analyticsTopicExchange).with(routingKey);
    }
}
