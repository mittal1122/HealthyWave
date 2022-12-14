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

import com.bean.DoctorBean;
import com.bean.ResponseBean;
import com.bean.SpecializationBean;
import com.bean.StaffBean;
import com.bean.UserBean;
import com.repository.StaffRepository;
import com.repository.UserRepository;

@RestController
public class StaffController {

	@Autowired
	StaffRepository staffRepo;
	
	@Autowired
	UserRepository userRepo;

	@PostMapping("/staff")
	public ResponseEntity<?> addStaff(@RequestBody @Valid StaffBean staffBean, BindingResult result) {
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
			UserBean user = userRepo.findByUserId(staffBean.getUser().getUserId());
			ResponseBean<StaffBean> res = new ResponseBean<>();
			if(user != null && user.getRole().getRoleName().equals("staff") && user.getIsApprove()==true) {
				staffBean.setUser(user);
				staffBean.setStatus(true);
				staffRepo.save(staffBean);				
				res.setData(staffBean);
				res.setMsg("Staff Added...");
				return ResponseEntity.status(HttpStatus.OK).body(res);
			}
			else {
				res.setMsg("Staff not Approved");
				return ResponseEntity.status(HttpStatus.OK).body(res);
			}
		}
	}

	@GetMapping("/staff")
	public ResponseEntity<?> getAllStaff() {
		List<StaffBean> staff = staffRepo.findAll();
		ResponseBean<List<StaffBean>> res = new ResponseBean<>();
		if (staff.size() != 0) {
			res.setData(staff);
			res.setMsg("staff List...");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
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
			res.setMsg(" deleted...");
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
			userRepo.save(staffbean.getUser());
			ResponseBean<StaffBean> res = new ResponseBean<>();
			res.setData(staffbean);
			res.setMsg("Staff Details Updated...");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
}
