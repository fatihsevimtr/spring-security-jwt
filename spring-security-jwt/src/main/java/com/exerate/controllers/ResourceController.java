package com.exerate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exerate.model.AuthenticationRequest;
import com.exerate.model.AuthenticationResponse;
import com.exerate.services.MyUserDetailsService;
import com.exerate.util.JwtUtil;

@RestController
@RequestMapping("/")
public class ResourceController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private JwtUtil jwtUtilToken;
	
	@GetMapping("/home")
	public String home() {

		return "<h2>Hello JWT<h2>";
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

	
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
					authenticationRequest.getPassword()));
			
		} catch (BadCredentialsException e) {
			throw new Exception("incorrect username or password",e);
		}
		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwtToken=jwtUtilToken.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
	}
}
