package org.example.service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
  ConsumerService(ConsumerMetricRepository consumerMetricRepository){
    this.consumerMetricRepository = consumerMetricRepository;
  }

  @KafkaListener(topics = "metrics-topic", groupId = "metrics-group")
  public void consume(Map<String, Object> metric) {
    log.info("Consum metric {}", metric.toString());
    Metric metrics = new Metric();
    metrics.setName(metric.get("name").toString());
    metrics.setType(metric.get("type").toString());
    metrics.setTags(Arrays.asList((String[]) metric.get("tags"))); // Преобразование обратно в массив строк
    metrics.setValue(Double.valueOf(metric.get("value").toString()));
    metrics.setTimestamp(Instant.now());
    consumerMetricRepository.save(metrics);
  }


  public List<Metric> getAllMetric(){
    return consumerMetricRepository.findAll();
  }

  public Optional<Metric> getMetricByName(String name){
    return consumerMetricRepository.findByName(name);
  }

}
