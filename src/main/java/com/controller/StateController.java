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
import com.bean.StateBean;
import com.repository.StateRepository;

@RestController
public class StateController {

	@Autowired
	StateRepository stateRepo;

	@PostMapping("/state")
	public ResponseEntity<?> addState(@RequestBody @Valid StateBean bean, BindingResult result) {
		if (result.hasErrors()) {
			ResponseBean<List<String>> res = new ResponseBean<>();
			List<String> error = new ArrayList<>();
			System.out.println("count : " + result.getErrorCount());
			for (int i = 0; i < result.getErrorCount(); i++) {
				String addError = result.getFieldErrors().get(i).getDefaultMessage();
				System.out.println("error : " + result.getFieldErrors().get(i).getDefaultMessage());
				error.add(addError);
			}
			res.setData(error);
			res.setMsg("Detail Fill Properly");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			stateRepo.save(bean);
			ResponseBean<StateBean> res = new ResponseBean<>();
			res.setData(bean);
			res.setMsg("State Add Successfully");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
		}

	}

	@GetMapping("/state")
	public ResponseEntity<?> listAllState() {

		List<StateBean> bean = stateRepo.findAll();
		ResponseBean<List<StateBean>> res = new ResponseBean<>();
		res.setData(bean);
		res.setMsg("States get Successfuly");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
	}

	@DeleteMapping("/state/{stateId}")
	public ResponseEntity<?> deleteStateById(@PathVariable("stateId") UUID stateId) {

		Optional<StateBean> bean = stateRepo.findById(stateId);
		if (bean.isPresent()) {
			stateRepo.deleteById(stateId);
			ResponseBean<Optional<StateBean>> res = new ResponseBean<>();
			res.setData(bean);
			res.setMsg("State Deleted Successfuly");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
		} else {
			ResponseBean<UUID> res = new ResponseBean<>();
			res.setData(stateId);
			res.setMsg("State is not Present");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		}

	}

	@PutMapping("/state")
	public ResponseEntity<?> updateStateById(@RequestBody @Valid StateBean statebean, BindingResult result) {

		Optional<StateBean> bean = stateRepo.findById(statebean.getStateId());
		if (bean.isPresent()) {

			if (result.hasErrors()) {
				ResponseBean<List<String>> res = new ResponseBean<>();
				List<String> error = new ArrayList<>();
				System.out.println("count : " + result.getErrorCount());
				for (int i = 0; i < result.getErrorCount(); i++) {
					String addError = result.getFieldErrors().get(i).getDefaultMessage();
					System.out.println("error : " + result.getFieldErrors().get(i).getDefaultMessage());
					error.add(addError);
				}
				res.setData(error);
				res.setMsg("Detail Fill Properly");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
			} else {

				stateRepo.save(statebean);
				ResponseBean<StateBean> res = new ResponseBean<>();
				res.setData(statebean);
				res.setMsg("State Update Successfuly");
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);

			}
		} else {
			ResponseBean<UUID> res = new ResponseBean<>();
			res.setData(statebean.getStateId());
			res.setMsg("State is not Present");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		}

	}
}
