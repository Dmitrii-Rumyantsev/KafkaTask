package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;
import org.example.model.Metric;

public class MetricDeserializer implements Deserializer<Metric> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
  }

  @Override
  public Metric deserialize(String topic, byte[] data) {
    try {
      return objectMapper.readValue(data, Metric.class);
    } catch (Exception e) {
      throw new RuntimeException("Failed to deserialize Metric object", e);
    }
  }

  @Override
  public void close() {
  }
}
