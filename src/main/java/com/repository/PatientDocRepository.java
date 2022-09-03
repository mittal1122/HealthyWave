package com.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bean.PatientDocumentBean;

public interface PatientDocRepository extends CrudRepository<PatientDocumentBean, UUID>{

	List<PatientDocumentBean> findAll();
	
	@Query(value = "select * from patientdocument where patient_id = :patientId",nativeQuery = true)
	List<PatientDocumentBean> findByPatientId(UUID patientId);

}
