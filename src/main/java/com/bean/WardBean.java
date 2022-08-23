package com.bean;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="ward")
public class WardBean {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID wardId;
	private String wardName;
	private String wardPrice;
	
	@ManyToMany(mappedBy = "wards")
	private Set<DoctorBean> doctors;
	
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
	public String getWardPrice() {
		return wardPrice;
	}
	public void setWardPrice(String wardPrice) {
		this.wardPrice = wardPrice;
	}

}
