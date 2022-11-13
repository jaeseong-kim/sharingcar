package com.example.sharingcar.car.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
public class Car {

	@Id
	private String carNumber;
	private String carName;
	private String type;
	private String location;
	private int rentalFeePerTenMin;
	private int insuranceFeePerTenMin;
	private int drivingFeePerKm;
	private boolean status;

	private LocalDateTime addDt;
}
