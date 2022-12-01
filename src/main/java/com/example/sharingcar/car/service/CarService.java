package com.example.sharingcar.car.service;

import com.example.sharingcar.car.entity.Car;
import com.example.sharingcar.car.model.CarInput;
import java.util.List;

public interface CarService {


	boolean addCar(CarInput carInput);

	boolean deleteCar(String parameter);

	List<Car> listForAdmin();

	List<Car> listForUser();

	Car findCar(String carNumber);

	boolean changeStatus(String carNumber);
}
