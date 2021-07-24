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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yourcourt.exceptions.user.InexistentEntity;
import yourcourt.model.Product;
import yourcourt.model.ProductPurchase;
import yourcourt.model.ProductPurchaseLine;
import yourcourt.model.projections.ProductPurchaseProjection;
import yourcourt.repository.ProductPurchaseLineRepository;
import yourcourt.repository.ProductPurchaseRepository;
import yourcourt.repository.ProductRepository;

@Service
public class ProductPurchaseService {

  private ProductPurchaseRepository productPurchaseRepository;

  private ProductPurchaseLineRepository productPurchaseLineRepository;

  private ProductRepository productRepository;

  @Autowired
  public ProductPurchaseService(ProductPurchaseRepository productPurchaseRepository,
      ProductPurchaseLineRepository productPurchaseLineRepository, ProductRepository productRepository) {
    this.productPurchaseRepository = productPurchaseRepository;
    this.productPurchaseLineRepository = productPurchaseLineRepository;
    this.productRepository = productRepository;
  }

  // ProductPurchase
  @Transactional(readOnly = true)
  public ProductPurchaseProjection findProductPurchaseById(final Long id) throws DataAccessException {
    return this.productPurchaseRepository.findProductPurchaseProjectionById(id)
        .orElseThrow(() -> new InexistentEntity("Compra"));
  }

  public Iterable<ProductPurchaseProjection> findAllProductPurchases() {
    return this.productPurchaseRepository.findAllProductPurchaseProjections();
  }

  public Iterable<ProductPurchaseProjection> findProductPurchasesFromUser(String username) {
    return this.productPurchaseRepository.findProductPurchasesFromUser(username);
  }

  @Transactional
  public ProductPurchase saveProductPurchase(final ProductPurchase productPurchase) throws DataAccessException {
    ProductPurchase newProductPurchase = this.productPurchaseRepository.save(productPurchase);
    return newProductPurchase;
  }

  public void deleteProductPurchaseById(Long id) {
    ProductPurchase productPurchase = productPurchaseRepository.findById(id)
        .orElseThrow(() -> new InexistentEntity("Compra"));

    for (ProductPurchaseLine line : productPurchase.getLines()) { // Update stock before deleting purchase
      Product product = line.getProduct();
      Integer quantity = line.getQuantity();
      Integer currentStock = line.getProduct().getStock();

      product.setStock(currentStock + quantity);
      productRepository.save(product);
    }

    productPurchaseRepository.delete(productPurchase);
  }

  // ProductPurchaseLine
  @Transactional(readOnly = true)
  public ProductPurchaseLine findProductPurchaseLineById(final Long id) throws DataAccessException {
    return this.productPurchaseLineRepository.findById(id).orElseThrow(() -> new InexistentEntity("Linea de compra"));
  }

  @Transactional
  public ProductPurchaseLine saveProductBookingLine(final ProductPurchaseLine productPurchaseLine)
      throws DataAccessException {
    ProductPurchaseLine newProductPurchaseLine = this.productPurchaseLineRepository.save(productPurchaseLine);
    return newProductPurchaseLine;
  }

  public void deleteProductPurchaseLineById(Long id) {
    ProductPurchaseLine productPurchaseLine = productPurchaseLineRepository.findById(id)
        .orElseThrow(() -> new InexistentEntity("Linea de compra"));
    productPurchaseLineRepository.delete(productPurchaseLine);
  }
}
