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
import com.bean.SpecializationBean;
import com.repository.SpecializationRepository;

@RestController
public class SpecializationController {

	@Autowired
	SpecializationRepository specializationRepo;

	@PostMapping("/specialization")
	public ResponseEntity<?> addSpecialization(@RequestBody @Valid SpecializationBean bean, BindingResult result) {
		if (result.hasErrors()) {
			ResponseBean<List<String>> res = new ResponseBean<>();
			List<String> error = new ArrayList<>();
			System.out.println("SpecializationError Count : " + result.getErrorCount());
			for (int i = 0; i < result.getErrorCount(); i++) {
				String addError = result.getFieldErrors().get(i).getDefaultMessage();
				System.out.println("error : " + result.getFieldErrors().get(i).getDefaultMessage());
				error.add(addError);
			}
			res.setData(error);
			res.setMsg("Detail Fill Properly");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			specializationRepo.save(bean);
			ResponseBean<SpecializationBean> res = new ResponseBean<>();
			res.setData(bean);
			res.setMsg("Specialization Add Successfully");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
		}
	}

	@GetMapping("/specialization")
	public ResponseEntity<?> listAllSpecialization() {

		List<SpecializationBean> bean = specializationRepo.findAll();
		if (bean.size() != 0) {
			ResponseBean<List<SpecializationBean>> res = new ResponseBean<>();
			res.setData(bean);
			res.setMsg("specialization get Successfuly");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
		} else {
			ResponseBean<List<SpecializationBean>> res = new ResponseBean<>();
			res.setData(bean);
			res.setMsg("specialization List Empty");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		}

	}

	@DeleteMapping("/specialization/{specializationId}")
	public ResponseEntity<?> deleteSpecializationById(@PathVariable("specializationId") UUID stateId) {

		Optional<SpecializationBean> bean = specializationRepo.findById(stateId);
		if (bean.isPresent()) {
			specializationRepo.deleteById(stateId);
			ResponseBean<Optional<SpecializationBean>> res = new ResponseBean<>();
			res.setData(bean);
			res.setMsg("specialization Deleted Successfuly");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
		} else {
			ResponseBean<UUID> res = new ResponseBean<>();
			res.setData(stateId);
			res.setMsg("State is not Present");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		}

	}

	@PutMapping("/specialization")
	public ResponseEntity<?> updateSpecializationById(@RequestBody @Valid SpecializationBean specialbean,
			BindingResult result) {

		Optional<SpecializationBean> bean = specializationRepo.findById(specialbean.getSpecializationId());
		if (bean.isPresent()) {
			if (result.hasErrors()) {
				ResponseBean<List<String>> res = new ResponseBean<>();
				List<String> error = new ArrayList<>();
				System.out.println("SpecializationError Count : " + result.getErrorCount());
				for (int i = 0; i < result.getErrorCount(); i++) {
					String addError = result.getFieldErrors().get(i).getDefaultMessage();
					System.out.println("error : " + result.getFieldErrors().get(i).getDefaultMessage());
					error.add(addError);
				}
				res.setData(error);
				res.setMsg("Detail Fill Properly");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
			} else {
				specializationRepo.save(specialbean);
				ResponseBean<SpecializationBean> res = new ResponseBean<>();
				res.setData(specialbean);
				res.setMsg("specialization Update Successfuly");
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
			}
		} else {
			ResponseBean<UUID> res = new ResponseBean<>();
			res.setData(specialbean.getSpecializationId());
			res.setMsg("specialization is not Present");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		}

	}
}
