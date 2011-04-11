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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.beust.jcommander.JCommander;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.intuit.ginsu.AppContext;
import com.intuit.ginsu.commands.CommandGenerateProject;
import com.intuit.ginsu.commands.CommandHelp;
import com.intuit.ginsu.commands.CommandInitEnv;
import com.intuit.ginsu.commands.CommandNull;
import com.intuit.ginsu.commands.CommandRunTests;
import com.intuit.ginsu.commands.ICommand;
import com.intuit.ginsu.commands.SupportedCommandCollection;

/**
 * @author rpfeffer
 * @dateCreated Mar 13, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public class CommandLineParsingServiceTest {

	
	private ByteArrayOutputStream outputStreamFixture;
	private PrintWriter printWriter;
	private File tempFile;
	private JCommander jCommander;
	private Injector injector;
	private Logger logger;
	
	@BeforeClass
	protected void setUpContext()
	{
		AppContext appContext = AppContext.getInstance();
		appContext.setAppModule(Modules.override( new GinsuCLIModule()).with(new GinsuTestModuleOverride()));
		this.injector = Guice.createInjector(appContext.getAppModule());
		this.logger = injector.getInstance(Logger.class);
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@BeforeMethod()
	protected void setUp() throws Exception {
		this.outputStreamFixture = new ByteArrayOutputStream();
		this.printWriter = new PrintWriter(this.outputStreamFixture, true);
		try
		{
			tempFile = File.createTempFile("ginsuTestFile",".txt");
			tempFile.deleteOnExit();
		}catch (IOException e)
		{
			//Most likely we were unable to write the file to the temp directory. :-(
			AssertJUnit.assertTrue(e.getMessage(), false);
		}
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@AfterMethod()
	protected void tearDown() throws Exception {
		try {
    		this.outputStreamFixture.flush();
    		this.outputStreamFixture.close();
		} catch (IOException e) {
			//if we get here we should throw a failure to show that something went wrong
			AssertJUnit.assertTrue(e.getMessage(), false);
		}
	}
	
	/**
	 * TODO: Fill this in
	 */
	@Test()
	public void testParseInputWithValidCommands()
	{
		String[][] testData = new String[][]  {
				new String[] {CommandHelp.NAME},
				new String[] {CommandInitEnv.NAME, 
						"-template",  tempFile.getAbsolutePath()},
				new String[] {CommandGenerateProject.NAME},
				new String[] {CommandRunTests.NAME},
		};
		
		ICommand[] expectedCommands = new ICommand[]  {
				new CommandHelp(printWriter, logger),
				new CommandInitEnv(printWriter, logger),
				new CommandGenerateProject(printWriter, logger),
				new CommandRunTests(printWriter, logger),
		};
		
		MainArgs mainArgs = injector.getInstance(MainArgs.class);
		
		
		for (int i = 0; i < testData.length; i++)
		{
			String[] args = testData[i];
			
			//The JCommander Object gains state when you invoke its "parse()" method,
			//so we need to keep recreating new ones in the loop.
			this.jCommander = injector.getInstance(JCommander.class);
			SupportedCommandCollection cmdCollection = injector.getInstance(SupportedCommandCollection.class);
			CommandLineParsingService parsingService = new CommandLineParsingService(
					this.printWriter, this.jCommander, mainArgs, cmdCollection);
			parsingService.parseInput(args);
			AssertJUnit.assertEquals(expectedCommands[i], parsingService.getCommand());
			AssertJUnit.assertEquals(mainArgs.getConfigurationOverride(), parsingService.getConfigurationOverride());
			AssertJUnit.assertEquals("", this.outputStreamFixture.toString());
		}
	}
	
	/**
	 * Test that a parsing error will result in a null command and showing an error/usage
	 * message to the user.
	 */
	@Test()
	public void testParseInputWithInvalidCommands()
	{
		//Set up the specifics for this test
		String[] testData = new String[] {"foobar"};
		ICommand expectedCommand = new CommandNull();
		MainArgs mainArgs = injector.getInstance(MainArgs.class);
		this.jCommander = injector.getInstance(JCommander.class);
		SupportedCommandCollection cmdCollection = injector.getInstance(SupportedCommandCollection.class);
		CommandLineParsingService parsingService = new CommandLineParsingService(
				this.printWriter, this.jCommander, mainArgs, cmdCollection);

		StringBuilder exepectedUsageString = new StringBuilder();
		this.jCommander.usage(exepectedUsageString);
		String expectedOutput = "Expected a command, got foobar" 
			+ System.getProperty("line.separator") 
			+ exepectedUsageString.toString(); 
		
		//parse the test data
		parsingService.parseInput(testData);
		
		//Validate the results
		AssertJUnit.assertEquals(expectedCommand, parsingService.getCommand());
		AssertJUnit.assertEquals(mainArgs.getConfigurationOverride(), parsingService.getConfigurationOverride());
		AssertJUnit.assertEquals(expectedOutput.trim(), this.outputStreamFixture.toString().trim());
	}
	
	@Test()
	public void testParseInputWithCommandHelp()
	{
		String[][] testArgs = new String[][]  {
				/*new String[] {CommandHelp.NAME, "-help"},*/
				new String[] {CommandInitEnv.NAME, 
						"-template",  tempFile.getAbsolutePath(), "-help"},
				new String[] {CommandGenerateProject.NAME, "-help"},
				new String[] {CommandRunTests.NAME, "-help"},
		};
		
		String[] expectedHelpNeedles = new String[]  {
				/*"Print out this help text.",*/
				CommandInitEnv.TEMPLATE,
				CommandGenerateProject.TARGET_DIR,
				CommandRunTests.PLACEHOLDER,
		};
		
		MainArgs mainArgs = injector.getInstance(MainArgs.class);
		
		for (int i = 0; i < testArgs.length; i++)
		{
			String[] args = testArgs[i];
			
			//The JCommander Object gains state when you invoke its "parse()" method,
			//so we need to keep recreating new ones in the loop.
			this.jCommander = injector.getInstance(JCommander.class);
			SupportedCommandCollection cmdCollection = injector.getInstance(SupportedCommandCollection.class);
			CommandLineParsingService parsingService = new CommandLineParsingService(
					this.printWriter, this.jCommander, mainArgs, cmdCollection);
			parsingService.parseInput(args);
			
			//try to force the flushing of the text from the printwriter
			this.printWriter.flush();
			
			String outputValue = this.outputStreamFixture.toString().trim();
			assert outputValue.contains(expectedHelpNeedles[i]) : "Output Value is: " + outputValue 
				+ " when we were looking for \"" + expectedHelpNeedles[i] + "\""; 
			
			//reset the byte array output stream to make sure we arent getting any false positives
			this.outputStreamFixture = new ByteArrayOutputStream();
			this.printWriter = new PrintWriter(this.outputStreamFixture, true);
		}
	}
}