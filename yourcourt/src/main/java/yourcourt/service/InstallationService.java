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

import yourcourt.model.Installation;
import yourcourt.model.InstallationType;
import yourcourt.repository.InstallationRepository;


@Service
public class InstallationService {
	
	private InstallationRepository installationRepository;

	@Autowired
	public InstallationService(InstallationRepository installationRepository) {
		this.installationRepository = installationRepository;
	}
	
	@Transactional(readOnly = true)
	public Optional<Installation> findInstallationById(final int id) throws DataAccessException {
		return this.installationRepository.findById(id);
	}
	
	@Transactional
	public void saveInstallation(final Installation installation) throws DataAccessException {
		this.installationRepository.save(installation);
	}
	
	@Transactional(readOnly = true)
	public InstallationType findInstallationTypeById(final int installationTypeId) throws DataAccessException {
		return this.installationRepository.findInstallationTypeById(installationTypeId);
	}

	@Transactional(readOnly = true)
	public List<InstallationType> findInstallationTypes() throws DataAccessException {
		return this.installationRepository.findInstallationTypes();
	}
}
