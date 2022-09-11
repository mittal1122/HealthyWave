package com.bean;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "doctor")
public class DoctorBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID doctorId;
	private Boolean status;

	@OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private UserBean user;

	@ManyToOne
	@JoinColumn(name = "specializationId", nullable = false)
	private SpecializationBean specialization;
	
	@JsonIgnore
	@OneToMany(mappedBy = "doctor")
	private Set<PatientBean> patient;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "ward_doctor", joinColumns = @JoinColumn(name = "doctorId"), inverseJoinColumns = @JoinColumn(name = "wardId"))
	private Set<WardBean> wards;
	
	
	@OneToMany(mappedBy = "doctor")
	private Set<SlotBean> slot;
	
	@Transient 
	private MultipartFile  profile;
	private String profilePath;
	
	
	public MultipartFile getProfile() {
		return profile;
	}

	public void setProfile(MultipartFile profile) {
		this.profile = profile;
	}

	public String getProfilePath() {
		return profilePath;
	}

	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}

	public UUID getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(UUID doctorId) {
		this.doctorId = doctorId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public SpecializationBean getSpecialization() {
		return specialization;
	}

	public void setSpecialization(SpecializationBean specialization) {
		this.specialization = specialization;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}
	
	public Set<PatientBean> getPatient() {
		return patient;
	}

	public void setPatient(Set<PatientBean> patient) {
		this.patient = patient;
	}

	public Set<WardBean> getWards() {
		return wards;
	}

	public void setWards(Set<WardBean> wards) {
		this.wards = wards;
	}
	
	
}
