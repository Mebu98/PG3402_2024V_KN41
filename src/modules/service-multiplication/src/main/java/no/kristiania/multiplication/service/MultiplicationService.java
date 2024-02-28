package no.kristiania.multiplication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.kristiania.multiplication.DTO.DtoMultiplicationChallenge;
import no.kristiania.multiplication.SubmitEnum;
import no.kristiania.multiplication.models.MultiplicationChallenge;
import no.kristiania.multiplication.models.ShortMultiplicationChallenge;
import no.kristiania.multiplication.repository.MultiplicationChallengeRepo;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class MultiplicationService {
    private final MultiplicationChallengeRepo repo;
    private final Random random = new Random();

    public MultiplicationChallenge generateChallenge(String userName, int difficulty){

        int num1 = generateRandomInt(difficulty);
        int num2 = generateRandomInt(difficulty);
        Long correctAnswer = ((long) num1) * ((long) num2);

        MultiplicationChallenge challenge = MultiplicationChallenge.builder()
                .userName(userName)
                .difficulty(difficulty)
                .num1(num1)
                .num2(num2)
                .correctAnswer(correctAnswer)
                .startTime(Timestamp.from(Instant.now()))
                .build();

        repo.save(challenge);

        return challenge;
    }

    public ShortMultiplicationChallenge generateShortChallenge(int difficulty){

        int num1 = generateRandomInt(difficulty);
        int num2 = generateRandomInt(difficulty);
        Long correctAnswer = ((long) num1) * ((long) num2);

        return ShortMultiplicationChallenge.builder()
                .num1(num1)
                .num2(num2)
                .correctAnswer(correctAnswer)
                .build();
    }

    public SubmitEnum submit(DtoMultiplicationChallenge dtoChallenge){
        SubmitEnum submitEnum = SubmitEnum.INCORRECT;
        boolean correct = false;
        Timestamp endTime = Timestamp.from(Instant.now());

        Optional<MultiplicationChallenge> internalChallenge = repo.findById(dtoChallenge.getId());
        // This will ideally never happen
        if(internalChallenge.isEmpty()){
            return SubmitEnum.NOT_FOUND;
        }

        // To prevent double submissions
        if(internalChallenge.get().getEndTime() != null){
            return SubmitEnum.ALREADY_SUBMITTED;
        }

        if(Objects.equals(dtoChallenge.getUserAnswer(), internalChallenge.get().getCorrectAnswer())){
            submitEnum = SubmitEnum.CORRECT;
            correct = true;
        }


        repo.updateUserAnswerAndCorrectAndEndTimeById(
                dtoChallenge.getUserAnswer(),
                correct,
                endTime,
                dtoChallenge.getId());

        return submitEnum;
    }

    // Generate a random number between 2 and 10^difficulty
    private int generateRandomInt(int difficulty) {
        return random.nextInt(2, (int) Math.pow(10, difficulty));
    }

    public ArrayList<MultiplicationChallenge> getAllChallenges() {
        return (ArrayList<MultiplicationChallenge>) repo.findAll();
    }

    public ArrayList<MultiplicationChallenge> getAllByUserName(String username) {
        return repo.findAllByUserName(username);
    }
}
