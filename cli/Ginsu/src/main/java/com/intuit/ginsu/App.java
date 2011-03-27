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
package com.intuit.ginsu;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.intuit.ginsu.cli.IInputParsingService;
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
    	//get a reference to the AppContext Singleton object.
    	AppContext appContext = AppContext.getInstance();
    	Injector injector = Guice.createInjector(appContext.getAppModule());
    	
    	//Load and parse the Input from the user 
    	IInputParsingService inputService = injector.getInstance(IInputParsingService.class);
    	inputService.parseInput(args);
    	ICommand mainCommand = inputService.getMainCommandContext();
    	ICommand command  = inputService.getCommand();
    	
    	//Load the configuration
    	IConfigurationService configService = injector.getInstance(IConfigurationService.class);
    	configService.loadConfiguration();//load configs from ginsu home and project home in that order
    	configService.loadConfigurationOverride(mainCommand);  //TODO Use reflection here on the main command
    	if(command.isRunnable())
    	{
    		configService.initialize();//we will check for accepting licence agreement here etc.
    	}
    	//TODO: Set the Configuration on the command

    	//run the loaded command using the command dispatch service
    	ICommandDispatchService commandDispatchService = injector.getInstance(ICommandDispatchService.class);
    	commandDispatchService.dispatch(command);
    	command.cleanUp();
    }
}
