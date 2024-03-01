package no.kristiania.analytics.repository;

import no.kristiania.analytics.models.AnalyticsData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;


@Repository
public interface AnalyticsRepo extends CrudRepository<AnalyticsData, Long> {
    Optional<AnalyticsData> findByDataType(String s);

    @Transactional
    @Modifying
    @Query("update AnalyticsData a set a.dataValue = ?1 where a.dataType = ?2")
    void updateByDataType(int dataValue, String dataType);
}
