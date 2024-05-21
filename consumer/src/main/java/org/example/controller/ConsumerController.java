package org.example.controller;

import java.util.List;
import java.util.function.Supplier;
import org.example.model.Metric;
import org.example.repository.ConsumerMetricRepository;
import org.example.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metric")
public class ConsumerController {

  private final ConsumerService consumerService;

  @Autowired
  public ConsumerController(ConsumerService consumerService) {
    this.consumerService = consumerService;
  }

  @GetMapping
  public List<Metric> getAllMetrics() {
    return consumerService.getAllMetric();
  }

  @GetMapping("/{name}")
  public Metric getMetricByName(@PathVariable String name) {
    return consumerService.getMetricByName(name).get();
  }
}
