/*******************************************************************************
* Copyright (c) 2009 Intuit, Inc.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.opensource.org/licenses/eclipse-1.0.php
* 
* Contributors:
*     Intuit, Inc - initial API and implementation
*******************************************************************************/
package com.intuit.ginsu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * @author rpfeffer
 * @dateCreated Apr 14, 2011
 * 
 *              This Interface defines how project resource files can be
 *              retrieved. Whereas the IApplicationResourceService deals with
 *              Application Resource FIles, this interface deals only with
 *              resource files that are specific to a particular ginsu
 *              automation project.
 * 
 */
public interface IProjectResourceService {

	public static final String ENV_DIR = "env";
	
	/**
	 * Return a file found in the user's project as a {@link File}.
	 * @param fileName The name of the file to look for.
	 */
	public File getProjectResourceFile(String fileName) throws FileNotFoundException;
	
	/**
	 * 
	 * @param propertiesFileName
	 * @return {@link Properties} object representing the 
	 */
	public Properties getProjectProperties(String propertiesFileName);
	
}
