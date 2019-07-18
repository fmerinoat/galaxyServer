package com.prueba.galaxy.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.prueba.galaxy.exceptions.ListExceptionNotFound;

public interface RebelService {

	public ResponseEntity<String> gestionaRebel(List<String> datos);
}
