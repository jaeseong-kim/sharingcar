package com.example.sharingcar.reserve.repository;

import com.example.sharingcar.car.entity.Car;
import com.example.sharingcar.reserve.entity.Reserve;
import com.example.sharingcar.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReserveRepository extends JpaRepository<Reserve, Integer> {


	@Query("select r "
		+ "from Reserve r "
		+ "where r.email = :email "
		+ "and r.status = true "
		+ "and (r.startTime between :startDt and :endDt "
		+ "or r.endTime between  :startDt and :endDt)")
	List<Reserve> findDuplicatedTimeForUser(
		@Param("email") User email,
		@Param("startDt") LocalDateTime startDt,
		@Param("endDt") LocalDateTime endDt
	);

	@Query("select r "
		+ "from Reserve r "
		+ "where r.carNumber = :carNumber "
		+ "and r.status = true "
		+ "and (r.startTime between :startDt and :endDt "
		+ "or r.endTime between :startDt and :endDt)")
	List<Reserve> findDuplicatedTimeForCar(
		@Param("carNumber") Car carNumber,
		@Param("startDt") LocalDateTime startDt,
		@Param("endDt") LocalDateTime endDt
	);

	List<Reserve> findAllByCarNumberAndStatusOrderByStartTime(Car carNumber,boolean status);

	List<Reserve> findAllByEmailOrderByStartTime(User user);
}
