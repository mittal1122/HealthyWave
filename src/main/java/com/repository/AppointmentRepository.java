package com.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.bean.AppointmentBean;

public interface AppointmentRepository extends CrudRepository<AppointmentBean, UUID>{

	List<AppointmentBean> findAll();
	
	AppointmentBean findByCaseNumber(Integer caseNumber);
}
