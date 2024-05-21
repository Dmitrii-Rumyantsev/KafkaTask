package org.example.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Metric;
import org.example.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public ResponseEntity<String> sendMetric(@RequestBody Metric metric) {
    log.info("Send metric {}", metric.toString());
    metric.setTimestamp(Instant.now());
    metricsProducerService.sendMetric(metric);
    return ResponseEntity.ok("Metric sent successfully");
  }
}
