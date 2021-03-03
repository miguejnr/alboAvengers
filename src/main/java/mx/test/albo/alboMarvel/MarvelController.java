package mx.test.albo.alboMarvel;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarvelController {
	
	/*FUNCION BASICA DE ROUTER PARA REGRESAR EL JSON STRING, 
	ACEPTA EL NOMBRE DE LA BUSQEUDA COMO PARAMETRO DENTRO DE LA URL*/
	
	@RequestMapping("/marvel/colaborators/{name}")
	public String collaborators(@PathVariable String name) {
		GetCollaborators getcollabs = new GetCollaborators();
		return getcollabs.info(name);
	}
	
	//ROUTER DUPLICADO, EN EL DOCUMENTO EL URL SE PRESENTA COMO COLABORATORS, PUEDE SER TYPO
	
	@RequestMapping("/marvel/collaborators/{name}")
	public String collaboratorsDup(@PathVariable String name) {
		GetCollaborators getcollabs = new GetCollaborators();
		return getcollabs.info(name);
	}
	
	@RequestMapping("/marvel/characters/{name}")
	public String characters(@PathVariable String name) {
		GetCharacters getchar = new GetCharacters();
		return getchar.info(name);
	}
	
}
