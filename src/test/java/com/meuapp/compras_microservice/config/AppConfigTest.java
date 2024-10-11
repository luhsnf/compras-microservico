package com.meuapp.compras_microservice.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AppConfigTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void testWebClientBeanIsNotNull() {
        assertThat(webClient).isNotNull();
    }

    @Test
    public void testWebClientBeanIsInstanceOfWebClient() {
        assertThat(webClient).isInstanceOf(WebClient.class);
    }
}
