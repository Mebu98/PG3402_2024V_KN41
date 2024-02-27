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
        challenge2.setDifficulty(2);

        repo.save(challenge);
        repo.save(challenge2);

        ArrayList<MultiplicationChallenge> multiplicationChallenges = repo.findAllByUserName(challenge.getUserName());

        assertEquals(2, multiplicationChallenges.toArray().length);

    }
}
