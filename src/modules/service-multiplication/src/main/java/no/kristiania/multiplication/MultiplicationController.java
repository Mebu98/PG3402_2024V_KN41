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

import java.util.ArrayList;

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

        return "Hello multiplication service " + test;
    }

    @GetMapping("/generate/username/{userName}/difficulty/{difficulty}")
    public DtoMultiplicationChallenge getChallenge(@PathVariable String userName, @PathVariable int difficulty){

        MultiplicationChallenge createdChallenge = multiplicationService.generateChallenge(userName, difficulty);

        // Asynchronously send the challenge to the analytics service to keep track of how many challenges have been generated etc.
        // to-do create a specific object containing the relevant data for the analytics service,
        // since the analytics service will not need all the data in the MultiplicationChallenge object
        rabbitTemplate.convertAndSend("exchange.analytics.challengeGeneration", "key", createdChallenge);

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
    public SubmitEnum submitAnswer(@Valid @RequestBody @NonNull DtoMultiplicationChallenge dtoChallenge){
        // returns an ENUM, either CORRECT, INCORRECT, ALREADY_SUBMITTED or NOT_FOUND
        SubmitEnum submitEnum = multiplicationService.submit(dtoChallenge);

        // Send the enum to the analytics service to keep track of how many sumbissions have been made and their response etc.
        rabbitTemplate.convertAndSend("exchange.analytics.challengeSubmissionsEnum", "key", submitEnum);

        return submitEnum;
    }

    @GetMapping("/get/all")
    public ArrayList<DtoMultiplicationChallenge> getAllChallenges(){

        ArrayList<MultiplicationChallenge> internalChallenges = multiplicationService.getAllChallenges();

        return internalToDtoArrayList(internalChallenges);
    }

    @GetMapping("/get/{userName}")
    public ArrayList<DtoMultiplicationChallenge> getAllByUserName(@PathVariable String userName){

        ArrayList<MultiplicationChallenge> internalChallenges = multiplicationService.getAllByUserName(userName);

        return internalToDtoArrayList(internalChallenges);
    }

    private ArrayList<DtoMultiplicationChallenge> internalToDtoArrayList(ArrayList<MultiplicationChallenge> internalChallenges) {
        ArrayList<DtoMultiplicationChallenge> dtoChallenges = new ArrayList<>();

        internalChallenges.forEach(challenge -> {
            dtoChallenges.add(DtoMultiplicationChallenge.builder()
                    .id(challenge.getId())
                    .userName(challenge.getUserName())
                    .type(challenge.getType())
                    .difficulty(challenge.getDifficulty())
                    .num1(challenge.getNum1())
                    .num2(challenge.getNum2())
                    .userAnswer(challenge.getUserAnswer())
                    .correct(challenge.isCorrect())
                    .startTime(challenge.getStartTime())
                    .endTime(challenge.getEndTime())
                    .build());
        });

        return dtoChallenges;
    }
}
