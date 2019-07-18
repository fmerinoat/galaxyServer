package com.prueba.galaxy.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.prueba.galaxy.config.Constants;
import com.prueba.galaxy.exceptions.ListExceptionNotFound;
import com.prueba.galaxy.exceptions.ListExceptionNotValid;
import com.prueba.galaxy.exceptions.PlanetNotValid;
import com.prueba.galaxy.exceptions.RebelNameNotValid;
import com.prueba.galaxy.model.Rebel;
import com.prueba.galaxy.service.RebelService;

/**
 * @author Francisco Merino
 */
@Service
public class RebelServiceImpl implements RebelService {
	
	private final Logger log = LoggerFactory.getLogger(RebelServiceImpl.class);
	
	/**
	 * @param Recibe una lista de String con dos posiciones la primera para el nombre del Rebelde y la segunda para el planeta
	 * @return ResponseEntity<String>
	 */
	public ResponseEntity<String> gestionaRebel(List<String> mensajes) {
		
		log.info("Inicio gestionaRebel");
		String error ="";
		if(mensajes == null) {
			error = Constants.errMensajeNulo;
			throw new ListExceptionNotFound(Constants.errMensajeNulo);
		}else {
			if(mensajes.size()!=2) {
				error = Constants.errMensajeNoValido;
				throw new ListExceptionNotValid(Constants.errMensajeNoValido);
			}else {
				if(mensajes.get(0) == null || mensajes.get(0).isEmpty()) {
					throw new RebelNameNotValid(Constants.errNombreRebelNoInformado);
				}
				if(mensajes.get(1) == null || mensajes.get(1).isEmpty()) {
					throw new PlanetNotValid(Constants.errPlanetaNoInformado);
				}
			}
		}
		if(!error.equals("")) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}
		
		List<Rebel>listado = lecturaFichero(mensajes);
		
		boolean existe = existsRebel(mensajes, listado);
	    if(!existe) {
	    	registrar(mensajes);
	    }else {
	    	 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.errRebeldeExists);
	    }
	    
	    log.info("Fin gestionaRebel");
	    return ResponseEntity.status(HttpStatus.OK).body("true");
	}
	
	/**
	 * @param mensajes
	 * @return
	 */
	private List<Rebel> lecturaFichero(List<String> mensajes){
		
		log.info("Inicio lecturaFichero");
		
		List<Rebel>listado = new ArrayList<Rebel>();
		
		try {
			if(!Files.exists(Paths.get(Constants.FILENAME))) {
				log.debug("No Existe el FICHERO");
				return listado;
			}
			
		    List<String> lines = Files.readAllLines(Paths.get(Constants.FILENAME));
		    for (String line : lines) {
		        String[] array = line.split("/");
		        String name = array.length > 0 ? array[0] : "";
		        String planet    = array.length > 1 ? array[1] : "";
		        String timestamp  = array.length > 2 ? array[2] : "";
		        log.debug("Nombre: %s%nPlaneta: %s%nTimestamp: %s%n", name, planet, timestamp);
		        log.debug("*******************");
		        Rebel rebel = new Rebel(name, planet);
		        listado.add(rebel);
		    }
		    log.debug("Done");
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Mensaje de la excepción: " + e.getMessage());
		}
		log.info("Fin lecturaFichero");
		return listado;
	}
	
	/**
	 * @param mensajes
	 * @param rebels
	 * @return true si existe el nombre del rebelde y el planeta del mensaje en la lista de rebeldes leido del fichero
	 */
	private Boolean existsRebel(List<String> mensajes, List<Rebel>rebels) {
		log.info("Inicio existsRebel");
		boolean exists = false;
		Rebel rebel = new Rebel(mensajes.get(0), mensajes.get(1));
		log.debug( "Nombre rebelde: "+rebel.getName()+" /planeta= "+rebel.getPlanet());
		
	    if(rebels.size() != 0) {
			 for(Rebel item: rebels) {
				 if(item.getName().equals(mensajes.get(0))&& item.getPlanet().equals(mensajes.get(1))) {
					 log.debug("Ya está registrado en el fichero.");
					 exists = true;
					 break;
				 }
			 }
	    }
	    log.info("Fin existsRebel");
		return exists;
	}
	
	/**
	 * Registra en el fichero el rebelde
	 * @param mensajes
	 */
	private void registrar(List<String> mensajes){
		
		log.info("Inicio registrar");
		FileWriter fichero = null;
		
		LocalDateTime localDateTime = LocalDateTime.now();
		
		try {
			fichero = new FileWriter(Constants.FILENAME, true);
			fichero.write(mensajes.get(0) + "/" + mensajes.get(1) +"/"+ localDateTime +"\n");

			fichero.close();

		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		log.info("Fin registrar");
	}
	
	
}
