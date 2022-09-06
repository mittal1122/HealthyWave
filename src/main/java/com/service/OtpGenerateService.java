package com.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.UserBean;
import com.repository.UserRepository;

@Service
public class OtpGenerateService {

	@Autowired
	UserRepository userRepo;

	public Integer generateToken() {

		UserBean dbuser = null;
		Random random = new Random();
		Integer otp = 0;
		do {
			otp = Integer.parseInt(String.format("%04d", random.nextInt(10000)));

			dbuser = userRepo.findByOtp(otp);
		} while (dbuser != null);
		System.out.println(otp + " : otp ");
		return otp;
	}
}
