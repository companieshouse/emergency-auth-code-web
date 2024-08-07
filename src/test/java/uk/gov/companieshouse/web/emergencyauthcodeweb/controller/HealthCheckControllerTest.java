package uk.gov.companieshouse.web.emergencyauthcodeweb.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.HealthCheckController;

@SpringBootTest
@ContextConfiguration(classes = HealthCheckController.class)
@AutoConfigureMockMvc
class HealthCheckControllerTest {
  @Autowired private MockMvc mvc;

  @Test
  void HealthCheckEndpointTest() throws Exception {
    this.mvc
        .perform(get("/healthcheck"))
        .andExpect(status().isOk())
        .andExpect(content().string("Emergency authcode requests service is healthy"));
  }
}
