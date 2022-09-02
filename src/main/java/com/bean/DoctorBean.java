package com.bean;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "doctor")
public class DoctorBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID doctorId;
	@NotBlank(message = "Plese Enter Name")
	private String doctorName;
	@NotBlank(message = "Plese Enter Email")
	private String doctorUsername;
	@NotBlank(message = "Plese Enter Password")
	private String doctorPass;
	@NotBlank(message = "Plese Enter Contact Number")
	private String doctorContact;
	@ManyToOne
	@JoinColumn(name="specializationId" ,nullable = false)
	private SpecializationBean specialization;
	
	@JsonIgnore
	@OneToMany(mappedBy = "doctor")
	private Set<PatientBean> patient;
	
	@ManyToMany
	@JoinTable(name="ward_doctor",joinColumns = @JoinColumn(name="doctorId"), inverseJoinColumns = @JoinColumn(name="wardId"))
	private Set<WardBean> wards;
	
	public UUID getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(UUID doctorId) {
		this.doctorId = doctorId;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getDoctorUsername() {
		return doctorUsername;
	}
	public void setDoctorUsername(String doctorUsername) {
		this.doctorUsername = doctorUsername;
	}
	public String getDoctorPass() {
		return doctorPass;
	}
	public void setDoctorPass(String doctorPass) {
		this.doctorPass = doctorPass;
	}
	public String getDoctorContact() {
		return doctorContact;
	}
	public void setDoctorContact(String doctorContact) {
		this.doctorContact = doctorContact;
	}
	public SpecializationBean getSpecialization() {
		return specialization;
	}
	public void setSpecialization(SpecializationBean specialization) {
		this.specialization = specialization;
	}
}
