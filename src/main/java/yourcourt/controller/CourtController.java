package yourcourt.controller;



import java.util.Optional;


import io.swagger.annotations.Api;
import yourcourt.exceptions.user.InexistentEntity;
import yourcourt.model.Court;
import yourcourt.model.dto.CourtDto;
import yourcourt.model.dto.Message;
import yourcourt.security.service.UserService;
import yourcourt.service.CourtService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(tags = "Court")
@RequestMapping("/courts")
public class CourtController {
    
	@Autowired
	private UserService userService;
	
    @Autowired
    private CourtService courtService;
    
    @GetMapping
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(courtService.findAllCourts(), HttpStatus.OK);
	}

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourt(@PathVariable("id") long id) {
    	return new ResponseEntity<>(courtService.findCourtById(id), HttpStatus.OK);
    }
    
    @PostMapping
	public ResponseEntity<?> createCourt(@RequestBody CourtDto courtDto) {
		String username = userService.getCurrentUsername();
		System.out.println(username);
		if(!username.equals("admin")){
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("El usuario no tiene permiso de creación."));
		}
		
		Court newCourt = new Court();
		BeanUtils.copyProperties(courtDto, newCourt);
		
		courtService.saveCourt(newCourt);
		
		return ResponseEntity.ok(courtService.findCourtById(newCourt.getId()));
	}	
    
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateCourt(@PathVariable Long id, @RequestBody CourtDto courtDto) {
		String username = userService.getCurrentUsername();
		if(!username.equals("admin")){
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("El usuario no tiene permiso de actualización."));
		}
		
		Optional<Court> courtToUpdate = courtService.findCourtById(id);
			
		try {
			Court obj = courtService.updateCourt(courtToUpdate.get(), courtDto);

			return new ResponseEntity<>(obj, HttpStatus.OK);
			
		} catch (InexistentEntity e) {
			return new ResponseEntity<>(
				    	new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourt(@PathVariable("id") long id) {
        if (!courtService.existsCourtById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe el court indicado"));
        } else {
			
			String username = userService.getCurrentUsername();
			if(!username.equals("admin")){
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("El usuario no tiene permiso de eliminación."));
			}
			courtService.deleteCourtById(id);
		}	
        return ResponseEntity.ok(new Message("Court eliminada correctamente"));
    }

}
