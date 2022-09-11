package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

import com.bean.DoctorBean;
import com.bean.ResponseBean;
import com.bean.SpecializationBean;
import com.bean.UserBean;
import com.repository.DoctorRepository;
//import com.repository.DoctorView;
import com.repository.SpecializationRepository;
import com.repository.UserRepository;

@RestController
public class DoctorController {

	@Autowired
	DoctorRepository doctorRepo;

	@Autowired
	SpecializationRepository spRepo;
	
	@Autowired
	UserRepository userRepo;

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
			UserBean user = userRepo.findByUserId(bean.getUser().getUserId());
			SpecializationBean spbean = spRepo.findBySpecializationId(bean.getSpecialization().getSpecializationId());
			ResponseBean<DoctorBean> res = new ResponseBean<>();
			System.out.println(user + " "+user.getRole().getRoleName().equals("doctor") + " " +user.getIsApprove() );
			if(user != null && user.getRole().getRoleName().equals("doctor") && user.getIsApprove()==true && spbean != null) {
				bean.setSpecialization(spbean);System.out.println("DOCTOR ADD : " + bean+" UID : " + bean.getUser().getUserId()+"SPEC : "+spbean.getSpcialization()+"SPECID : "+spbean.getSpecializationId());
				bean.setUser(user);
				bean.setStatus(true);
				doctorRepo.save(bean);				
				res.setData(bean);
				res.setMsg("Doctor added..");
				return ResponseEntity.status(HttpStatus.OK).body(res);
			}
			else {
				res.setMsg("Doctor not Approved");
				return ResponseEntity.status(HttpStatus.OK).body(res);
			}
			
		}
	}

	@GetMapping("/public/doctor")
	public ResponseEntity<?> getAllDoctors() {
		List<DoctorBean> doctor = doctorRepo.findAll();
		System.out.println("AAAAAAAAAAAAAA");
		System.out.println("doctor.get(0).getDoctorId()" + doctor.get(0).getDoctorId());
		System.out.println("doctor.get(0).getSpecialization()" + doctor.get(0).getSpecialization().getSpcialization());
		System.out.println("doctor.get(0).getUser().getFirstName()" + doctor.get(0).getUser().getFirstName());

		if (doctor.size() != 0) {
			System.out.println("BBBBBBBBBB");
//			ResponseBean<SpecializationBean> res = new ResponseBean<>();
//			res.setData(doctor.get(0).getSpecialization());
			ResponseBean<List<DoctorBean>> res = new ResponseBean<>();
			res.setData(doctor);
			res.setMsg("doctors List...");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			System.out.println("DDDDDDDDDDDDDDDDDD");
			ResponseBean<List<DoctorBean>> res = new ResponseBean<>();
			res.setData(doctor);
			res.setMsg("doctor List is Empty...");
			System.out.println("EEEEEEEEEEEEEEEE");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}

	}

//	@GetMapping("/doctorbyname/{name}")
//	public ResponseEntity<?> getAllDoctorsByName(@PathVariable("name") String docName) {
//		List<DoctorView> doctor = doctorRepo.findByDoctorName(docName);
//		if (doctor.size() != 0) {
//			ResponseBean<List<DoctorView>> res = new ResponseBean<>();
//			res.setData(doctor);
//			res.setMsg("doctors List...");
//			return ResponseEntity.status(HttpStatus.OK).body(res);
//		} else {
//			ResponseBean<List<DoctorView>> res = new ResponseBean<>();
//			res.setData(doctor);
//			res.setMsg("doctor List is Empty...");
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
//		}
//
//	}



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
			res.setMsg(bean.getUser().getFirstName() + " deleted...");
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
			userRepo.save(doctorbean.getUser());
			ResponseBean<DoctorBean> res = new ResponseBean<>();
			res.setData(doctorbean);
			res.setMsg("Doctor " + doctorbean.getUser().getFirstName() + "'s Details Updated...");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
	
	
	
//	public String saveProfile(DoctorBean bean){
//		
//		doctorRepo.save(bean); // firstname email password 
//		UUID userId = bean.getUser().getUserId();
//
//		imageUpload(bean.getProfile(),userId);
//		bean.setProfilePath("resources\\images\\"+userId+"\\"+bean.getProfile().getOriginalFilename());
//
//		//image localstore -> cloud -> cloud url angualr 
//		//image -> string -> angular -> strig -> image 
//
//		doctorRepo.save(bean);
//	
//	}

}
