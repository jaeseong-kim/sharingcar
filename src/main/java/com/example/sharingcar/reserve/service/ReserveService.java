package com.example.sharingcar.reserve.service;

import com.example.sharingcar.car.entity.Car;
import com.example.sharingcar.reserve.entity.Reserve;
import com.example.sharingcar.reserve.model.ReserveReqInput;
import com.example.sharingcar.reserve.model.ServiceResult;
import com.example.sharingcar.user.entity.User;
import java.util.List;

public interface ReserveService {

	ServiceResult req(ReserveReqInput parameter);

	List<Reserve> listReservedTime(Car carNumber);

	List<Reserve> listForMyPage(User user);

	ServiceResult cancel(Integer reservationId);
}
