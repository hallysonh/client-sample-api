package com.halmeida.clientapi;

import com.halmeida.clientapi.controller.ClientController;
import com.halmeida.clientapi.models.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientApiApplicationTests {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientController controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void getClientListShouldReturnList() {
        assertThat(this.restTemplate.getForObject(makeUrl("client"), Client[].class))
                .isInstanceOf(Client[].class);
    }

    @Test
    public void getClientByIdShouldReturnClient() {
        final Client client = this.restTemplate.getForObject(makeUrl("client/1"), Client.class);
        assertThat(client).isNotNull().isInstanceOf(Client.class);
        assertThat(client.getId()).isEqualTo(1);
    }

    private String makeUrl(String routeParam) {
        final String host = "http://localhost:" + port;
        final String basePrefixed = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
        final String base = basePrefixed.endsWith("/") ? basePrefixed.substring(0, basePrefixed.length() - 2) : basePrefixed;
        final String route = routeParam.startsWith("/") ? routeParam : "/" + routeParam;
        return host + base + route;
    }
}
