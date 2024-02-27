package no.kristiania.multiplication.repository;

import no.kristiania.multiplication.models.MultiplicationChallenge;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;


@Repository
public interface MultiplicationChallengeRepo extends CrudRepository<MultiplicationChallenge, Long> {
    @Transactional
    @Modifying
    @Query("update MultiplicationChallenge m set m.userAnswer = ?1, m.correct = ?2, m.endTime = ?3 where m.id = ?4")
    void updateUserAnswerAndCorrectAndEndTimeById(Long userAnswer, boolean correct, Timestamp endTime, Long id);

    ArrayList<MultiplicationChallenge> findAllByUserName(String userName);

}
