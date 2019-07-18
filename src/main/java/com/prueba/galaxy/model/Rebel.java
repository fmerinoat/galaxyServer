package com.prueba.galaxy.model;

public class Rebel {

	private String name;
	private String planet;
	
	public Rebel() {
	}
	
	public Rebel(String name, String planet) {
		this.name = name;
		this.planet = planet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlanet() {
		return planet;
	}

	public void setPlanet(String planet) {
		this.planet = planet;
	}
	
}
