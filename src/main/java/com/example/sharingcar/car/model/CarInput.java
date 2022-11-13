package com.example.sharingcar.car.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CarInput {

	private String carNumber;
	private String carName;
	private String carType;
	private String location;
	private int rentalFee;
	private int insuranceFee;
	private int drivingFee;
}
