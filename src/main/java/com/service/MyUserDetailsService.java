//package com.service;
//
//import java.util.ArrayList;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.bean.UserBean;
//import com.repository.UserRepository;
//
//@Service
//public class MyUserDetailsService implements UserDetailsService{
//
//	@Autowired
//	UserRepository userRepo;
//	
//	@Override
//	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//		
//	UserBean bean =	userRepo.findByEmail(userName);
//		return new User(bean.getEmail(),bean.getPassword(),new ArrayList<>());
//	}
//
//}
