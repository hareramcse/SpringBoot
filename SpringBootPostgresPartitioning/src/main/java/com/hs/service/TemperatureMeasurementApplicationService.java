package com.hs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hs.model.TemperatureMeasurement;
import com.hs.model.UpdateTemperatureMeasurementCommand;
import com.hs.repository.TemperatureMeasurementJpaRepository;

@Service
public class TemperatureMeasurementApplicationService {

	@Autowired
	private TemperatureMeasurementJpaRepository temperatureMeasurementJpaRepository;

	@Transactional
	public TemperatureMeasurement updateTemperatureMeasurement(
			UpdateTemperatureMeasurementCommand updateTemperatureMeasurementCommand) {

		return temperatureMeasurementJpaRepository
				.findByMeasurementId(updateTemperatureMeasurementCommand.getMeasurementId()).map(temperature -> {
					temperature.setTemperature(updateTemperatureMeasurementCommand.getNewTemperature());
					return temperature;
				}).map(temperature -> temperatureMeasurementJpaRepository.save(temperature)).orElseThrow();

	}

}
