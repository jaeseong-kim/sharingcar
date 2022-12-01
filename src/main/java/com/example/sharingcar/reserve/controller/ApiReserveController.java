package com.example.sharingcar.reserve.controller;

import com.example.sharingcar.common.model.ResponseResult;
import com.example.sharingcar.reserve.model.CancelReqInput;
import com.example.sharingcar.reserve.model.ReserveReqInput;
import com.example.sharingcar.reserve.model.ServiceResult;
import com.example.sharingcar.reserve.service.ReserveService;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class ApiReserveController {

	private final ReserveService reserveService;

	@PostMapping("/api/reserve/req")
	public ResponseEntity<?> reserveReq(Model model,
		@RequestBody ReserveReqInput parameter, Principal principal) {

		parameter.setEmail(principal.getName());

		ServiceResult result = reserveService.req(parameter);
		if (!result.isResult()) {
			ResponseResult responseResult = new ResponseResult(false, result.getMessage());

			return ResponseEntity.ok().body(responseResult);
		}

		ResponseResult responseResult = new ResponseResult(true, result.getMessage());

		return ResponseEntity.ok().body(responseResult);
	}

	@PostMapping("/api/reserve/cancel/")
	public ResponseEntity<?> cancelReq(@RequestBody CancelReqInput parameter) {
		ServiceResult result = reserveService.cancel(parameter.getReservationId());
		if (!result.isResult()) {
			ResponseResult responseResult = new ResponseResult(false, result.getMessage());

			return ResponseEntity.ok().body(responseResult);
		}

		ResponseResult responseResult = new ResponseResult(true, result.getMessage());

		return ResponseEntity.ok().body(responseResult);
	}
}
