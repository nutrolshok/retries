package net.nutrolshok.retries.testing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ApiClientConfiguration {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                // code..
                .build();
    }
}
