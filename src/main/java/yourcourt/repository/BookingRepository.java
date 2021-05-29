package yourcourt.repository;

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
}
