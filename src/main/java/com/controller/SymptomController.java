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
import com.bean.SymptomBean;
import com.repository.SymptomRepository;

@RestController
public class SymptomController {

	@Autowired
	SymptomRepository symptomRepo;
	
	@PostMapping("/symptom")
	public ResponseEntity<?> addSymptom(@RequestBody @Valid SymptomBean symptomBean,BindingResult result){
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
			symptomRepo.save(symptomBean);
			ResponseBean<SymptomBean> resp = new ResponseBean<>();
			resp.setData(symptomBean);
			resp.setMsg("Symptom Added...");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}
	}
	
	@GetMapping("/symptom")
	public ResponseEntity<?> getAllSymptoms(){
		List<SymptomBean> symptoms = symptomRepo.findAll();
		ResponseBean<List<SymptomBean>> resp = new ResponseBean<>();
		if(symptoms.size() != 0) {
			resp.setData(symptoms);
			resp.setMsg("Symptom List...");
			
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}else {
			resp.setMsg("Symptom List is Empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
		}
	}
	
	@DeleteMapping("/symptom/{symptomId}")
	public ResponseEntity<?> deleteSymptom(@PathVariable("symptomId") UUID symptomid){
		Optional<SymptomBean> symptom = symptomRepo.findById(symptomid);
		ResponseBean<SymptomBean> resp = new ResponseBean<>();
		if(symptom.isPresent()) {
			symptomRepo.deleteById(symptomid);
			resp.setData(symptom.get());
			resp.setMsg(symptom.get().getSymptom() + " Symptom Deleted");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}else {
			resp.setMsg("No Symptom Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
		}
	}
	
	@GetMapping("/symptom/{symptomId}")
	public ResponseEntity<?> getSymptomById(@PathVariable("symptomId") UUID symptomid){
		Optional<SymptomBean> symptom = symptomRepo.findById(symptomid);
		ResponseBean<SymptomBean> resp = new ResponseBean<>();
		
		if(symptom.isPresent()) {
			resp.setData(symptom.get());
			resp.setMsg("Symptom Details...");
			return ResponseEntity.status(HttpStatus.OK).body(resp);	
		}else {
			resp.setMsg("Symptom Not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
		}
	}
	
	@PutMapping("/symptom")
	public ResponseEntity<?> updateSymptom(@RequestBody @Valid SymptomBean symptomBean,BindingResult result){
		ResponseBean<SymptomBean> resp = new ResponseBean<>();
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
			symptomRepo.save(symptomBean);
			resp.setData(symptomBean);
			resp.setMsg(symptomBean.getSymptom() + " Symptom Updated");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}
	}
}
