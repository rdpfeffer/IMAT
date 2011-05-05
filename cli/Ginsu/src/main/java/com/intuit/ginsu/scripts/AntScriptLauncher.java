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
package com.intuit.ginsu.scripts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import com.intuit.ginsu.IApplicationResourceService;
import com.intuit.ginsu.IScriptLauncher;

/**
 * @author rpfeffer
 * @dateCreated Apr 12, 2011
 * 
 *              This class implements the IScriptLauncher interface for Ant
 *              scripts and allows ant to be embedded within ginsu.
 * 
 */
public class AntScriptLauncher implements IScriptLauncher {

	private String script = "";
	private Hashtable<String, String> properties = new Hashtable<String, String>();
	private DefaultLogger consoleLogger;
	private Logger logger;
	
	private final IApplicationResourceService resourceService;
	
	AntScriptLauncher (IApplicationResourceService resourceService, Logger logger)
	{
		this.resourceService = resourceService;
		this.logger = logger;
	}
	
	/* (non-Javadoc)
	 * @see com.intuit.ginsu.scripts.IScriptLauncher#setScript(java.lang.String)
	 */
	public void setScript(String script) {
		this.script = script;
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.scripts.IScriptLauncher#getScript()
	 */
	public String getScript() {
		return script;
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.scripts.IScriptLauncher#getScriptAsFile()
	 */
	public File getScriptAsFile() throws FileNotFoundException {
		File scriptFile = resourceService.getAppScript(script);
		String loggedMessage = "getScriptAsFile() found: " 
			+ (scriptFile == null ? "null when trying to get script: " + script : scriptFile.getPath());
		logger.debug(loggedMessage);
		assert scriptFile != null : loggedMessage; //script file should never be null. if it is we are misconfigured.
		return scriptFile;
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.scripts.IScriptLauncher#setScriptProperties()
	 */
	public void setProperties( Hashtable<String, String> properties) {
		this.properties = properties;
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.scripts.IScriptLauncher#runScript()
	 */
	public int runScript() {
		Project antProject = new Project();
		int exitStatus = 1; 
		try
		{
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
			//antProject.fireBuildFinished(null);
			exitStatus = 0;
		}
		catch (BuildException e)
		{
			antProject.fireBuildFinished(e);
			logger.error(e.getMessage());
			throw new AssertionError(e);
		} 
		catch (FileNotFoundException e) 
		{
			String errMessage = "Could not find the Ant Script Needed to run this command. " +
					"Script: " + this.script +
					" Underlying Reason: " + e.getMessage() +
					" See the" + " logs for more details";
			logger.error(errMessage);
			throw new AssertionError(errMessage);
		}
		return exitStatus;
	}
	
	/* (non-Javadoc)
	 * Determine if the current script set on this launcher is a runnable script
	 */
	private void preValidateScript(File antScript) throws BuildException
	{
		if (!antScript.canRead())
		{
			throw new BuildException("We did not have the right permissions to run the following " +
					"script: " + antScript.getAbsolutePath() +
					" See the Log for more details");
		}
		else if (!antScript.isFile())
		{
			throw new BuildException("The script we tried to run was not actually a file. " +
					"script: " + antScript.getAbsolutePath() +
					" See the Log for more details");
		}
	}
	
	/* (non-Javadoc) Take the set of properties set in the Hashtable of
	 * properties of the launcher and set them on the Project
	 */
	private void loadPropertiesIntoProject(Project project)
	{
		if (this.properties != null && !this.properties.isEmpty())
		{
			for(Entry<String, String> pair : this.properties.entrySet())
			{
				project.setUserProperty(pair.getKey(), pair.getValue());
			}
		}
	}
	
	/* (non-Javadoc)
	 * Initialize the console loger to use the print stream passed in.
	 */
	public void setProjectListener(PrintStream printStream)
	{
		consoleLogger = new DefaultLogger();
		consoleLogger.setErrorPrintStream(printStream);
		consoleLogger.setOutputPrintStream(printStream);
		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
	}

}