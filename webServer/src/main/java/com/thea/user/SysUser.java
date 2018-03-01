package com.thea.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "password")
public class SysUser {

	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "First name is required.")
	private String username;

	@NotEmpty(message = "Password is required.")
	private @JsonIgnore String password;
	private String[] roleArr;

	public SysUser() {
	}

	public SysUser(String name, String password, String... roles) {
		this.username = name;
		this.setPassword(password);
		this.roleArr = roles;
	}

	public void setPassword(String password) {
		this.password = PASSWORD_ENCODER.encode(password);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String[] getRoleArr() {
		return roleArr;
	}

	public void setRoleArr(String[] roleArr) {
		this.roleArr = roleArr;
	}

	public String getPassword() {
		return password;
	}
	
}
