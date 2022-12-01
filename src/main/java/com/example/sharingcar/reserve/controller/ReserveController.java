package com.example.sharingcar.reserve.controller;

import com.example.sharingcar.car.entity.Car;
import com.example.sharingcar.car.service.CarService;
import com.example.sharingcar.reserve.entity.Reserve;
import com.example.sharingcar.reserve.model.ReserveInput;
import com.example.sharingcar.reserve.service.ReserveService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ReserveController {

	private final CarService carService;
	private final ReserveService reserveService;

	@GetMapping("/reserve/reserve")
	public String reserve(Model model) {

		List<Car> list = carService.listForUser();
		model.addAttribute("list", list);

		return "reserve/reserve";
	}

	@GetMapping("reserve/reserve/{carNumber}")
	public String reserveDetail(Model model, ReserveInput parameter) {

		Car car = carService.findCar(parameter.getCarNumber());
		List<Reserve> list = reserveService.listReservedTime(car);
		System.out.println(list.size());
		LocalDate date = LocalDate.now();
		model.addAttribute("car", car);
		model.addAttribute("list", list);

		return "reserve/reserve_detail";
	}
}
