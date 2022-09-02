package com.bean;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="specialization")
public class SpecializationBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID specializationId;
	@NotBlank(message = "Plese Enter Specialization")
	private String spcialization;
	@JsonIgnore
	@OneToMany(mappedBy ="specialization")
	private Set<DoctorBean> doctor = new HashSet<DoctorBean>();
	public UUID getSpecializationId() {
		return specializationId;
	}
	public void setSpecializationId(UUID specializationId) {
		this.specializationId = specializationId;
	}
	public String getSpcialization() {
		return spcialization;
	}
	public void setSpcialization(String spcialization) {
		this.spcialization = spcialization;
	}
}
