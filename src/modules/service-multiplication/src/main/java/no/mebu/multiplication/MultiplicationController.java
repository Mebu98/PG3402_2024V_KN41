package no.mebu.multiplication;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@AllArgsConstructor
public class MultiplicationController {

    private RestTemplate restTemplate;
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "queue.multiplication")
    @RabbitHandler
    public void test(final String test) {
        System.out.println(test);
    }

    @GetMapping("/")
    public String helloWorld() {
        log.info("I will send rabbit message");
        rabbitTemplate.convertAndSend("exchange.multiplication", "key", "asd");

        log.info("I will send REST message");
        String test = restTemplate.getForObject("http://division", String.class);
        return "Hello multiplication service " + test;
    }

}
