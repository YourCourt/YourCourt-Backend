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

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yourcourt.exceptions.user.InexistentEntity;
import yourcourt.model.News;
import yourcourt.model.dto.NewsDto;
import yourcourt.repository.NewsRepository;

@Service
public class NewsService {
	
	private NewsRepository newsRepository;

	@Autowired
	public NewsService(NewsRepository newsRepository) {
		this.newsRepository = newsRepository;
	}
	
	public Iterable<News> findAllNews() {
		
		return newsRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<News> findNewsById(final Long id) throws DataAccessException {
		return this.newsRepository.findById(id);
	}
	
	@Transactional
	public void saveNews(final News news) throws DataAccessException {
		this.newsRepository.save(news);
	}

	public News updateNews(News newsToUpdate, NewsDto newsRequest) {
		
		try {
			BeanUtils.copyProperties(newsRequest, newsToUpdate, "id");
			newsRepository.save(newsToUpdate);
		
		} catch (NoSuchElementException e) {
			throw new InexistentEntity("Noticia");
		}
		
		return newsToUpdate;
	}

	public boolean existsNewsById(long id) {
		return newsRepository.existsById(id);
    }

	public void deleteNewsById(long id) {
		newsRepository.deleteById(id);
		
	}
	

}
