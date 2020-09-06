/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.metadatadeploy.handler.impl;

import org.openmrs.Privilege;
import org.openmrs.annotation.Handler;
import org.openmrs.api.UserService;
import org.openmrs.module.metadatadeploy.ObjectUtils;
import org.openmrs.module.metadatadeploy.handler.AbstractObjectDeployHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Collections;

/**
 * Deployment handler for privileges
 */
@Handler(supports = { Privilege.class })
public class PrivilegeDeployHandler extends AbstractObjectDeployHandler<Privilege> {

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	/**
	 * @see org.openmrs.module.metadatadeploy.handler.ObjectDeployHandler#getIdentifier(org.openmrs.OpenmrsObject)
	 */
	@Override
	public String getIdentifier(Privilege obj) {
		return obj.getPrivilege();
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.handler.ObjectDeployHandler#fetch(String)
	 */
	@Override
	public Privilege fetch(String identifier) {
		return userService.getPrivilege(identifier);
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.handler.ObjectDeployHandler#save(org.openmrs.OpenmrsObject)
	 */
	@Override
	public Privilege save(Privilege obj) {
		return userService.savePrivilege(obj);
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.handler.ObjectDeployHandler#findAlternateMatch(org.openmrs.OpenmrsObject)
	 */
	@Override
	public Privilege findAlternateMatch(Privilege incoming) {
		return userService.getPrivilegeByUuid(incoming.getUuid());
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.handler.ObjectDeployHandler#uninstall(org.openmrs.OpenmrsObject, String)
	 * @param obj the object to uninstall
	 */
	@Override
	public void uninstall(Privilege obj, String reason) {
		userService.purgePrivilege(obj);
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.handler.ObjectDeployHandler#overwrite(org.openmrs.OpenmrsObject, org.openmrs.OpenmrsObject)
	 */
	@Override
	public void overwrite(Privilege incoming, Privilege existing) {
		// Do per-field copy of incoming to existing, excluding UUID
		ObjectUtils.overwrite(incoming, existing, Collections.singleton("uuid"));
	}
}