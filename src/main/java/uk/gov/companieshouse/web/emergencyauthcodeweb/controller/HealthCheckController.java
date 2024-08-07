package uk.gov.companieshouse.web.emergencyauthcodeweb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

  @GetMapping("/healthcheck")
  public ResponseEntity<String> healthcheck() {
    return new ResponseEntity("Emergency authcode requests service is healthy", HttpStatus.OK);
  }
}
