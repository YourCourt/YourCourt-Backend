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

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yourcourt.model.Product;
import yourcourt.model.ProductType;
import yourcourt.repository.ProductRepository;


@Service
public class ProductService {
	
	private ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Transactional(readOnly = true)
	public Optional<Product> findProductById(final Long id) throws DataAccessException {
		return this.productRepository.findById(id);
	}
	
	@Transactional
	public void saveProduct(final Product product) throws DataAccessException {
		this.productRepository.save(product);
	}
	
	@Transactional(readOnly = true)
	public ProductType findProductTypeById(final int productTypeId) throws DataAccessException {
		return this.productRepository.findProductTypeById(productTypeId);
	}

	@Transactional(readOnly = true)
	public List<ProductType> findProductTypes() throws DataAccessException {
		return this.productRepository.findProductTypes();
	}
}
