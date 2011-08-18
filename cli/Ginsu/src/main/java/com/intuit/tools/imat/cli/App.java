/*******************************************************************************
 * Copyright (c) 2011 Intuit, Inc.
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

import com.google.inject.Injector;
import com.intuit.tools.imat.AppContext;
import com.intuit.tools.imat.ICommand;
import com.intuit.tools.imat.ICommandDispatchService;
import com.intuit.tools.imat.IConfigurationService;
import com.intuit.tools.imat.IInputHandlingService;
import com.intuit.tools.imat.IncompleteCommandException;
import com.intuit.tools.imat.MisconfigurationException;
import com.intuit.tools.imat.ProjectConfigurationNotFoundException;
import com.intuit.tools.imat.commands.IMATCommandsModule;
import com.intuit.tools.imat.config.IMATConfigModule;
import com.intuit.tools.imat.io.IMATIOModule;
import com.intuit.tools.imat.logging.BindLog4JWithClassNameModule;
import com.intuit.tools.imat.monitor.IMATMonitorModule;
import com.intuit.tools.imat.reporting.IMATReportingModule;
import com.intuit.tools.imat.scripts.IMATScriptsModule;

/**
 * 
 * @author rpfeffer
 * @dateCreated Mar 11, 2011
 * 
 *              This is the main Class used to execute all of the imat command
 *              line interface.
 */
public class App {
	public static final String APP_NAME_NORMAL = "IMAT";
	public static final String APP_NAME_LOWERCASE = APP_NAME_NORMAL.toLowerCase();
	public static final String APP_NAME_UPPERCASE = APP_NAME_NORMAL.toUpperCase();
	private static final int APP_HOME_ARGS_INDEX = 1;

	/**
	 * @TODO DocMe
	 * @param args
	 */
	public static void main(String[] args) {
		// Application initialization starts
		int exitStatus = ExitStatus.UNSET.getStatus();
		AppContext appContext = App.initAppContext(args);
		Injector injector = appContext.getInjector();
		Logger logger = injector.getInstance(Logger.class);

		logger.info("Parsing Input.");
		IInputHandlingService inputService = injector
				.getInstance(IInputHandlingService.class);
		inputService.handleInput(args);
		ICommand command = inputService.getCommand();
		IConfigurationService configService = injector
				.getInstance(IConfigurationService.class);

		// If we have a runnable command and the application is not
		// initialized...
		if (command.isRunnable()
				&& configService.isNotInitialized(appContext
						.getProperty(AppContext.APP_HOME_KEY))) {
			logger.debug("Running first time initialization...");

			// load the main config override so that we can get access to the
			// home directory.
			appContext.overrideProperties(inputService
					.getConfigurationOverride());

			// TODO RP: depending on the action from legal, we will check for
			// accepting license agreement here etc.
			configService.doFirstTimeInitialization();
		}
		try {
			Hashtable<String, String> configOverride = inputService
					.getConfigurationOverride();
			appContext.setProperty(AppContext.PROJECT_HOME_KEY,
					configOverride.get(AppContext.PROJECT_HOME_KEY));
			logger.debug("Setting Project Home before Confguration is loaded: Project Home is:"
					+ configOverride.get(AppContext.PROJECT_HOME_KEY));

			// load properties
			logger.info("Loading Configuration.");
			configService.loadConfiguration(command.expectsProject());
			appContext.overrideProperties(configOverride);

			// run the loaded command using the command dispatch service
			ICommandDispatchService commandDispatchService = injector
					.getInstance(ICommandDispatchService.class);
			logger.info("Running Command...");
			commandDispatchService.dispatch(command);
			exitStatus = command.getExitStatus();
			logger.debug("Command Named: " + command.getName()
					+ " finished with an exit status of "
					+ String.valueOf(exitStatus));
			logger.info("Command Completed.");

			// TODO RP: After the command has run, dispatch the update command
			// in the background and exit.
			// this is pending review by legal privacy.
		} catch (MisconfigurationException e) {
			logger.fatal(
					"IMAT is currently Misconfigured and will not run until corrected.",
					e);
		} catch (IncompleteCommandException e) {
			logger.fatal(
					"The Application tried to move on before the command had completed."
							+ " Command is: " + command.getName(), e);

		} catch (ProjectConfigurationNotFoundException e) {
			logger.fatal("A IMAT Automation project was not found. \nThe command: "
					+ command.getName()
					+ " expected a file called project.properties.\nPlease re-run this command from the same directory as your project home (where project.properties is) or alternatively, run the command setting the \"-p\" flag to the directory where the project.properties file is.");
		}
		if (!configService.shouldSkipExitStatus()) {
			System.exit(exitStatus);
		}
	}

	public static AppContext initAppContext(String[] args) {
		AppContext appContext = AppContext.INSTANCE;
		appContext.setProperty(AppContext.APP_HOME_KEY,
				args[APP_HOME_ARGS_INDEX]);
		appContext.addAppModule(new IMATCLIModule());
		appContext.addAppModule(new BindLog4JWithClassNameModule());
		appContext.addAppModule(new IMATCommandsModule());
		appContext.addAppModule(new IMATIOModule());
		appContext.addAppModule(new IMATConfigModule());
		appContext.addAppModule(new IMATScriptsModule());
		appContext.addAppModule(new IMATMonitorModule());
		appContext.addAppModule(new IMATReportingModule());
		return appContext;
	}
}
