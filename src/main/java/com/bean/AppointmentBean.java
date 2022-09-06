package com.bean;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	@NotBlank(message = "Plese Enter Patient Relative Name ")
	private String patientRelativeName;
	@NotBlank(message = "Plese Enter Patient Relative Contact")
	private String patientRelativeContact;
	@NotBlank(message = "Plese Select Gender")
	private String gender;
	private String dateTime;
	@NotBlank(message = "Plese Enter Reason")
	private String reason;

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

	public String getPatientRelativeName() {
		return patientRelativeName;
	}
	public void setPatientRelativeName(String patientRelativeName) {
		this.patientRelativeName = patientRelativeName;
	}

	public String getPatientRelativeContact() {
		return patientRelativeContact;
	}
	public void setPatientRelativeContact(String patientRelativeContact) {
		this.patientRelativeContact = patientRelativeContact;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

}
