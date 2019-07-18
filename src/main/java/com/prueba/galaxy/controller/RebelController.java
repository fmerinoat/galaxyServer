package com.prueba.galaxy.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.galaxy.config.Constants;
import com.prueba.galaxy.exceptions.RebelNameNotValid;
import com.prueba.galaxy.service.RebelService;



@RestController
@RequestMapping("/rebel")
public class RebelController {
	
	@Autowired
	RebelService rebelService;
	
	@PostMapping("/add")
	public ResponseEntity<String> add(@RequestBody List<String> datos){
		
		return rebelService.gestionaRebel(datos);
	}
}
