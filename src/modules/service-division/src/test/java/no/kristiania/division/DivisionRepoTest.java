package no.kristiania.division;

import no.kristiania.division.repo.DivisionChallengeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnableAutoConfiguration
@DataJpaTest
public class DivisionRepoTest {

    @Autowired
    private DivisionChallengeRepo repo;

    private DivisionChallenge challenge;

    @BeforeEach
    public void beforeEach(){
        challenge = DivisionChallenge.builder()
                .userName("username")
                .difficulty(1)
                .num1(1L)
                .num2(2L)
                .correctAnswer(2L)
                .userAnswer(2L)
                .correct(true)
                .build();
    }

    @Test
    public void testThatTestWorks(){
        assertEquals(0,0);
    }

    @Test
    public void testGetAllByUsername(){
        DivisionChallenge challenge2 = DivisionChallenge.builder()
                .userName(challenge.getUserName())
                .build();

        DivisionChallenge challenge3diffUsername = DivisionChallenge.builder()
                .userName("other username")
                .build();

        repo.save(challenge);
        repo.save(challenge2);
        repo.save(challenge3diffUsername);

        ArrayList<DivisionChallenge> repoMultiplicationChallenges = repo.findAllByUserName(challenge.getUserName());

        assertTrue(repoMultiplicationChallenges.contains(challenge));
        assertTrue(repoMultiplicationChallenges.contains(challenge2));

        // Assert that all challenges have the same username
        repoMultiplicationChallenges.forEach(internalChallenge -> {
            assertEquals(challenge.getUserName(), internalChallenge.getUserName());
        });
    }

    @Test
    public void testGetAll(){
        DivisionChallenge challenge2 = DivisionChallenge.builder()
                .userName("Other user")
                .build();

        repo.save(challenge);
        repo.save(challenge2);

        ArrayList<DivisionChallenge> repoMultiplicationChallenges = (ArrayList<DivisionChallenge>) repo.findAll();

        // Assert that both challenges are in the findAll arrayList
        assertTrue(repoMultiplicationChallenges.contains(challenge));
        assertTrue(repoMultiplicationChallenges.contains(challenge2));
    }
}
