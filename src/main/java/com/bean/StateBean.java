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
@Table(name="state")
public class StateBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID stateId;
	@NotBlank(message = "Plese Enter state Name")
	private String stateName;
	@JsonIgnore
	@OneToMany(mappedBy = "state")
	private Set<CityBean> city = new HashSet<CityBean>();
	
	
	public UUID getStateId() {
		return stateId;
	}
	public void setStateId(UUID stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Set<CityBean> getCity() {
		return city;
	}
	public void setCity(Set<CityBean> city) {
		this.city = city;
	}
	
	
}
