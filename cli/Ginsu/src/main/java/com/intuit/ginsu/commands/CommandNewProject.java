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
package com.intuit.ginsu.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.intuit.ginsu.IApplicationResourceService;
import com.intuit.ginsu.ICommand;
import com.intuit.ginsu.IProjectResourceService;
import com.intuit.ginsu.IScriptLauncher;
import com.intuit.ginsu.MisconfigurationException;
import com.intuit.ginsu.cli.converters.FileConverter;
import com.intuit.ginsu.cli.validators.JavaScriptVariableValidator;

/**
 * @author rpfeffer
 * @dateCreated Mar 25, 2011
 * 
 *              This class is represents the command to be executed in order
 *              generate a project directory structure. It exists as the main
 *              executor of the command and does all of the heavy lifting to
 *              make the project generation happen.
 * 
 */
@Parameters(commandDescription = "Create a new automation project containing the basic project files needed to hit the ground running with good patterns and documented code.")
public class CommandNewProject extends ScriptedCommand implements ICommand {

	private final IApplicationResourceService appResourceService;

	CommandNewProject(PrintWriter printwriter, Logger logger,
			IScriptLauncher scriptLauncher,
			IApplicationResourceService appResourceService) {
		super(printwriter, logger, scriptLauncher);
		this.appResourceService = appResourceService;
	}

	/**
	 * The name of the command.
	 */
	public static final String NAME = "new-project";

	/**
	 * The option for setting the target directory where the project files
	 * should be generated
	 */
	public static final String TARGET_DIR = "-targetDir";
	@Parameter(names = { TARGET_DIR, "-t" }, converter = FileConverter.class, description = "The path to the location where the root project "
			+ "folder should be placed. Note: We assume that this path "
			+ "already exists. If the path does not exist, an error will"
			+ " occur and the command will not run. If this argument is"
			+ " not provided, it is assumed to be the current directory: \".\"")
	File targetDir = new File(".");

	public static final String GLOBAL_OBJECT_VAR = "-globalObjVar";
	public static final String GLOBAL_OBJECT_VAR_DEFAULT_VAL = "AUTO";
	@Parameter(names = { GLOBAL_OBJECT_VAR, "-g" }, validateWith = JavaScriptVariableValidator.class, description = "The variable  name of the global object which will hold "
			+ "reference to all objects that are created within the JavaScript "
			+ "source of your automation project. In JavaScript, it is good "
			+ "practice to nest all functions within your own global namespace "
			+ "so that none of your objects collide with third party code. "
			+ "It is a good idea to name this variable using a abreviation "
			+ "that somehow represents your project.                       "
			+ "                                                            "
			+ "For example, if you had an application under test called "
			+ "\"FooBar\" your global variable might be \"FB\".                  "
			+ "bash$ ginsu new-project -g FB                               "
			+ "                                                            "
			+ "Finally, you should also know that the variable GINSU is "
			+ "taken, and should not be used. If it is used, an error will ocurr "
			+ "and the command will not be run. If you do not supply a "
			+ "value for this, a default value of \""
			+ GLOBAL_OBJECT_VAR_DEFAULT_VAL
			+ "\" will be used "
			+ "instead. It is a good idea for this letter to be in ALL_CAPS and "
			+ "contain \"_\" characters to represent spaces. If any of the "
			+ "following conditions are met when entering this string, a "
			+ "validatoin error will occur:                                      "
			+ "1) anything that starts with a number                       "
			+ "2) text that has anything but alphanumeric characters and the"
			+ "   characters \"$\", \"_\" and/or \"-\".                   ")
	String globalObjectVar = GLOBAL_OBJECT_VAR_DEFAULT_VAL;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.commands.ICommand#run()
	 */
	public int run() throws MisconfigurationException {
		try {
			// set up the propertes
			LinkedHashMap<String, String> properties = new LinkedHashMap<String, String>();
			String absolutePathToTarget = this.targetDir.getAbsolutePath();
			properties.put("target.dir", absolutePathToTarget);
			properties.put("global.object.var", this.globalObjectVar);

			File fromPath = new File(absolutePathToTarget + File.separator
					+ IProjectResourceService.ENV_DIR);
			String pathToGinsu = appResourceService
					.getRelativePathToAppHome(fromPath);
			properties.put("path.to.ginsu", pathToGinsu);

			// the only reason we are setting this here, and not in the script
			// is if for any
			// reason, this path became dynamic, we wanted the opportunity to
			// set it.
			properties.put("project.dir", ".." + File.separator + "templates"
					+ File.separator + "Project");

			IScriptLauncher scriptLauncher = this.getScriptLauncher();
			scriptLauncher.setScript("generateProject.xml");
			scriptLauncher.setProperties(properties);
			exitStatus = scriptLauncher.runScript();
		} catch (FileNotFoundException fileNotFoundException) {
			MisconfigurationException e = new MisconfigurationException(
					"Could Not run the command: " + this.getName());
			e.initCause(fileNotFoundException);
			throw e;
		}
		return exitStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.commands.ICommand#getName()
	 */
	public String getName() {
		return CommandNewProject.NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.commands.ICommand#isRunnable()
	 */
	public boolean isRunnable() {
		return true;
	}

}
