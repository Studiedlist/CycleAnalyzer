package org.einnfeigr.iot.controller;

import org.einnfeigr.iot.pojo.Gyro;
import org.einnfeigr.iot.repository.GyroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GyroController {

	@Autowired
	private GyroRepository gyroRepository;
	
	public Gyro save(Gyro gyro) {
		return gyroRepository.save(gyro);
	}
	
}
