package yourcourt.repository;




import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import yourcourt.model.Product;
import yourcourt.model.projections.ProductProjection;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{

  @Query("select p from Product as p where p.id=:id")
  Optional<ProductProjection> findProjectionById(@Param("id") Long id);
	
    @Query("select p from Product as p where p.productType.typeName=:typeName")
  Iterable<ProductProjection> findProductsByProductType(@Param("typeName") String typeName);
}
