package no.kristiania.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.tracing.ConditionalOnEnabledTracing;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@ConditionalOnEnabledTracing
@EnableDiscoveryClient
@SpringBootApplication
public class AnalyticsServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsServiceApp.class, args);
	}

}
