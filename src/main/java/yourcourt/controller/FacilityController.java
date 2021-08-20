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
import yourcourt.model.Facility;
import yourcourt.model.FacilityType;
import yourcourt.model.Image;
import yourcourt.model.ValidationUtils;
import yourcourt.model.dto.FacilityDto;
import yourcourt.model.dto.Message;
import yourcourt.service.FacilityService;
import yourcourt.service.ImageService;

@RestController
@Api(tags = "Facility")
@RequestMapping("/facilities")
@CrossOrigin
public class FacilityController {

  private final String IS_ADMIN="hasRole('ROLE_ADMIN')";

  @Autowired
  private FacilityService facilityService;

  @Autowired
  private ImageService imageService;

  @GetMapping
  public ResponseEntity<?> getAllFacilities() {
    return new ResponseEntity<>(facilityService.findAllFacilities(), HttpStatus.OK);
  }

  @PreAuthorize(IS_ADMIN)
  @GetMapping("/facilityTypes")
  public ResponseEntity<?> getAllFacilityTypes() {
    return new ResponseEntity<>(facilityService.findAllFacilityTypes(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getFacility(@PathVariable("id") Long id) {
    try {
      Facility facility = facilityService.findFacilityById(id);
      return new ResponseEntity<>(facility, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PreAuthorize(IS_ADMIN)
  @PostMapping
  public ResponseEntity<?> createFacility(@Valid @RequestBody FacilityDto facilityDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(bindingResult));
    }

    try {
      Facility newFacility = new Facility();
      BeanUtils.copyProperties(facilityDto, newFacility);

      FacilityType facilityType;
      if (facilityService.existsFacilityTypeByName(facilityDto.getFacilityType()) == false) {
        facilityType = new FacilityType(facilityDto.getFacilityType());
        facilityService.saveFacilityType(facilityType);
      } else {
        facilityType = facilityService.findFacilityTypeByType(facilityDto.getFacilityType());
      }

      Optional<Image> defaultImage = imageService.findById(1);
      if (!defaultImage.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new Message("La imagen seleccionada no ha sido encontrada."));
      }
      newFacility.setImage(defaultImage.get());
      newFacility.setFacilityType(facilityType);
      Facility facilityCreated = facilityService.saveFacility(newFacility);

      return new ResponseEntity<>(facilityCreated, HttpStatus.CREATED);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

  }

  @PreAuthorize(IS_ADMIN)
  @PutMapping("/{id}")
  public ResponseEntity<Object> updateFacility(@PathVariable Long id, @RequestBody FacilityDto facilityDto,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(bindingResult));
    }

    try {
      Facility facilityToUpdate = facilityService.findFacilityById(id);
      Facility facilityUpdated = facilityService.updateFacility(facilityToUpdate, facilityDto);
      return new ResponseEntity<>(facilityUpdated, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PreAuthorize(IS_ADMIN)
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteFacility(@PathVariable("id") Long id) {
    try {
      facilityService.deleteFacilityById(id);
      return new ResponseEntity<>(new Message("Instalacion eliminada"), HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
}
