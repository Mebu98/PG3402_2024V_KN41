package no.kristiania.division.repo;

import no.kristiania.division.DivisionChallenge;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;


@Repository
public interface DivisionChallengeRepo extends CrudRepository<DivisionChallenge, Long> {
    @Transactional
    @Modifying
    @Query("update DivisionChallenge m set m.userAnswer = ?1, m.correct = ?2, m.endTime = ?3 where m.id = ?4")
    void updateUserAnswerAndCorrectAndEndTimeById(Long userAnswer, boolean correct, Timestamp endTime, Long id);

    ArrayList<DivisionChallenge> findAllByUserName(String userName);

}
