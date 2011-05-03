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

import org.apache.log4j.Logger;

import com.intuit.ginsu.IApplicationResourceService;

/**
 * @author rpfeffer
 * @dateCreated May 2, 2011
 * 
 *              This file is used to expose the AntScript launchers package
 *              private constructor so that we can use it for testing purposes
 * 
 */
public class AntScriptLauncherForTesting extends AntScriptLauncher {

	public AntScriptLauncherForTesting(
			IApplicationResourceService resourceService, Logger logger) {
		super(resourceService, logger);
	}
}
