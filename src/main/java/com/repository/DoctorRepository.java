package com.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.DoctorBean;

@Repository
public interface DoctorRepository extends CrudRepository<DoctorBean, UUID>{
	List<DoctorBean> findAll();
//
//	List<DoctorBean> findByDoctorName(String docName);
	

//	List<DoctorView> findByDoctorName(String docName);
	

//	List<DoctorView> findByDoctorName(String docName);

	DoctorBean findByDoctorId(UUID doctorId);

}
