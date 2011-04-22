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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
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
	private final Logger logger;

	@Inject
	public FileSystemResourceService(Logger logger)
	{
		this.logger = logger;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.intuit.ginsu.io.IResourceManager#getResourceFile(java.lang.String)
	 */
	public File getAppResourceFile(String fileName, boolean skipClassloader) throws FileNotFoundException {
		File resourceFile;
		URL fileUrl = null;
		if(!skipClassloader)
		{
			logger.trace("Looking for file on classpath. File: " + fileName);
			fileUrl = ClassLoader.getSystemResource(fileName);
		}
		if (fileUrl == null)
		{
			logger.trace("Looking for file in Home Directory. File: " + getHomeDir() + fileName);
			resourceFile = new File(getHomeDir() + fileName);
			if (!resourceFile.exists())
			{
				logger.trace("Looking for file in Target Directory (DEV ONLY). File: " + getTargetDir() + fileName);
				resourceFile = new File(getTargetDir() + fileName);
			}
			
			if (!resourceFile.exists())
			{
				logger.trace("Finally, Looking for file in Resources Directory (DEV ONLY). File: " + getDevResourcesDir() + fileName);
				resourceFile = new File(getDevResourcesDir() + fileName);
			}
		}
		else
		{
			logger.trace("Found File on the classpath: " + fileUrl.getPath());
			resourceFile =  new File(fileUrl.getPath());
		}
		if (!resourceFile.exists())
		{
			logger.debug("Failed all attempts to get file. Throwing Exception. File: " + fileName);
			throw new FileNotFoundException("Could not find file: " + fileName);
		}
		return resourceFile;
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.io.IProjectResourceService#getProjectResourceFile(java.lang.String)
	 */
	public File getProjectResourceFile(String fileName)
			throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.io.IApplicationResourceService#getAppScript(java.lang.String)
	 */
	public File getAppScript(String scriptName) throws FileNotFoundException {
		logger.debug("Looking for script. File: " + scriptName);
		return this.getAppResourceFile(getScriptsDir() + scriptName, true);
	}
	
	/* (non-Javadoc)
	 * @see com.intuit.ginsu.io.IApplicationResourceService#getAppProperties(java.lang.String)
	 */
	public Properties getAppProperties(String propertiesFileName)
	{
		Properties props = new Properties();
		try 
		{
			logger.debug("Looking for Properties File: " + propertiesFileName);
			props.load(new FileInputStream(this.getAppResourceFile(propertiesFileName, true)));
		} 
		catch (SecurityException e)
		{
			logger.debug("SecurityException. Properties will be empty. Message:" + e.getMessage());
		} catch (FileNotFoundException e) {
			logger.debug("FileNotFoundException. Properties will be empty. Message:" + e.getMessage());
		} catch (IOException e) {
			logger.debug("IOException. Properties will be empty. Message:" + e.getMessage());
		}
		return props;
	}
	
	/**
	 * TODO Doc Me
	 * @return
	 */
	protected String getHomeDir()
	{
		return AppContext.getInstance().getProperty(AppContext.APP_HOME_KEY) + File.separator;
	}
	
	/**
	 * TODO Doc Me
	 * @return
	 */
	protected String getTargetDir()
	{
		return TARGET_RESOURCES_DIR;
	}
	
	/**
	 * TODO Doc Me
	 * @return
	 */
	protected String getDevResourcesDir()
	{
		return DEV_RESOURCE_DIR;
	}
	
	/**
	 * TODO Doc Me
	 * @return
	 */
	protected String getScriptsDir()
	{
		return SCRIPTS_DIR;
	}

}
