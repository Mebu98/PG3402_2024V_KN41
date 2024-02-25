package no.mebu.multiplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.tracing.ConditionalOnEnabledTracing;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@ConditionalOnEnabledTracing
@EnableDiscoveryClient
@SpringBootApplication
public class MultiplicationServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(MultiplicationServiceApp.class, args);
	}

}
