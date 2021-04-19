package yourcourt.repository;


import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import yourcourt.model.Product;
import yourcourt.model.ProductType;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{
	

}
