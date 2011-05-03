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

import org.apache.log4j.Logger;

import com.beust.jcommander.IDefaultProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.intuit.ginsu.IApplicationResourceService;
import com.intuit.ginsu.IConfigurationService;
import com.intuit.ginsu.IProjectResourceService;
import com.intuit.ginsu.annotations.ConfigFile;

/**
 * @author rpfeffer
 * @dateCreated Apr 28, 2011
 * 
 *              This class defines the configuration for classes in the
 *              com.intuit.ginsu.config module.
 * 
 */
public class GinsuConfigModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {

		bind(IDefaultProvider.class).to(PropertyFileConfigurationService.class)
				.asEagerSingleton();
		bind(IConfigurationService.class).to(
				PropertyFileConfigurationService.class).asEagerSingleton();
	}

	@Provides @Singleton PropertyFileConfigurationService provideConfigService(
			IApplicationResourceService appResourceService, 
			IProjectResourceService projResourceService, 
			@ConfigFile String configFile, 
			Logger logger )
	{
		return new PropertyFileConfigurationService(appResourceService, 
				projResourceService, configFile, logger);
	}
}
