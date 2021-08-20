package yourcourt.controller;

import io.swagger.annotations.Api;
import java.time.LocalDate;
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
import yourcourt.model.Image;
import yourcourt.model.News;
import yourcourt.model.ValidationUtils;
import yourcourt.model.dto.Message;
import yourcourt.model.dto.NewsDto;
import yourcourt.service.ImageService;
import yourcourt.service.NewsService;

@RestController
@Api(tags = "News")
@RequestMapping("/news")
@CrossOrigin
public class NewsController {

  private static final String IS_ADMIN="hasRole('ROLE_ADMIN')";

  @Autowired
  private NewsService newsService;
  
  @Autowired
  private ImageService imageService;

  @GetMapping
  public ResponseEntity<?> getAllNews() {
    return new ResponseEntity<>(newsService.findAllNews(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getNews(@PathVariable("id") long id) {
    try {
      News news = newsService.findNewsById(id);
      return new ResponseEntity<>(news, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PreAuthorize(IS_ADMIN)
  @PostMapping
  public ResponseEntity<?> createNews(
    @Valid @RequestBody NewsDto newsDto,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ValidationUtils.validateDto(bindingResult));
    }

    LocalDate creationDate = LocalDate.now();
    LocalDate editionDate = LocalDate.now();

    News newNews = new News();
    BeanUtils.copyProperties(newsDto, newNews);
    newNews.setCreationDate(creationDate);
    newNews.setEditionDate(editionDate);
    
    Optional<Image> defaultImage = imageService.findById(1);
	if (!defaultImage.isPresent()) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Message("La imagen seleccionada no ha sido encontrada."));
	}
	newNews.setImage(defaultImage.get());

    News newsCreated = newsService.saveNews(newNews);

    return new ResponseEntity<>(newsCreated, HttpStatus.CREATED);
  }

  @PreAuthorize(IS_ADMIN)
  @PutMapping("/{id}")
  public ResponseEntity<Object> updateNews(
    @PathVariable Long id,
    @RequestBody NewsDto newsDto,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ValidationUtils.validateDto(bindingResult));
    }

    try {
      News newsToUpdate = newsService.findNewsById(id);
      News newsUpdated = newsService.updateNews(newsToUpdate, newsDto);

      return new ResponseEntity<>(newsUpdated, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PreAuthorize(IS_ADMIN)
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteNews(@PathVariable("id") Long id) {
    try {
      newsService.deleteNewsById(id);
      return new ResponseEntity<>(new Message("Noticia eliminada"), HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
}
