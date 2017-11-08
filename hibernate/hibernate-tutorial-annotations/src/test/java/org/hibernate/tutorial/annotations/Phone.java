package org.hibernate.tutorial.annotations;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/*
 * For testing @ManytoOne with Person.java
 * */

@Entity
@Table(name="phone")
public class Phone {

	@Id
	@Column(name="phone_id")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	private long id;
	
	@Column(name="phone_num")
	private String num;
	
	@ManyToOne
	@JoinColumn(name="person_id")
	private Person person;
	

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	
}
