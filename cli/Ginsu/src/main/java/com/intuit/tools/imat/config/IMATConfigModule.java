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

import org.apache.log4j.Logger;

import com.beust.jcommander.IDefaultProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.intuit.tools.imat.IApplicationResourceService;
import com.intuit.tools.imat.IConfigurationService;
import com.intuit.tools.imat.IProjectResourceService;
import com.intuit.tools.imat.annotations.ConfigFile;

/**
 * @author rpfeffer
 * @dateCreated Apr 28, 2011
 * 
 *              This class defines the configuration for classes in the
 *              com.intuit.tools.imat.config module.
 * 
 */
public class IMATConfigModule extends AbstractModule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(IDefaultProvider.class).to(PropertyFileConfigurationService.class)
				.asEagerSingleton();
		bind(IConfigurationService.class).to(
				PropertyFileConfigurationService.class).asEagerSingleton();
	}

	/**
	 * @TODO DocMe
	 * @param appResourceService
	 * @param projResourceService
	 * @param configFile
	 * @param logger
	 * @return
	 */
	@Provides
	@Singleton
	PropertyFileConfigurationService provideConfigService(
			IApplicationResourceService appResourceService,
			IProjectResourceService projResourceService,
			@ConfigFile String configFile, Logger logger) {
		return new PropertyFileConfigurationService(appResourceService,
				projResourceService, configFile, logger);
	}
}
