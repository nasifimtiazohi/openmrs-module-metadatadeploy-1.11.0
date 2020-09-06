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

import org.apache.commons.lang3.StringUtils;
import org.openmrs.GlobalProperty;
import org.openmrs.annotation.Handler;
import org.openmrs.api.AdministrationService;
import org.openmrs.module.metadatadeploy.handler.AbstractObjectDeployHandler;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Deployment handler for global properties
 */
@Handler(supports = { GlobalProperty.class })
public class GlobalPropertyDeployHandler extends AbstractObjectDeployHandler<GlobalProperty> {

	@Autowired
	@Qualifier("adminService")
	private AdministrationService adminService;

	/**
	 * @see org.openmrs.module.metadatadeploy.handler.ObjectDeployHandler#getIdentifier(org.openmrs.OpenmrsObject)
	 */
	@Override
	public String getIdentifier(GlobalProperty obj) {
		return obj.getProperty();
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.handler.ObjectDeployHandler#fetch(String)
	 */
	@Override
	public GlobalProperty fetch(String identifier) {
		return adminService.getGlobalPropertyObject(identifier);
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.handler.ObjectDeployHandler#save(org.openmrs.OpenmrsObject)
	 */
	@Override
	public GlobalProperty save(GlobalProperty obj) {
		return adminService.saveGlobalProperty(obj);
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.handler.ObjectDeployHandler#findAlternateMatch(org.openmrs.OpenmrsObject)
	 */
	@Override
	public GlobalProperty findAlternateMatch(GlobalProperty incoming) {
		return adminService.getGlobalPropertyByUuid(incoming.getUuid());
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.handler.ObjectDeployHandler#uninstall(org.openmrs.OpenmrsObject, String)
	 * @param obj the object to uninstall
	 */
	@Override
	public void uninstall(GlobalProperty obj, String reason) {
		adminService.purgeGlobalProperty(obj);
	}

	/**
	 * @param incoming
	 * @param existing
	 * @see org.openmrs.module.metadatadeploy.handler.AbstractObjectDeployHandler#overwrite(org.openmrs.OpenmrsObject, org.openmrs.OpenmrsObject)
	 */
	@Override
	public void overwrite(GlobalProperty incoming, GlobalProperty existing) {
		boolean datatypeMatches = OpenmrsUtil.nullSafeEquals(incoming.getDatatypeClassname(), existing.getDatatypeClassname());

		Object existingValue = existing.getValue();

		// We keep the existing property value if the incoming property doesn't have a value and the datatypes match
		boolean preserveValue = !hasValue(incoming) && datatypeMatches;

		super.overwrite(incoming, existing);

		// The value field won't have been copied as it is transient, so we need to explicitly set the value now
		Object value = preserveValue ? existingValue : incoming.getValue();
		if (value != null) {
			existing.setValue(value);
		}
	}

	/**
	 * Global properties don't really distinguish between blank and null values since the UI doesn't let a user
	 * distinguish between the two. This method determines if a global property has a value.
	 * @param obj the global property
	 * @return true if it has value
	 */
	private boolean hasValue(GlobalProperty obj) {
		Object val = obj.getValue();
		if (val == null) {
			return false;
		}
		else if (val instanceof String) {
			return StringUtils.isNotEmpty((String) val);
		}
		return true;
	}
}