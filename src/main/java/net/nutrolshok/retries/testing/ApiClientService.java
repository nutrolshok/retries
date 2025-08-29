package net.nutrolshok.retries.testing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class ApiClientService {

    private final RestClient client;

    @Retryable(
            retryFor = { RetryableHttpException.class, RestClientException.class },
            noRetryFor = { NonRetryableHttpException.class, RestClientResponseException.class },
            maxAttempts = 4,
            backoff = @Backoff(delay = 200, maxDelay = 2000, multiplier = 2.0, random = true)
    )
    public Map<String, Object> fetchOrder(String orderId) {
        log.info("GET /orders/{}", orderId);

        return this.client.get()
                .uri("/orders/{id}", orderId)
                .retrieve()
                .onStatus(
                        status -> status.is5xxServerError() || status == HttpStatus.TOO_MANY_REQUESTS,
                        (req, res) -> {
                            throw new RetryableHttpException("Retryable status: " + res.getStatusCode());
                        }
                )
                .onStatus(
                        status -> status.is4xxClientError() && status != HttpStatus.TOO_MANY_REQUESTS,
                        (req, res) -> {
                            throw new NonRetryableHttpException("Non-retryable: " + res.getStatusCode());
                        }
                )
                .toEntity(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .getBody();
    }

    @Recover
    public Map<String, Object> recover(RetryableHttpException cause, String orderId) {
        log.error("Retries exhausted for {}: {}", orderId, cause.getMessage());
        throw new RuntimeException("External API unavailable, try later");
    }

    @Recover
    public Map<String, Object> recover(RuntimeException cause, String orderId) {
        log.warn(
                "Recover called with non-retryable cause for {}: {}",
                orderId, cause.getClass().getSimpleName()
        );
        throw cause;
    }
}