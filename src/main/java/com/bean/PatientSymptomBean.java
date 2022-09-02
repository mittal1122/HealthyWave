package com.bean;

import java.util.List;
import java.util.UUID;

public class PatientSymptomBean {

	private UUID patientId;
	private List<UUID> symptomId;
	public UUID getPatientId() {
		return patientId;
	}
	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
	}
	public List<UUID> getSymptomId() {
		return symptomId;
	}
	public void setSymptomId(List<UUID> symptomId) {
		this.symptomId = symptomId;
	}
	
}
