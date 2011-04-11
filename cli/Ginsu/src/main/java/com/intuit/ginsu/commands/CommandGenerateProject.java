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
import java.io.PrintWriter;
import java.util.logging.Logger;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.intuit.ginsu.cli.converters.FileConverter;

/**
 * @author rpfeffer
 * @dateCreated Mar 25, 2011
 * 
 *              //TODO Explain why this file exists and how it is used.
 * 
 */
@Parameters(commandDescription = "Generate the basic project files to start a new Ginsu automation project.")
public class CommandGenerateProject extends Command implements ICommand {

	public CommandGenerateProject(PrintWriter printwriter, Logger logger) {
		super(printwriter, null);
	}

	/**
	 * The name of the command. In this case "generate-project"
	 */
	public static final String NAME = "generate-project";

	/**
	 * The option for setting the target directory where the project files
	 * should be generated
	 */
	public static final String TARGET_DIR = "-targetDir";
	@Parameter(names = { TARGET_DIR, "-t" }, converter = FileConverter.class, 
			description = "The path to the location where the root project "
			+ "folder should be placed. Note: We assume that this path "
			+ "already exists. If the path does not exist, an error will"
			+ " occur and the command will not run. If this argument is"
			+ " not provided, it is assumed to be the current directory: \".\"")
	File targetDir = new File(".");

	public static final String GLOBAL_OBJECT_VAR = "-globalObjVar";
	public static final String GLOBAL_OBJECT_VAR_DEFAULT_VAL = "AUTO";
	@Parameter(names = { GLOBAL_OBJECT_VAR, "-g" }, description = 
			 "The variable  name of the global object which will hold "
			+"reference to all objects that are created within the JavaScript "
			+"source of your automation project. In JavaScript, it is good "
			+"practice to nest all functions within your own global namespace "
			+"so that none of your objects collide with third party code. "
			+"It is a good idea to name this variable using a abreviation "
			+"that somehow represents your project.                       "
			+"                                                            "
			+"For example, if you had an application under test called "
			+"\"FooBar\" your global variable might be \"FB\".                  "
			+"bash$ ginsu generate-project -g FB                          "
			+"                                                            " 
			+"Finally, you should also know that the variable GINSU is " 
			+"taken, and should not be used. If it is used, an error will ocurr "
			+"and the command will not be run. If you do not supply a "
			+"value for this, a default value of \"" + GLOBAL_OBJECT_VAR_DEFAULT_VAL + "\" will be used "
			+"instead. It is a good idea for this letter to be in ALL_CAPS and "
			+"contain \"_\" characters to represent spaces. If any of the "
			+"following conditions are met when entering this string, a "
			+"validatoin error will occur:                                      "
			+"1) anything that starts with a number                       "
			+"2) text that has anything but alphanumeric characters and the"
			+"   characters \"_\" and/or \"-\".                           ")
	String globalObjectVar = GLOBAL_OBJECT_VAR_DEFAULT_VAL;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.commands.ICommand#run()
	 */
	public int run() {
		int exitStatus = 0;
		// TODO Auto-generated method stub

		return exitStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.commands.ICommand#cleanUp()
	 */
	public void cleanUp() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.commands.ICommand#getName()
	 */
	public String getName() {
		return CommandGenerateProject.NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object command) {
		return (command != null && command instanceof CommandGenerateProject 
				&& ((CommandGenerateProject) command).getName() == this.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.ginsu.commands.ICommand#isRunnable()
	 */
	public boolean isRunnable() {
		// TODO Auto-generated method stub
		return true;
	}

}
