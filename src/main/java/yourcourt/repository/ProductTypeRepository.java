package yourcourt.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yourcourt.model.ProductType;

@Repository
public interface ProductTypeRepository extends CrudRepository<ProductType, Long> {
  @Query(
    "select case when count(p)>= 1 then true else false end from Product p where p.productType.id = :productTypeId"
  )
  Boolean isUsedProductTypeById(Long productTypeId);

  @Query(
    "select case when count(type)> 0 then true else false end from ProductType type where type.typeName = :productType"
  )
  Boolean existsProductTypeByType(String productType);

  Optional<ProductType> findProductTypeByTypeName(String productType);
}
