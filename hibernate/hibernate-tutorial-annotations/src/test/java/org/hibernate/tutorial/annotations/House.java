package org.hibernate.tutorial.annotations;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "house" )
public class House {

	@Id
	@Column(name="house_id")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	private long id;
	private String name;
	
	/*
	 * 
	 * cascade option requires to save the rooms of the house, when only the house is used in the save method from sessions.
	 * 
	 * */
	@OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Room> rooms = new ArrayList<Room>();
	
	public List<Room> getRooms() {
		return rooms;
	}
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/*
	 * Whenever a bidirectional association is formed, the application developer
	 * must make sure both sides are in-sync at all times. The addRoom() and
	 * removeRoom() are utilities methods that synchronize both ends whenever a
	 * child element is added or removed.
	 * 
	 * 
	 * http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#associations-one-to-many
	 * 
	 */
	
	public void addRoom(Room room) {
		rooms.add(room);
		room.setHouse(this);
	}
	
	
	public void removeRoom(Room room) {
		rooms.remove(room);
		room.setHouse(null);
	}
	
}
