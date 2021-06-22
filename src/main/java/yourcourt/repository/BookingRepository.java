package yourcourt.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yourcourt.model.Booking;
import yourcourt.model.projections.BookingProjection;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
  @Query("SELECT b FROM Booking b WHERE b.id=:id")
  Optional<BookingProjection> findBookingProjectionById(@Param("id") Long id);

  @Query("SELECT b FROM Booking b ")
  Iterable<BookingProjection> findAllBookingProjections();

  @Query("select CONCAT(TO_CHAR(b.startDate,'HH24'),':',TO_CHAR(b.startDate,'MI')) as startDate, CONCAT(TO_CHAR(b.endDate ,'HH24'),':',TO_CHAR(b.endDate ,'MI')) as endDate from Booking b where :date=CAST(b.startDate as date) and b.court.id=:courtId")
  Iterable<List<String>> findBookingsFromDate(@Param("date") Date date, @Param("courtId") Long courtId);
}
