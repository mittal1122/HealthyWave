package com.bean;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "roles")
public class RoleBean {

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO )
	private UUID roleId;
	@Column(length = 15,nullable = false,unique = true)
	private String roleName;
	
	@JsonIgnore
	@OneToMany(mappedBy = "role")
	private Set<UserBean> users;
	
	public String getRoleName() {
		return roleName;
	}
	public UUID getRoleId() {
		return roleId;
	}
	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
