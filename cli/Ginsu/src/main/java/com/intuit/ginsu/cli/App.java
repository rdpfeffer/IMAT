/*******************************************************************************
* Copyright (c) 2011 Intuit, Inc.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.opensource.org/licenses/eclipse-1.0.php
* 
* Contributors:
*     Intuit, Inc - initial API and implementation
*******************************************************************************/
package com.intuit.ginsu.cli;

import com.google.inject.Injector;
import com.intuit.ginsu.AppContext;
import com.intuit.ginsu.commands.ICommand;
import com.intuit.ginsu.commands.ICommandDispatchService;
import com.intuit.ginsu.config.IConfigurationService;

/**
 * 
 * @author rpfeffer
 * @dateCreated Mar 11, 2011
 *
 * This is the main Class used to execute all of the ginsu command line interface.
 */
public class App 
{
    public static void main( String[] args )
    {
    	//get a reference to the AppContext Singleton object and initialize the CLI module.
    	AppContext appContext = AppContext.getInstance();
    	appContext.setAppModule(new GinsuCLIModule());
    	Injector injector = appContext.getInjector();
    	
    	//Load and parse the Input from the user 
    	IInputParsingService inputService = injector.getInstance(IInputParsingService.class);
    	inputService.parseInput(args);
    	ICommand command  = inputService.getCommand();
    	
    	//Load the configuration
    	IConfigurationService configService = injector.getInstance(IConfigurationService.class);
    	
    	//If we have a runnable command 
    	if(command.isRunnable() && configService.isNotInitialized())
    	{
    		//run the first time initialization
    		configService.doFirstTimeInitialization();//we will check for accepting license agreement here etc.
    	}

    	//run the loaded command using the command dispatch service
    	ICommandDispatchService commandDispatchService = injector.getInstance(ICommandDispatchService.class);
    	commandDispatchService.dispatch(command);
    	
    	//TODO after the command has run, dispatch the update command in the background and exit
    }
}
