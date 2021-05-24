package yourcourt.repository;


import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import yourcourt.model.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long>{
	

}
