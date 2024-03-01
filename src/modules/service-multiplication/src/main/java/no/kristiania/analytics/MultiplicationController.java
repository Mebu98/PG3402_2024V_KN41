package no.kristiania.analytics;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.kristiania.analytics.DTO.DtoMultiplicationChallenge;
import no.kristiania.analytics.DTO.DtoShortMultiplicationChallenge;
import no.kristiania.analytics.models.MultiplicationChallenge;
import no.kristiania.analytics.models.ShortMultiplicationChallenge;
import no.kristiania.analytics.service.MultiplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("multiplication/api/v1")
public class MultiplicationController {

    private MultiplicationService multiplicationService;

    @GetMapping("/helloworld")
    public String helloWorld() {
        return("Hello World from multiplication service");
    }

    @GetMapping("/generate/username/{userName}/difficulty/{difficulty}")
    public DtoMultiplicationChallenge getChallenge(@PathVariable String userName, @PathVariable int difficulty){

        MultiplicationChallenge createdChallenge = multiplicationService.generateChallenge(userName, difficulty);

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
