package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
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
import org.springframework.web.bind.annotation.RestController;

import com.bean.DoctorBean;
import com.bean.ResponseBean;
import com.bean.SpecializationBean;
import com.repository.DoctorRepository;
import com.repository.DoctorView;
import com.repository.SpecializationRepository;

@RestController
public class DoctorController {

	@Autowired
	DoctorRepository doctorRepo;

	@Autowired
	SpecializationRepository spRepo;

	@PostMapping("/doctor")
	public ResponseEntity<?> addDoctor(@RequestBody @Valid DoctorBean bean, BindingResult result) {
		System.out.println("Here at AddDoctor");
		if (result.hasErrors()) {
			ResponseBean<List<String>> res = new ResponseBean<>();
			List<String> error = new ArrayList<>();
			System.out.println("DoctorError Count : " + result.getErrorCount());
			for (int i = 0; i < result.getErrorCount(); i++) {
				String addError = result.getFieldErrors().get(i).getDefaultMessage();
				System.out.println("error : " + result.getFieldErrors().get(i).getDefaultMessage());
				error.add(addError);
			}
			res.setData(error);
			res.setMsg("Fill Details Properly");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			SpecializationBean spbean = spRepo.findBySpecializationId(bean.getSpecialization().getSpecializationId());
			bean.setSpecialization(spbean);
			doctorRepo.save(bean);
			ResponseBean<DoctorBean> res = new ResponseBean<>();
			res.setData(bean);
			res.setMsg("Doctor added..");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

	@GetMapping("/doctor")
	public ResponseEntity<?> getAllDoctors() {
		List<DoctorBean> doctor = doctorRepo.findAll();
		if (doctor.size() != 0) {
			ResponseBean<List<DoctorBean>> res = new ResponseBean<>();
			res.setData(doctor);
			res.setMsg("doctors List...");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			ResponseBean<List<DoctorBean>> res = new ResponseBean<>();
			res.setData(doctor);
			res.setMsg("doctor List is Empty...");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}

	}
	
	
	@GetMapping("/doctorbyname/{name}")
	public ResponseEntity<?> getAllDoctorsByName(@PathVariable("name") String docName) {
		List<DoctorView> doctor = doctorRepo.findByDoctorName(docName);
		if (doctor.size() != 0) {
			ResponseBean<List<DoctorView>> res = new ResponseBean<>();
			res.setData(doctor);
			res.setMsg("Doctors List...");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			ResponseBean<List<DoctorView>> res = new ResponseBean<>();
			res.setData(doctor);
			res.setMsg("doctor List is Empty...");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}

	}

	@GetMapping("/doctor/{doctorId}")
	public ResponseEntity<?> getdoctorById(@PathVariable("doctorId") UUID doctorId) {
		Optional<DoctorBean> doctor = doctorRepo.findById(doctorId);
		if (doctor.isPresent()) {
			DoctorBean bean = doctor.get();
			ResponseBean<DoctorBean> res = new ResponseBean<>();
			res.setData(bean);
			res.setMsg("Doctor Details...");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			ResponseBean<DoctorBean> res = new ResponseBean<>();
			res.setMsg("This Doctor is Not Availible");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}

	}

	@DeleteMapping("/doctor/{doctorId}")
	public ResponseEntity<?> deleteDoctorById(@PathVariable("doctorId") UUID doctorId) {
		Optional<DoctorBean> doctor = doctorRepo.findById(doctorId);
		if (doctor.isPresent()) {
			DoctorBean bean = doctor.get();
			doctorRepo.delete(bean);
			ResponseBean<DoctorBean> res = new ResponseBean<>();
			res.setData(bean);
			res.setMsg(bean.getDoctorName() + " deleted...");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			ResponseBean<DoctorBean> res = new ResponseBean<>();
			res.setMsg("Doctor List is Empty...");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}

	}

	@PutMapping("/doctor")
	public ResponseEntity<?> updateDoctor(@RequestBody @Valid DoctorBean doctorbean, BindingResult result) {
		if (result.hasErrors()) {
			ResponseBean<List<String>> res = new ResponseBean<>();
			List<String> error = new ArrayList<>();
			System.out.println("DoctorError Count : " + result.getErrorCount());
			for (int i = 0; i < result.getErrorCount(); i++) {
				String addError = result.getFieldErrors().get(i).getDefaultMessage();
				System.out.println("error : " + result.getFieldErrors().get(i).getDefaultMessage());
				error.add(addError);
			}
			res.setData(error);
			res.setMsg("Detail Fill Properly");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			SpecializationBean spbean = spRepo
					.findBySpecializationId(doctorbean.getSpecialization().getSpecializationId());
			doctorbean.setSpecialization(spbean);
			doctorRepo.save(doctorbean);
			ResponseBean<DoctorBean> res = new ResponseBean<>();
			res.setData(doctorbean);
			res.setMsg(doctorbean.getDoctorName() + " Updated...");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

}
