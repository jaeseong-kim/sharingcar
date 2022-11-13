package com.example.sharingcar.car.service.impl;

import com.example.sharingcar.car.entity.Car;
import com.example.sharingcar.car.model.CarInput;
import com.example.sharingcar.car.repository.CarRepository;
import com.example.sharingcar.car.service.CarService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {

	private final CarRepository carRepository;

	@Override
	public boolean addCar(CarInput parameter) {
		Optional<Car> optionalCar = carRepository.findById(parameter.getCarNumber());
		if (optionalCar.isPresent()) {
			return false;
		}

		Car car = Car.builder()
			.carNumber(parameter.getCarNumber())
			.carName(parameter.getCarName())
			.type(parameter.getCarType())
			.location(parameter.getLocation())
			.rentalFeePerTenMin(parameter.getRentalFee())
			.insuranceFeePerTenMin(parameter.getInsuranceFee())
			.drivingFeePerKm(parameter.getDrivingFee())
			.status(true)
			.addDt(LocalDateTime.now())
			.build();

		carRepository.save(car);

		return true;
	}

	@Override
	public boolean deleteCar(String parameter) {
		carRepository.deleteById(parameter);

		return true;
	}

	@Override
	public List<Car> list() {
		return carRepository.findAll();
	}

	@Override
	public boolean changeStatus(String carNumber) {
		Optional<Car> optionalCar = carRepository.findById(carNumber);
		if (optionalCar.isEmpty()) {
			return false;
		}

		Car car = optionalCar.get();
		if (car.isStatus()) {
			car.setStatus(false);
		} else {
			car.setStatus(true);
		}

		carRepository.save(car);

		return true;
	}
}
