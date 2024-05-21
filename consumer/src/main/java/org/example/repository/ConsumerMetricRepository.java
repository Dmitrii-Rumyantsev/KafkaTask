package org.example.repository;

import java.util.List;
import org.example.model.Metric;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerMetricRepository extends CrudRepository<Metric,Long> {

  List<Metric> findByName(String name);
  List<Metric> findAll();
}
