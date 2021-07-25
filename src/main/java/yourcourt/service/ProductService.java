package yourcourt.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yourcourt.exceptions.user.InexistentEntity;
import yourcourt.model.Product;
import yourcourt.model.ProductType;
import yourcourt.model.dto.ProductDto;
import yourcourt.model.projections.ProductProjection;
import yourcourt.repository.ProductRepository;
import yourcourt.repository.ProductTypeRepository;

@Service
public class ProductService {
  private ProductRepository productRepository;

  private ProductTypeRepository productTypeRepository;

  @Autowired
  public ProductService(
    ProductRepository productRepository,
    ProductTypeRepository productTypeRepository
  ) {
    this.productRepository = productRepository;
    this.productTypeRepository = productTypeRepository;
  }

  //Product
  @Transactional(readOnly = true)
  public Product findProductById(final Long id) throws DataAccessException {
    return this.productRepository.findById(id)
      .orElseThrow(() -> new InexistentEntity("Producto"));
  }

  @Transactional(readOnly = true)
  public ProductProjection findProductProjectionById(final Long id) throws DataAccessException {
    return this.productRepository.findProjectionById(id)
      .orElseThrow(() -> new InexistentEntity("Producto"));
  }

  public Iterable<Product> findAllProducts() {
    return this.productRepository.findAll();
  }

  public Iterable<ProductProjection> findProductsByProductType(final String typeName) throws DataAccessException {
    return this.productRepository.findProductsByProductType(typeName);
  }
  
///Servicio
 public Iterable<ProductProjection> findBookableProductsByProductType(final String typeName) throws DataAccessException {
    return this.productRepository.findBookableProductsByProductType(typeName);
  }

  @Transactional
  public Product saveProduct(final Product product) throws DataAccessException {
    Product newProduct = this.productRepository.save(product);
    return newProduct;
  }

  public Product updateProduct(Product productToUpdate, ProductDto productRequest) {
	  BeanUtils.copyProperties(productRequest, productToUpdate,"productType");
	  ProductType productType;
	  Long productTypeId=productToUpdate.getProductType().getId();
	  if(existsProductTypeByName(productRequest.getProductType())==false) { //If the product_type entered already exists
	    	productType = new ProductType(productRequest.getProductType());
	    	productToUpdate.setProductType(productType);
	    	saveProductType(productType);
			checkUnusedProductTypeById(productTypeId); //Checks if the previous product_type remains unused
	    }else {
	    	productType = findProductTypeByType(productRequest.getProductType());
	    	productToUpdate.setProductType(productType);
	    	checkUnusedProductTypeById(productTypeId);//Checks if the previous product_type remains unused
	    }
    
    this.productRepository.save(productToUpdate);
    return productToUpdate;
  }

  public void deleteProductById(Long id) {
    Product product = productRepository
      .findById(id)
      .orElseThrow(() -> new InexistentEntity("Producto"));
    Long productTypeId=product.getProductType().getId();

    productRepository.delete(product);
    //If after deleting that product, its product_type is not used in another product, it will be deleted too.
    if (isUsedProductTypeById(productTypeId) == false) { 
        deleteProductTypeById(productTypeId);
      }
  }

  //ProductType

  @Transactional(readOnly = true)
  public ProductType findProductTypeById(final Long id) throws DataAccessException {
    return this.productTypeRepository.findById(id)
      .orElseThrow(() -> new InexistentEntity("Tipo de producto"));
  }

  public ProductType findProductTypeByType(final String type) throws DataAccessException {
    return this.productTypeRepository.findProductTypeByTypeName(type)
      .orElseThrow(() -> new InexistentEntity("Tipo de producto"));
  }

  public Iterable<ProductType> findAllProductTypes() {
    return this.productTypeRepository.findAll();
  }

  public boolean existsProductTypeByName(String type) {
    return this.productTypeRepository.existsProductTypeByType(type);
  }

  /**Checks if a product_type is used by one product at least*/
  public boolean isUsedProductTypeById(Long id) {
	    return this.productTypeRepository.isUsedProductTypeById(id);
	  }

  public void checkUnusedProductTypeById(Long id) {
    boolean isUsed = isUsedProductTypeById(id);
    if (isUsed == false) {
      deleteProductTypeById(id);
    }
  }

  @Transactional
  public ProductType saveProductType(final ProductType productType)
    throws DataAccessException {
    ProductType newProductType = this.productTypeRepository.save(productType);
    return newProductType;
  }

  public void deleteProductTypeById(Long id) {
    ProductType productType = productTypeRepository
      .findById(id)
      .orElseThrow(() -> new InexistentEntity("Tipo de producto"));
    productTypeRepository.delete(productType);
  }
}
