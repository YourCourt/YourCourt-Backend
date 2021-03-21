package yourcourt.controller;

import java.time.LocalDateTime;

import io.swagger.annotations.Api;
import yourcourt.service.CourtService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    private CourtService courtService;
    
    @GetMapping
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(courtService.findAllCourts(), HttpStatus.OK);
	}

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourt(@PathVariable("id") long id) {
    	return new ResponseEntity<>(courtService.findCourtById(id), HttpStatus.OK);
    }

}
