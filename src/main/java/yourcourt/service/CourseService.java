/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package yourcourt.service;

import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yourcourt.exceptions.InexistentEntity;
import yourcourt.model.Course;
import yourcourt.model.dto.CourseDto;
import yourcourt.model.projections.CourseProjection;
import yourcourt.repository.CourseRepository;

@Service
public class CourseService {
	
  private CourseRepository courseRepository;

  @Autowired
  public CourseService(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Transactional(readOnly = true)
  public Course findCourseById(final Long id) throws DataAccessException {
    return this.courseRepository.findById(id)
      .orElseThrow(() -> new InexistentEntity("Curso"));
  }

  @Transactional(readOnly = true)
  public CourseProjection findCourseProjectionById(final Long id) throws DataAccessException {
    return this.courseRepository.findCourseProjectionById(id)
      .orElseThrow(() -> new InexistentEntity("Curso"));
  }

  public Iterable<CourseProjection> findAllCourses() {
    return this.courseRepository.findAllCourseProjections();
  }

  @Transactional
  public CourseProjection saveCourse(final Course course) throws DataAccessException {
	  Course newCourse = this.courseRepository.save(course);
    return findCourseProjectionById(newCourse.getId());
  }

  public CourseProjection updateCourse(Course courseToUpdate, CourseDto courseRequest) {
    BeanUtils.copyProperties(courseRequest, courseToUpdate);
    this.courseRepository.save(courseToUpdate);
    return findCourseProjectionById(courseToUpdate.getId());
  }

  public void deleteCourseById(Long id) {
	  Course course = courseRepository
      .findById(id)
      .orElseThrow(() -> new InexistentEntity("Inscripción"));
	  courseRepository.delete(course);
  }
}
