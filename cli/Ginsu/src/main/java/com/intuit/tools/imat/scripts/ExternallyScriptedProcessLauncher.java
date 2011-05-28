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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;

import com.intuit.tools.imat.IApplicationResourceService;
import com.intuit.tools.imat.IScriptLauncher;

/**
 * @author rpfeffer
 * @dateCreated Apr 22, 2011
 * 
 *              This class provides an abstract implementation for all
 *              externally scripted processes, like AppleScript
 * 
 */
public abstract class ExternallyScriptedProcessLauncher implements
		IScriptLauncher {

	private String script = "";
	private LinkedHashMap<String, String> properties = new LinkedHashMap<String, String>();
	private PrintStream printStream;
	protected final Logger logger;
	private final IApplicationResourceService resourceService;

	/**
	 * @return the resourceService
	 */
	public IApplicationResourceService getResourceService() {
		return resourceService;
	}

	/**
	 * @param resourceService
	 * @param logger
	 */
	ExternallyScriptedProcessLauncher(
			IApplicationResourceService resourceService, Logger logger) {
		this.resourceService = resourceService;
		this.logger = logger;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.scripts.IScriptLauncher#setScript(java.lang.String)
	 */
	public void setScript(String script) {
		this.script = script;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.scripts.IScriptLauncher#getScript()
	 */
	public String getScript() {
		return script;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.scripts.IScriptLauncher#getScriptAsFile()
	 */
	public File getScriptAsFile() throws FileNotFoundException {
		File scriptFile = getResourceService().getAppScript(getScript());
		String loggedMessage = "getScriptAsFile() found: "
				+ (scriptFile == null ? "null when trying to get script: "
						+ getScript() : scriptFile.getPath());
		logger.debug(loggedMessage);
		assert scriptFile != null : loggedMessage; // script file should never
													// be null. if it is we are
													// misconfigured.
		return scriptFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.intuit.tools.imat.scripts.IScriptLauncher#setScriptProperties()
	 */
	public LinkedHashMap<String, String> getProperties() {
		return this.properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(LinkedHashMap<String, String> properties) {
		this.properties = properties;
	}

	/*
	 * (non-Javadoc) Determine if the current script set on this launcher is a
	 * runnable script
	 */
	protected void preValidateScript(File antScript) throws BuildException {
		if (!antScript.canRead()) {
			throw new BuildException(
					"We did not have the right permissions to run the following "
							+ "script: " + antScript.getAbsolutePath()
							+ " See the Log for more details");
		} else if (!antScript.isFile()) {
			throw new BuildException(
					"The script we tried to run was not actually a file. "
							+ "script: " + antScript.getAbsolutePath()
							+ " See the Log for more details");
		}
	}

	/**
	 * @param command
	 * @return
	 */
	protected int executeCommand(String command) {
		int exitStatus = 1;

		Runtime rt = Runtime.getRuntime();
		Process pr;
		try {
			pr = rt.exec(command);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					pr.getInputStream()));
			BufferedReader errorInput = new BufferedReader(
					new InputStreamReader(pr.getErrorStream()));
			String line = null;
			while ((line = input.readLine()) != null) {
				printStream.println(line);
			}
			while ((line = errorInput.readLine()) != null) {
				printStream.println(line);
			}
			exitStatus = pr.waitFor();
		} catch (InterruptedException e) {
			logger.warn("Command interupted: ", e);
		} catch (IOException e) {
			logger.warn("IO Exception While running command: ", e);
		}
		logger.debug("Exited with error code " + String.valueOf(exitStatus));
		return exitStatus;
	}

	/**
	 * @return
	 */
	protected String getOrderedParamterString() {
		StringBuilder parameters = new StringBuilder();
		Collection<String> values = getProperties().values();
		for (Iterator<String> it = values.iterator(); it.hasNext();) {
			parameters.append(" ");
			parameters.append(it.next());
		}
		return parameters.toString();
	}

	/**
	 * @param printStream
	 */
	public void setPrintStream(PrintStream printStream) {
		this.printStream = printStream;
	}

}
