package no.kristiania.multiplication;

import no.kristiania.multiplication.service.MultiplicationService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class MultiplicationServiceTests {
    @MockBean
    private MultiplicationService multiplicationService;

    //@MockBean

}
