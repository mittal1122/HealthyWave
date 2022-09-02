package com.controller.publicapi;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.LoginBean;
import com.bean.ResponseBean;
import com.bean.RoleBean;
import com.bean.UserBean;
import com.repository.RoleRepository;
import com.repository.UserRepository;
//import com.service.TokenService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/public")
public class SessionController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	BCryptPasswordEncoder bcrypt;

//	@Autowired
//	TokenService tokenService;

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
			}else{
				user.setRole(role);
				user.setPassword(bcrypt.encode(user.getPassword()));
				userRepo.save(user);
				res.setData(user);
				res.setMsg("Signup Successfuly...");
				return ResponseEntity.ok(res);
				}
//			}else {
//				res.setMsg("Contact Already Taken");
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
//			}
		} else {
			res.setData(user);
			res.setMsg("Email Already Taken");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res); // ResponseBean Object
		}

	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody LoginBean login) {
		UserBean dbUser =  userRepo.findByEmail(login.getEmail());
		if(dbUser == null || !bcrypt.matches(login.getPassword(),dbUser.getPassword())) {
			ResponseBean<LoginBean> res = new ResponseBean<>();
			res.setData(login);
			res.setMsg("Invalid Credentials");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		}else if(dbUser.getIsApprove() == false) { 
			ResponseBean<LoginBean> res = new ResponseBean<>();
			res.setData(login);
			res.setMsg("You are not Approved by Admin");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
	
		}else {
//			dbUser.setAuthToken(tokenService.generateToken(16));
			userRepo.save(dbUser);
			
			ResponseBean<UserBean> res = new ResponseBean<>();
			res.setData(dbUser);
			res.setMsg("Login Successfuly");
			log.info(dbUser.getFirstName()+"Login Successfuly");
			
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

}
