package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.PatientBean;
import com.bean.PatientSymptomBean;
import com.bean.ResponseBean;
import com.bean.SymptomBean;
import com.repository.PatientRepository;
import com.repository.SymptomRepository;

@RestController
public class PatientSymptomController {
	
	
	@Autowired
	PatientRepository patientRepo;
	
	@Autowired
	SymptomRepository symptomRepo;
	
	@PostMapping("/patientsymptoms")
	public ResponseEntity<?> addPatientSymptoms(@RequestBody PatientSymptomBean psBean) {
		ResponseBean<PatientBean> res = new ResponseBean<>();

	PatientBean patientBean = patientRepo.findByPatientId(psBean.getPatientId());
	System.out.println("patientBean : "+patientBean);
	if (patientBean != null) {
		for (int i = 0; i < psBean.getSymptomId().size(); i++) {
			SymptomBean symptomBean = symptomRepo.findBySymptomId(psBean.getSymptomId().get(i));
			System.out.println("symptomBean : "+symptomBean);
			if(symptomBean!=null) {					
				patientBean.getSymptoms().add(symptomBean);
				patientRepo.save(patientBean);
			}else {
				List<UUID> nullUser = new ArrayList<UUID>() ;
				nullUser.add(psBean.getSymptomId().get(i));
				
			}
		}
		
		return ResponseEntity.ok().body(patientBean);
	}else {
		res.setData(patientBean);
		res.setMsg("Patient is Null");
		return ResponseEntity.ok().body(psBean);
		
	}
	
	}
}
