package no.kristiania.analytics.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DtoShortMultiplicationChallenge {
    private int num1;
    private int num2;
    private Long correctAnswer;
}
