package com.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.PatientDocumentBean;
import com.bean.ResponseBean;
import com.repository.PatientDocRepository;

@RestController
public class PatientDocController {

	@Autowired
	PatientDocRepository patientDocRepo;
	
	@PostMapping("/patientDocument")
	public ResponseEntity<?> addPDoc(@RequestBody PatientDocumentBean patientDocBean){
		ResponseBean<PatientDocumentBean> resp = new ResponseBean<>();
		patientDocRepo.save(patientDocBean);
		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}
	@GetMapping("/patientDocument")
	public ResponseEntity<?> allPDoc(){
		ResponseBean<List<PatientDocumentBean>> resp = new ResponseBean<>();
		List<PatientDocumentBean> pDoc = patientDocRepo.findAll();
		resp.setData(pDoc);
		resp.setMsg("Patient Document List...");
		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}
	
	@GetMapping("/patientDocument")
	public ResponseEntity<?> getPDocByDocId(@PathVariable("pDocId") UUID pDocid){
		ResponseBean<PatientDocumentBean> resp = new ResponseBean<>();
		Optional<PatientDocumentBean> pDoc = patientDocRepo.findById(pDocid);
		resp.setData(pDoc.get());
		resp.setMsg("Patient Document Details...");
		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}

	@GetMapping("/patientDocument")
	public ResponseEntity<?> getPDocByPatientId(@PathVariable("patientId") UUID patientid){
		ResponseBean<List<PatientDocumentBean>> resp = new ResponseBean<>();
		List<PatientDocumentBean> pDoc = patientDocRepo.findByPatientId(patientid);
		resp.setData(pDoc);
		resp.setMsg("Patient Document Details...");
		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}
	
	@DeleteMapping("/patientDocument/{pDocId}")
	public ResponseEntity<?> deletePDoc(@PathVariable("pDocId") UUID pDocid){
		ResponseBean<PatientDocumentBean> resp = new ResponseBean<>();
		Optional<PatientDocumentBean> pDoc = patientDocRepo.findById(pDocid);
		if(pDoc.isPresent()) {
			patientDocRepo.deleteById(pDocid);
			resp.setData(pDoc.get());
			resp.setMsg("Patient Document Deleted...");
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		}else {
			resp.setMsg("Patient Document not Deleted");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
		}
	}

	
	@PutMapping("/patientDocument")
	public ResponseEntity<?> updatePDoc(@RequestBody PatientDocumentBean patientDocBean){
		ResponseBean<PatientDocumentBean> resp = new ResponseBean<>();
		patientDocRepo.save(patientDocBean);
		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}

}
