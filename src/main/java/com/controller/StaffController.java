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
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.StaffBean;
import com.repository.StaffRepository;

@RestController
public class StaffController {

	@Autowired
	StaffRepository staffRepo;

	@PostMapping("/staff")
	public ResponseEntity<?> addStaff(@RequestBody @Valid StaffBean staffbean, BindingResult result) {
		if (result.hasErrors()) {
			ResponseBean<List<String>> res = new ResponseBean<>();
			List<String> error = new ArrayList<>();
			//System.out.println("StaffError Count : " + result.getErrorCount());
			
			for (int i = 0; i < result.getErrorCount(); i++) {
				String addError = result.getFieldErrors().get(i).getDefaultMessage();
				System.out.println("error : " + result.getFieldErrors().get(i).getDefaultMessage());
				error.add(addError);
			}
			res.setData(error);
			res.setMsg("Detail Fill Properly");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			staffRepo.save(staffbean);
			ResponseBean<StaffBean> res = new ResponseBean<>();
			res.setData(staffbean);
			res.setMsg("staff added...");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

	@GetMapping("/staff")
	public ResponseEntity<?> getAllStaff() {
		List<StaffBean> staff = staffRepo.findAll();
		if (staff.size() != 0) {
			ResponseBean<List<StaffBean>> res = new ResponseBean<>();
			res.setData(staff);
			res.setMsg("staff List...");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			ResponseBean<List<StaffBean>> res = new ResponseBean<>();
			res.setData(staff);
			res.setMsg("staff List is Empty...");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}

	}

	@GetMapping("/staff/{staffId}")
	public ResponseEntity<?> getAllStaff(@PathVariable("staffId") UUID staffId) {
		Optional<StaffBean> staff = staffRepo.findById(staffId);
		if (staff.isPresent()) {
			StaffBean bean = staff.get();
			ResponseBean<StaffBean> res = new ResponseBean<>();
			res.setData(bean);
			res.setMsg("staff List...");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			ResponseBean<StaffBean> res = new ResponseBean<>();
			res.setMsg("staff List is Empty...");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}

	}

	@DeleteMapping("/staff/{staffId}")
	public ResponseEntity<?> deleteStaffById(@PathVariable("staffId") UUID staffId) {
		Optional<StaffBean> staff = staffRepo.findById(staffId);
		if (staff.isPresent()) {
			StaffBean bean = staff.get();
			staffRepo.delete(bean);
			ResponseBean<StaffBean> res = new ResponseBean<>();
			res.setData(bean);
			res.setMsg(bean.getStaffFirstName() + " deleted...");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			ResponseBean<StaffBean> res = new ResponseBean<>();
			res.setMsg("staff List is Empty...");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}

	}

	@PutMapping("/staff")
	public ResponseEntity<?> updateStaff(@RequestBody @Valid StaffBean staffbean, BindingResult result) {
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
			staffRepo.save(staffbean);
			ResponseBean<StaffBean> res = new ResponseBean<>();
			res.setData(staffbean);
			res.setMsg(staffbean.getStaffFirstName() + " Updated...");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
}
