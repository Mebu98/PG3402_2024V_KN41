package no.kristiania.analytics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.kristiania.analytics.DTO.DtoMultiplicationChallenge;
import no.kristiania.analytics.SubmitEnum;
import no.kristiania.analytics.eventPublisher.ChallengeEventPublisher;
import no.kristiania.analytics.models.MultiplicationChallenge;
import no.kristiania.analytics.models.ShortMultiplicationChallenge;
import no.kristiania.analytics.repository.MultiplicationChallengeRepo;
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
    private final ChallengeEventPublisher challengeEventPublisher;

    public MultiplicationChallenge generateChallenge(String userName, int difficulty){

        log.info("[MultiplicationService] Generating multiplicationChallenge for user " + userName + " with difficulty " + difficulty + ".");

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

        log.info("[MultiplicationService] Saving multiplicationChallenge with num1="
                + challenge.getNum1() + ", num2=" + challenge.getNum2()
                + " and correctAnswer=" + challenge.getCorrectAnswer() +" to database.");
        repo.save(challenge);

        log.info("[MultiplicationService] Challenge saved to database, returning challenge to user.");

        challengeEventPublisher.challengeCreated(challenge.getId());

        return challenge;
    }

    public ShortMultiplicationChallenge generateShortChallenge(int difficulty){
        log.info("[MultiplicationService] Got a request for a shortMultiplicationChallenge with difficulty= " + difficulty + ".");

        int num1 = generateRandomInt(difficulty);
        int num2 = generateRandomInt(difficulty);
        Long correctAnswer = ((long) num1) * ((long) num2);

        log.info("[MultiplicationService] Generated shortMultiplicationChallenge with num1="
                + num1 + ", num2=" + num2
                + " and correctAnswer=" + correctAnswer +".");

        log.info("[MultiplicationService] Returning shortMultiplicationChallenge.");

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
            log.info("[MultiplicationService] Submit function: Challenge with id " + dtoChallenge.getId() + " not found in database.");
            return SubmitEnum.NOT_FOUND;
        }

        // To prevent double submissions
        if(internalChallenge.get().getEndTime() != null){
            log.info("[MultiplicationService] Submit function: Challenge with id " + dtoChallenge.getId() + " has already been submitted.");
            return SubmitEnum.ALREADY_SUBMITTED;
        }

        if(Objects.equals(dtoChallenge.getUserAnswer(), internalChallenge.get().getCorrectAnswer())){
            submitEnum = SubmitEnum.CORRECT;
            correct = true;
        }

        log.info(String.format("[MultiplicationService] Submit function: Updating challenge database with id=%d with userAnswer=%d, correctAnswer=%d, correct=%b and endTime=%s.",
                dtoChallenge.getId(), dtoChallenge.getUserAnswer(), internalChallenge.get().getCorrectAnswer(), correct, endTime));

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
