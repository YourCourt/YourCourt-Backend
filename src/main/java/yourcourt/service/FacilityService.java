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
import yourcourt.exceptions.InexistentEntity;
import yourcourt.model.Facility;
import yourcourt.model.FacilityType;
import yourcourt.model.dto.FacilityDto;
import yourcourt.repository.FacilityRepository;
import yourcourt.repository.FacilityTypeRepository;

@Service
public class FacilityService {
  private FacilityRepository facilityRepository;
  private FacilityTypeRepository facilityTypeRepository;

  @Autowired
  public FacilityService(
    FacilityRepository facilityRepository,
    FacilityTypeRepository facilityTypeRepository
  ) {
    this.facilityRepository = facilityRepository;
    this.facilityTypeRepository = facilityTypeRepository;
  }

  public Iterable<Facility> findAllFacilities() {
    return facilityRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Facility findFacilityById(final Long id) throws DataAccessException {
    return this.facilityRepository.findById(id)
      .orElseThrow(() -> new InexistentEntity("Instalacion"));
  }

  @Transactional
  public Facility saveFacility(final Facility facility) throws DataAccessException {
    this.facilityRepository.save(facility);
    return facility;
  }
  
  public Facility updateFacility(Facility facilityToUpdate, FacilityDto facilityRequest) {
	  BeanUtils.copyProperties(facilityRequest, facilityToUpdate,"facilityType");
	  FacilityType facilityType;
	  Long facilityTypeId=facilityToUpdate.getFacilityType().getId();
	  if(existsFacilityTypeByName(facilityRequest.getFacilityType())==false) { //If the facility_type entered already exists
		  facilityType = new FacilityType(facilityRequest.getFacilityType());
		  facilityToUpdate.setFacilityType(facilityType);
	    	saveFacilityType(facilityType);
			checkUnusedFacilityTypeById(facilityTypeId); //Checks if the previous facility_type remains unused
	    }else {
	    	facilityType = findFacilityTypeByType(facilityRequest.getFacilityType());
	    	facilityToUpdate.setFacilityType(facilityType);
	    	checkUnusedFacilityTypeById(facilityTypeId);//Checks if the previous facility_type remains unused
	    }
    
    this.facilityRepository.save(facilityToUpdate);
    return facilityToUpdate;
  }
  
  public void deleteFacilityById(Long id) {
	  Facility facility = facilityRepository
	      .findById(id)
	      .orElseThrow(() -> new InexistentEntity("Instalacion"));
	    Long facilityTypeId=facility.getFacilityType().getId();

	    facilityRepository.delete(facility);
	    //If after deleting that facility, its facility_type is not used in another facility, it will be deleted too.
	    if (isUsedFacilityTypeById(facilityTypeId) == false) { 
	        deleteFacilityTypeById(facilityTypeId);
	      }
	  }

  //FacilityType
  @Transactional(readOnly = true)
  public FacilityType findFacilityTypeById(final Long id) throws DataAccessException {
    return this.facilityTypeRepository.findById(id)
      .orElseThrow(() -> new InexistentEntity("Tipo de instalacion"));
  }

  public FacilityType findFacilityTypeByType(final String type)
    throws DataAccessException {
    return this.facilityTypeRepository.findFacilityTypeByTypeName(type)
      .orElseThrow(() -> new InexistentEntity("Tipo de instalacion"));
  }

  public Iterable<FacilityType> findAllFacilityTypes() {
    return this.facilityTypeRepository.findAll();
  }

  public boolean existsFacilityTypeByName(String type) {
    return this.facilityTypeRepository.existsFacilityTypeByType(type);
  }

  /**Checks if a facility_type is used by one facility at least*/
  public boolean isUsedFacilityTypeById(Long id) {
    return this.facilityTypeRepository.isUsedFacilityTypeById(id);
  }

  public void checkUnusedFacilityTypeById(Long id) {
    boolean isUsed = isUsedFacilityTypeById(id);
    if (isUsed == false) {
      deleteFacilityTypeById(id);
    }
  }

  @Transactional
  public FacilityType saveFacilityType(final FacilityType facilityType)
    throws DataAccessException {
    FacilityType newFacilityType = this.facilityTypeRepository.save(facilityType);
    return newFacilityType;
  }

  public void deleteFacilityTypeById(Long id) {
    FacilityType facilityType = facilityTypeRepository
      .findById(id)
      .orElseThrow(() -> new InexistentEntity("Tipo de instalacion"));
    facilityTypeRepository.delete(facilityType);
  }
}
