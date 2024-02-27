package no.kristiania.multiplication;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.kristiania.multiplication.DTO.DtoMultiplicationChallenge;
import no.kristiania.multiplication.DTO.DtoShortMultiplicationChallenge;
import no.kristiania.multiplication.models.MultiplicationChallenge;
import no.kristiania.multiplication.models.ShortMultiplicationChallenge;
import no.kristiania.multiplication.service.MultiplicationService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/multiplication")
public class MultiplicationController {

    private RestTemplate restTemplate;
    private RabbitTemplate rabbitTemplate;

    private MultiplicationService multiplicationService;

    @RabbitListener(queues = "queue.multiplication")
    @RabbitHandler
    public void test(final String test) {
        System.out.println(test);
    }

    @GetMapping("/helloworld")
    public String helloWorld() {
        log.info("I will send rabbit message");
        rabbitTemplate.convertAndSend("exchange.multiplication", "key", "asd");

        log.info("I will send REST message");
        String test = restTemplate.getForObject("http://division", String.class);

        return "Hello multiplication service " + test + multiplicationService.generateChallenge(1);
    }

    @GetMapping("/generate")
    public DtoMultiplicationChallenge getChallenge(@Valid @RequestBody @NonNull DtoMultiplicationChallenge dtoChallenge){

        MultiplicationChallenge createdChallenge = multiplicationService.generateChallenge(dtoChallenge.getDifficulty());

        return DtoMultiplicationChallenge.builder()
                .id(createdChallenge.getId())
                .userName(createdChallenge.getUserName())
                .type(createdChallenge.getType())
                .difficulty(createdChallenge.getDifficulty())
                .num1(createdChallenge.getNum1())
                .num2(createdChallenge.getNum2())
                .userAnswer(createdChallenge.getUserAnswer())
                .correct(createdChallenge.isCorrect())
                .startTime(createdChallenge.getStartTime())
                .endTime(createdChallenge.getEndTime())
                .build();
    }

    @GetMapping("/generate-short/{difficulty}")
    public DtoShortMultiplicationChallenge getShortChallenge(@PathVariable int difficulty){
        ShortMultiplicationChallenge generatedShortChallenge = multiplicationService.generateShortChallenge(difficulty);
        return DtoShortMultiplicationChallenge.builder()
                .num1(generatedShortChallenge.getNum1())
                .num2(generatedShortChallenge.getNum2())
                .correctAnswer(generatedShortChallenge.getCorrectAnswer())
                .build();
    }

    @PostMapping("/submit")
    public DtoMultiplicationChallenge submitAnswer(@Valid @RequestBody @NonNull DtoMultiplicationChallenge dtoChallenge){


        return null;
    }
}
