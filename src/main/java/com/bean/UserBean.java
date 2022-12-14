package com.bean;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
//@Data
public class UserBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID userId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String password;
	private String contactNum;
	@ManyToOne
	@JoinColumn(name = "roleId", nullable = false)
	private RoleBean role;
	private String gender;
	private Boolean isApprove;
	private String authToken;
	@JsonIgnore
	@OneToOne(mappedBy = "user")
	private DoctorBean doctor;
	@OneToOne(mappedBy = "user")
	private StaffBean staff;
	private Integer otp;

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public RoleBean getRole() {
		return role;
	}

	public void setRole(RoleBean role) {
		this.role = role;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Boolean getIsApprove() {
		return isApprove;
	}

	public void setIsApprove(Boolean isApprove) {
		this.isApprove = isApprove;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public DoctorBean getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorBean doctor) {
		this.doctor = doctor;
	}

	public StaffBean getStaff() {
		return staff;
	}

	public void setStaff(StaffBean staff) {
		this.staff = staff;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}
	
	
}
