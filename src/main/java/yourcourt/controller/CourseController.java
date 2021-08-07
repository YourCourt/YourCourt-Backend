package yourcourt.controller;

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

import io.swagger.annotations.Api;
import yourcourt.exceptions.InexistentEntity;
import yourcourt.model.Course;
import yourcourt.model.ValidationUtils;
import yourcourt.model.dto.CourseDto;
import yourcourt.model.dto.Message;
import yourcourt.service.CourseService;

@RestController
@Api(tags = "Course")
@RequestMapping("/courses")
@CrossOrigin
public class CourseController {
  @Autowired
  private CourseService courseService;
  
  @GetMapping
  public ResponseEntity<?> getAllCourses() {
    return new ResponseEntity<>(courseService.findAllCourses(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getCourse(@PathVariable("id") Long id) {
    try {
    	Course course = courseService.findCourseById(id);
    	return new ResponseEntity<>(course, HttpStatus.OK);
      
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping
  public ResponseEntity<?> createCourse(
    @Valid @RequestBody CourseDto courseDto,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ValidationUtils.validateDto(bindingResult));
    }

    Course newCourse = new Course();
    BeanUtils.copyProperties(courseDto, newCourse);

    if(courseDto.getStartDate().isAfter(courseDto.getEndDate())) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Message("La fecha de inicio no puede ser posterior a la de fin."));
	}
    
    Course courseCreated = courseService.saveCourse(newCourse);

    return new ResponseEntity<>(courseCreated, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateCourse(
    @PathVariable Long id,
    @RequestBody CourseDto courseDto,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ValidationUtils.validateDto(bindingResult));
    }

    try {
    	Course courseToUpdate = courseService.findCourseById(id);
    	
    	if(courseDto.getStartDate().isAfter(courseDto.getEndDate())) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Message("La fecha de inicio no puede ser posterior a la de fin."));
    	}
    	
    	Course courseUpdated = courseService.updateCourse(courseToUpdate, courseDto);

    	return new ResponseEntity<>(courseUpdated, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCourse(@PathVariable("id") Long id) {
    try {
    	
    	courseService.deleteCourseById(id);
    	
    	return new ResponseEntity<>(new Message("Curso eliminado"), HttpStatus.OK);
    	
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
      
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
}
