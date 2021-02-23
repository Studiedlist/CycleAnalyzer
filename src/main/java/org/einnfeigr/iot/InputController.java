package org.einnfeigr.iot;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InputController {

	@PostMapping("/api/upload")
	public ResponseEntity<String> uploadData(@RequestBody String data, @RequestParam String token) {
		
		return new ResponseEntity<>("ok", HttpStatus.OK);
	}
	
}
