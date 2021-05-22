package yourcourt.controller;

import io.swagger.annotations.Api;

import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yourcourt.exceptions.user.InexistentEntity;
import yourcourt.model.Course;
import yourcourt.model.Inscription;
import yourcourt.model.ValidationUtils;
import yourcourt.model.dto.InscriptionDto;
import yourcourt.model.dto.Message;
import yourcourt.security.model.User;
import yourcourt.security.service.UserService;
import yourcourt.service.CourseService;
import yourcourt.service.InscriptionService;

@RestController
@Api(tags = "Inscription")
@RequestMapping("/inscriptions")
@CrossOrigin
public class InscriptionController {
  @Autowired
  private InscriptionService inscriptionService;
  
  private UserService userService;
  
  private CourseService courseService;

  @GetMapping()
  public ResponseEntity<?> getAllInscriptions() {
    try {
    	Iterable<Inscription> inscription = inscriptionService.findAllInscriptions();
      return new ResponseEntity<>(inscription, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<?> getInscription(@PathVariable("id") Long id) {
    try {
    	Inscription inscription = inscriptionService.findInscriptionById(id);
      return new ResponseEntity<>(inscription, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
  
  @GetMapping("/user/{username}")
  public ResponseEntity<?> getAllInscriptionsFromUserUsername(@PathVariable("username") String username) {
    try {
    	Iterable<Inscription> inscriptions = inscriptionService.findAllInscriptionsByUser(username);
      return new ResponseEntity<>(inscriptions, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
  
  @GetMapping("/user/{id}")
  public ResponseEntity<?> getAllInscriptionsFromUserId(@PathVariable("id") String username) {
    try {
    	Iterable<Inscription> inscriptions = inscriptionService.findAllInscriptionsByUser(username);
      return new ResponseEntity<>(inscriptions, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/course/{id}")
  public ResponseEntity<?> createInscription(@PathVariable("id") Long courseId, @Valid @RequestBody InscriptionDto inscriptionDto, BindingResult bindingResult) {
	  
    if (bindingResult.hasErrors()) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ValidationUtils.validateDto(bindingResult));
    }

    String username = userService.getCurrentUsername();
    
    Optional<User> usuario = userService.findByUsername(username);
    Course course = courseService.findCourseById(courseId);
    
    Inscription newInscription = new Inscription();
    newInscription.setUser(usuario.get());
    newInscription.setCourse(course);
    BeanUtils.copyProperties(inscriptionDto, newInscription);

    Inscription inscriptionCreated = inscriptionService.saveInscription(newInscription);

    return new ResponseEntity<>(inscriptionCreated, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateInscription(
    @PathVariable Long id,
    @RequestBody InscriptionDto inscriptionDto,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ValidationUtils.validateDto(bindingResult));
    }

    try {
    	Inscription inscriptionToUpdate = inscriptionService.findInscriptionById(id);
    	Inscription inscriptionUpdated = inscriptionService.updateInscription(inscriptionToUpdate, inscriptionDto);

      return new ResponseEntity<>(inscriptionUpdated, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteInscription(@PathVariable("id") Long id) {
    try {
      inscriptionService.deleteInscriptionById(id);
      return new ResponseEntity<>(new Message("Curso eliminado"), HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
}
