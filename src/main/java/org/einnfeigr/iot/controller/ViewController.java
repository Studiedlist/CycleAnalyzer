package org.einnfeigr.iot.controller;

import java.io.FileNotFoundException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ViewController {

	@GetMapping("/")
	public ModelAndView index() throws FileNotFoundException {
		return null;
	}
	
}
