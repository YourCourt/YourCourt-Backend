package yourcourt.security.controller;

import java.util.Collections;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import yourcourt.model.dto.Message;
import yourcourt.security.jwt.JwtProvider;
import yourcourt.security.model.dto.JwtDto;
import yourcourt.security.model.dto.LoginUser;


@RestController
@RequestMapping("/auth")
@Api(tags = "Auth")
@CrossOrigin
public class AuthController {
	

	@Autowired
    AuthenticationManager authenticationManager;
	
	
	@Autowired
	JwtProvider jwtProvider;
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody @Valid LoginUser loginUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(new Message("Binding error"), HttpStatus.BAD_REQUEST);
		}
		
		Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), Collections.emptyList()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = this.jwtProvider.generateToken(authentication);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
		return new ResponseEntity<>(jwtDto, HttpStatus.CREATED);
	}
}
