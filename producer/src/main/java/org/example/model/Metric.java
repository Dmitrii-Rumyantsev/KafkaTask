package org.example.model;

import java.time.Instant;
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

  private Instant timestamp;
}
