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
import com.bean.WardBean;
import com.repository.wardRepository;

@RestController
public class WardController {

	@Autowired
	wardRepository wardRepo;
	
	@PostMapping("/ward")
	public ResponseEntity<?> addBed(@RequestBody @Valid WardBean wardBean,BindingResult result){
		if(result.hasErrors()) {
			ResponseBean<List<String>> resp = new ResponseBean<>();
			List<String> errors = new ArrayList<>();
			for(int i=0;i<result.getErrorCount();i++) {
				String error = result.getFieldErrors().get(i).getDefaultMessage();
				System.out.println("============== Error ============== " + error);
				errors.add(error);
			}
			resp.setData(errors);
			resp.setMsg("Fill the Details Properly");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
			
		}else {
			wardRepo.save(wardBean);
			ResponseBean<WardBean> resp = new ResponseBean<>();
			resp.setData(wardBean);
			resp.setMsg("Ward Added...");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}
	}
	
	@GetMapping("/ward")
	public ResponseEntity<?> getAllWards(){
		List<WardBean> wards = wardRepo.findAll();
		ResponseBean<List<WardBean>> resp = new ResponseBean<>();
		if(wards.size() != 0) {
			resp.setData(wards);
			resp.setMsg("Ward List...");
			
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}else {
			resp.setMsg("Ward List is Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
		}
	}
	
	@DeleteMapping("/ward/{wardId}")
	public ResponseEntity<?> deleteWard(@PathVariable("wardId") UUID wardid){
		Optional<WardBean> ward = wardRepo.findById(wardid);
		ResponseBean<WardBean> resp = new ResponseBean<>();
		if(ward.isPresent()) {
			wardRepo.deleteById(wardid);
			resp.setData(ward.get());
			resp.setMsg(ward.get().getWardName() + " Ward Deleted");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}else {
			resp.setMsg("No Ward Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
		}
	}
	
	@GetMapping("/ward/{wardId}")
	public ResponseEntity<?> getAppointmentById(@PathVariable("wardId") UUID wardid){
		Optional<WardBean> bed = wardRepo.findById(wardid);
		ResponseBean<WardBean> resp = new ResponseBean<>();
		
		if(bed.isPresent()) {
			resp.setData(bed.get());
			resp.setMsg("Ward Details...");
			return ResponseEntity.status(HttpStatus.OK).body(resp);	
		}else {
			resp.setMsg("Ward Not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
		}
	}
	
	@PutMapping("/ward")
	public ResponseEntity<?> updateWard(@RequestBody @Valid WardBean wardBean,BindingResult result){
		ResponseBean<WardBean> resp = new ResponseBean<>();
		if (result.hasErrors()) {
			ResponseBean<List<String>> res = new ResponseBean<>();
			List<String> errors = new ArrayList<>();
			for (int i = 0; i < result.getErrorCount(); i++) {
				String error = result.getFieldErrors().get(i).getDefaultMessage();
				System.out.println("============== Error ============== " + error);
				errors.add(error);
			}
			res.setData(errors);
			res.setMsg("Detail Fill Properly");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			wardRepo.save(wardBean);
			resp.setData(wardBean);
			resp.setMsg(wardBean.getWardName() + " Ward Details Updated");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}
	}
}
