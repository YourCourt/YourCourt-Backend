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
import yourcourt.model.Court;
import yourcourt.model.dto.CourtDto;
import yourcourt.model.projections.BookingProjection;
import yourcourt.repository.BookingRepository;
import yourcourt.repository.CourtRepository;

@Service
public class CourtService {
  private CourtRepository courtRepository;

  private BookingRepository bookingRepository;

  @Autowired
  public CourtService(CourtRepository courtRepository, BookingRepository bookingRepository) {
    this.courtRepository = courtRepository;
    this.bookingRepository = bookingRepository;
  }

  public Iterable<Court> findAllCourts() {
    return courtRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Court findCourtById(final Long id) throws DataAccessException {
    return this.courtRepository.findById(id)
      .orElseThrow(() -> new InexistentEntity("Pista"));
  }

  @Transactional
  public Court saveCourt(final Court court) throws DataAccessException {
    this.courtRepository.save(court);
    return court;
  }

  @Transactional
  public Court updateCourt(Court courtToUpdate, CourtDto courtRequest) {
    BeanUtils.copyProperties(courtRequest, courtToUpdate);
    Court courtUpdated = courtRepository.save(courtToUpdate);
    return courtUpdated;
  }

  public void deleteCourtById(Long id) {
    Court court = courtRepository
      .findById(id)
      .orElseThrow(() -> new InexistentEntity("Pista"));
    courtRepository.delete(court);
  }

  public boolean existsCourtById(Long id) {
    return courtRepository.existsById(id);
  }

  public Iterable<BookingProjection> findBookingsByCourt(Long courtId){
    return bookingRepository.findBookingsByCourt(courtId);
  }
}
