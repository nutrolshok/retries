package net.nutrolshok.retries.testing;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig(classes = {
        ApiClientService.class,
        ApiClientConfiguration.class,
        ApiClientServiceTest.ApiClientTestConfiguration.class
})
class ApiClientServiceTest {

    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();
    @Autowired
    ApiClientService service;

    @BeforeEach
    void resetWireMock() {
        wireMock.resetAll();
    }

    @Test
    void success_after_retries() {
        wireMock.stubFor(get(urlPathEqualTo("/orders/42"))
                .inScenario("flaky").whenScenarioStateIs(STARTED)
                .willReturn(aResponse().withStatus(500))
                .willSetStateTo("step2"));

        wireMock.stubFor(get(urlPathEqualTo("/orders/42"))
                .inScenario("flaky").whenScenarioStateIs("step2")
                .willReturn(aResponse().withStatus(503))
                .willSetStateTo("ok"));

        wireMock.stubFor(get(urlPathEqualTo("/orders/42"))
                .inScenario("flaky").whenScenarioStateIs("ok")
                .willReturn(
                        aResponse().withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{\"id\":42,\"status\":\"PAID\"}")
                ));

        var res = service.fetchOrder("42");

        assertThat(res).containsEntry("id", 42).containsEntry("status", "PAID");
        wireMock.verify(3, getRequestedFor(urlPathEqualTo("/orders/42")));
    }

    @Test
    void no_retry_on_400() {
        wireMock.stubFor(get(urlPathEqualTo("/orders/13"))
                .willReturn(aResponse().withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\":\"bad request\"}")));

        assertThatThrownBy(() -> service.fetchOrder("13"))
                .isInstanceOf(NonRetryableHttpException.class)
                .hasMessageContaining("Non-retryable");

        wireMock.verify(1, getRequestedFor(urlPathEqualTo("/orders/13")));
    }

    @Test
    void exhaust_and_recover() {
        wireMock.stubFor(get(urlPathEqualTo("/orders/99"))
                .willReturn(aResponse().withStatus(502)));

        assertThatThrownBy(() -> service.fetchOrder("99"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("External API unavailable");

        wireMock.verify(4, getRequestedFor(urlPathEqualTo("/orders/99")));
    }

    @TestConfiguration
    @EnableRetry
    static class ApiClientTestConfiguration {
        @Bean
        @Primary
        RestClient restClient() {
            return RestClient.builder()
                    .baseUrl("http://localhost:" + wireMock.getPort())
                    .build();
        }
    }
}