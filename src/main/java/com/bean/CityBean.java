package com.bean;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="city")
public class CityBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID cityId;
	@NotBlank(message = "plese enter cityName")
	private String cityName;
	@ManyToOne
	@JoinColumn(name="stateId",nullable=false)
//	@NotNull(message = "Please Select State")
	private StateBean state;
	
	@JsonIgnore
	@OneToMany(mappedBy = "city")
	private Set<PatientBean> patient;
	
	@JsonIgnore
	@OneToMany(mappedBy = "city")
	private Set<AppointmentBean> appointment;

	public UUID getCityId() {
		return cityId;
	}
	public void setCityId(UUID cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public StateBean getState() {
		return state;
	}
	public void setState(StateBean state) {
		this.state = state;
	}
	public Set<PatientBean> getPatient() {
		return patient;
	}
	public void setPatient(Set<PatientBean> patient) {
		this.patient = patient;
	}
	public Set<AppointmentBean> getAppointment() {
		return appointment;
	}
	public void setAppointment(Set<AppointmentBean> appointment) {
		this.appointment = appointment;
	}
	
}
