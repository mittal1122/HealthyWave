package com.bean;

import java.util.UUID;

public class DoctorSlotBean {

	UUID doctorId;
	String date;
	public UUID getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(UUID doctorId) {
		this.doctorId = doctorId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
