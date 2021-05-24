package yourcourt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yourcourt.model.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {}
