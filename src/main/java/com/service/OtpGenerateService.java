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
	public Integer generateToken(int size) {
//		String allWords = "0123456789";
//		int otp = 0;
//		UserBean dbuser = null;
//		do{
//			for(int i = 1;i<=size;i++) {
//			int index = (int) (allWords.length()*Math.random());
//			otp = otp + allWords.charAt(index);
//		}
//		dbuser = userRepo.findByOtp(otp);
//		}while(dbuser != null);
		Random random = new Random();
		Integer otp = Integer.parseInt(String.format("%04d", random.nextInt(10000)));
		System.out.println(otp +" : otp ");
		return otp;
}
}
