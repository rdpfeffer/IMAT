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
package com.intuit.ginsu;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.intuit.ginsu.cli.CommandHelper;
import com.intuit.ginsu.cli.CommandNotFoundException;
import com.intuit.ginsu.cli.InvalidCommandException;
import com.intuit.ginsu.cli.HelpTextGenerator;
import com.intuit.ginsu.cli.InternalCommandParsingException;
import com.intuit.ginsu.cli.InvalidMainArgumentArray;
import com.intuit.ginsu.cli.OptionsFactory;

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
    	//declare the options for the command passed in.
    	Options options = null;
    	
    	//get a reference to the AppContext Singleton object.
    	AppContext appContext = AppContext.getInstance();
    	
    	//initialize the helpTextGenerator
    	HelpTextGenerator helpTextGenerator = new HelpTextGenerator(
    			appContext.getPrintWriter());
    	try
    	{
    		// create the parser
    	    CommandLineParser parser = new GnuParser();
    	    
	    	//First pull apart the command from the arguments
	    	String command = CommandHelper.getCommandFromMainArgs(args);
	    	
	    	//These are the actual arguments we want to parse for options
	    	String cmdArgs[] = CommandHelper.getCommandOptionsFromMainArgs(args);
	    	
	    	OptionsFactory optionsFactory = new OptionsFactory();
	    	options = optionsFactory.generateCommandOptions(command);
    		CommandLine commandLine = parser.parse( options, cmdArgs );
	    	
    	} catch (ParseException e) {
			//TODO use a help generator class to print the right help message
    		HelpFormatter helpFormatter = new HelpFormatter();
			
		} catch (InternalCommandParsingException e) {
			appContext.getPrintWriter().println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			appContext.getPrintWriter().println(e.getMessage());
    		helpTextGenerator.printUsage();
		}
    }
}
