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
package com.intuit.ginsu.cli;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * @author rpfeffer
 * @dateCreated Mar 12, 2011
 *
 * This class is used to Generate and Print help text for all of the different
 * ways in which ginsu can be run.
 *
 */
public class HelpTextGenerator {

		private HelpFormatter helpFormatter;
		private PrintWriter printWriter;
		private static final int HELP_TEXT_WIDTH = 80;
		private static final String USAGE_SYNTAX = 
			"ginsu [OPTION]... [SUBCOMMAND] [OPTION1 [OPTION2] [OPTION3] ...]]";
		
		public HelpTextGenerator(PrintWriter printWriter)
		{
			this.printWriter = printWriter;
			this.helpFormatter = new HelpFormatter();
		}
		
		public void printUsage(Exception e)
		{
			this.printWriter.println(e.getMessage());
			this.printUsage();
		}
		
		public void printUsage()
		{
			OptionsFactory optionsFactory = new OptionsFactory();
			this.usageHelper(this.printWriter, optionsFactory.generateCoreOptions());
		}
		
		public String getUsage()
		{
			OptionsFactory optionsFactory = new OptionsFactory();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    	PrintWriter printWriter = new PrintWriter(outputStream, true);
			this.usageHelper(printWriter, optionsFactory.generateCoreOptions());
			return outputStream.toString();
		}
		
		public void printUsageForCommand()
		{
			
		}
		
		public void printOptionsForCommand(String cmd)
		{
			
		}
		
		public void printOptionsForAllCommands()
		{
			
		}
		
		public void printHelp()
		{
			
		}
		
		private void usageHelper(PrintWriter printWriter, Options options)
		{
			this.helpFormatter.printUsage(printWriter, HELP_TEXT_WIDTH,
					USAGE_SYNTAX);
		}
}
