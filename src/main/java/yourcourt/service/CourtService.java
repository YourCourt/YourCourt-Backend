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

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yourcourt.model.Court;
import yourcourt.model.dto.CourtDto;
import yourcourt.repository.CourtRepository;


@Service
public class CourtService {
	
	private CourtRepository courtRepository;

	@Autowired
	public CourtService(CourtRepository courtRepository) {
		this.courtRepository = courtRepository;
	}
	
	public Iterable<Court> findAllCourts() {
        return courtRepository.findAll();
    }
	
	@Transactional(readOnly = true)
	public Optional<Court> findCourtById(final Long id) throws DataAccessException {
		return this.courtRepository.findById(id);
	}
	
	@Transactional
	public void saveCourt(final Court court) throws DataAccessException {
		this.courtRepository.save(court);
	}
	
	@Transactional
	public Court updateCourt(Court courtToUpdate, CourtDto courtRequest) {
		
		
		BeanUtils.copyProperties(courtRequest, courtToUpdate, "courtType");
		courtRepository.save(courtToUpdate);
		return courtToUpdate;
	}
	
	public void deleteCourtById(long id) {
		courtRepository.deleteById(id);
    }
	
	public boolean existsCourtById(long id) {
		return courtRepository.existsById(id);
    }
	
	/*
	@Transactional(readOnly = true)
	public CourtType findCourtTypeById(final int courtTypeId) throws DataAccessException {
		return this.courtRepository.findCourtTypeById(courtTypeId);
	}

	@Transactional(readOnly = true)
	public List<CourtType> findCourtTypes() throws DataAccessException {
		return this.courtRepository.findCourtTypes();
	}*/
}
