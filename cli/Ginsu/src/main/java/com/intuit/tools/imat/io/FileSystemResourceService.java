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
package com.intuit.tools.imat.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.intuit.tools.imat.AppContext;
import com.intuit.tools.imat.IApplicationResourceService;
import com.intuit.tools.imat.IProjectResourceService;

/**
 * @author rpfeffer
 * @dateCreated Apr 14, 2011
 * 
 *              This Service retrieves resources from the file system handling
 *              all of the different ways the application could be run.
 *              Regardless of when in the software development life-cycle, or
 *              where the application is launched, this class should be able to
 *              find the application resource.
 *              
 *              @TODO RP: Consider consolidating the Interfaces into one and
 *              differentiate by internal state. This should reduce the 
 *              conditional logic in getAppResourceFile()
 * 
 */
public class FileSystemResourceService implements IApplicationResourceService,
		IProjectResourceService {

	private static final String TARGET_RESOURCES_DIR = "target"
			+ File.separator + "classes" + File.separator;
	private static final String DEV_RESOURCE_DIR = "src" + File.separator
			+ "main" + File.separator + "resources" + File.separator;
	private static final String SCRIPTS_DIR = "scripts" + File.separator;
	private final Logger logger;
	private final PathAnalyzer pathAnalyzer;

	/**
	 * @param logger
	 * @param pathAnalyzer
	 */
	FileSystemResourceService(Logger logger, PathAnalyzer pathAnalyzer) {
		this.logger = logger;
		this.pathAnalyzer = pathAnalyzer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.intuit.tools.imat.io.IApplicationResourceService#getAppProperties(java
	 * .lang.String)
	 */
	public Properties getAppProperties(String propertiesFileName) {
		Properties props = new Properties();
		try {
			getPropertiesHelper(props,
					this.getAppResourceFile(propertiesFileName, true));
		} catch (FileNotFoundException e) {
			logger.debug("FileNotFoundException. Application Properties will be empty. Message:"
					+ e.getMessage());
		}
		return props;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.intuit.tools.imat.io.IResourceManager#getResourceFile(java.lang.String)
	 */
	public File getAppResourceFile(String fileName, boolean skipClassloader)
			throws FileNotFoundException {
		File resourceFile;
		URL fileUrl = null;
		if (!skipClassloader) {
			logger.trace("Looking for file on classpath. File: " + fileName);
			fileUrl = ClassLoader.getSystemResource(fileName);
		}
		if (fileUrl == null) {
			logger.trace("Looking for file in Home Directory. File: "
					+ getHomeDir() + fileName);
			resourceFile = new File(getHomeDir() + fileName);
			if (!resourceFile.exists()) {
				logger.trace("Looking for file in Target Directory (DEV ONLY). File: "
						+ getTargetDir() + fileName);
				resourceFile = new File(getTargetDir() + fileName);
			}

			if (!resourceFile.exists()) {
				logger.trace("Finally, Looking for file in Resources Directory (DEV ONLY). File: "
						+ getDevResourcesDir() + fileName);
				resourceFile = new File(getDevResourcesDir() + fileName);
			}
		} else {
			logger.trace("Found File on the classpath: " + fileUrl.getPath());
			resourceFile = new File(fileUrl.getPath());
		}
		if (!resourceFile.exists()) {
			logger.debug("Failed all attempts to get Application Resource file. Throwing Exception. File: "
					+ fileName);
			throw new FileNotFoundException(
					"Could not find Application Resource file: " + fileName);
		}
		return resourceFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.intuit.tools.imat.io.IApplicationResourceService#getAppScript(java.lang
	 * .String)
	 */
	public File getAppScript(String scriptName) throws FileNotFoundException {
		logger.debug("Looking for script. File: " + scriptName);
		return this.getAppResourceFile(getScriptsDir() + scriptName, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.intuit.tools.imat.io.IApplicationResourceService#getAppProperties(java
	 * .lang.String)
	 */
	public Properties getProjectProperties(String propertiesFileName) {
		Properties props = new Properties();
		try {
			getPropertiesHelper(props,
					getProjectResourceFile(propertiesFileName));
		} catch (FileNotFoundException e) {
			logger.debug("FileNotFoundException. Project Properties will be empty. Message:"
					+ e.getMessage());
		}
		return props;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.intuit.tools.imat.io.IProjectResourceService#getProjectResourceFile(java
	 * .lang.String)
	 */
	public File getProjectResourceFile(String fileName)
			throws FileNotFoundException {
		String projectHome = AppContext.INSTANCE
				.getProperty(AppContext.PROJECT_HOME_KEY);
		File pathToProjectHome = new File(projectHome);
		logger.debug("Trying to get project resource file when project home is: "
				+ projectHome);
		File resourceFile = null;
		try {
			resourceFile = new File(pathToProjectHome.getCanonicalFile()
					+ File.separator + fileName);
		} catch (IOException e) {
			logger.debug(e.getStackTrace());
		}
		if (resourceFile == null || !resourceFile.exists()) {
			logger.debug("Failed all attempts to get Project Resource file. File: "
					+ resourceFile.getAbsolutePath() + " does not exist!");
			throw new FileNotFoundException();
		}
		return resourceFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.intuit.tools.imat.IApplicationResourceService#getRelativePath(java.io.
	 * File, java.lang.String)
	 */
	public String getRelativePathToAppHome(File fromPath)
			throws FileNotFoundException {
		String relativePath = "";
		File toPath = this.getAppResourceFile("", true);
		try {
			URI fromURI = fromPath.getCanonicalFile().toURI();
			URI toURI = toPath.getCanonicalFile().toURI();
			relativePath = pathAnalyzer.getRelativePath(fromURI, toURI);
		} catch (IOException ioException) {
			FileNotFoundException e = new FileNotFoundException();
			e.initCause(ioException);
			throw e;
		}
		return relativePath;
	}

	/**
	 * This is used in development configurations only. Returns the path to the
	 * Development resources directory for use during testing
	 * 
	 * @return
	 */
	protected String getDevResourcesDir() {
		return DEV_RESOURCE_DIR;
	}

	/**
	 * Returns the path to the base directory of the application.
	 * 
	 * @return
	 */
	protected String getHomeDir() {
		return AppContext.INSTANCE.getProperty(AppContext.APP_HOME_KEY)
				+ File.separator;
	}

	/**
	 * Returns the path to the scripts directory of the application.
	 * 
	 * @return
	 */
	protected String getScriptsDir() {
		return SCRIPTS_DIR;
	}

	/**
	 * This is used in development configurations only. Returns the path to the
	 * Target directory for use during testing.
	 * 
	 * @return
	 */
	protected String getTargetDir() {
		return TARGET_RESOURCES_DIR;
	}

	/**
	 * 
	 * @param propertiesFile
	 * @return
	 */
	private void getPropertiesHelper(Properties target, File propertiesFile) {
		try {
			logger.debug("Looking for Properties File: "
					+ propertiesFile.getAbsolutePath());
			target.load(new FileInputStream(propertiesFile));
		} catch (SecurityException e) {
			logger.debug("SecurityException. Properties will be empty. Message:"
					+ e.getMessage());
		} catch (IOException e) {
			logger.debug("IOException. Properties will be empty. Message:"
					+ e.getMessage());
		}
	}
}
