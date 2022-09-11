package com.seed;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bean.RoleBean;
import com.repository.RoleRepository;

import lombok.extern.log4j.Log4j2;
@Component
@Log4j2
public class Roleseed {

	@Autowired
	RoleRepository roleRepo;

	@PostConstruct
	void createRoles() {

		RoleBean role = roleRepo.findByRoleName("staff");
		RoleBean role1 = roleRepo.findByRoleName("doctor");
		RoleBean role2 = roleRepo.findByRoleName("admin");
		if (role == null || role1 == null || role2 == null) {
			RoleBean admin = new RoleBean();
			admin.setRoleName("admin");
			roleRepo.save(admin);

			RoleBean doctor = new RoleBean();
			doctor.setRoleName("doctor");
			roleRepo.save(doctor);


			RoleBean staff = new RoleBean();
			staff.setRoleName("staff");
			roleRepo.save(staff);

			System.out.println("Role Added.....");

		} else {
//			log.info("Roled Already Added....");
			
		}
	}
}
