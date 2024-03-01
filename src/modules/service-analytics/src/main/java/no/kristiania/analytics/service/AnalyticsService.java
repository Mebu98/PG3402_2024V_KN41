package no.kristiania.analytics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.kristiania.analytics.models.AnalyticsData;
import no.kristiania.analytics.repository.AnalyticsRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalyticsService {
    private final AnalyticsRepo repo;


    public void updateChallengeCount() {
        log.info("Updating challenge count");
        String dataType = "challengeCount";
        repo.findByDataType(dataType).ifPresentOrElse(
                analyticsData -> {
                    analyticsData.setDataValue(analyticsData.getDataValue() + 1);
                    repo.updateByDataType(analyticsData.getDataValue(), dataType);
                    log.info("Updated challenge count to: {}", analyticsData.getDataValue());
                },
                () -> {
                    log.info("No challenge data found, creating new");
                    repo.save(AnalyticsData.builder()
                            .dataType(dataType)
                            .dataValue(1)
                            .build());
                }
        );
    }

    public int getChallengeCount() {
        return repo.findByDataType("challengeCount")
                .map(AnalyticsData::getDataValue)
                .orElse(0);
    }
}
