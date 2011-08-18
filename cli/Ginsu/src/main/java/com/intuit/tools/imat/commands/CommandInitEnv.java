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
package com.intuit.tools.imat.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.intuit.tools.imat.AppContext;
import com.intuit.tools.imat.IApplicationResourceService;
import com.intuit.tools.imat.ICommand;
import com.intuit.tools.imat.IProjectResourceService;
import com.intuit.tools.imat.IScriptLauncher;
import com.intuit.tools.imat.MisconfigurationException;
import com.intuit.tools.imat.cli.ExitStatus;
import com.intuit.tools.imat.cli.converters.FileConverter;

/**
 * @author rpfeffer
 * @dateCreated Mar 25, 2011
 * 
 *              Command to Initialize an IMAT automation project within a new
 *              environment. This is used to resolve paths when someone
 *              downloads an existing project and must map their project back to
 *              the installation of IMAT somewhere else on their system.
 * 
 */
@Parameters(commandDescription = "Initializes an existing IMAT project when " +
		"sharing between environments.")
public class CommandInitEnv extends ScriptedCommand implements ICommand {

	private final IApplicationResourceService appResourceService;

	CommandInitEnv(PrintWriter printwriter, Logger logger,
			IScriptLauncher scriptLauncher,
			IApplicationResourceService appResourceService) {
		super(printwriter, logger, scriptLauncher);
		this.appResourceService = appResourceService;
	}

	public static final String NAME = "init-env";

	public static final String TEMPLATE = "-template";
	@Parameter(names = { TEMPLATE, "-t" }, converter = FileConverter.class, description = "The instruments trace template file to use when "
			+ "running iOS Automation.")
	File template;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.ICommand#run()
	 */
	public ExitStatus run() throws MisconfigurationException {
		try {
			LinkedHashMap<String, String> properties = new LinkedHashMap<String, String>();
			String projectHome = AppContext.INSTANCE
					.getProperty(AppContext.PROJECT_HOME_KEY);
			String absolutePathToTarget = new File(projectHome)
					.getAbsolutePath();
			logger.debug("Setting projectHome as target.dir="
					+ absolutePathToTarget);
			properties.put("target.dir", absolutePathToTarget);

			File fromPath = new File(absolutePathToTarget + File.separator
					+ IProjectResourceService.ENV_DIR);
			String pathToIMAT = appResourceService
					.getRelativePathToAppHome(fromPath);
			properties.put("path.to.app.home", pathToIMAT);
			if (template != null) {
				properties.put("trace.file", template.getAbsolutePath());
			}
			// Set the project template directory...
			// the only reason we are setting this here, and not in the script
			// is if for any reason, this path became dynamic, we wanted the
			// opportunity to set it.
			properties.put("project.dir", ".." + File.separator + "templates"
					+ File.separator + "Project");

			IScriptLauncher scriptLauncher = this.getScriptLauncher();
			scriptLauncher.setScript("initEnvironment.xml");
			scriptLauncher.setProperties(properties);
			exitStatus = scriptLauncher.runScript();
		} catch (FileNotFoundException fileNotFoundException) {
			MisconfigurationException e = new MisconfigurationException(
					"Could not run the command: " + this.getName());
			e.initCause(fileNotFoundException);
			throw e;
		}
		return exitStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.ICommand#getName()
	 */
	public String getName() {
		return CommandInitEnv.NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.ICommand#isRunnable()
	 */
	public boolean isRunnable() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.commands.Command#expectsProject()
	 */
	public boolean expectsProject() {
		return true;
	}
}
