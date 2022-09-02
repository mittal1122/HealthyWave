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

@Entity
@Table(name = "doctor")
public class DoctorBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID doctorId;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private UserBean user;

	@ManyToOne
	@JoinColumn(name = "specializationId", nullable = false)
	private SpecializationBean specialization;

	@ManyToMany
	@JoinTable(name = "ward_doctor", joinColumns = @JoinColumn(name = "doctorId"), inverseJoinColumns = @JoinColumn(name = "wardId"))
	private Set<WardBean> wards;

	public UUID getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(UUID doctorId) {
		this.doctorId = doctorId;
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

	public Set<WardBean> getWards() {
		return wards;
	}

	public void setWards(Set<WardBean> wards) {
		this.wards = wards;
	}
	
}
