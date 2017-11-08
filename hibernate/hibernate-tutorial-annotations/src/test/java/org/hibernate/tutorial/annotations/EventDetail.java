package org.hibernate.tutorial.annotations;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "EVENTS_DETAILS")
public class EventDetail {

	
	private int id;
//	private long eventId;
	private String description;
	private String venue;
	private Event event;
	
	public EventDetail() {
		
	}
	
	public EventDetail(String desc, String venue) {
//		this.eventId = eventId;
		this.description = desc;
		this.venue = venue;
	}
	
	@Id
	@Column(name = "EVENT_DETAIL_ID")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
//	@Column(name = "EVENT_ID")
//	public long getEventId() {
//		return eventId;
//	}
//	public void setEventId(long eventId) {
//		this.eventId = eventId;
//	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}

	@OneToOne
	@JoinColumn(name = "EVENT_ID")
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	
	
}
