package com.exerate.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ResourceController {

	@GetMapping("/home")
	public String home() {
		
		return "<h2>Hello JWT<h2>";
	}
}
