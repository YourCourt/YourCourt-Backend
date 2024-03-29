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
import yourcourt.model.Inscription;
import yourcourt.model.dto.InscriptionDto;
import yourcourt.model.projections.InscriptionProjection;
import yourcourt.repository.InscriptionRepository;

@Service
public class InscriptionService {
	
  /**
   *
   */
  private static final String INSCRIPCION = "Inscripcion";
  private InscriptionRepository inscriptionRepository;

  @Autowired
  public InscriptionService(InscriptionRepository inscriptionRepository) {
    this.inscriptionRepository = inscriptionRepository;
  }
  
  public Iterable<Inscription> findAllInscriptions() {
	    return this.inscriptionRepository.findAll();
	  }

  @Transactional(readOnly = true)
  public Inscription findInscriptionById(final Long id) throws DataAccessException {
    return this.inscriptionRepository.findById(id)
      .orElseThrow(() -> new InexistentEntity(INSCRIPCION));
  }
  
  @Transactional(readOnly = true)
  public Inscription findInscriptionByName(String name, String surnames) throws DataAccessException {
    return this.inscriptionRepository.findInscriptionByName(name, surnames).orElse(null);
  }

  @Transactional(readOnly = true)
  public boolean   existsInscriptionByName(String name, String surnames) throws DataAccessException {
    return this.inscriptionRepository.existsInscriptionByName(name, surnames);
  }

  
  @Transactional(readOnly = true)
  public Iterable<InscriptionProjection> findAllInscriptionByCourseId(final Long courseId) throws DataAccessException {
    return this.inscriptionRepository.findAllInscriptionsByCourseId(courseId);
  }

  public Iterable<Inscription> findAllInscriptionsByUserUsername(String username) {
    return this.inscriptionRepository.findAllInscriptionsByUserUsername(username);
  }

  @Transactional
  public Inscription saveInscription(final Inscription inscription) throws DataAccessException {
	  Inscription newInscription = this.inscriptionRepository.save(inscription);
    return newInscription;
  }

  public Inscription updateInscription(Inscription inscriptionToUpdate, InscriptionDto inscriptionRequest) {
    BeanUtils.copyProperties(inscriptionRequest, inscriptionToUpdate);
    this.inscriptionRepository.save(inscriptionToUpdate);
    return inscriptionToUpdate;
  }

  public void deleteInscriptionById(Long id) {
    Inscription inscription = inscriptionRepository
      .findById(id)
      .orElseThrow(() -> new InexistentEntity(INSCRIPCION));
    inscriptionRepository.delete(inscription);
  }
  
  public void deleteInscriptions(Iterable<Inscription> inscriptions) {
	    
	    inscriptionRepository.deleteAll(inscriptions);
	  }

public InscriptionProjection findInscriptionProjectionById(Long id) {
	return this.inscriptionRepository.findProjectionById(id)
		      .orElseThrow(() -> new InexistentEntity(INSCRIPCION));
}

public Iterable<InscriptionProjection> findAllInscriptionProjections() {
	return this.inscriptionRepository.findAllInscriptionProjections();
}

public Iterable<InscriptionProjection> findAllInscriptionProjectionsByUserUsername(String username) {
	return this.inscriptionRepository.findAllInscriptionProjectionsByUserUsername(username);
	}
  
}

