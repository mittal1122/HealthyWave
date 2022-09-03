package com.repository;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.UserBean;

@Repository
public interface UserRepository extends CrudRepository<UserBean, Integer> {
	UserBean findByEmail(String email);
	UserBean findByUserId(Integer userId);
	@Query(value ="select * from users where auth_token = :authToken",nativeQuery = true)
	UserBean findByAuthToken(String authToken);
	
	UserBean findByOtp(Integer otp);
	
}
