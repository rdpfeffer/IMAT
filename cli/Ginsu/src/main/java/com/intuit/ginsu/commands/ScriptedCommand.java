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

import java.io.PrintWriter;
import java.util.logging.Logger;

import com.intuit.ginsu.scripts.IScriptLauncher;

/**
 * @author rpfeffer
 * @dateCreated Apr 12, 2011
 * 
 *              This abstract class provides an instance variable for launching
 *              scripts that assist commands that are mostly invoked using some
 *              sort of external scripting tool like ant.
 * 
 */
public abstract class ScriptedCommand extends Command {

	private final IScriptLauncher scriptLauncher;
	
	
	/**
	 * @param printwriter the {@link PrintWriter} this command will write to
	 * @param logger the {@link Logger} this command will log to.
	 * @param scriptLauncher the {@link IScriptLauncher} used to run the scripted command.
	 */
	public ScriptedCommand(PrintWriter printwriter, Logger logger, IScriptLauncher scriptLauncher) {
		super(printwriter, logger);
		this.scriptLauncher = scriptLauncher;
	}
	
	/**
	 * @return the {@link IScriptLauncher} for this application.
	 */
	public IScriptLauncher getScriptLauncher()
	{
		return this.scriptLauncher;
	}

}
