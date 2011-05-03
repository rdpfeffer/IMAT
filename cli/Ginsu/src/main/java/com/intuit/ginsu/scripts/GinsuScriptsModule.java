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
		bind(IScriptLauncher.class).to(AntScriptLauncher.class);
	}
	
	/**
	 * Create an {@link IScriptLauncher} object that will launch the ant scripts for
	 * Ginsu.
	 * 
	 * @param printStream
	 *            a {@link PrintStream} which the script launcher object will
	 *            write to as it executes
	 * @param resourceService The {@link IApplicationResourceService} used to retrive the scripts
	 * @return an instance of {@link IScriptLauncher} that runs ant scripts.
	 */
	@Provides AntScriptLauncher provideScriptLauncher(PrintStream printStream, 
			IApplicationResourceService resourceService) 
	{
		AntScriptLauncher antScriptLauncher = new AntScriptLauncher(
				resourceService, Logger.getLogger(AntScriptLauncher.class));
		antScriptLauncher.setProjectListener(printStream);
		return antScriptLauncher;
	}

}
