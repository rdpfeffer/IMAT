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

/**
 * @author rpfeffer
 * @dateCreated Mar 25, 2011
 * 
 *              The First responsibility of the {@link IConfigurationService} is
 *              to define an interface for initializing Ginsu configuration.
 * 
 *              The {@link IConfigurationService} also provides an interface for
 *              loading and overriding properties of the runtime configuration.
 *              This is is main responsibility throughout the lifetime of an
 *              installation of ginsu. Although the data structure for accessing
 *              the values of the configuration is a {@link Properties} object,
 *              we leave it up to the implementer as to how these properties
 *              should be stored. For example, the easiest way to store them
 *              would be in a file on disk, but they could also be stored in a
 *              database, XML file, or otherwise...
 * 
 */
public interface IConfigurationService {

	/**
	 * Set up the initial configuration of Ginsu. This initialization is meant
	 * to happen during the first use of Ginsu to make sure that the project is
	 * set up correctly. This includes
	 */
	public void doFirstTimeInitialization();

	/**
	 * <p>
	 * Determines if Ginsu has been initialized. In general, the intent of this
	 * method is to provide a conditional data point on whether the application
	 * should be initialized. Since it is more likely that the app has been
	 * initialized than the opposite, and that there may be other factors that
	 * would determine whether or not we should run the first time
	 * initialization, running first time initialization is explicitly a special
	 * case. Therefore, we have named this from the perspective of the consuming
	 * code so that it would read easier within a conditional:
	 * </p>
	 * <code>
	 * ...
	 * if ( configService.isNotInitialized() && ...)
	 * {
	 * 		configService.doFirstTimeInitialization();
	 * }
	 * ...
	 * </code>
	 * <p>
	 * ... 
	 * </p>
	 * @return true if ginsu has not been initialized, false if it has.
	 */
	public boolean isNotInitialized();

	/**
	 * Load the configuration from the default configuration store.
	 */
	public void loadConfiguration();

	/**
	 * Load the configuration from the default configuration store and override
	 * the default configuration properties with those passed in.
	 * 
	 * @param configurationOverride
	 *            The {@link Properties} object containing properties which will
	 *            override that which is default configuration store.
	 */
	public void loadConfiguration(
			Hashtable<String, Object> configurationOverride);

	/**
	 * Get the current configuration
	 * 
	 * @return a {@link Properties} object representing the current
	 *         configuration
	 */
	public Properties getConfiguration();

}
