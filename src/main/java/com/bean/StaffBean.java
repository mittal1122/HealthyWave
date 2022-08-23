package com.bean;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.NotBlank;

import net.bytebuddy.implementation.bind.annotation.Default;

@Entity
@Table(name="staff")
public class StaffBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID staffId;
	@NotBlank(message = "Plese Enter First Name ")
	private String staffFirstName;
	@NotBlank(message = "Plese Enter Middle Name ")
	private String staffMiddlename;
	@NotBlank(message = "Plese Enter Last Name ")
	private String staffLastname;
	@Column(unique = true)
	@NotBlank(message = "Plese Enter Email ")
	private String staffUsername;
	@NotBlank(message = "Plese Enter password ")
	private String staffPassword;
	@NotBlank(message = "Plese Enter Gender ")
	private String gender;
	
	@NotBlank(message = "Plese Enter Contact Number ")
	private String staffContactNumber;
//	@ColumnDefault(value = "false")
	private Boolean status = false;
	public UUID getStaffId() {
		return staffId;
	}
	public void setStaffId(UUID staffId) {
		this.staffId = staffId;
	}
	public String getStaffFirstName() {
		return staffFirstName;
	}
	public void setStaffFirstName(String staffFirstName) {
		this.staffFirstName = staffFirstName;
	}
	public String getStaffMiddlename() {
		return staffMiddlename;
	}
	public void setStaffMiddlename(String staffMiddlename) {
		this.staffMiddlename = staffMiddlename;
	}
	public String getStaffLastname() {
		return staffLastname;
	}
	public void setStaffLastname(String staffLastname) {
		this.staffLastname = staffLastname;
	}
	public String getStaffUsername() {
		return staffUsername;
	}
	public void setStaffUsername(String staffUsername) {
		this.staffUsername = staffUsername;
	}
	public String getStaffPassword() {
		return staffPassword;
	}
	public void setStaffPassword(String staffPassword) {
		this.staffPassword = staffPassword;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getStaffContactNumber() {
		return staffContactNumber;
	}
	public void setStaffContactNumber(String staffContactNumber) {
		this.staffContactNumber = staffContactNumber;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	
}
