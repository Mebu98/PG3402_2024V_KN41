package no.kristiania.division;

import io.micrometer.tracing.annotation.ContinueSpan;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.kristiania.division.DTO.DtoDivisionChallenge;
import no.kristiania.division.service.DivisionService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/division/api/v1")
public class DivisionController {

    private final DivisionService divisionService;

    @ContinueSpan
    @GetMapping("/")
    public String helloWorld() {
        log.info("REST message received");
        return "Hello division service";
    }

    @GetMapping("/generate/username/{userName}/difficulty/{difficulty}")
    public DtoDivisionChallenge getChallenge(@PathVariable String userName, @PathVariable int difficulty){

        DivisionChallenge createdChallenge = divisionService.generateChallenge(userName, difficulty);


        return DtoDivisionChallenge.builder()
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


    @PostMapping("/submit")
    public SubmitEnum submitAnswer(@Valid @RequestBody @NonNull DtoDivisionChallenge dtoChallenge){
        // returns an ENUM, either CORRECT, INCORRECT, ALREADY_SUBMITTED or NOT_FOUND
        SubmitEnum submitEnum = divisionService.submit(dtoChallenge);

        return submitEnum;
    }

    @GetMapping("/get/all")
    public ArrayList<DtoDivisionChallenge> getAllChallenges(){

        ArrayList<DivisionChallenge> internalChallenges = divisionService.getAllChallenges();

        return internalToDtoArrayList(internalChallenges);
    }

    @GetMapping("/get/{userName}")
    public ArrayList<DtoDivisionChallenge> getAllByUserName(@PathVariable String userName){

        ArrayList<DivisionChallenge> internalChallenges = divisionService.getAllByUserName(userName);

        return internalToDtoArrayList(internalChallenges);
    }

    private ArrayList<DtoDivisionChallenge> internalToDtoArrayList(ArrayList<DivisionChallenge> internalChallenges) {
        ArrayList<DtoDivisionChallenge> dtoChallenges = new ArrayList<>();

        internalChallenges.forEach(challenge -> {
            dtoChallenges.add(DtoDivisionChallenge.builder()
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
    }}
