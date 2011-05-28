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
package com.intuit.tools.imat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * @author rpfeffer
 * @dateCreated Apr 14, 2011
 * 
 *              This Interface defines how application resource files can be
 *              retrieved. Application resource files are defined as files
 *              within the application's home directory.
 * 
 */
public interface IApplicationResourceService extends IPathAnalysis {

	/**
	 * Return a file found on the classpath as a {@link File}.
	 * 
	 * @param fileName
	 *            The name of the file to look for.
	 * @param skipClassloader
	 *            For implementations of this interface that use a Classloader
	 *            object, this parameter provides a way to get at the files
	 *            without using a classloader. This is helpful in instances
	 *            where you want extracted files that you can point to rather
	 *            than files embeded within a java archive file like a JAR or a
	 *            WAR
	 * @return The {@link File} which represents the Application resource
	 *         requested.
	 * @throws {@link FileNotFoundException} when the file given by fileName
	 *         cannot be found.
	 */
	public File getAppResourceFile(String fileName, boolean skipClassloader)
			throws FileNotFoundException;

	/**
	 * Return an application script as a {@link File}.
	 * 
	 * @param fileName
	 *            The name of the file to look for.
	 * @throws {@link FileNotFoundException} when the script given by scriptName
	 *         cannot be found.
	 */
	public File getAppScript(String scriptName) throws FileNotFoundException;

	/**
	 * Get the Properties defined in the given properties file.
	 * 
	 * @param propertiesFileName
	 *            The name of the properties file to retrieve.
	 * @return {@link Properties} object representing the properties of the
	 *         application. When the file defined by propertiesFileName does not
	 *         exist, the result is an empty properties object.
	 */
	public Properties getAppProperties(String propertiesFileName);
}
