package yourcourt.security.controller;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import yourcourt.model.ValidationUtils;
import yourcourt.security.jwt.JwtProvider;
import yourcourt.security.model.dto.JwtDto;
import yourcourt.security.model.dto.LoginUser;


@RestController
@RequestMapping("/auth")
@Api(tags = "Auth")
@CrossOrigin
public class AuthController {

	private static final String IS_ANONYMOUS="!hasRole('ROLE_ADMIN') and !hasRole('ROLE_USER')";

	@Autowired
    AuthenticationManager authenticationManager;
	
	
	@Autowired
	JwtProvider jwtProvider;
	
	@PreAuthorize(IS_ANONYMOUS)
	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody final LoginUser loginUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(bindingResult));
		}
		
		Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), Collections.emptyList()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = this.jwtProvider.generateToken(authentication);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
		return new ResponseEntity<>(jwtDto, HttpStatus.CREATED);
	}
}
