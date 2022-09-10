package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.BedBean;
import com.bean.CityBean;
import com.bean.DoctorBean;
import com.bean.PatientBean;
import com.bean.ResponseBean;
import com.bean.StateBean;
import com.bean.SymptomBean;
import com.repository.BedRepository;
import com.repository.CityRepository;
import com.repository.DoctorRepository;
import com.repository.PatientRepository;
import com.repository.StateRepository;
import com.repository.SymptomRepository;

@RestController
@RequestMapping("/staff")
public class PatientController {

	@Autowired
	PatientRepository patientRepo;

	@Autowired
	CityRepository cityRepo;

	@Autowired
	StateRepository stateRepo;

	@Autowired
	BedRepository bedRepo;

	@Autowired
	DoctorRepository doctorRepo;

	@Autowired
	SymptomRepository symptomRepo;

	@PostMapping("/patient")
	public ResponseEntity<?> addPatient(@RequestBody @Valid PatientBean patientBean, BindingResult result) {

		if (result.hasErrors()) {
			ResponseBean<List<String>> resp = new ResponseBean<>();
			List<String> errors = new ArrayList<>();
			for (int i = 0; i < result.getErrorCount(); i++) {
				String error = result.getFieldErrors().get(i).getDefaultMessage();
				System.out.println("============== Error ============== " + error);
				errors.add(error);
			}
			resp.setData(errors);
			resp.setMsg("Fill the Details Properly");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);

		} else {
			CityBean city = cityRepo.findByCityId(patientBean.getCity().getCityId());
			StateBean state = stateRepo.findByStateId(patientBean.getState().getStateId());
			BedBean bed = bedRepo.findByBedId(patientBean.getBed().getBedId());
			DoctorBean doctor = doctorRepo.findByDoctorId(patientBean.getDoctor().getDoctorId());
			
			ResponseBean<PatientBean> resp = new ResponseBean<>();
			if (city != null && state != null && bed != null && doctor != null) {
				patientBean.setCity(city);
				patientBean.setState(state);
				patientBean.setBed(bed);
				patientBean.setDoctor(doctor);
				patientRepo.save(patientBean);
				resp.setData(patientBean);
				resp.setMsg("Patient Added...");
				return ResponseEntity.status(HttpStatus.OK).body(resp);
			} else {
				resp.setMsg("Invalid Id for City or State or Bed or Doctor");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
			}
		}
	}

	@GetMapping("/patient")
	public ResponseEntity<?> getAllPatients() {
		List<PatientBean> patients = patientRepo.findAll();
		ResponseBean<List<PatientBean>> resp = new ResponseBean<>();
		if (patients.size() != 0) {
			resp.setData(patients);
			resp.setMsg("Patient List...");

			return ResponseEntity.status(HttpStatus.OK).body(resp);
		} else {
			resp.setMsg("Patient List is Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
		}
	}

//	
//	@DeleteMapping("/patient/{patientId}")
//	public ResponseEntity<?> deletePatient(@PathVariable("patientId") UUID patientId){
//		Optional<PatientBean> patient= patientRepo.findById(patientId);
//		return null;
//	}

	@DeleteMapping("/patient/{patientId}")
	public ResponseEntity<?> deletePatient(@PathVariable("patientId") UUID patientid) {
		Optional<PatientBean> patient = patientRepo.findById(patientid);
		ResponseBean<PatientBean> resp = new ResponseBean<>();
		if (patient.isPresent()) {
			patientRepo.deleteById(patientid);
			resp.setData(patient.get());
			resp.setMsg(patient.get().getPatientName() + " Symptom Deleted");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		} else {
			resp.setMsg("Patient Not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
		}
	}

	@GetMapping("/patient/{patientId}")
	public ResponseEntity<?> getPatientById(@PathVariable("patientId") UUID patientid) {
		Optional<PatientBean> patient = patientRepo.findById(patientid);
		ResponseBean<PatientBean> resp = new ResponseBean<>();

		if (patient.isPresent()) {
			resp.setData(patient.get());
			resp.setMsg("Patient Details...");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		} else {
			resp.setMsg("Patient Not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
		}
	}

	@PutMapping("/patient")
	public ResponseEntity<?> updatePatient(@RequestBody @Valid PatientBean patientBean, BindingResult result) {
		ResponseBean<PatientBean> resp = new ResponseBean<>();
		if (result.hasErrors()) {
			ResponseBean<List<String>> res = new ResponseBean<>();
			List<String> errors = new ArrayList<>();
			for (int i = 0; i < result.getErrorCount(); i++) {
				String error = result.getFieldErrors().get(i).getDefaultMessage();
				System.out.println("============== Error ============== " + error);
				errors.add(error);
			}
			res.setData(errors);
			res.setMsg("Fill the Details Properly");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			patientRepo.save(patientBean);
			resp.setData(patientBean);
			resp.setMsg("Patient " + patientBean.getPatientName() + " Updated");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}
	}
}
