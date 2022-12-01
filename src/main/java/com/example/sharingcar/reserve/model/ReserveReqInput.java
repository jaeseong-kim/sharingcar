package com.example.sharingcar.reserve.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReserveReqInput {

	String email;
	String carNumber;
	String dateStart;
	String timeStart;
	String dateEnd;
	String timeEnd;
	String insurance;
}
