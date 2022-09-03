package com.bean;

import java.util.List;
import java.util.UUID;

public class PatientSymptomBean {

	private UUID patientId;
	private List<UUID> symptom;
	public UUID getPatientId() {
		return patientId;
	}
	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
	}
	public List<UUID> getSymptom() {
		return symptom;
	}
	public void setSymptom(List<UUID> symptom) {
		this.symptom = symptom;
	}
	
	
}
