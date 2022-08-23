package com.bean;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
@Entity
@Table(name="city")
public class CityBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID cityId;
	@NotBlank(message = "plese enter cityName")
	private String cityName;
	public UUID getCityId() {
		return cityId;
	}
	public void setCityId(UUID cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public StateBean getState() {
		return state;
	}
	public void setState(StateBean state) {
		this.state = state;
	}
	@ManyToOne
	@JoinColumn(name="stateId",nullable=false)
//	@NotNull(message = "Plese Select State")
	private StateBean state;
}
