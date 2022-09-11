package com.bean;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="slot")
public class SlotBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID slotId;
	private String time;
	private String date;
	@ManyToOne
	@JoinColumn(name = "doctorId",nullable = false)
	private DoctorBean doctor;
	
	@OneToOne(mappedBy = "slot")
	private AppointmentBean appointment;
	
	public UUID getSlotId() {
		return slotId;
	}
	public void setSlotId(UUID slotId) {
		this.slotId = slotId;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public DoctorBean getDoctor() {
		return doctor;
	}
	public void setDoctor(DoctorBean doctor) {
		this.doctor = doctor;
	}
	public AppointmentBean getAppointment() {
		return appointment;
	}
	public void setAppointment(AppointmentBean appointment) {
		this.appointment = appointment;
	}
	
	
}
