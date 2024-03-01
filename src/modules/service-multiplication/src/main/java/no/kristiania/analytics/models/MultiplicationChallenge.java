package no.kristiania.analytics.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultiplicationChallenge {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name = "user_name")
    private String userName;

    //@Column(name = "challenge_type")
    @Builder.Default
    private String type = "multiplication";

    //@Column(name = "difficulty")
    private int difficulty;

    //@Column(name = "num1")
    private int num1;

    //@Column(name = "num2")
    private int num2;

    //@Column(name = "correct_answer")
    private Long correctAnswer;

    //@Column(name = "user_answer")
    private Long userAnswer;

    //@Column(name = "correct_bool")
    private boolean correct;

    //@Column(name = "start_time")
    private Timestamp startTime;

    //@Column(name = "end_time")
    private Timestamp endTime;
}
