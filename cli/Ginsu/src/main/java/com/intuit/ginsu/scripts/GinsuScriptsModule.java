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

import java.io.PrintStream;

import org.apache.log4j.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.intuit.ginsu.IApplicationResourceService;
import com.intuit.ginsu.IScriptLauncher;
import com.intuit.ginsu.annotations.AntScript;
import com.intuit.ginsu.annotations.AppleScript;

/**
 * @author rpfeffer
 * @dateCreated Apr 28, 2011
 * 
 *              This class provides Google guice configuration for the classes
 *              within the com.intuit.ginsu.scripts module
 * 
 */
public class GinsuScriptsModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(IScriptLauncher.class).annotatedWith(AntScript.class).to(AntScriptLauncher.class);
		bind(IScriptLauncher.class).annotatedWith(AppleScript.class).to(AppleScriptLauncher.class);
	}
	
	/**
	 * Create an {@link IScriptLauncher} object that will launch the ant scripts for
	 * Ginsu.
	 * 
	 * @param printStream
	 *            a {@link PrintStream} which the script launcher object will
	 *            write to as it executes
	 * @param resourceService The {@link IApplicationResourceService} used to retrieve the scripts
	 * @return an instance of {@link IScriptLauncher} that runs ant scripts.
	 */
	@Provides AntScriptLauncher provideAntScriptLauncher(PrintStream printStream, 
			IApplicationResourceService resourceService) 
	{
		AntScriptLauncher antScriptLauncher = new AntScriptLauncher(
				resourceService, Logger.getLogger(AntScriptLauncher.class));
		antScriptLauncher.setProjectListener(printStream);
		return antScriptLauncher;
	}
	
	/**
	 * Create an {@link IScriptLauncher} object that will launch the apple scripts for
	 * Ginsu.
	 * 
	 * @param printStream
	 *            a {@link PrintStream} which the script launcher object will
	 *            write to as it executes
	 * @param resourceService The {@link IApplicationResourceService} used to retrieve the scripts
	 * @return an instance of {@link IScriptLauncher} that runs ant scripts.
	 */
	@Provides AppleScriptLauncher provideAppleScriptLauncher(PrintStream printStream, 
			IApplicationResourceService resourceService) 
	{
		AppleScriptLauncher appleScriptLauncher = new AppleScriptLauncher(
				resourceService, Logger.getLogger(AntScriptLauncher.class));
		appleScriptLauncher.setPrintStream(printStream);
		return appleScriptLauncher;
	}

}
