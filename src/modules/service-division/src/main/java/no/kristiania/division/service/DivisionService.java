package no.kristiania.division.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.kristiania.division.DTO.DtoDivisionChallenge;
import no.kristiania.division.DTO.DtoShortMultiplicationChallenge;
import no.kristiania.division.DivisionChallenge;
import no.kristiania.division.SubmitEnum;
import no.kristiania.division.repo.DivisionChallengeRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class DivisionService {
    private final DivisionChallengeRepo repo;

    private RestTemplate restTemplate;

    public DivisionChallenge generateChallenge(String userName, int difficulty){

        log.info("[DivisionService] Generating division challenge for user " + userName + " with difficulty " + difficulty + ".");

        // Division is the reverse of multiplication, so we can use the multiplication service to generate our numbers,
        // then we can swap num1 and the correct answer to get our division challenge

        // i.e 5 * 3 = 15, then 15 / 3 = 5

        String url = "http://multiplication/multiplication/api/v1/generate-short/" + difficulty;
        log.info("[DivisionService] Sending request to multiplication service to generate a multiplication challenge");
        ResponseEntity<DtoShortMultiplicationChallenge> response = restTemplate.getForEntity(url, DtoShortMultiplicationChallenge.class);
        DtoShortMultiplicationChallenge dtoShortMultiplicationChallenge = response.getBody();

        if(dtoShortMultiplicationChallenge == null){
            log.error("Failed to get a multiplication challenge.");
            return null;
        }

        log.info("[DivisionService] Got a multiplication challenge, swapping num1 and correct answer to get a division challenge.");
        Long num1 = dtoShortMultiplicationChallenge.getCorrectAnswer();
        Long correctAnswer = (long) dtoShortMultiplicationChallenge.getNum1();
        Long num2 = (long) dtoShortMultiplicationChallenge.getNum2();

        DivisionChallenge challenge = DivisionChallenge.builder()
                .userName(userName)
                .difficulty(difficulty)
                .num1(num1)
                .num2(num2)
                .correctAnswer(correctAnswer)
                .startTime(Timestamp.from(Instant.now()))
                .build();

        log.info("[DivisionService] Saving division challenge with num1="
                + challenge.getNum1() + ", num2=" + challenge.getNum2()
                + " and correctAnswer=" + challenge.getCorrectAnswer() +" to database.");

        repo.save(challenge);

        log.info("[DivisionService] Challenge saved to database, returning challenge to user.");
        return challenge;
    }


    public SubmitEnum submit(DtoDivisionChallenge dtoChallenge){
        SubmitEnum submitEnum = SubmitEnum.INCORRECT;
        boolean correct = false;
        Timestamp endTime = Timestamp.from(Instant.now());

        Optional<DivisionChallenge> internalChallenge = repo.findById(dtoChallenge.getId());
        // This will ideally never happen
        if(internalChallenge.isEmpty()){
            log.info("[DivisionService] Submit function: Challenge with id " + dtoChallenge.getId() + " not found in database.");
            return SubmitEnum.NOT_FOUND;
        }

        // To prevent multiple submissions for the same challenge
        if(internalChallenge.get().getEndTime() != null){
            log.info("[DivisionService] Submit function: Challenge with id " + dtoChallenge.getId() + " already submitted.");
            return SubmitEnum.ALREADY_SUBMITTED;
        }

        if(Objects.equals(dtoChallenge.getUserAnswer(), internalChallenge.get().getCorrectAnswer())){
            submitEnum = SubmitEnum.CORRECT;
            correct = true;
        }

        log.info(String.format("[DivisionService] Submit function: Updating challenge database with id=%d with userAnswer=%d, correctAnswer=%d, correct=%b and endTime=%s.",
                dtoChallenge.getId(), dtoChallenge.getUserAnswer(), internalChallenge.get().getCorrectAnswer(), correct, endTime));

        repo.updateUserAnswerAndCorrectAndEndTimeById(
                dtoChallenge.getUserAnswer(),
                correct,
                endTime,
                dtoChallenge.getId());

        return submitEnum;
    }

    public ArrayList<DivisionChallenge> getAllChallenges() {
        return (ArrayList<DivisionChallenge>) repo.findAll();
    }

    public ArrayList<DivisionChallenge> getAllByUserName(String username) {
        return repo.findAllByUserName(username);
    }
}
