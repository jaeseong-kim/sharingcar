package com.example.sharingcar.user.repository;

import com.example.sharingcar.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
	Optional<User> findByEmailAuthKey(String emailAuthKey);
}
