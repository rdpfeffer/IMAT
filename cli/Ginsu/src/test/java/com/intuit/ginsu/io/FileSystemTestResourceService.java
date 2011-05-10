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

import org.apache.log4j.Logger;

import com.google.inject.Inject;

/**
 * @author rpfeffer
 * @dateCreated Apr 17, 2011
 * 
 *              This class Mocks out the FileSystem resource service for testing
 *              of the AntScript Test launcher.
 * 
 */
public class FileSystemTestResourceService extends FileSystemResourceService {

	/**
	 * @param logger
	 * @param pathAnalyzer
	 */
	@Inject
	public FileSystemTestResourceService(Logger logger, PathAnalyzer pathAnalyzer) {
		super(logger, pathAnalyzer);
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.io.FileSystemResourceService#getAppScript(java.lang.String)
	 */
	@Override
	public File getAppScript(String scriptName) throws FileNotFoundException {
		return this.getAppResourceFile(scriptName, true);
	}
	
	/* (non-Javadoc)
	 * @see com.intuit.ginsu.io.FileSystemResourceService#getTargetDir()
	 */
	@Override
	protected String getTargetDir()
	{
		return "";
	}
	
	/* (non-Javadoc)
	 * @see com.intuit.ginsu.io.FileSystemResourceService#getDevResourcesDir()
	 */
	@Override
	protected String getDevResourcesDir()
	{
		return "";
	}
	
	/* (non-Javadoc)
	 * @see com.intuit.ginsu.io.FileSystemResourceService#getScriptsDir()
	 */
	@Override
	protected String getScriptsDir()
	{
		return this.getDevResourcesDir();
	}
}
