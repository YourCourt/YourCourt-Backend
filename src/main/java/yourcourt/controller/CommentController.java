package yourcourt.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import yourcourt.exceptions.InexistentEntity;
import yourcourt.exceptions.RestrictedEntity;
import yourcourt.model.Comment;
import yourcourt.model.News;
import yourcourt.model.ValidationUtils;
import yourcourt.model.dto.CommentDto;
import yourcourt.model.dto.Message;
import yourcourt.security.model.User;
import yourcourt.security.service.UserService;
import yourcourt.service.CommentService;
import yourcourt.service.NewsService;

@RestController
@Api(tags = "Comment")
@RequestMapping("/comments")
@CrossOrigin
public class CommentController {

	private final String IS_ADMIN_OR_IS_USER="hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')";

    @Autowired
    private CommentService commentService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;
    @PreAuthorize(IS_ADMIN_OR_IS_USER)
    @PostMapping
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDto commentDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(result));
        }
        
        try {
        	String username = userService.getCurrentUsername();

            News news = newsService.findNewsById(commentDto.getNewsId());
            
            if (news.getComments() != null) {
            	for (Comment comment : news.getComments()) {
        	        if (username.equals(comment.getUser().getUsername())) {
        	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
        	                    .body(new Message("El usuario ya ha realizado un comentario en esta noticia"));
        	        }
                }
            }
           
            Optional<User> findAuthor = userService.findByUsername(username);
            
            if (!findAuthor.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Message("El usuario actual no existe"));
            }
            
            User author = findAuthor.get();

            Comment newComment = new Comment();
            BeanUtils.copyProperties(commentDto, newComment, "user","news");

            newComment.setNews(news);
            newComment.setUser(author);
            newComment.setCreationDate(LocalDate.now());
            Comment createdComment = commentService.save(newComment);

            return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
            
        } catch (InexistentEntity e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
          return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        
    }

    @PreAuthorize(IS_ADMIN_OR_IS_USER)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") long id) {
    	
    	try {
            Optional<Comment> foundComment = commentService.findById(id);

            if (!foundComment.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe el comentario indicado"));
            }

            Comment commentToDelete = foundComment.get();
            ValidationUtils.accessRestrictedObjectById(commentToDelete.getUser().getId(), userService, "un comentario");
        
            commentService.deleteById(commentToDelete.getId());

            return new ResponseEntity<>(new Message("Comentario eliminado"), HttpStatus.OK);
            
    	} catch (InexistentEntity e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (RestrictedEntity e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
        
    }
    
}