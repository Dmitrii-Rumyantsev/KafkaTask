package org.example.service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProducerService {

  private KafkaTemplate<String, Object> kafkaTemplate;

  private MeterRegistry meterRegistry;

  @Autowired
  public ProducerService(KafkaTemplate<String, Object> kafkaTemplate, MeterRegistry meterRegistry) {
    this.kafkaTemplate = kafkaTemplate;
    this.meterRegistry = meterRegistry;
  }

  public void sendMetric(Metric metric) {
    log.info("Metric send {}", metric.toString());
    kafkaTemplate.send("metrics-topic", metric);
  }

  @Scheduled(fixedRate = 10000)
  public void sendMetricActuator(){
    meterRegistry.getMeters().forEach(meter -> {
      Meter.Id id = meter.getId();
      Map<String, Object> metrics = new HashMap<>();
      metrics.put("name", id.getName());
      metrics.put("type", id.getType().name());
      metrics.put("tags", id.getTags().toArray(new String[0]));
      metrics.put("value", meter.measure().iterator().next().getValue());
      log.info("Send metricActucator {}", metrics);
      kafkaTemplate.send("metrics-topic", metrics);
    });
  }
}
