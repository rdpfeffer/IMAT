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
package com.intuit.tools.imat.scripts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import com.intuit.tools.imat.IApplicationResourceService;
import com.intuit.tools.imat.IScriptLauncher;
import com.intuit.tools.imat.cli.ExitStatus;

/**
 * @author rpfeffer
 * @dateCreated Apr 12, 2011
 * 
 *              This class implements the IScriptLauncher interface for Ant
 *              scripts and allows ant to be embedded within IMAT.
 * 
 */
public class AntScriptLauncher extends ExternallyScriptedProcessLauncher
		implements IScriptLauncher {

	private DefaultLogger consoleLogger;

	/**
	 * @param resourceService
	 * @param logger
	 */
	AntScriptLauncher(IApplicationResourceService resourceService, Logger logger) {
		super(resourceService, logger);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.scripts.IScriptLauncher#runScript()
	 */
	public ExitStatus runScript() {
		Project antProject = new Project();
		ExitStatus exitStatus = ExitStatus.INTERNAL_ERROR;
		try {
			File buildFile = this.getScriptAsFile();
			this.preValidateScript(buildFile);
			antProject.setUserProperty("ant.file", buildFile.getAbsolutePath());
			antProject.setUserProperty("basedir", buildFile.getParent());
			this.loadPropertiesIntoProject(antProject);
			antProject.addBuildListener(this.consoleLogger);
			antProject.fireBuildStarted();
			antProject.init();
			ProjectHelper helper = ProjectHelper.getProjectHelper();
			antProject.addReference("ant.projectHelper", helper);
			helper.parse(antProject, buildFile);
			String defaultTarget = antProject.getDefaultTarget();
			antProject.executeTarget(defaultTarget);
			exitStatus = ExitStatus.SUCCESS;
		} catch (BuildException e) {
			antProject.fireBuildFinished(e);
			logger.error(e.getMessage());
			throw new AssertionError(e);
		} catch (FileNotFoundException e) {
			String errMessage = "Could not find the Ant Script Needed to run this command. Script: "
					+ getScript()
					+ " Underlying Reason: "
					+ e.getMessage()
					+ " See the logs for more details";
			logger.error(errMessage);
			throw new AssertionError(errMessage);
		}
		return exitStatus;
	}

	/*
	 * (non-Javadoc) Take the set of properties set in the Hashtable of
	 * properties of the launcher and set them on the Project
	 */
	private void loadPropertiesIntoProject(Project project) {
		if (getProperties() != null && !getProperties().isEmpty()) {
			for (Entry<String, String> pair : getProperties().entrySet()) {
				project.setUserProperty(pair.getKey(), pair.getValue());
			}
		}
	}

	/*
	 * (non-Javadoc) Initialize the console loger to use the print stream passed
	 * in.
	 */
	public void setProjectListener(PrintStream printStream) {
		consoleLogger = new DefaultLogger();
		consoleLogger.setErrorPrintStream(printStream);
		consoleLogger.setOutputPrintStream(printStream);
		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
	}
}
