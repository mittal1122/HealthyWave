package com.bean;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="ward")
public class WardBean {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID wardId;
	@NotBlank(message = "Please Enter Ward Name")
	private String wardName;
	@NotNull(message = "Please Enter Ward Price")
	private Float wardPrice;
	
	@ManyToMany(mappedBy = "wards")
	private Set<DoctorBean> doctors;
	
	@OneToMany(mappedBy = "ward")
	private Set<BedBean> beds;
	
	public UUID getWardId() {
		return wardId;
	}
	public void setWardId(UUID wardId) {
		this.wardId = wardId;
	}
	public String getWardName() {
		return wardName;
	}
	public void setWardName(String wardName) {
		this.wardName = wardName;
	}
	public Float getWardPrice() {
		return wardPrice;
	}
	public void setWardPrice(Float wardPrice) {
		this.wardPrice = wardPrice;
	}

}
