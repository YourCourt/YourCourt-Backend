package yourcourt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yourcourt.model.ProductPurchase;
import yourcourt.model.projections.ProductPurchaseProjection;

@Repository
public interface ProductPurchaseRepository extends CrudRepository<ProductPurchase, Long> {

    @Query("SELECT p FROM ProductPurchase p WHERE p.id=:id")
  Optional<ProductPurchaseProjection> findProductPurchaseProjectionById(@Param("id") Long id);

  @Query("SELECT p FROM ProductPurchase p ORDER BY p.creationDate")
  Iterable<ProductPurchaseProjection> findAllProductPurchaseProjections();

  @Query("SELECT p FROM ProductPurchase p where p.user.username=:username ORDER BY p.creationDate")
  Iterable<ProductPurchaseProjection> findProductPurchasesFromUser(@Param("username") String username);
}
