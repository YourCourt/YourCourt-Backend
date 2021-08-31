package yourcourt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yourcourt.model.ProductPurchaseLine;

@Repository
public interface ProductPurchaseLineRepository extends CrudRepository<ProductPurchaseLine, Long> {
}
