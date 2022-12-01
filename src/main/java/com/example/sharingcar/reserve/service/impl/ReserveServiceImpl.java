package com.example.sharingcar.reserve.service.impl;

import com.example.sharingcar.car.entity.Car;
import com.example.sharingcar.car.repository.CarRepository;
import com.example.sharingcar.reserve.entity.Reserve;
import com.example.sharingcar.reserve.model.ReserveReqInput;
import com.example.sharingcar.reserve.model.ServiceResult;
import com.example.sharingcar.reserve.repository.ReserveRepository;
import com.example.sharingcar.reserve.service.ReserveService;
import com.example.sharingcar.user.entity.User;
import com.example.sharingcar.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReserveServiceImpl implements ReserveService {

	private final ReserveRepository reserveRepository;
	private final UserRepository userRepository;
	private final CarRepository carRepository;

	@Override
	public ServiceResult req(ReserveReqInput parameter) {
		ServiceResult serviceResult = new ServiceResult();

		//시작시간, 종료시간 LocalDateTime으로 변환
		String startStr = parameter.getDateStart() + " " + parameter.getTimeStart();
		String endStr = parameter.getDateEnd() + " " + parameter.getTimeEnd();
		LocalDateTime startDt = LocalDateTime.parse(startStr,
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		LocalDateTime endDt = LocalDateTime.parse(endStr,
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

		//유저가 예약한 차량 중 예약하는 차량과 시간이 중복되는지 체크
		Optional<User> optionalUser = userRepository.findById(parameter.getEmail());
		if (optionalUser.isEmpty()) {
			serviceResult.setResult(false);
			serviceResult.setMessage("유저가 존재하지 않습니다.");
			return serviceResult;
		}
		User user = optionalUser.get();
		List<Reserve> duplicatedTimeForUser = reserveRepository.findDuplicatedTimeForUser(
			user, startDt, endDt);
		if (!duplicatedTimeForUser.isEmpty()) {
			serviceResult.setResult(false);
			serviceResult.setMessage("기존에 예약한 차량과 시간이 중복됩니다.");
			return serviceResult;
		}

		//해당 차량의 예약된 시간과 중복되는지 체크
		Optional<Car> optionalCar = carRepository.findById(parameter.getCarNumber());
		if (optionalCar.isEmpty()) {
			serviceResult.setResult(false);
			serviceResult.setMessage("해당 차량이 없습니다.");
			return serviceResult;
		}
		Car car = optionalCar.get();
		List<Reserve> duplicatedTimeForCar = reserveRepository.findDuplicatedTimeForCar(
			car, startDt, endDt);
		if (!duplicatedTimeForCar.isEmpty()) {
			serviceResult.setResult(false);
			serviceResult.setMessage("차량이 이미 해당시간에 예약되어 있습니다.");
			return serviceResult;
		}

		//결제 금액 계산(대여료 + 보험료)
		long diff = startDt.until(endDt, ChronoUnit.MINUTES);
		int rentFee = car.getRentalFeePerTenMin();
		int insuranceFee;
		if (Boolean.parseBoolean(parameter.getInsurance())) {
			insuranceFee = car.getInsuranceFeePerTenMin();
		} else {
			insuranceFee = 0;
		}
		int chargeFee = (int) (diff / 10) * (rentFee + insuranceFee);
		if (diff <= 0 || chargeFee < 0) {
			serviceResult.setMessage("예약날짜를 올바르게 설정해 주세요");
			serviceResult.setResult(false);
			return serviceResult;
		}

		Reserve reserve = Reserve.builder()
			.email(user)
			.carNumber(car)
			.startTime(startDt)
			.endTime(endDt)
			.location(car.getLocation())
			.insuranceYn(Boolean.parseBoolean(parameter.getInsurance()))
			.type(car.getType())
			.status(true)
			.reserveDt(LocalDateTime.now())
			.chargeRentIns(chargeFee)
			.build();
		reserveRepository.save(reserve);

		serviceResult.setResult(true);
		serviceResult.setMessage("대여료 : " + rentFee + "원\n" +
			"보험료 : " + insuranceFee + "원\n" +
			"총 결제 금액 : " + chargeFee + "원 입니다.");

		return serviceResult;
	}

	@Override
	public List<Reserve> listForMyPage(User user) {
		return reserveRepository.findAllByEmailOrderByStartTime(user);
	}

	@Override
	public List<Reserve> listReservedTime(Car carNumber) {
		return reserveRepository.findAllByCarNumberAndStatusOrderByStartTime(carNumber, true);
	}

	@Override
	public ServiceResult cancel(Integer parameter) {
		ServiceResult serviceResult = new ServiceResult();
		Optional<Reserve> optionalReserve = reserveRepository.findById(parameter);
		if (optionalReserve.isEmpty()) {
			serviceResult.setMessage("해당 예약이 존재하지 않습니다.");
			serviceResult.setResult(false);

			return serviceResult;
		}

		Reserve reserve = optionalReserve.get();
		reserve.setStatus(false);
		reserveRepository.save(reserve);

		serviceResult.setMessage("예약이 취소되었습니다.");
		serviceResult.setResult(true);

		return serviceResult;
	}
}
