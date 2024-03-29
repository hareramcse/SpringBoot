package com.hs.controller;

import com.hs.model.TemperatureMeasurement;
import com.hs.model.UpdateTemperatureMeasurementCommand;
import com.hs.repository.TemperatureMeasurementJpaRepository;
import com.hs.service.TemperatureMeasurementApplicationService;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/temperatures")
public class TemperatureMeasurementController {

	@Autowired
	private TemperatureMeasurementJpaRepository temperatureMeasurementJpaRepository;

	@Autowired
	private TemperatureMeasurementApplicationService temperatureMeasurementApplicationService;

	@GetMapping(value = "/today", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TemperatureMeasurement>> getReadingsForToday() {
		return temperatureMeasurementJpaRepository.findAllByMeasurementDate(LocalDate.now()).map(ResponseEntity::ok)
				.orElse(ResponseEntity.noContent().build());
	}

	@PostMapping("/{measurementId}")
	public ResponseEntity<TemperatureMeasurement> updateReading(@PathVariable("measurementId") String measurementId,
			@RequestBody String newTemperature) {
		return ResponseEntity.ok(temperatureMeasurementApplicationService.updateTemperatureMeasurement(
				new UpdateTemperatureMeasurementCommand(measurementId, Double.valueOf(newTemperature))));

	}
}