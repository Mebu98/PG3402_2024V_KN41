package no.kristiania.division.DTO;

import lombok.*;

@Getter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoShortMultiplicationChallenge {
    private int num1;
    private int num2;
    private Long correctAnswer;
}
