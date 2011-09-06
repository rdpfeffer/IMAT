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
package com.intuit.tools.imat.config;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.beust.jcommander.IDefaultProvider;
import com.intuit.tools.imat.AppContext;
import com.intuit.tools.imat.IApplicationResourceService;
import com.intuit.tools.imat.IConfigurationService;
import com.intuit.tools.imat.IProjectResourceService;
import com.intuit.tools.imat.MisconfigurationException;
import com.intuit.tools.imat.ProjectConfigurationNotFoundException;
import com.intuit.tools.imat.annotations.ConfigFile;

/**
 * @author rpfeffer
 * @dateCreated Mar 26, 2011
 * 
 *              This class provides a property file based implementation of the
 *              {@link IConfigurationService}.
 * 
 */
public class PropertyFileConfigurationService implements IConfigurationService,
		IDefaultProvider {

	private final AppContext appContext = AppContext.INSTANCE;
	private final IApplicationResourceService appResourceService;
	private final IProjectResourceService projResourceService;
	private final String configFile;
	private final Logger logger;

	/**
	 * Create a new PropertyFileConfigurationService
	 * 
	 * @param appResourceService
	 *            The {@link IApplicationResourceService} to use when retrieving
	 *            application resource files
	 * @param projResourceService
	 *            The {@link IProjectResourceService} to use when retrieving
	 *            project resource files.
	 * @param configFile
	 *            {@link String} the name of the resource file for the
	 *            applicaiton configuration
	 * @param logger
	 *            The {@link Logger} for this class
	 */
	PropertyFileConfigurationService(
			IApplicationResourceService appResourceService,
			IProjectResourceService projResourceService,
			@ConfigFile String configFile, Logger logger) {
		this.appResourceService = appResourceService;
		this.projResourceService = projResourceService;
		this.configFile = configFile;
		this.logger = logger;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.intuit.tools.imat.config.IConfigurationService#doFirstTimeInitialization()
	 */
	public void doFirstTimeInitialization() {
		// / TODO RP: Pending Decision by legal to allow background updating of
		// IMAT on a regular basis.
		// / Once a decision has been made there, we will then have a basis for
		// whether or not we will
		// / implement this method.

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.config.IConfigurationService#isNotInitialized()
	 */
	public boolean isNotInitialized(String homeDir) {
		// / TODO RP: Pending Decision by legal to allow background updating of
		// IMAT on a regular basis.
		// / Once a decision has been made there, we will then have a basis for
		// whether or not we will
		// / implement this method.
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.config.IConfigurationService#loadConfiguration()
	 */
	public void loadConfiguration(boolean expectsProjectConfig)
			throws MisconfigurationException,
			ProjectConfigurationNotFoundException {
		logger.debug("Loading Configuration");
		loadAppConfiguration();
		loadProjectConfiguration(expectsProjectConfig);
		logger.debug("Configuration Loaded Successfully.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.beust.jcommander.IDefaultProvider#getDefaultValueFor(java.lang.String
	 * )
	 */
	public String getDefaultValueFor(String key) {
		// TODO RP: Remove this if we decide not to use it for command defaults.
		return null;
	}

	/**
	 * @throws MisconfigurationException
	 */
	private void loadAppConfiguration() throws MisconfigurationException {
		logger.debug("Loading Application Configuration.");
		Properties props = this.appResourceService.getAppProperties(configFile);
		if (props.isEmpty()) {
			throw new MisconfigurationException(
					"The Applicaiton was unable "
							+ "to load any properties from the Application configuration file. "
							+ "Please see the log for more details");
		}
		loadPropsIntoAppContext(props);
	}

	/**
	 * @param expectsProjectConfig
	 * @throws ProjectConfigurationNotFoundException
	 */
	private void loadProjectConfiguration(boolean expectsProjectConfig)
			throws ProjectConfigurationNotFoundException {
		if (expectsProjectConfig) {
			logger.info("Loading Project Configuration.");
		}
		Properties props = this.projResourceService
				.getProjectProperties("project.properties");
		if (props.isEmpty()) {
			if (expectsProjectConfig) {
				throw new ProjectConfigurationNotFoundException(
						"We expected "
								+ "project configuration propterties to be set but there "
								+ "were none.");
			} else {
				logger.debug("The Applicaiton was unable to load any properties from"
						+ " the project configuration file.");
			}
		}
		loadPropsIntoAppContext(props);
	}

	/**
	 * @param props
	 */
	private void loadPropsIntoAppContext(Properties props) {
		Enumeration<?> keys = props.propertyNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			this.appContext.setProperty(key, props.getProperty(key));
			logger.trace("Loading Property Pair <" + key + ", "
					+ props.getProperty(key) + ">");
		}
	}

	/* (non-Javadoc)
	 * @see com.intuit.tools.imat.IConfigurationService#shouldSkipExitStatus()
	 */
	public boolean shouldSkipExitStatus() {
		String property = AppContext.INSTANCE
				.getProperty(AppContext.SKIP_EXIT_STATUS);
		boolean result = false;
		if (!property.isEmpty()) {
			result = Boolean.valueOf(property);
		}
		return result;
	}

}
