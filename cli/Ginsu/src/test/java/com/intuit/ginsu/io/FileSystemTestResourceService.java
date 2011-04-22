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
 * //TODO Explain why this file exists and how it is used.
 *
 */
public class FileSystemTestResourceService extends FileSystemResourceService {

	@Inject
	public FileSystemTestResourceService(Logger logger) {
		super(logger);
	}

	@Override
	public File getAppScript(String scriptName) throws FileNotFoundException {
		// TODO Auto-generated method stub
		return this.getAppResourceFile(scriptName, true);
	}
	
	@Override
	protected String getTargetDir()
	{
		return "";
	}
	
	@Override
	protected String getDevResourcesDir()
	{
		return "";
	}
	
	@Override
	protected String getScriptsDir()
	{
		return this.getDevResourcesDir();
	}
}
