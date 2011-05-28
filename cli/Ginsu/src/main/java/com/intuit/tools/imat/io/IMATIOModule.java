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
package com.intuit.tools.imat.io;

import java.util.Timer;

import org.apache.log4j.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.intuit.tools.imat.IApplicationResourceService;
import com.intuit.tools.imat.IFileMonitoringService;
import com.intuit.tools.imat.IProjectResourceService;

/**
 * @author rpfeffer
 * @dateCreated Apr 28, 2011
 * 
 *              This class defines the configuration of classes within the
 *              com.intuit.tools.imat.io module
 * 
 */
public class IMATIOModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(IApplicationResourceService.class).to(FileSystemResourceService.class);
		bind(IProjectResourceService.class).to(FileSystemResourceService.class);
		bind(Timer.class);
	}

	@Provides PathAnalyzer providePathAnalyzer()
	{
		return new PathAnalyzer(Logger.getLogger(PathAnalyzer.class));
	}
	
	@Provides FileSystemResourceService provideFileSystemResourceService(PathAnalyzer pathAnalyzer)
	{
		return new FileSystemResourceService(
				Logger.getLogger(FileSystemResourceService.class), 
				pathAnalyzer);
	}
	
	@Provides IFileMonitoringService provideFileMonitoringService(Timer timer)
	{
		return new FileMonitoringService(timer);
	}
}
