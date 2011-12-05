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
package com.intuit.tools.imat.cli;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.beust.jcommander.Parameter;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.intuit.tools.imat.AppContext;

/**
 * @author rpfeffer
 * @dateCreated Mar 25, 2011
 * 
 *              This object holds the main arguments set by the user of the
 *              command line interface for IMAT. When the input is parsed, all
 *              of the main options get loaded into this class. These options
 *              can then be passed off somewhere else so that they can override
 *              the default configuration
 * 
 */
@Singleton
public class MainArgs {

	@Inject
	private Logger logger;

	public static final String NAME = "main";

	public static final String AUTO_UPDATE_OFF = "-auto_update_off";
	@Parameter(names = AUTO_UPDATE_OFF, description = "Set the "
			+ AUTO_UPDATE_OFF + " flag if you do not want to update "
			+ "the CLI tool when it runs.", hidden = true)
	public boolean autoUpdateOff = false;

	public static final String HOME = "-home";
	@Parameter(names = HOME, hidden = true)
	public String appHome;

	public static final String SKIP_EXIT_STATUS = "-SkipExit";
	@Parameter(names = SKIP_EXIT_STATUS, hidden = true)
	public boolean skipExit = false;

	public static final String PROJECT_DIR = "-project_dir";
	@Parameter(names = { PROJECT_DIR, "-p" }, description = "Set the "
			+ PROJECT_DIR
			+ " to the base directory of the automation project "
			+ "relative to the current directory. If this is not set correctly "
			+ "for certain commands (like running tests), imat will error out "
			+ "and not run the command.")
	public String projectHome = System.getProperty("user.dir");

	/**
	 * Return the configuration override to the runtime configuration of the
	 * Application, translating its internal representation of key/value pairs
	 * to the keys and values expected by the rest of the application.
	 * 
	 * @return a {@link Hashtable} of key/value pairs, both of type
	 *         {@link String}
	 */
	public Hashtable<String, String> getConfigurationOverride() {
		Hashtable<String, String> config = new Hashtable<String, String>();
		config.put(MainArgs.AUTO_UPDATE_OFF, String.valueOf(this.autoUpdateOff));

		logger.debug("APP_HOME_KEY is: " + this.appHome);
		if (this.appHome != null) {
			config.put(AppContext.APP_HOME_KEY, this.appHome);
		}

		logger.debug("PROJECT_HOME_KEY is: " + this.projectHome);
		if (this.projectHome != null) {
			config.put(AppContext.PROJECT_HOME_KEY, this.projectHome);
		}

		logger.debug("SKIP_EXIT_STATUS is: " + String.valueOf(skipExit));
		if (skipExit) {
			config.put(AppContext.SKIP_EXIT_STATUS, String.valueOf(skipExit));
		}

		return config;
	}
}
