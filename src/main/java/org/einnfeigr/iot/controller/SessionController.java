package org.einnfeigr.iot.controller;

import java.util.List;
import java.util.Optional;

import org.einnfeigr.iot.pojo.Session;
import org.einnfeigr.iot.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private RecordController recordController;
	
	@Autowired
	private GyroController gyroController;
	
	@PostMapping(value="/api/session/add", produces="application/json")
	public Session addSession(@RequestBody Session session) {
		session.setStart(session.getStart().withNano(0));
		session.setEnd(session.getEnd().withNano(0));
		return sessionRepository.save(session);
	}
	
	@GetMapping(value="/api/session/get", produces="application/json")
	public List<Session> getAll() {
		return sessionRepository.findAll();
	}
	
	@GetMapping(value="/api/session/{id}", produces="application/json")
	public Session get(@PathVariable Integer id) {
		Optional<Session> optional = sessionRepository.findById(id);
		if(!optional.isPresent()) {
			throw new IllegalArgumentException();
		}
		return optional.get();
	}
	
	@PostMapping(value="/api/session/{id}", produces="application/json")
	public Session update(@PathVariable Integer id, @RequestBody Session session) {
		session.setId(id);
		return sessionRepository.save(session);
	}
	
	@DeleteMapping(value="/api/session/{id}", produces="application/json")
	public void delete(@PathVariable Integer id) {
		sessionRepository.deleteById(id);
	}
}
