package org.einnfeigr.iot.controller;

import java.util.Random;

import org.einnfeigr.iot.pojo.AccessToken;
import org.einnfeigr.iot.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

	@Autowired
	private TokenRepository tokenRepository;
	
	private final static int TOKEN_LENGTH = 20;
	
	@GetMapping("/api/token/check")
	private ResponseEntity<String> isValid(@RequestParam String token) {
		if(tokenRepository.existsByToken(token)) {
			return new ResponseEntity<>("ok", HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>("token cannot be found", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/api/token/generate")
	public AccessToken generate() {
		AccessToken token = new AccessToken();
		token.setToken(generateToken());
		return tokenRepository.save(token);
	}
	
	@DeleteMapping("/api/token/{id}")
	public void remove(@PathVariable Long id) {
		tokenRepository.deleteById(id);
	}
	
	private String generateToken() {
		Random random = new Random();
		StringBuilder token = new StringBuilder();
		while(token.length() < TOKEN_LENGTH) {
			token.append('0'+random.nextInt('Z'-'0'));
		}
		return token.toString();
	}
	
}
