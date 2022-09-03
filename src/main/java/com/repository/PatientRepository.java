package com.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bean.BedBean;
import com.bean.CityBean;
import com.bean.DoctorBean;
import com.bean.PatientBean;
import com.bean.StateBean;

public interface PatientRepository extends CrudRepository<PatientBean, UUID>{

	List<PatientBean> findAll();
	
//	@Query(value ="select * from patient where patient_id = :patientId",nativeQuery = true)
	PatientBean findByPatientId(UUID patientId);
}
