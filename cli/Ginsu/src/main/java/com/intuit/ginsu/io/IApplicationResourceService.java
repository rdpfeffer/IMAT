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

/**
 * @author rpfeffer
 * @dateCreated Apr 14, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public interface IApplicationResourceService {

	/**
	 * Return a file found on the classpath as a {@link File}.
	 * @param fileName The name of the file to look for.
	 */
	public File getAppResourceFile(String fileName) throws FileNotFoundException;
	
	/**
	 * Return a file found on the classpath as a {@link File}.
	 * @param fileName The name of the file to look for.
	 */
	public File getScript(String scriptName) throws FileNotFoundException;
}