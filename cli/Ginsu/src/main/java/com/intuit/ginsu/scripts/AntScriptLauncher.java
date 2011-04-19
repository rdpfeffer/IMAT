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

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import com.google.inject.Inject;
import com.intuit.ginsu.io.IApplicationResourceService;

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
	private PrintStream printStream;
	
	private final IApplicationResourceService resourceService;
	
	@Inject
	public AntScriptLauncher (IApplicationResourceService resourceService)
	{
		this.resourceService = resourceService;
		this.printStream = System.out;
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
	public File getScriptAsFile() {
		//if the script is null, pass the empty string so that we do 
		//not get a null pointer exception
		File scriptFile = new File("");
		try {
			scriptFile = this.resourceService.getAppScript(this.script);
		} catch (FileNotFoundException e) {
			// TODO We should write this out to the Log
			this.printStream.println(e.getStackTrace());
		}
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
	public void runScript() {
		assert this.isScriptRunnable() : "Could not run Script. Was not runnable. Check that the script exists and can be read.";
		
		File buildFile = this.getScriptAsFile();
		
		Project antProject = new Project();
		antProject.setUserProperty("ant.file", buildFile.getAbsolutePath());
		antProject.setUserProperty("basedir", buildFile.getParent());
		this.loadPropertiesIntoProject(antProject);
		antProject.addBuildListener(this.consoleLogger);
		 
		// parse the ant script
		try
		{
			antProject.fireBuildStarted();
			antProject.init();
			ProjectHelper helper = ProjectHelper.getProjectHelper();
			antProject.addReference("ant.projectHelper", helper);
			helper.parse(antProject, buildFile);
			String defaultTarget = antProject.getDefaultTarget();
			antProject.executeTarget(defaultTarget);
			antProject.fireBuildFinished(null);
		}
		catch (BuildException e)
		{
			antProject.fireBuildFinished(e);
			throw new AssertionError(e);
		}
	}
	
	/* (non-Javadoc)
	 * Determine if the current script set on this launcher is a runnable script
	 */
	private boolean isScriptRunnable()
	{
		boolean isRunnable = false;
		File antScript = this.getScriptAsFile();
		if (this.script != null && antScript.exists() && antScript.canRead()
				&& antScript.isFile())
		{
			isRunnable = true;
		}
		return isRunnable;
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
		consoleLogger.setErrorPrintStream(System.err);
		consoleLogger.setOutputPrintStream(printStream);
		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
		
		//TODO:Remove the line below when we are fully configured with log4J
		this.setPrinStream(printStream);
	}

	/*
	 * (non-Javadoc)
	 * @see com.intuit.ginsu.scripts.IScriptLauncher#setPrinStream(java.io.PrintStream)
	 */
	public void setPrinStream(PrintStream stdOut) {
		this.printStream = stdOut;
	}

}
