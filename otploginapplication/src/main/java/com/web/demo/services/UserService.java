package com.web.demo.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.web.demo.entites.User;
import com.web.demo.repositories.UserRepository;

import java.time.Duration;

@Service
public class UserService {

	UserRepository userRepository;
	JavaMailSender mailSender;
	public UserService(UserRepository userRepository,JavaMailSender mailSender) {
		this.userRepository = userRepository;
		this.mailSender = mailSender;
	}
	
	public boolean loginService(String username, String password) {
		User user = userRepository.findByUsername(username);
		try {
		if(user!=null & password.equals(user.getPassword())) {
			Integer newotp = new Random().nextInt(999999);
			String genotp=String.valueOf(newotp);
			user.setOtp(genotp);
			user.setOtptime(LocalDateTime.now());
			userRepository.save(user);
			
			String email = user.getEmail();
			
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email);
			message.setSubject("Your OTP Code");
			message.setText("Your OTp code is: "+genotp);
			mailSender.send(message);
			
			return true;
		}
		
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	public String verify(String otp) {
		User user = userRepository.findByOtp(otp);
		if (user != null) {
			LocalDateTime otpGeneratedTime = user.getOtptime();
			if (otpGeneratedTime != null) {
				Duration duration = Duration.between(otpGeneratedTime, LocalDateTime.now());
				if (duration.toMinutes() > 1) {
					user.setOtp(null);
					user.setOtptime(null);
					userRepository.save(user);
					return "otpexpired";
				}
				return "success";
			}
		}
		return "failure";
	}
}
