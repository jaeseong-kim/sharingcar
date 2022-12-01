package com.example.sharingcar.admin.car;

import com.example.sharingcar.car.entity.Car;
import com.example.sharingcar.car.model.CarInput;
import com.example.sharingcar.car.service.CarService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AdminCarController {

	private final CarService carService;

	@GetMapping("/admin/car/car_manage")
	public String carManage(Model model) {

		List<Car> list = carService.listForAdmin();
		model.addAttribute("list", list);
		return "admin/car/car_manage";
	}

	@GetMapping("/admin/car/car_add")
	public String carAdd() {
		return "admin/car/car_add";
	}

	@PostMapping("/admin/car/car_add")
	public String carAddSubmit(Model model, CarInput parameter) {

		boolean result = carService.addCar(parameter);

		model.addAttribute("result", result);

		return "admin/car/car_add_complete";
	}

	@PostMapping("/admin/car/car_delete")
	public String deleteCar(Model model, CarInput parameter) {

		boolean result = carService.deleteCar(parameter.getCarNumber());

		return "redirect:/admin/car/car_manage";
	}

	@PostMapping("/admin/car/car_change")
	public String changeStatus(CarInput parameter) {

		boolean result = carService.changeStatus(parameter.getCarNumber());

		return "redirect:/admin/car/car_manage";
	}
}
