package yourcourt.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import yourcourt.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

}