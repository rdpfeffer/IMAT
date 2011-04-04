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
import com.google.inject.Singleton;

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

	
	
	/* (non-Javadoc)
	 * @see com.intuit.ginsu.config.IConfigurationService#loadConfiguration()
	 */
	public void loadConfiguration() {
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

	public void doFirstTimeInitialization() {
		// TODO Auto-generated method stub
		
	}

	public boolean isNotInitialized() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getDefaultValueFor(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
