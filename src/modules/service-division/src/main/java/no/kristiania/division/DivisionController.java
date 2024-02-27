package no.kristiania.division;

import io.micrometer.tracing.annotation.ContinueSpan;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class DivisionController {

    private final AmqpTemplate amqpTemplate;

    @RabbitListener(queues = "queue.multiplication")
    @RabbitHandler
    public void test(final String test) {
        System.out.println(test);
        log.info("Rabbit message received");
    }

    @ContinueSpan
    @GetMapping("/")
    public String helloWorld() {
        log.info("REST message received");
        return "Hello division service";
    }
}
