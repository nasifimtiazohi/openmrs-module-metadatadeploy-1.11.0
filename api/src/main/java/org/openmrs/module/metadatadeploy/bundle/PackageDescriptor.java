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

package org.openmrs.module.metadatadeploy.bundle;

import org.openmrs.module.metadatasharing.ImportMode;

/**
 * Describes a metadata package (MDS)
 */
public class PackageDescriptor {

	private String filename;

	private ClassLoader classLoader;

	private String groupUuid;

    private ImportMode importMode;

    /**
     * Constructs a package descriptor
     * @param filename the filename
     * @param classLoader the classLoader
     * @param groupUuid the group UUID
     * @param importMode the import mode to use when importing
     */
    public PackageDescriptor(String filename, ClassLoader classLoader, String groupUuid, ImportMode importMode) {
        this.filename = filename;
        this.classLoader = classLoader;
        this.groupUuid = groupUuid;
        this.importMode = importMode;
    }

	/**
	 * Constructs a package descriptor
	 * @param filename the filename
	 * @param classLoader the classLoader
	 * @param groupUuid the group UUID
	 */
	public PackageDescriptor(String filename, ClassLoader classLoader, String groupUuid) {
		this.filename = filename;
		this.classLoader = classLoader;
		this.groupUuid = groupUuid;
	}

	/**
	 * Gets the filename
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Gets the class loader
	 * @return the class loader
	 */
	public ClassLoader getClassLoader() {
		return classLoader;
	}

	/**
	 * Gets the group UUID
	 * @return the group UUID
	 */
	public String getGroupUuid() {
		return groupUuid;
	}

    public ImportMode getImportMode() {
        return importMode;
    }
}