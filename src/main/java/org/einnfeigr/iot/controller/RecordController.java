package org.einnfeigr.iot.controller;

import java.util.List;

import org.einnfeigr.iot.pojo.Record;
import org.einnfeigr.iot.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecordController {

	@Autowired
	private RecordRepository recordRepository;
	
	@PostMapping("/api/record/")
	public List<Record> saveAll(List<Record> records) {
		return recordRepository.saveAll(records);
	}
	
}
