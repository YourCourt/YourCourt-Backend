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
import java.time.LocalDate;
import yourcourt.exceptions.InexistentEntity;
import yourcourt.exceptions.RestrictedEntity;
import yourcourt.model.Course;
import yourcourt.model.Inscription;
import yourcourt.model.ValidationUtils;
import yourcourt.model.dto.InscriptionDto;
import yourcourt.model.dto.Message;
import yourcourt.model.projections.InscriptionProjection;
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

	@Autowired
	private UserService userService;

	@Autowired
	private CourseService courseService;

	@GetMapping()
	public ResponseEntity<?> getAllInscriptions() {
		try {
			Iterable<InscriptionProjection> inscription = inscriptionService.findAllInscriptionProjections();
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

			InscriptionProjection inscription = inscriptionService.findInscriptionProjectionById(id);

			ValidationUtils.accessRestrictedObjectById(inscription.getUser(), userService, "una inscripcion");

			return new ResponseEntity<>(inscription, HttpStatus.OK);
		} catch (InexistentEntity e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (RestrictedEntity e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/user/{username}")
	public ResponseEntity<?> getAllInscriptionsFromUserUsername(@PathVariable("username") String username) {
		try {
			Iterable<InscriptionProjection> inscriptions = inscriptionService
					.findAllInscriptionProjectionsByUserUsername(username);

			ValidationUtils.accessRestrictedObjectByUsername(username, userService, "una inscripcion");
			return new ResponseEntity<>(inscriptions, HttpStatus.OK);
		} catch (InexistentEntity e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (RestrictedEntity e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/course/{courseId}")
	public ResponseEntity<?> getAllInscriptionsFromCourse(@PathVariable("courseId") Long courseId) {
		try {
			Iterable<InscriptionProjection> inscriptions = inscriptionService.findAllInscriptionByCourseId(courseId);

			return new ResponseEntity<>(inscriptions, HttpStatus.OK);
		} catch (InexistentEntity e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/course/{id}")
	public ResponseEntity<?> createInscription(@PathVariable("id") Long courseId,
			@Valid @RequestBody InscriptionDto inscriptionDto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(bindingResult));
		}

		try {
			String username = userService.getCurrentUsername();

			Optional<User> usuario = userService.findByUsername(username);
			Course course = courseService.findCourseById(courseId);

			if (course.getStartDate().isBefore(LocalDate.now())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ValidationUtils.throwError("fecha", "El curso ya ha comenzado"));
			}

			Inscription newInscription = new Inscription();
			newInscription.setUser(usuario.get());
			newInscription.setCourse(course);
			BeanUtils.copyProperties(inscriptionDto, newInscription);

			String name = inscriptionDto.getName();
			String surnames = inscriptionDto.getSurnames();

			if (inscriptionService.findInscriptionByName(name, surnames) != null) {

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						ValidationUtils.throwError(name + " " + surnames, "No se puede inscribir la misma persona"));
			}

			Inscription inscriptionCreated = inscriptionService.saveInscription(newInscription);
			InscriptionProjection inscriptionProjected = inscriptionService
					.findInscriptionProjectionById(inscriptionCreated.getId());

			return new ResponseEntity<>(inscriptionProjected, HttpStatus.CREATED);

		} catch (InexistentEntity e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateInscription(@PathVariable Long id, @RequestBody InscriptionDto inscriptionDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(bindingResult));
		}

		try {
			Inscription inscriptionToUpdate = inscriptionService.findInscriptionById(id);
			Inscription inscriptionUpdated = inscriptionService.updateInscription(inscriptionToUpdate, inscriptionDto);

			InscriptionProjection inscriptionProjected = inscriptionService
					.findInscriptionProjectionById(inscriptionUpdated.getId());

			ValidationUtils.accessRestrictedObjectById(inscriptionProjected.getUser(), userService, "una inscripcion");
			return new ResponseEntity<>(inscriptionProjected, HttpStatus.OK);
		} catch (InexistentEntity e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (RestrictedEntity e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteInscription(@PathVariable("id") Long id) {
		try {
			InscriptionProjection inscriptionProjected = inscriptionService.findInscriptionProjectionById(id);

			if (inscriptionProjected.getCourse().getStartDate().isBefore(LocalDate.now())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(ValidationUtils.throwError("fecha", "El curso ya ha comenzado"));

			}
			ValidationUtils.accessRestrictedObjectById(inscriptionProjected.getUser(), userService, "una inscripcion");

			inscriptionService.deleteInscriptionById(id);
			return new ResponseEntity<>(new Message("Inscripcion eliminada"), HttpStatus.OK);
		} catch (InexistentEntity e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (RestrictedEntity e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
}
