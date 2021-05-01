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
import yourcourt.model.Facility;
import yourcourt.repository.FacilityRepository;


@Service
public class FacilityService {
	
	private FacilityRepository facilityRepository;

	@Autowired
	public FacilityService(FacilityRepository facilityRepository) {
		this.facilityRepository = facilityRepository;
	}
	
	@Transactional(readOnly = true)
	public Facility findFacilityById(final Long id) throws DataAccessException {
		return this.facilityRepository.findById(id).orElseThrow(() -> new InexistentEntity("Instalacion"));
	}
	
	@Transactional
	public Facility saveFacility(final Facility facility) throws DataAccessException {
		this.facilityRepository.save(facility);
		return facility;
	}

}
