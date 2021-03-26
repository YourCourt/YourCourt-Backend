package yourcourt.controller;



import java.util.Optional;



import io.swagger.annotations.Api;
import yourcourt.exceptions.user.InexistentEntity;
import yourcourt.model.News;
import yourcourt.model.dto.Message;
import yourcourt.model.dto.NewsDto;
import yourcourt.security.service.UserService;
import yourcourt.service.NewsService;

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
@Api(tags = "News")
@RequestMapping("/news")
public class NewsController {
    
	@Autowired
	private UserService userService;
	
    @Autowired
    private NewsService newsService;
    
    @GetMapping
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(newsService.findAllNews(), HttpStatus.OK);
	}

    @GetMapping("/{id}")
    public ResponseEntity<?> getNews(@PathVariable("id") long id) {
    	return new ResponseEntity<>(newsService.findNewsById(id), HttpStatus.OK);
    }
    
    @PostMapping
	public ResponseEntity<?> createNews(@RequestBody NewsDto newsDto) {
		String username = userService.getCurrentUsername();
		System.out.println(username);
		if(!username.equals("admin")){
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("El usuario no tiene permiso de creación."));
		}
		
		News newNews = new News();
		BeanUtils.copyProperties(newsDto, newNews);
		
		newsService.saveNews(newNews);
		
		return ResponseEntity.ok(newsService.findNewsById(newNews.getId()));
	}	
    
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateNews(@PathVariable Long id, @RequestBody NewsDto newsDto) {
		String username = userService.getCurrentUsername();
		if(!username.equals("admin")){
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("El usuario no tiene permiso de actualización."));
		}
		
		Optional<News> newsToUpdate = newsService.findNewsById(id);
			
		try {
			News obj = newsService.updateNews(newsToUpdate.get(), newsDto);

			return new ResponseEntity<>(obj, HttpStatus.OK);
			
		} catch (InexistentEntity e) {
			return new ResponseEntity<>(
				    	new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable("id") long id) {
        if (!newsService.existsNewsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe la noticia indicada"));
        } else {
			
			String username = userService.getCurrentUsername();
			if(!username.equals("admin")){
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("El usuario no tiene permiso de eliminación."));
			}
			newsService.deleteNewsById(id);
		}	
        return ResponseEntity.ok(new Message("Noticia eliminada correctamente"));
    }

}
