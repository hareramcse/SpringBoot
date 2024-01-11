package com.hs.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hs.model.TemperatureMeasurement;

@Repository
public interface TemperatureMeasurementJpaRepository extends JpaRepository<TemperatureMeasurement, String> {

	Optional<TemperatureMeasurement> findByMeasurementId(String measurementId);

	Optional<List<TemperatureMeasurement>> findAllByMeasurementDate(LocalDate measurementDate);

}
