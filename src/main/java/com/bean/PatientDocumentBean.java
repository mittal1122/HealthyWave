package com.bean;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "patientdocument")
public class PatientDocumentBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID documentId;
	private String document;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "patientId",nullable = false)
	private PatientBean patient;
	
	
	
	public UUID getDocumentId() {
		return documentId;
	}
	public void setDocumentId(UUID documentId) {
		this.documentId = documentId;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public PatientBean getPatientId() {
		return patient;
	}
	public void setPatientId(PatientBean patientId) {
		this.patient = patientId;
	}	
	public PatientBean getPatient() {
		return patient;
	}
	public void setPatient(PatientBean patient) {
		this.patient = patient;
	}
}
