package com.devsuperior.dsmoviejacoco.security;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cors")
public record CorsProperties(
		List<String> origins) {}
