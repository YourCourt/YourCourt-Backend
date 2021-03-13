package yourcourt.repository;


import org.springframework.data.repository.CrudRepository;

import yourcourt.model.News;

public interface NewsRepository extends CrudRepository<News, Integer> {
	
}
