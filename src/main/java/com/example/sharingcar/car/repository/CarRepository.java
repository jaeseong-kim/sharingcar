package com.example.sharingcar.car.repository;

import com.example.sharingcar.car.entity.Car;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,String> {
	@Query("select c from Car c where c.status is true")
	List<Car> findByStatus();
}
