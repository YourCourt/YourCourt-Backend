package yourcourt.controller;

import io.swagger.annotations.Api;

import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


import yourcourt.exceptions.InexistentEntity;
import yourcourt.model.Court;
import yourcourt.model.Image;
import yourcourt.model.ValidationUtils;
import yourcourt.model.dto.CourtDto;
import yourcourt.model.dto.Message;
import yourcourt.model.projections.BookingProjection;
import yourcourt.service.CourtService;
import yourcourt.service.ImageService;

@RestController
@Api(tags = "Court")
@RequestMapping("/courts")
@CrossOrigin
public class CourtController {

  private final String IS_ADMIN="hasRole('ROLE_ADMIN')";

  @Autowired
  private CourtService courtService;
  
  @Autowired
  private ImageService imageService;

  @GetMapping
  public ResponseEntity<?> getAllCourts() {
    return new ResponseEntity<>(courtService.findAllCourts(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getCourt(@PathVariable("id") Long id) {
    try {
      Court court = courtService.findCourtById(id);
      return new ResponseEntity<>(court, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PreAuthorize(IS_ADMIN)
  @GetMapping("/bookings/{courtId}")
  public ResponseEntity<?> getBookingsByCourt(@PathVariable("courtId") Long courtId) {
    try {
      Iterable<BookingProjection> bookings = courtService.findBookingsByCourt(courtId);
      return new ResponseEntity<>(bookings, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PreAuthorize(IS_ADMIN)
  @PostMapping
  public ResponseEntity<?> createCourt(
    @Valid @RequestBody CourtDto courtDto,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ValidationUtils.validateDto(bindingResult));
    }
    
	try {
		Court newCourt = new Court();
		BeanUtils.copyProperties(courtDto, newCourt);

		Optional<Image> defaultImage = imageService.findById(1);
		if (!defaultImage.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Message("La imagen seleccionada no ha sido encontrada."));
		}
		newCourt.setImage(defaultImage.get());

		Court courtCreated = courtService.saveCourt(newCourt);

		return new ResponseEntity<>(courtCreated, HttpStatus.CREATED);
	} catch (InexistentEntity e) {
		return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
	} catch (Exception e) {
		return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

  }

  @PreAuthorize(IS_ADMIN)
  @PutMapping("/{id}")
  public ResponseEntity<Object> updateCourt(
    @PathVariable Long id,
    @RequestBody CourtDto courtDto,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ValidationUtils.validateDto(bindingResult));
    }

    try {
      Court courtToUpdate = courtService.findCourtById(id);
      Court courtUpdated = courtService.updateCourt(courtToUpdate, courtDto);
      return new ResponseEntity<>(courtUpdated, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PreAuthorize(IS_ADMIN)
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCourt(@PathVariable("id") Long id) {
    try {
      courtService.deleteCourtById(id);
      return new ResponseEntity<>(new Message("Pista eliminada"), HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
}
