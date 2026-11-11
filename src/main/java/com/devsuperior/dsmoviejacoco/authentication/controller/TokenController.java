package com.devsuperior.dsmoviejacoco.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmoviejacoco.authentication.dto.TokenDTO;
import com.devsuperior.dsmoviejacoco.authentication.service.TokenService;

@RestController
public class TokenController {

	private final TokenService tokenService;

	public TokenController(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@PostMapping("/oauth2/token")
	ResponseEntity<TokenDTO> create(
			@RequestParam(name = "grant_type") String grantType,
			@RequestParam String username,
			@RequestParam String password) {
		TokenDTO tokenDTO = tokenService.create(grantType, username, password);
		return ResponseEntity.ok(tokenDTO);
	}

}
