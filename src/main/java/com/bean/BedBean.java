package com.bean;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bed")
public class BedBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID bedId;
	@NotBlank(message = "Please Enter Bed Number")
	private String bedNumber;
//	@NotBlank(message = "Please Select Bed Status")
	private Boolean occupied;
	
	@ManyToOne
	@JoinColumn(name = "wardId", nullable = false)
	private WardBean ward;
	
	@JsonIgnore
	@OneToOne(mappedBy = "bed")
	private PatientBean patient;
	
	public UUID getBedId() {
		return bedId;
	}
	public void setBedId(UUID bedId) {
		this.bedId = bedId;
	}
	public String getBedNumber() {
		return bedNumber;
	}
	public void setBedNumber(String bedNumber) {
		this.bedNumber = bedNumber;
	}
	public Boolean getOccupied() {
		return occupied;
	}
	public void setOccupied(Boolean occupied) {
		this.occupied = occupied;
	}
	public WardBean getWard() {
		return ward;
	}
	public void setWard(WardBean ward) {
		this.ward = ward;
	}
	public PatientBean getPatient() {
		return patient;
	}
	public void setPatient(PatientBean patient) {
		this.patient = patient;
	}	
	
}
