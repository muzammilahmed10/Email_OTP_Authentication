package com.web.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.demo.entites.User;
import java.util.List;




@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	public User  findByUsername(String username);
	public User  findByOtp(String otp);
}
