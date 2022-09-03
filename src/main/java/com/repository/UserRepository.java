package com.repository;



import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.UserBean;

@Repository
public interface UserRepository extends CrudRepository<UserBean, UUID> {
	UserBean findByEmail(String email);
	UserBean findByUserId(UUID userId);
	@Query(value ="select * from users where auth_token = :authToken",nativeQuery = true)
	UserBean findByAuthToken(String authToken);
	
	UserBean findByOtp(Integer otp);
	
}
