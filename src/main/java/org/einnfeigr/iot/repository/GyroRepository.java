package org.einnfeigr.iot.repository;

import org.einnfeigr.iot.pojo.Gyro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GyroRepository extends JpaRepository<Gyro, Long> {

}
