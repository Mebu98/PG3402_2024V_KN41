package no.kristiania.division.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtoDivisionChallenge {
    private Long id;
    private String userName;
    @Builder.Default
    private final String type = "multiplication";
    private int difficulty;
    private Long num1;
    private Long num2;
    private Long userAnswer;
    private boolean correct;
    private Timestamp startTime;
    private Timestamp endTime;
}
