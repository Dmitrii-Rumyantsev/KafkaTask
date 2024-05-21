package org.example.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Metric;
import org.example.repository.ConsumerMetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

  private final ConsumerMetricRepository consumerMetricRepository;

  @Autowired
  ConsumerService(ConsumerMetricRepository consumerMetricRepository) {
    this.consumerMetricRepository = consumerMetricRepository;
  }

  @KafkaListener(topics = "metrics-topic", groupId = "metrics-group")
  public void consume(Metric metric) {
    log.info("Consume metric {}", metric.toString());
    consumerMetricRepository.save(metric);
  }


  public List<Metric> getAllMetric() {
    return consumerMetricRepository.findAll();
  }

  public List<Metric> getMetricByName(String name) {
    return consumerMetricRepository.findByName(name);
  }
}
