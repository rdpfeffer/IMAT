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

import org.apache.log4j.Logger;

import com.intuit.ginsu.ICommand;
import com.intuit.ginsu.ICommandDispatchService;
import com.intuit.ginsu.MisconfigurationException;

/**
 * @author rpfeffer
 * @dateCreated Mar 26, 2011
 * 
 *              The {@link SynchronousCommandDispatchService} runs commands set up by
 *              the applicaiton. This simple command dispatch service simply
 *              runs the command on the same thread and does nothing else
 * 
 */
public class SynchronousCommandDispatchService implements ICommandDispatchService {

	private final Logger logger;
	
	SynchronousCommandDispatchService(Logger logger)
	{
		this.logger = logger;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.intuit.ginsu.commands.ICommandDispatchService#dispatch(com.intuit
	 * .ginsu.commands.ICommand)
	 */
	public void dispatch(ICommand command) throws MisconfigurationException {
		logger.debug("Start Dispatching Command. Command Named: " + command.getName());
		command.run();
		logger.debug("Finished Dispatching Command. Command Named: " + command.getName());
	}

}
