package yourcourt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yourcourt.model.ProductBooking;

@Repository
public interface ProductBookingRepository extends CrudRepository<ProductBooking, Long> {}
