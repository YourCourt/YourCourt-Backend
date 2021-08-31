package yourcourt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yourcourt.model.ProductBookingLine;

@Repository
public interface ProductBookingLineRepository
  extends CrudRepository<ProductBookingLine, Long> {}
