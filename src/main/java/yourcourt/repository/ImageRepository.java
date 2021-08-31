package yourcourt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import yourcourt.model.Image;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

}
