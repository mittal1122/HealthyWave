package com.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "symptom")
public class SymptomBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID symptomId;
	@NotNull(message = "Please Enter Symptom Name")
	private String symptom;
//	@NotBlank(message = "Please Select Status")
	private Boolean isActive;
	
	@ManyToMany(mappedBy = "symptoms")
	private List<PatientBean> patient = new ArrayList<>();
	
	public UUID getSymptomId() {
		return symptomId;
	}
	public void setSymptomId(UUID symptomId) {
		this.symptomId = symptomId;
	}
	public String getSymptom() {
		return symptom;
	}
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
}
