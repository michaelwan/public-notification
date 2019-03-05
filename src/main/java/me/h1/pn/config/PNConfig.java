package me.h1.pn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
@EnableJpaAuditing
public class PNConfig {

    @Bean
    public SnsClient createSnsClient() {
        return SnsClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.AP_SOUTHEAST_2)
                .build();
    }
}
