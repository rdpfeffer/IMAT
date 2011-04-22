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
package com.intuit.ginsu.config;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.beust.jcommander.IDefaultProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.intuit.ginsu.AppContext;
import com.intuit.ginsu.annotations.ConfigFile;
import com.intuit.ginsu.io.IApplicationResourceService;

/**
 * @author rpfeffer
 * @dateCreated Mar 26, 2011
 * 
 *              This class provides a property file based implementation of the
 *              {@link IConfigurationService}.
 * 
 */
@Singleton
public class PropertyFileConfigurationService implements IConfigurationService, IDefaultProvider {
	
	private AppContext appContext = AppContext.getInstance();
	
	private final IApplicationResourceService resourceService;
	private final String configFile;
	private final Logger logger;
	
	/**
	 * Create a new PropertyFileConfigurationService
	 * @param resourceService
	 *            The {@link IApplicationResourceService} to use when retrieving
	 *            application resource files
	 */
	@Inject
	public PropertyFileConfigurationService(IApplicationResourceService resourceService, @ConfigFile String configFile, Logger logger )
	{
		this.resourceService = resourceService;
		this.configFile = configFile;
		this.logger = logger;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.intuit.ginsu.config.IConfigurationService#doFirstTimeInitialization()
	 */
	public void doFirstTimeInitialization() {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.intuit.ginsu.config.IConfigurationService#isNotInitialized()
	 */
	public boolean isNotInitialized(String homeDir) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.intuit.ginsu.config.IConfigurationService#loadConfiguration()
	 */
	public void loadConfiguration() throws MisconfigurationException{
		logger.debug("Loading Configuration");
		Properties props = this.resourceService.getAppProperties(configFile);
		
		if(props.isEmpty())
		{
			throw new MisconfigurationException("The Applicaiton was unable " +
					"to load any properties from the configuration file. " +
					"Please see the log for more details");
		}
		
		Enumeration<?> keys = props.propertyNames();
		while(keys.hasMoreElements())
		{
			String key = (String) keys.nextElement();
			this.appContext.setProperty(key, props.getProperty(key));
			logger.trace("Loading Property Pair <" + key + ", " + 
					props.getProperty(key) + ">");
		}
		logger.debug("Configuration Loaded Successfully.");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.intuit.ginsu.config.IConfigurationService#storeConfiguration(java.util.Hashtable)
	 */
	public void storeConfiguration(Hashtable<String, String> configuration) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.config.IConfigurationService#getConfiguration()
	 */
	public Properties getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.beust.jcommander.IDefaultProvider#getDefaultValueFor(java.lang.String)
	 */
	public String getDefaultValueFor(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
