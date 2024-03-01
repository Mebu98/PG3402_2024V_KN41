package no.kristiania.analytics;

import no.kristiania.analytics.service.MultiplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MultiplicationServiceTests {
    @MockBean
    private MultiplicationService multiplicationService;

    //@MockBean
    @Test
    void test() {
        assertTrue(true);
    }
}
