package com.spice.smsotpms.entity;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user", uniqueConstraints = {
		@UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email"),
})
@Getter @Setter
public class User {
	
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private UUID id;
	
	@Id
	@Column(name = "username")
	private String username;
	
	@Column(name = "active")
	private int active;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "mobile_no")
	private String moblieno;

	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES", 
    joinColumns = { @JoinColumn(referencedColumnName="username") }, 
    inverseJoinColumns = { @JoinColumn(referencedColumnName="name") })
    private Set<Role> roles;

	public User() {}
	
	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
}
