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
package com.intuit.ginsu.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * @author rpfeffer
 * @dateCreated Apr 14, 2011
 * 
 *              This Interface defines how application resource files can be
 *              retrieved.
 * 
 */
public interface IApplicationResourceService {

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
	 */
	public File getAppResourceFile(String fileName, boolean skipClassloader) throws FileNotFoundException;
	
	/**
	 * Return a file found on the classpath as a {@link File}.
	 * @param fileName The name of the file to look for.
	 */
	public File getAppScript(String scriptName) throws FileNotFoundException;
	
	/**
	 * 
	 * @param propertiesFileName
	 * @return {@link Properties} object representing the 
	 */
	public Properties getAppProperties(String propertiesFileName);
}
