package no.kristiania.multiplication;

import no.kristiania.multiplication.models.MultiplicationChallenge;
import no.kristiania.multiplication.repository.MultiplicationChallengeRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnableAutoConfiguration
@DataJpaTest
public class MultiplicationRepoTest {

    @Autowired
    private MultiplicationChallengeRepo repo;

    private MultiplicationChallenge challenge;


    @BeforeEach
    public void beforeEach(){
        challenge = MultiplicationChallenge.builder()
                .userName("username")
                .difficulty(1)
                .num1(1)
                .num2(2)
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
        MultiplicationChallenge challenge2 = MultiplicationChallenge.builder()
                .userName(challenge.getUserName())
                .build();

        MultiplicationChallenge challenge3diffUsername = MultiplicationChallenge.builder()
                .userName("other username")
                .build();

        repo.save(challenge);
        repo.save(challenge2);
        repo.save(challenge3diffUsername);

        ArrayList<MultiplicationChallenge> repoMultiplicationChallenges = repo.findAllByUserName(challenge.getUserName());

        assertTrue(repoMultiplicationChallenges.contains(challenge));
        assertTrue(repoMultiplicationChallenges.contains(challenge2));

        // Assert that all challenges have the same username
        repoMultiplicationChallenges.forEach(internalChallenge -> {
            assertEquals(challenge.getUserName(), internalChallenge.getUserName());
        });
    }

    @Test
    public void testGetAll(){
        MultiplicationChallenge challenge2 = MultiplicationChallenge.builder()
                .userName("Other user")
                .build();

        repo.save(challenge);
        repo.save(challenge2);

        ArrayList<MultiplicationChallenge> repoMultiplicationChallenges = (ArrayList<MultiplicationChallenge>) repo.findAll();

        // Assert that both challenges are in the findAll arrayList
        assertTrue(repoMultiplicationChallenges.contains(challenge));
        assertTrue(repoMultiplicationChallenges.contains(challenge2));
    }
}
