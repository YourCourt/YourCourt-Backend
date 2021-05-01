/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package yourcourt.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yourcourt.exceptions.user.InexistentEntity;
import yourcourt.model.Product;
import yourcourt.model.dto.ProductDto;
import yourcourt.repository.ProductRepository;

@Service
public class ProductService {
  private ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Transactional(readOnly = true)
  public Product findProductById(final Long id) throws DataAccessException {
    return this.productRepository.findById(id)
      .orElseThrow(() -> new InexistentEntity("Producto"));
  }

  public Iterable<Product> findAllProducts() {
    return this.productRepository.findAll();
  }

  @Transactional
  public Product saveProduct(final Product product) throws DataAccessException {
    Product newProduct = this.productRepository.save(product);
    return newProduct;
  }

  public Product updateProduct(Product productToUpdate, ProductDto productRequest) {
    BeanUtils.copyProperties(productRequest, productToUpdate);
    this.productRepository.save(productToUpdate);
    return productToUpdate;
  }

  public void deleteProductById(Long id) {
    Product product = productRepository
      .findById(id)
      .orElseThrow(() -> new InexistentEntity("Producto"));
    productRepository.delete(product);
  }
}
