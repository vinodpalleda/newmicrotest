package com.spice.smsotpms.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter @Setter
public class Role {
	
    @GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
       
}
