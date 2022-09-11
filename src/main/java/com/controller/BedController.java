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

import com.bean.BedBean;
import com.bean.ResponseBean;
import com.bean.WardBean;
import com.repository.BedRepository;
import com.repository.WardRepository;

@RestController
public class BedController {

	
	@Autowired
	BedRepository bedRepo;
	
	@Autowired 
	WardRepository wardRepo;
	
	@PostMapping("/bed")
	public ResponseEntity<?> addBed(@RequestBody @Valid BedBean bedBean,BindingResult result){
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
			WardBean ward = wardRepo.findByWardId(bedBean.getWard().getWardId());
			ResponseBean<BedBean> resp = new ResponseBean<>();
			if(ward != null) {
				bedBean.setWard(ward);
				bedRepo.save(bedBean);
				resp.setData(bedBean);
				resp.setMsg("Bed Added...");
				return ResponseEntity.status(HttpStatus.OK).body(resp);
			}else {
				resp.setMsg("Ward Not Found...");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);

			}
		}
	}
	
	@GetMapping("/staff/bed")
	public ResponseEntity<?> getAllBeds(){
		List<BedBean> beds = bedRepo.findAll();
		ResponseBean<List<BedBean>> resp = new ResponseBean<>();
		if(beds.size() != 0) {
			resp.setData(beds);
			resp.setMsg("Bed List...");
			
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}else {
			resp.setData(beds);
			resp.setMsg("Bed List is Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
		}
	}
	
	@DeleteMapping("/bed/{bedId}")
	public ResponseEntity<?> deleteBed(@PathVariable("bedId") UUID bedid){
		Optional<BedBean> bed = bedRepo.findById(bedid);
		ResponseBean<BedBean> resp = new ResponseBean<>();
		if(bed.isPresent()) {
			bedRepo.deleteById(bedid);
			resp.setData(bed.get());
			resp.setMsg(bed.get().getBedNumber() + " Bed Deleted");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}else {
			resp.setMsg("Bed Not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
		}
	}	
	
	@GetMapping("/bed/{bedId}")
	public ResponseEntity<?> getBedById(@PathVariable("bedId") UUID bedid){
		Optional<BedBean> bed = bedRepo.findById(bedid);
		ResponseBean<BedBean> resp = new ResponseBean<>();
		
		if(bed.isPresent()) {
			resp.setData(bed.get());
			resp.setMsg("Bed Details...");
			return ResponseEntity.status(HttpStatus.OK).body(resp);	
		}else {
			resp.setMsg("Bed Not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
		}
	}
	
	@PutMapping("/bed")
	public ResponseEntity<?> updateAppointment(@RequestBody @Valid BedBean bedBean,BindingResult result){
		ResponseBean<BedBean> resp = new ResponseBean<>();
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
			WardBean ward = wardRepo.findByWardId(bedBean.getWard().getWardId());
			bedBean.setWard(ward);
			bedRepo.save(bedBean);
			resp.setData(bedBean);
			resp.setMsg("Bed number " + bedBean.getBedNumber() + " Details Updated");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}
	}

}

