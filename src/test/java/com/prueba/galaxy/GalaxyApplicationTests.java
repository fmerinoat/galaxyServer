package com.prueba.galaxy;

import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.prueba.galaxy.config.Constants;
import com.prueba.galaxy.controller.RebelController;
import com.prueba.galaxy.service.RebelService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GalaxyApplicationTests {
	
	
	@Autowired
	RebelService rebelService;

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testRebelde() {
		List<String> datos = new ArrayList<String>();
		datos.add("NamePlanet"+UUID.randomUUID().toString());
		datos.add("PlanetName");
		
		ResponseEntity res = rebelService.gestionaRebel(datos);
		assertEquals(res, ResponseEntity.status(HttpStatus.OK).body("true"));
		
		res = rebelService.gestionaRebel(datos);
		assertEquals(res, ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.errRebeldeExists));
	}
	
	
}
