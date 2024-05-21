package org.example.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.example.exception.MetricNotFoundException;
import org.example.model.Metric;
import org.example.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "302",
          description = "Получение всех метрик",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = Metric.class)
          )
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Ошибка при получении метрик",
          content = @Content(mediaType = "text/plain")
      )
  })
  public ResponseEntity<List<Metric>> getAllMetrics() {
    return new ResponseEntity<>(consumerService.getAllMetric(), HttpStatus.FOUND);
  }

  @GetMapping("/{name}")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "302",
          description = "Получение метрики по name",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = Metric.class)
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Пользователь не найден",
          content = @Content(
              mediaType = "application/json"
          )
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Ошибка при получении метрик",
          content = @Content(mediaType = "text/plain")
      )
  })
  public ResponseEntity<String> getMetricByName(@PathVariable("name") String name) {
    try {
      List<Metric> metrics = consumerService.getMetricByName(name);
      if (metrics.isEmpty()) {
        throw new MetricNotFoundException("Метрика с именем " + name + " не найдена");
      }
      return ResponseEntity.ok(metrics.toString());
    } catch (MetricNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Ошибка при получении метрик: " + e.getMessage());
    }
  }

}
