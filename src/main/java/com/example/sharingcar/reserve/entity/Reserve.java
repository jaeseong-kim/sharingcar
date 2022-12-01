package com.example.sharingcar.reserve.entity;

import com.example.sharingcar.car.entity.Car;
import com.example.sharingcar.user.entity.User;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@ToString
public class Reserve {

	@Id
	private int reservationId;

	@ManyToOne
	@JoinColumn(name = "email")
	private User email;
	@ManyToOne
	@JoinColumn(name = "carNumber")
	private Car carNumber;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String location;
	private boolean insuranceYn;
	private String type;
	private boolean status;
	private int chargeRentIns;
	private LocalDateTime reserveDt;


}
