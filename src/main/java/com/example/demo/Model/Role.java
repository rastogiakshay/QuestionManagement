package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role_master")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int role_id;

	@Enumerated(EnumType.STRING)
	private EAuthRoles name;

	public Role() {
	}

	public Role(EAuthRoles name) {

		this.name = name;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public EAuthRoles getName() {
		return name;
	}

	public void setName(EAuthRoles name) {
		this.name = name;
	}

}
