package yourcourt.repository;


import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import yourcourt.model.Course;
import yourcourt.model.projections.CourseProjection;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long>{
	
    
    @Query("SELECT c FROM Course c WHERE c.id=:id")
    Optional<CourseProjection> findCourseProjectionById(@Param("id") Long id);

    @Query("SELECT c FROM Course c")
    Collection<CourseProjection> findAllCourseProjections();
  
}
