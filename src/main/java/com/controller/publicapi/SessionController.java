package com.controller.publicapi;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.EmailDetails;
import com.bean.LoginBean;
import com.bean.LogoutBean;
import com.bean.ResponseBean;
import com.bean.RoleBean;
import com.bean.UserBean;
import com.bean.VerifyEmailBean;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.service.EmailService;
//import com.service.TokenService;
import com.service.OtpGenerateService;
import com.service.TokenService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/public")
public class SessionController {


	Integer generatedOTP;
	
	@Autowired
	UserRepository userRepo;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	BCryptPasswordEncoder bcrypt;

	@Autowired
	TokenService tokenService;

	@Autowired
	OtpGenerateService otpService;

	@Autowired
	EmailService emailService;

	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserBean user) {
		UserBean dbUser = userRepo.findByEmail(user.getEmail());
		ResponseBean<UserBean> res = new ResponseBean<>();
//		if(dbUser.getContactNum() != user.getContactNum()) {
		if (dbUser == null) {
			RoleBean role = roleRepo.findByRoleName(user.getRole().getRoleName());
			if (role == null) {
				res.setMsg("role is invalid");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res); // ResponseBean Object
			} else {
				user.setRole(role);
				user.setPassword(bcrypt.encode(user.getPassword()));
				user.setIsApprove(false);
				userRepo.save(user);
				res.setData(user);
				res.setMsg("Signup Done! Please wait for approval from Admin...");
				return ResponseEntity.ok(res);
			}
//			}else {
//				res.setMsg("Contact Already Taken");
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
//			}
		} else {
			res.setMsg("Email Already Taken");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res); // ResponseBean Object
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody LoginBean login) {
		
		UserBean dbUser = userRepo.findByEmail(login.getEmail());
		if (dbUser == null || !bcrypt.matches(login.getPassword(), dbUser.getPassword())) {
//			log.info("dbUser null in login");
			ResponseBean<LoginBean> res = new ResponseBean<>();
			res.setData(login);
			res.setMsg("Invalid Credentials");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		} else if (dbUser.getIsApprove() == false) {
			ResponseBean<LoginBean> res = new ResponseBean<>();
			res.setData(login);
			res.setMsg("You are not Approved by Admin");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);

		} else {
			dbUser.setAuthToken(tokenService.generateToken(16));
			userRepo.save(dbUser);

			ResponseBean<UserBean> res = new ResponseBean<>();
			res.setData(dbUser);
			res.setMsg("Login Successfuly");
//			log.info(dbUser.getFirstName() + " Login Successfuly");
//			log.info(dbUser.getAuthToken()+" Login Successfuly");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
	
	@PostMapping("/sendotp")
	public ResponseEntity<?> verifyEmail(@RequestBody VerifyEmailBean emailBean){
	UserBean userBean = userRepo.findByEmail(emailBean.getEmail());
		if(userBean != null) {
			generatedOTP = otpService.generateToken();
			userBean.setOtp(generatedOTP);
			userRepo.save(userBean);
			String subject = "OTP For Reset Password";
			String msgBody = "'" + generatedOTP+ "' is the One Time Password (OTP) to update your password associated with email address '"+ userBean.getEmail()+"' on the Healthywave webApp. This OTP is valid for next 3 minutes.If You don't have asked for this kindly ignore it.";
			
			EmailDetails emailDetailBean = new EmailDetails();

			emailDetailBean.setRecipient(emailBean.getEmail());;
			emailDetailBean.setSubject(subject);
			emailDetailBean.setMsgBody(msgBody);
			String status = emailService.sendSimpleMail(emailDetailBean);
//			log.info(status);
			return ResponseEntity.ok().body(userBean);
		}else {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	
	@PostMapping("/verifyotp")
	public ResponseEntity<?> verifyOTP(@RequestBody VerifyEmailBean verifyOtp){
	UserBean userBean = userRepo.findByOtp(verifyOtp.getOtp());
		if(userBean != null) {
			return ResponseEntity.ok().body(userBean);
		}else {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PutMapping("/updatepassword")
	public ResponseEntity<?> updatePassword(@RequestBody VerifyEmailBean updatePassword){
		System.out.println("updatePassword().email..."+updatePassword.getEmail());
		System.out.println("updatePassword().password..."+updatePassword.getPassword());
	UserBean userBean = userRepo.findByEmail(updatePassword.getEmail());
		if(userBean != null) {
			System.out.println("userBean.getPassword();..."+userBean.getPassword());
			userBean.setPassword(bcrypt.encode(updatePassword.getPassword()));
			userRepo.save(userBean);
			return ResponseEntity.ok().body(userBean);
		}else {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}


//	@PostMapping("/sendMail")
//	public String sendMail(@RequestBody EmailDetails details) {
//		String status = emailService.sendSimpleMail(details);
//
//		return status;
//	}
//
//	// Sending email with attachment with
//	@PostMapping("/sendMailWithAttachment")
//	public String sendMailWithAttachment(@RequestBody EmailDetails details) {
//		String status = emailService.sendMailWithAttachment(details);
//
//		return status;
//	}

	@PostMapping("/logout/{userId}")
	public ResponseEntity<?> logout(@PathVariable("userId") UUID userId){
		System.out.println("userId:"+userId);
		UserBean user = userRepo.findByUserId(userId);
		user.setAuthToken(null);
		
		System.out.println("AUth TOken::::=>"+user.getAuthToken());
		userRepo.save(user);
		return ResponseEntity.ok(user);
	}

}
