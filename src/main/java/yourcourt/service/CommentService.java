package yourcourt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yourcourt.model.Comment;
import yourcourt.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Optional<Comment> findById(final Long id) {
        return commentRepository.findById(id);
    }

    public Comment save(Comment comment) {
        Comment newComment = null;
        try {
            newComment = commentRepository.save(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newComment;
    }
    
    public void deleteById(long id) {
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
