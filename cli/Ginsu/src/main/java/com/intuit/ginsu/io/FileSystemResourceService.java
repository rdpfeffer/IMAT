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
import java.net.URL;

import com.intuit.ginsu.AppContext;

/**
 * @author rpfeffer
 * @dateCreated Apr 14, 2011
 * 
 *              This Service retrieves resources from the file system handling
 *              all of the different ways the application could be run.
 *              Regardless of when in the software development lifecycle, or
 *              where the application is launched, this class should be able to
 *              find the application resoruce.
 * 
 */
public class FileSystemResourceService implements IApplicationResourceService, IProjectResourceService {

	private static final String TARGET_RESOURCES_DIR = "target" + File.separator + "classes" + File.separator;
	private static final String DEV_RESOURCE_DIR = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
	private static final String SCRIPTS_DIR = "scripts" + File.separator;
	
	/* (non-Javadoc)
	 * @see com.intuit.ginsu.io.IResourceManager#getResourceFile(java.lang.String)
	 */
	public File getAppResourceFile(String fileName, boolean skipClassloader) throws FileNotFoundException {
		File resourceFile;
		URL fileUrl = null;
		if(!skipClassloader)
		{
			//note that the classloader also returns null when the class cannot be found.
			fileUrl = ClassLoader.getSystemResource(fileName);
		}
		if (fileUrl == null)
		{
			//if we don't get a url back, look for it in the current directory.
			resourceFile = new File(getHomeDir() + fileName);
			if (!resourceFile.exists())
			{
				//try again in the target directory.
				resourceFile = new File(getTargetDir() + fileName);
			}
			
			if (!resourceFile.exists())
			{
				//if we still do not get what we want, try to get it from the development
				//directory structure.
				resourceFile = new File(getDevResourcesDir() + fileName);
			}
		}
		else
		{
			resourceFile =  new File(fileUrl.getPath());
		}
		if (!resourceFile.exists())
		{
			throw new FileNotFoundException("Could not find file: " + fileName);
		}
		return resourceFile;
	}

	public File getProjectResourceFile(String fileName)
			throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public File getAppScript(String scriptName) throws FileNotFoundException {
		// TODO Auto-generated method stub
		return this.getAppResourceFile(getScriptsDir() + scriptName, true);
	}
	
	protected String getHomeDir()
	{
		return AppContext.getInstance().getProperty(AppContext.APP_HOME_KEY) + File.separator;
	}
	
	protected String getTargetDir()
	{
		return TARGET_RESOURCES_DIR;
	}
	
	protected String getDevResourcesDir()
	{
		return DEV_RESOURCE_DIR;
	}
	
	protected String getScriptsDir()
	{
		return SCRIPTS_DIR;
	}

}
