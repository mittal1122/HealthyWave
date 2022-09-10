package com.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.RestController;

import com.bean.AppointmentBean;
import com.bean.ResponseBean;
import com.repository.AppointmentRepository;
import com.service.CaseNumberService;

@RestController
public class AppointmentController {

	@Autowired
	AppointmentRepository appointmentRepo;

	@Autowired
	CaseNumberService caseService;

//	@GetMapping("/public/caseNumber")
//	public ResponseEntity<?> getAppointmentForCaseNumber() {
//
//		Integer caseNumber = caseService.generateCaseNumber();
//		System.out.println(caseNumber);
//		return ResponseEntity.status(HttpStatus.OK).body(caseNumber);
//
//	}

	@PostMapping("/public/appointment")
	public ResponseEntity<?> addAppointment(@RequestBody @Valid AppointmentBean appointmentBean, BindingResult result) {
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
			LocalDateTime date1 = LocalDateTime.now();
			DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

			String formattedDate = date.format(date1);
			System.out.println("After formatting: " + formattedDate);
			appointmentBean.setDateTime(formattedDate);
			
			Integer caseNumber = caseService.generateCaseNumber();
			appointmentBean.setCaseNumber(caseNumber);
			appointmentBean.setIsApproved(false);
			appointmentRepo.save(appointmentBean);
			System.out.println("data added");
			ResponseBean<AppointmentBean> resp = new ResponseBean<>();
			resp.setData(appointmentBean);
			resp.setMsg("Appointment Added...");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}
	}

	@GetMapping("/staff/appointment")
	public ResponseEntity<?> getAllAppointment() {
		List<AppointmentBean> appointments = appointmentRepo.findAll();
		ResponseBean<List<AppointmentBean>> resp = new ResponseBean<>();
		if (appointments.size() != 0) {
			resp.setData(appointments);
			resp.setMsg("Appointment List...");

			return ResponseEntity.status(HttpStatus.OK).body(resp);
		} else {
			resp.setData(appointments);
			resp.setMsg("Appointment List is Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
		}
	}

//	@DeleteMapping("/staff/declineappointment/{appointmentId}")
//	public ResponseEntity<?> deleteAppointment(@PathVariable("appointmentId") UUID appointmentid) {
//		AppointmentBean appointment = appointmentRepo.findByAppointmentId(appointmentid);
//		ResponseBean<AppointmentBean> resp = new ResponseBean<>();
//		if (appointment != null) {
//			appointmentRepo.deleteById(appointmentid);
//			resp.setData(appointment);
//			resp.setMsg(appointment.getPatientName() + "'s Appointment Deleted");
//			return ResponseEntity.status(HttpStatus.OK).body(resp);
//		} else {
//			resp.setMsg("No Appointment Found");
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
//		}
//	}

	@GetMapping("/staff/appointment/{appointmentId}")
	public ResponseEntity<?> getAppointmentById(@PathVariable("appointmentId") UUID appointmentid) {
		Optional<AppointmentBean> appointment = appointmentRepo.findById(appointmentid);
		ResponseBean<AppointmentBean> resp = new ResponseBean<>();

		if (appointment!= null) {
			resp.setData(appointment.get());
			resp.setMsg("Appointment Details...");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		} else {
			resp.setMsg("Appointment Details Not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
		}
	}

	@PutMapping("/staff/appointment")
	public ResponseEntity<?> updateAppointment(@RequestBody @Valid AppointmentBean appointmentBean,
			BindingResult result) {
		ResponseBean<AppointmentBean> resp = new ResponseBean<>();
		if (result.hasErrors()) {
			ResponseBean<List<String>> res = new ResponseBean<>();
			List<String> error = new ArrayList<>();
			System.out.println("StaffError Count : " + result.getErrorCount());
			for (int i = 0; i < result.getErrorCount(); i++) {
				String addError = result.getFieldErrors().get(i).getDefaultMessage();
				System.out.println("error : " + result.getFieldErrors().get(i).getDefaultMessage());
				error.add(addError);
			}
			res.setData(error);
			res.setMsg("Detail Fill Properly");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			appointmentRepo.save(appointmentBean);
			resp.setData(appointmentBean);
			resp.setMsg(appointmentBean.getPatientName() + "'s Appointment Details Updated");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}
	}
	
	@PutMapping("/staff/declineappointment/{appointmentId}")
	public ResponseEntity<?> deleteAppointment(@PathVariable("appointmentId") UUID appointmentid) {
		Optional<AppointmentBean> appointment = appointmentRepo.findById(appointmentid);
		ResponseBean<AppointmentBean> resp = new ResponseBean<>();System.out.println("AppO:"+appointment);
		if (appointment != null) {
			appointment.get().setIsApproved(false);
			appointmentRepo.save(appointment.get());
			resp.setData(appointment.get());
			resp.setMsg(appointment.get().getPatientName() + "'s Appointment Updated");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		} else {
			resp.setMsg("No Appointment Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
		}
	}
	
	@PutMapping("/staff/approveappointment/{appointmentId}")
	public ResponseEntity<?> approveAppointment(@PathVariable("appointmentId") UUID appointmentid) {
		Optional<AppointmentBean> appointment = appointmentRepo.findById(appointmentid);
		ResponseBean<AppointmentBean> resp = new ResponseBean<>();
		if (appointment != null) {
			appointment.get().setIsApproved(true);
			appointmentRepo.save(appointment.get());
			resp.setData(appointment.get());
			resp.setMsg(appointment.get().getPatientName() + "'s Appointment Updated");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		} else {
			resp.setMsg("No Appointment Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
		}
	}
}