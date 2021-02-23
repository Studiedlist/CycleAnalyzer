package org.einnfeigr.iot.pojo;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="gyros")
public class Gyro {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="pos_x")
	private Float posX;
	
	@Column(name="pos_y")
	private Float posY;
	
	@Column(name="pos_z")
	private Float posZ;
	
	@Column(name="acc_x")
	private Float accX;
	
	@Column(name="acc_y")
	private Float accY;
	
	@Column(name="acc_z")
	private Float accZ;
	
	@ManyToOne
	private Record record;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getPosX() {
		return posX;
	}

	public void setPosX(Float posX) {
		this.posX = posX;
	}

	public Float getPosY() {
		return posY;
	}

	public void setPosY(Float posY) {
		this.posY = posY;
	}

	public Float getPosZ() {
		return posZ;
	}

	public void setPosZ(Float posZ) {
		this.posZ = posZ;
	}

	public Float getAccX() {
		return accX;
	}

	public void setAccX(Float accX) {
		this.accX = accX;
	}

	public Float getAccY() {
		return accY;
	}

	public void setAccY(Float accY) {
		this.accY = accY;
	}

	public Float getAccZ() {
		return accZ;
	}

	public void setAccZ(Float accZ) {
		this.accZ = accZ;
	}

	@JsonBackReference
	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accX, accY, accZ, id, posX, posZ, posY);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Gyro)) {
			return false;
		}
		Gyro other = (Gyro) obj;
		return Objects.equals(accX, other.accX) && Objects.equals(accY, other.accY) 
				&& Objects.equals(accZ, other.accZ) && Objects.equals(id, other.id)
				&& Objects.equals(posX, other.posX) && Objects.equals(posZ, other.posZ)
				&& Objects.equals(posY, other.posY);
	}

		
}
