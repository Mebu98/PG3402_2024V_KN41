package no.kristiania.analytics;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.kristiania.analytics.service.AnalyticsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("analytics/api/v1")
public class AnalyticsController {

    AnalyticsService analyticsService;

    @GetMapping("/helloworld")
    public String helloWorld() {
        return("Hello World from analytics service");
    }

    @RabbitListener(queues = "${amqp.queue.analytics.challenge.created}")
    public void challengeCreated(String message) {
        log.info("Received rabbit message: {}", message);

        analyticsService.updateChallengeCount();
    }

    @GetMapping("/challenges/count")
    public int getChallengeCount() {
        return analyticsService.getChallengeCount();
    }
}
