package com.bean;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="slot")
public class SlotBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID slotId;
	private String slot;
	private String date;
	@ManyToOne
	@JoinColumn(name = "doctorId",nullable = false)
	private DoctorBean doctor;
	public UUID getSlotId() {
		return slotId;
	}
	public void setSlotId(UUID slotId) {
		this.slotId = slotId;
	}
	public String getSlot() {
		return slot;
	}
	public void setSlot(String slot) {
		this.slot = slot;
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
	
	
}
