package no.kristiania.multiplication.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ShortMultiplicationChallenge {
    private int num1;
    private int num2;
    private Long correctAnswer;
}
