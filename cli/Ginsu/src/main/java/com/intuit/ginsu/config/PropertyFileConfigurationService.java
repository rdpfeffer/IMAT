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

import java.util.Hashtable;
import java.util.Properties;

import com.beust.jcommander.IDefaultProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.intuit.ginsu.AppContext;
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
	
	/**
	 * Create a new PropertyFileConfigurationService
	 * @param resourceService
	 *            The {@link IApplicationResourceService} to use when retrieving
	 *            application resource files
	 */
	@Inject
	public PropertyFileConfigurationService(IApplicationResourceService resourceService)
	{
		this.resourceService = resourceService;
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
	public void loadConfiguration() {
		// TODO Auto-generated method stub

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.intuit.ginsu.config.IConfigurationService#storeConfiguration(java.util.Hashtable)
	 */
	public void storeConfiguration(Hashtable<String, String> configuration) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.config.IConfigurationService#loadConfigurationOverride(com.intuit.ginsu.commands.ICommand)
	 */
	public void loadConfiguration(Hashtable<String, Object> configurationOverride) {
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
