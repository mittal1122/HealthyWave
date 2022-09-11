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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.AppointmentBean;
import com.bean.CityBean;
import com.bean.DoctorBean;
import com.bean.EmailDetails;
import com.bean.ResponseBean;
import com.bean.SlotBean;
import com.bean.StateBean;
import com.repository.AppointmentRepository;
import com.repository.CityRepository;
import com.repository.DoctorRepository;
import com.repository.SlotRepository;
import com.repository.StateRepository;
import com.service.CaseNumberService;
import com.service.EmailService;

@RestController
public class AppointmentController {

	@Autowired
	AppointmentRepository appointmentRepo;

	@Autowired
	CaseNumberService caseService;

	@Autowired
	EmailService emailService;
	
	@Autowired
	StateRepository stateRepo;
	
	@Autowired
	CityRepository cityRepo;
	
	@Autowired
	DoctorRepository doctorRepo;
	
	@Autowired 
	SlotRepository slotRepo;
	
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
			Optional<StateBean> stateBean = stateRepo.findById(appointmentBean.getState().getStateId());
			Optional<CityBean> cityBean = cityRepo.findById(appointmentBean.getCity().getCityId());
			Optional<DoctorBean> doctorBean = doctorRepo.findById(appointmentBean.getDoctor().getDoctorId());
			Optional<SlotBean> slotBean = slotRepo.findById(appointmentBean.getSlot().getSlotId());
			LocalDateTime date1 = LocalDateTime.now();
			DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

			String formattedDate = date.format(date1);
			System.out.println("After formatting: " + formattedDate);
			appointmentBean.setCreatedAt(formattedDate);
			System.out.println(appointmentBean.getSlot());
			Integer caseNumber = caseService.generateCaseNumber();
			appointmentBean.setCaseNumber(caseNumber);

			EmailDetails email = new EmailDetails();
			email.setRecipient(appointmentBean.getEmail());
			email.setSubject("Appointment Submitted");
			email.setMsgBody("Your appointment Submitted Successfully with Case Number : "
					+ appointmentBean.getCaseNumber()
					+ ". Please Wait for Confirmation from HEALTHYWAVE. \nCase Number : "
					+ appointmentBean.getCaseNumber() + "\nPatient Name : " + appointmentBean.getPatientName()
					+ "\nGender : " + appointmentBean.getGender() + "\nContact Number : " + appointmentBean.getContact()
					+ "\nEmail :" + appointmentBean.getEmail() + "\nReason : " + appointmentBean.getReason());
			String status = emailService.sendSimpleMail(email);

			appointmentBean.setIsApproved(false);
			appointmentRepo.save(appointmentBean);
			System.out.println("data added");
			ResponseBean<AppointmentBean> resp = new ResponseBean<>();
			resp.setData(appointmentBean);
			resp.setMsg("Appointment Added..."+status);
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

		if (appointment != null) {
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

	@PutMapping("/staff/approveappointment")
	public ResponseEntity<?> approveAppointment(@RequestBody AppointmentBean bean) {
		Optional<AppointmentBean> appointment = appointmentRepo.findById(bean.getAppointmentId());
		ResponseBean<AppointmentBean> resp = new ResponseBean<>();
		if (appointment.isPresent() == true) {
			bean.setIsApproved(true);
			appointmentRepo.save(bean);
			resp.setData(bean);
			resp.setMsg(bean.getPatientName() + "'s Appointment Approved");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		} else {
			resp.setMsg("this appointment is not valid");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
		}

	}

	@PutMapping("/staff/declineappointment")
	public ResponseEntity<?> deleteAppointment(@RequestBody AppointmentBean bean) {
		Optional<AppointmentBean> appointment = appointmentRepo.findById(bean.getAppointmentId());
		ResponseBean<AppointmentBean> resp = new ResponseBean<>();
		System.out.println("AppO:" + appointment);
		if (appointment != null) {
			appointment.get().setIsApproved(false);
			appointmentRepo.save(appointment.get());
			resp.setData(appointment.get());
			resp.setMsg(appointment.get().getPatientName() + "'s Appointment Decline");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		} else {
			resp.setMsg("this appointment is not valid");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
		}
	}
}