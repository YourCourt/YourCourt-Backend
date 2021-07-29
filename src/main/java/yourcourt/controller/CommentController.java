package yourcourt.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import yourcourt.exceptions.user.InexistentEntity;
import yourcourt.model.Comment;
import yourcourt.model.News;
import yourcourt.model.ValidationUtils;
import yourcourt.model.dto.CommentDto;
import yourcourt.model.dto.Message;
import yourcourt.security.model.RoleType;
import yourcourt.security.model.User;
import yourcourt.security.service.UserService;
import yourcourt.service.CommentService;
import yourcourt.service.NewsService;

@RestController
@Api(tags = "Comment")
@RequestMapping("/comments")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDto commentDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(result));
        }
        
        try {
        	String username = userService.getCurrentUsername();

            if (username.equals("anonymousUser")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new Message("El usuario no tiene permiso de comentar una noticia sin registrarse"));
            }

            News news = newsService.findNewsById(commentDto.getNews());
            
            if (news.getComments() != null) {
            	for (Comment comment : news.getComments()) {
        	        if (username.equals(comment.getAuthor().getUsername())) {
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
            BeanUtils.copyProperties(commentDto, newComment, "itinerary");

            newComment.setNews(news);
            newComment.setAuthor(author);
            newComment.setCreateDate(LocalDateTime.now());
            Comment createdComment = commentService.save(newComment);

            return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
            
        } catch (InexistentEntity e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
          return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") long id) {
    	
    	try {
    		String username = userService.getCurrentUsername();
            Optional<Comment> foundComment = commentService.findById(id);

            if (!foundComment.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("No existe el comentario indicado"));
            }

            Comment commentToDelete = foundComment.get();
            Optional<User> foundUser = userService.findByUsername(username);

            if (!username.equals(commentToDelete.getAuthor().getUsername()) && !(foundUser.isPresent() && foundUser.get().getRoles().stream().anyMatch(r->r.getRoleType().equals(RoleType.ROLE_ADMIN)))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new Message("No puede eliminar un comentario de un itinerario del que no es creador"));
            }
            commentService.deleteById(commentToDelete.getId());

            return new ResponseEntity<>(new Message("Comentario eliminado"), HttpStatus.OK);
            
    	} catch (InexistentEntity e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
          return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        
    }
    
}