package com.pro.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeApi {

	@GetMapping({"", "/", "/health"})
	public ResponseEntity<String> index() {
		return ResponseEntity.ok("----- Springboot ZXing | Home API | Root Index -----");
	}
}