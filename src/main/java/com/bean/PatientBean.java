package com.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "patient")
public class PatientBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID patientId;
	private Integer caseNumber;
	@NotBlank(message = "Please Enter Patient Name")
	private String patientName;	
	@NotBlank(message = "Please Enter Contact")
	private String contact;
	@NotBlank(message = "Please Enter Email")
	private String email;
	@NotBlank(message = "Please Enter Relative Name")
	private String patientRelativeName;
	@NotBlank(message = "Please Enter Relative Contact Number")
	private String patientRelativeContact;
	@NotBlank(message = "Please Select Gender")
	private String gender;
	@NotNull(message = "Please Select Date of Birth")
	private Date dob;
	@NotBlank(message = "Please Select Date & Time")
	private String dateTime;
	@NotBlank(message = "Please Enter Address")
	private String line1;
	private String line2;
	@Column(length = 6)
	@NotNull(message = "Please Enter Pincode")
	private Integer pincode;
	
	@ManyToOne
	@JoinColumn(name = "cityId", nullable = false)
	@NotNull(message = "Please Select City")
	private CityBean city;
	
	@ManyToOne
	@JoinColumn(name = "stateId", nullable = false)
	@NotNull(message = "Please Select State")
	private StateBean state;
	
	@OneToOne
	@JoinColumn(name = "bedId",nullable = false)
	@NotNull(message = "Please Select Bed")
	private BedBean bed;
	
	@ManyToOne
	@JoinColumn(name = "doctorId",nullable = false)
	@NotNull(message = "Please Select Doctor")
	private DoctorBean doctor;
	
	@NotBlank(message = "Please Enter Previous Medical History")
	private String previousHistory;
	@NotBlank(message = "Please Select Doctor Visiting Date & Time")
	private String doctorVisitTime;
	@NotBlank(message = "Please Select Patient Status")
	private String patientStatus;
	
//	@JsonIgnore
	@OneToMany(mappedBy = "patient")
	private Set<PatientDocumentBean> patientDocument;
	
	@ManyToMany
	@JoinTable(name = "patient_symptom",joinColumns = @JoinColumn(name="patientId"),inverseJoinColumns = @JoinColumn(name = "symptomId"))
	@NotNull(message = "Please Enter atleast one Syptom")
	private List<SymptomBean> symptoms = new ArrayList<>();

	public UUID getPatientId() {
		return patientId;
	}

	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public Integer getPincode() {
		return pincode;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
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

	public BedBean getBed() {
		return bed;
	}

	public void setBed(BedBean bed) {
		this.bed = bed;
	}

	public DoctorBean getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorBean doctor) {
		this.doctor = doctor;
	}

	public String getPreviousHistory() {
		return previousHistory;
	}

	public void setPreviousHistory(String previousHistory) {
		this.previousHistory = previousHistory;
	}

	public String getDoctorVisitTime() {
		return doctorVisitTime;
	}

	public void setDoctorVisitTime(String doctorVisitTime) {
		this.doctorVisitTime = doctorVisitTime;
	}

	public String getPatientStatus() {
		return patientStatus;
	}

	public void setPatientStatus(String patientStatus) {
		this.patientStatus = patientStatus;
	}

	public Set<PatientDocumentBean> getPatientDocument() {
		return patientDocument;
	}

	public void setPatientDocument(Set<PatientDocumentBean> patientDocument) {
		this.patientDocument = patientDocument;
	}

	public List<SymptomBean> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(List<SymptomBean> symptoms) {
		this.symptoms = symptoms;
	}	
}
