package org.einnfeigr.iot.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="records")
public class Record {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="impulses_rear")
	private Byte impulsesRear;

	@Column(name="impulses_front")
	private Byte impulsesFront;

	@OneToMany(mappedBy="record", cascade=CascadeType.ALL)
	@JsonManagedReference
	private List<Gyro> gyros = new ArrayList<>();
	
	@ManyToOne
	@JsonBackReference
	private Session session;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Byte getImpulsesRear() {
		return impulsesRear;
	}

	public void setImpulsesRear(Byte impulsesRear) {
		this.impulsesRear = impulsesRear;
	}

	public Byte getImpulsesFront() {
		return impulsesFront;
	}

	public void setImpulsesFront(Byte impulsesFront) {
		this.impulsesFront = impulsesFront;
	}

	public List<Gyro> getGyros() {
		return gyros;
	}

	public void setGyros(List<Gyro> gyros) {
		this.gyros = gyros;
	}
	
	public void addGyro(Gyro gyro) {
		gyros.add(gyro);
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, impulsesFront, impulsesRear);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Record)) {
			return false;
		}
		Record other = (Record) obj;
		return Objects.equals(id, other.id)
				&& Objects.equals(impulsesFront, other.impulsesFront)
				&& Objects.equals(impulsesRear, other.impulsesRear);
	}

	
}
