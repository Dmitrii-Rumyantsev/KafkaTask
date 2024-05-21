package org.example.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Metric {

  private String name;

  private Double value;

  private String type;

  private List<String> tags;

  private Instant timestamp;
}
