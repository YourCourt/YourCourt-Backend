package yourcourt.controller;

import io.swagger.annotations.Api;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yourcourt.exceptions.user.InexistentEntity;
import yourcourt.model.Booking;
import yourcourt.model.Court;
import yourcourt.model.Product;
import yourcourt.model.ProductBooking;
import yourcourt.model.ProductBookingLine;
import yourcourt.model.ValidationUtils;
import yourcourt.model.dto.BookingDto;
import yourcourt.model.dto.Message;
import yourcourt.model.projections.BookingProjection;
import yourcourt.model.dto.ProductBookingLineDto;
import yourcourt.security.model.User;
import yourcourt.security.service.UserService;
import yourcourt.service.BookingService;
import yourcourt.service.CourtService;
import yourcourt.service.ProductService;

@RestController
@Api(tags = "Booking")
@RequestMapping("/bookings")
@CrossOrigin
public class BookingController {
  @Autowired
  private BookingService bookingService;

  @Autowired
  private UserService userService;

  @Autowired
  private CourtService courtService;

  @Autowired
  private ProductService productService;

  @GetMapping
  public ResponseEntity<?> getAllBookings() {
    return new ResponseEntity<>(bookingService.findAllBookings(), HttpStatus.OK);
  }

  // @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  @GetMapping("/{id}")
  public ResponseEntity<?> getBooking(@PathVariable("id") Long id) {
    try {
      BookingProjection booking = bookingService.findBookingById(id);

      return new ResponseEntity<>(booking, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/date")
  public ResponseEntity<?> getBookingsByDatetime(@RequestParam("date") String dateString,
      @RequestParam("courtId") Long courtId) {
    try {
      Date date;
      if (dateString != null) {
        String target = dateString;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date result = df.parse(target);
        date = result;
      } else {
        date = Date.from(Instant.now());
      }

      Iterable<List<String>> bookings = bookingService.findBookingsFromDate(date, courtId);

      return new ResponseEntity<>(bookings, HttpStatus.OK);
    } catch (ParseException e) {
      return new ResponseEntity<>(new Message("El formato debe ser yyyy-MMM-dd."), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/user")
  public ResponseEntity<?> getBookingsByUser(@RequestParam("username") String username) {

    try {
      Iterable<BookingProjection> bookings = bookingService.findBookingsFromUser(username);

      return new ResponseEntity<>(bookings, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping
  public ResponseEntity<?> createBooking(@Valid @RequestBody BookingDto bookingDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(bindingResult));
    }
    try {
      // Booking
      LocalDate creationDate = LocalDate.now();
      Booking newBooking = new Booking(creationDate, bookingDto.getStartDate(), bookingDto.getEndDate());
      User user = userService.findUserById(bookingDto.getUser());
      Court court = courtService.findCourtById(bookingDto.getCourt());
      newBooking.setUser(user);
      newBooking.setCourt(court);
      Booking bookingCreated = bookingService.saveBooking(newBooking);

      // ProductBooking
      ProductBooking productBooking = new ProductBooking();
      productBooking.setBooking(bookingCreated);
      ProductBooking productBookingCreated = bookingService.saveProductBooking(productBooking);

      bookingCreated.setProductBooking(productBookingCreated);

      // ProductBookingLine
      for (ProductBookingLineDto line : bookingDto.getLines()) {
        ProductBookingLine productBookingLine = new ProductBookingLine(line.getQuantity(), line.getDiscount());
        productBookingLine.setProductBooking(productBookingCreated);
        Product product = productService.findProductById(line.getProductId());
        productBookingLine.setProduct(product);
        bookingService.saveProductBookingLine(productBookingLine);
      }

      return new ResponseEntity<>(bookingCreated, HttpStatus.CREATED);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteBooking(@PathVariable("id") Long id) {
    try {
      bookingService.deleteBookingById(id);
      return new ResponseEntity<>(new Message("Reserva eliminada"), HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
}
