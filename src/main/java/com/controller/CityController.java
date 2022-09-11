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

import com.bean.CityBean;
import com.bean.ResponseBean;
import com.bean.StateBean;
import com.repository.CityRepository;
import com.repository.CityView;
import com.repository.StateRepository;

@RestController
public class CityController {

	@Autowired
	CityRepository cityRepo;

	@Autowired
	StateRepository stateRepo;

	@PostMapping("/public/city")
	public ResponseEntity<?> addCity(@RequestBody @Valid CityBean cityBean, BindingResult result) {
		if (result.hasErrors()) {
			ResponseBean<List<String>> res = new ResponseBean<>();
			List<String> error = new ArrayList<>();
			System.out.println("CityError Count : " + result.getErrorCount());
			for (int i = 0; i < result.getErrorCount(); i++) {
				String addError = result.getFieldErrors().get(i).getDefaultMessage();
				System.out.println("error : " + result.getFieldErrors().get(i).getDefaultMessage());
				error.add(addError);
			}
			res.setData(error);
			res.setMsg("Detail Fill Properly");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			StateBean stateBean = stateRepo.findByStateId(cityBean.getState().getStateId());
			cityBean.setState(stateBean);
			cityRepo.save(cityBean);
			ResponseBean<CityBean> res = new ResponseBean<>();
			res.setData(cityBean);
			res.setMsg("City Add SuccesFully");
			return ResponseEntity.status(HttpStatus.OK).body(res);

		}
	}

	@GetMapping("/public/city")
	public ResponseEntity<?> getAllCity() {

		List<CityBean> city = cityRepo.findAll();
		ResponseBean<List<CityBean>> res = new ResponseBean<>();
		res.setData(city);
		res.setMsg("City Get SuccesFully");
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

	 
	@GetMapping("/cityname/{stateId}")
	public ResponseEntity<?> getAllCityName(@PathVariable("stateId") UUID stateId) {

		List<CityView> city = cityRepo.findByState(stateId);
		ResponseBean<List<CityView>> res = new ResponseBean<>();
		res.setData(city);
		res.setMsg("City Get SuccesFully");
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

	@GetMapping("/public/city/{stateId}")
	public ResponseEntity<?> getCityById(@PathVariable("stateId") UUID stateId) {
		ResponseBean<List<CityBean>> res = new ResponseBean<>();
		try {

			List<CityBean> city = cityRepo.findAllById(stateId);
			res.setData(city);
			res.setMsg("City Get From " + city.get(0).getState().getStateName() + " SuccesFully");
		} catch (IndexOutOfBoundsException e) {
			res.setMsg("City is Not present");
		}
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

	@DeleteMapping("/city/{cityId}")
	public ResponseEntity<?> deleteCityById(@PathVariable("cityId") UUID cityId) {
		Optional<CityBean> city = cityRepo.findById(cityId);
		ResponseBean<Optional<CityBean>> res = new ResponseBean<>();
		if (city.isPresent()) {
			cityRepo.deleteById(cityId);
			CityBean bean = city.get();
			res.setData(city);
			res.setMsg(bean.getCityName() + " deleted From " + bean.getState().getStateName() + " SuccesFully");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			res.setMsg("City is not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
		}

	}

	@PutMapping("/city")
	public ResponseEntity<?> getCityById(@RequestBody @Valid CityBean citybean, BindingResult result) {
		Optional<CityBean> city = cityRepo.findById(citybean.getCityId());
		if (city.isPresent()) {
			if (result.hasErrors()) {
				ResponseBean<List<String>> res = new ResponseBean<>();
				List<String> error = new ArrayList<>();
				System.out.println("CityError Count : " + result.getErrorCount());
				for (int i = 0; i < result.getErrorCount(); i++) {
					String addError = result.getFieldErrors().get(i).getDefaultMessage();
					System.out.println("error : " + result.getFieldErrors().get(i).getDefaultMessage());
					error.add(addError);
				}
				res.setData(error);
				res.setMsg("Detail Fill Properly");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
			} else {
				StateBean stateBean = stateRepo.findByStateId(citybean.getState().getStateId());
				citybean.setState(stateBean);
				cityRepo.save(citybean);
				CityBean bean = city.get();
				ResponseBean<CityBean> res = new ResponseBean<>();
				res.setData(bean);
				res.setMsg("update " + bean.getCityName() + " From " + bean.getState().getStateName() + " SuccesFully");
				return ResponseEntity.status(HttpStatus.OK).body(res);
			}
		}
		ResponseBean<CityBean> res = new ResponseBean<>();
		res.setData(citybean);
		res.setMsg("SMW");
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(res);
	}
}
