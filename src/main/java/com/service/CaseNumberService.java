package com.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.AppointmentBean;
import com.repository.AppointmentRepository;

@Service
public class CaseNumberService {

	@Autowired
	AppointmentRepository appointmentRepo;

	public Integer generateCaseNumber() {

		AppointmentBean dbappointment = null;
		Random random = new Random();
		Integer cNum = 0;
		do {
			cNum = Integer.parseInt(String.format("%04d", random.nextInt(10000)));

			dbappointment = appointmentRepo.findByCaseNumber(cNum);
		} while (dbappointment != null);
		System.out.println(cNum + " : caseNumber ");
		return cNum;
	}
}
