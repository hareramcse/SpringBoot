package com.hs.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "temperature_measurements")
@Getter
@Setter
public class TemperatureMeasurement {

	@Id
	private String measurementId;

	private LocalDate measurementDate;

	private String city;

	private String country;

	private Double temperature;

}
