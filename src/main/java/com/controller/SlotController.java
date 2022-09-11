package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.CityBean;
import com.bean.DoctorBean;
import com.bean.DoctorSlotBean;
import com.bean.ResponseBean;
import com.bean.SlotBean;
import com.bean.StateBean;
import com.repository.DoctorRepository;
import com.repository.SlotRepository;

@RestController
public class SlotController {

	@Autowired
	SlotRepository slotRepo;
	
	@Autowired
	DoctorRepository doctorRepo;
	
	@PostMapping("/public/slot")
	public ResponseEntity<?> addSlot(@RequestBody SlotBean bean) {
	
			DoctorBean doctorBean = doctorRepo.findById(bean.getDoctor().getDoctorId()).get();
			bean.setDoctor(doctorBean);
			slotRepo.save(bean);
			ResponseBean<SlotBean> res = new ResponseBean<>();
			res.setData(bean);
			res.setMsg("slot Add SuccesFully");
			return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	@GetMapping("/public/slot")
	public ResponseEntity<?> getDoctorById(@RequestBody DoctorSlotBean doctorSlotBean) {
		ResponseBean<List<SlotBean>> res = new ResponseBean<>();
		try {

			List<SlotBean> slots = slotRepo.findByDoctorIdDate(doctorSlotBean.getDoctorId() ,doctorSlotBean.getDate());
			res.setData(slots);
			res.setMsg("slot get SuccesFully");
		} catch (IndexOutOfBoundsException e) {
			res.setMsg("slot is Not present");
		}
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
}
