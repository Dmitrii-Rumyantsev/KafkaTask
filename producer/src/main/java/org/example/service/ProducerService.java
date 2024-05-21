package org.example.service;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProducerService {

  private KafkaTemplate<String, Metric> kafkaTemplate;
  private MeterRegistry meterRegistry;

  @Autowired
  public ProducerService(KafkaTemplate<String, Metric> kafkaTemplate, MeterRegistry meterRegistry) {
    this.kafkaTemplate = kafkaTemplate;
    this.meterRegistry = meterRegistry;
  }

  public void sendMetricActuator() {
    meterRegistry.getMeters().forEach(meter -> {
      Meter.Id id = meter.getId();
      Metric metric = new Metric();
      metric.setName(id.getName());
      metric.setType(id.getType().name());
      metric.setValue(meter.measure().iterator().next().getValue());
      metric.setTimestamp(Instant.now());
      log.info("Send metricActuator {}", metric);
      kafkaTemplate.send("metrics-topic", metric);
    });
  }
}
