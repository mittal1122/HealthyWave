package com.bean;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="staff")
public class StaffBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID staffId;
	@OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userId", nullable = false)
	private UserBean user;
	private Boolean status;
	
	public UUID getStaffId() {
		return staffId;
	}
	public void setStaffId(UUID staffId) {
		this.staffId = staffId;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	
}
