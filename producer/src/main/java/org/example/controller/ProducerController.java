package org.example.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.example.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metric")
@Slf4j
public class ProducerController {
  private final ProducerService metricsProducerService;

  @Autowired
  public ProducerController(ProducerService metricsProducerService) {
    this.metricsProducerService = metricsProducerService;
  }

  @PostMapping("/send")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Метрики успешно отправлены",
          content = @Content(mediaType = "text/plain")
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Ошибка при отправке метрик",
          content = @Content(mediaType = "text/plain")
      )
  })
  public ResponseEntity<String> sendMetric() {
    try {
      metricsProducerService.sendMetricActuator();
      return ResponseEntity.ok("Метрики успешно отправлены!");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Ошибка при отправке метрик: " + e.getMessage());
    }
  }

}
