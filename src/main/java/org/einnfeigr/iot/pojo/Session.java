package org.einnfeigr.iot.pojo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.datetime.standard.DateTimeFormatterFactory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="sessions")
public class Session {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="start_date")
	private LocalDateTime start;
	
	@Column(name="end_date")
	private LocalDateTime end;
	
	@Column(name="distance")
	private Float distance;
	
	@OneToMany(mappedBy="session", cascade=CascadeType.ALL)
	@JsonManagedReference
	private List<Record> records = new LinkedList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}
	
	public void addRecord(Record record) {
		records.add(record);
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	@Override
	public int hashCode() {
		return Objects.hash(distance, end, id, start);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Session)) {
			return false;
		}
		Session other = (Session) obj;
		if(records.size() != other.records.size()) {
			return false;
		}
		return Objects.equals(distance, other.distance) 
				&& Objects.equals(end, other.end)
				&& Objects.equals(id, other.id) 
				&& Objects.equals(start, other.start);
	}
	
}
