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

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yourcourt.exceptions.InexistentEntity;
import yourcourt.model.Booking;
import yourcourt.model.ProductBooking;
import yourcourt.model.ProductBookingLine;
import yourcourt.model.projections.BookingProjection;
import yourcourt.repository.BookingRepository;
import yourcourt.repository.ProductBookingLineRepository;
import yourcourt.repository.ProductBookingRepository;

@Service
public class BookingService {
  private BookingRepository bookingRepository;

  private ProductBookingRepository productBookingRepository;

  private ProductBookingLineRepository productBookingLineRepository;

  @Autowired
  public BookingService(BookingRepository bookingRepository, ProductBookingRepository productBookingRepository,
      ProductBookingLineRepository productBookingLineRepository) {
    this.bookingRepository = bookingRepository;
    this.productBookingRepository = productBookingRepository;
    this.productBookingLineRepository = productBookingLineRepository;
  }

  // Booking
  @Transactional(readOnly = true)
  public BookingProjection findBookingById(final Long id) throws DataAccessException {
    return this.bookingRepository.findBookingProjectionById(id).orElseThrow(() -> new InexistentEntity("Reserva"));
  }

  public Iterable<BookingProjection> findAllBookings() {
    return this.bookingRepository.findAllBookingProjections();
  }

  public Iterable<List<String>> findBookingsFromDate(Date date, Long courtId) {
    return this.bookingRepository.findBookingsFromDate(date,courtId);
  }

  public Iterable<BookingProjection> findBookingsFromUser(String username) {
    return this.bookingRepository.findBookingsFromUser(username);
  }

  @Transactional
  public Booking saveBooking(final Booking booking) throws DataAccessException {
    Booking newBooking = this.bookingRepository.save(booking);
    return newBooking;
  }

  public void deleteBookingById(Long id) {
    Booking booking = bookingRepository.findById(id).orElseThrow(() -> new InexistentEntity("Reserva"));
    bookingRepository.delete(booking);
  }

  // ProductBooking
  @Transactional(readOnly = true)
  public ProductBooking findProductBookingById(final Long id) throws DataAccessException {
    return this.productBookingRepository.findById(id).orElseThrow(() -> new InexistentEntity("Reserva"));
  }

  @Transactional
  public ProductBooking saveProductBooking(final ProductBooking productBooking) throws DataAccessException {
    ProductBooking newProductBooking = this.productBookingRepository.save(productBooking);
    return newProductBooking;
  }

  public void deleteProductBookingById(Long id) {
    ProductBooking productBooking = productBookingRepository.findById(id)
        .orElseThrow(() -> new InexistentEntity("Reserva"));
    productBookingRepository.delete(productBooking);
  }

  // ProductBookingLine
  @Transactional(readOnly = true)
  public ProductBookingLine findProductBookingLineById(final Long id) throws DataAccessException {
    return this.productBookingLineRepository.findById(id).orElseThrow(() -> new InexistentEntity("Linea de reserva"));
  }

  @Transactional
  public ProductBookingLine saveProductBookingLine(final ProductBookingLine productBookingLine)
      throws DataAccessException {
    ProductBookingLine newProductBookingLine = this.productBookingLineRepository.save(productBookingLine);
    return newProductBookingLine;
  }

  public void deleteProductBookingLineById(Long id) {
    ProductBookingLine productBookingLine = productBookingLineRepository.findById(id)
        .orElseThrow(() -> new InexistentEntity("Linea de reserva"));
    productBookingLineRepository.delete(productBookingLine);
  }
}
