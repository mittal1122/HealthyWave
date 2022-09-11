package com.bean;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "appointment")
public class AppointmentBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID appointmentId;
	@Column(unique = true)
	private Integer caseNumber;
	@NotBlank(message = "Plese Enter Patient Name ")
	private String patientName;
	@Column(unique = true)
	@NotBlank(message = "Plese Enter Contact ")
	private String contact;
	@Column(unique = true)
	@NotBlank(message = "Plese Enter Email ")
	private String email;
	@NotBlank(message = "Plese Select Gender")
	private String gender;
	@NotNull(message = "Please Select Date of Birth")
	private Date dob;
	@NotBlank(message = "Please Enter Address")
	private String address;
	@ManyToOne
	@JoinColumn(name = "cityId", nullable = false)
	@NotNull(message = "Please Select City")
	private CityBean city;
	
	@ManyToOne
	@JoinColumn(name = "stateId", nullable = false)
	@NotNull(message = "Please Select State")
	private StateBean state;
	@ManyToOne
	@JoinColumn(name = "doctorId",nullable = false)
	@NotNull(message = "Please Select Doctor")
	private DoctorBean doctor;
	
	private String createdAt;
	@NotBlank(message = "Please Enter Reason")
	private String reason;
	private Boolean isApproved;
	@OneToOne
	@JoinColumn(name = "slotId",nullable = false)
	private SlotBean slot;
	
	public UUID getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(UUID appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Integer getCaseNumber() {
		return caseNumber;
	}
	public void setCaseNumber(Integer caseNumber) {
		this.caseNumber = caseNumber;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public CityBean getCity() {
		return city;
	}
	public void setCity(CityBean city) {
		this.city = city;
	}
	public StateBean getState() {
		return state;
	}
	public void setState(StateBean state) {
		this.state = state;
	}
	public DoctorBean getDoctor() {
		return doctor;
	}
	public void setDoctor(DoctorBean doctor) {
		this.doctor = doctor;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Boolean getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}
	public SlotBean getSlot() {
		return slot;
	}
	public void setSlot(SlotBean slot) {
		this.slot = slot;
	}	

}
