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

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.beust.jcommander.JCommander;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.intuit.ginsu.commands.CommandGenerateProject;
import com.intuit.ginsu.commands.CommandInitEnv;
import com.intuit.ginsu.commands.CommandNull;
import com.intuit.ginsu.commands.CommandRunTests;
import com.intuit.ginsu.commands.ICommand;
import com.intuit.ginsu.commands.SupportedCommandCollection;
import com.intuit.ginsu.io.FileSystemResourceService;
import com.intuit.ginsu.logging.BindLog4JWithClassNameModule;
import com.intuit.ginsu.scripts.AntScriptLauncher;

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
		this.injector = Guice.createInjector(
				Modules.override(new GinsuCLIModule()).with(
						new GinsuTestModuleOverride()),
				new BindLog4JWithClassNameModule());
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
				new String[] {UsagePrinter.NAME},
				new String[] {CommandInitEnv.NAME, 
						"-template",  tempFile.getAbsolutePath()},
				new String[] {CommandGenerateProject.NAME},
				new String[] {CommandRunTests.NAME},
		};
		
		ICommand[] expectedCommands = new ICommand[]  {
				new UsagePrinter(printWriter, ""),
				new CommandInitEnv(printWriter, logger),
				new CommandGenerateProject(printWriter, logger, 
						new AntScriptLauncher(
								new FileSystemResourceService(
										Logger.getLogger(FileSystemResourceService.class)), null)),
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
		ICommand expectedCommand = new UsagePrinter(printWriter, "");
		
		//parse the test data
		parsingService.parseInput(testData);
		ICommand resultingCommand = parsingService.getCommand();
		AssertJUnit.assertEquals(expectedCommand.getName(), resultingCommand.getName());
		AssertJUnit.assertEquals(mainArgs.getConfigurationOverride(), parsingService.getConfigurationOverride());
		
		resultingCommand.run();
		AssertJUnit.assertEquals(expectedOutput.trim(), this.outputStreamFixture.toString().trim());
	}
	
	@Test()
	public void testParseInputWithCommandHelp()
	{
		String[][] testArgs = new String[][]  {
				new String[] {UsagePrinter.NAME},
				new String[] {CommandInitEnv.NAME, "-help"},
				new String[] {CommandGenerateProject.NAME, "-help"},
				new String[] {CommandRunTests.NAME, "-help"},
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
			
			ICommand command = parsingService.getCommand();
			assert command.getName() == UsagePrinter.NAME;
		}
	}
}