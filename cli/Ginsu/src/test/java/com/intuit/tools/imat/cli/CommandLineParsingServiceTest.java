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
package com.intuit.tools.imat.cli;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.beust.jcommander.JCommander;
import com.google.inject.Injector;
import com.intuit.tools.imat.ICommand;
import com.intuit.tools.imat.MisconfigurationException;
import com.intuit.tools.imat.cli.CommandLineParsingService;
import com.intuit.tools.imat.cli.MainArgs;
import com.intuit.tools.imat.cli.UsagePrinter;
import com.intuit.tools.imat.commands.CommandInitEnv;
import com.intuit.tools.imat.commands.CommandNewProject;
import com.intuit.tools.imat.commands.CommandRunTests;
import com.intuit.tools.imat.commands.SupportedCommandCollection;

/**
 * @author rpfeffer
 * @dateCreated Mar 13, 2011
 * 
 *              This class tests the command line parsing Service at a
 *              functional level.
 * 
 */
public class CommandLineParsingServiceTest extends BaseFunctionalTest {

	private ByteArrayOutputStream outputStreamFixture;
	private PrintWriter printWriter;
	private File tempFile;
	private JCommander jCommander;
	private Injector injector;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@BeforeMethod()
	protected void setUp() throws Exception {
		this.outputStreamFixture = new ByteArrayOutputStream();
		this.printWriter = new PrintWriter(this.outputStreamFixture, true);
		this.injector = getFunctionalTestInjector(outputStreamFixture);
		try {
			tempFile = File.createTempFile("imatTestFile", ".txt");
			tempFile.deleteOnExit();
		} catch (IOException e) {
			// Most likely we were unable to write the file to the temp
			// directory. :-(
			AssertJUnit.assertTrue(e.getMessage(), false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@AfterMethod()
	protected void tearDown() throws Exception {
		this.outputStreamFixture.flush();
		this.outputStreamFixture.close();
	}

	/**
	 * Test that we handle basic and valid commands
	 */
	@Test()
	public void testHandleInputWithValidCommands() {
		String[][] testData = new String[][] {
				new String[] { UsagePrinter.NAME },
				new String[] { CommandInitEnv.NAME, "-template",
						tempFile.getAbsolutePath() },
				new String[] { CommandNewProject.NAME },
				new String[] { CommandRunTests.NAME }, };

		ICommand[] expectedCommands = new ICommand[] {
				injector.getInstance(UsagePrinter.class),
				injector.getInstance(CommandInitEnv.class),
				injector.getInstance(CommandNewProject.class),
				injector.getInstance(CommandRunTests.class), };

		MainArgs mainArgs = injector.getInstance(MainArgs.class);
		mainArgs.appHome = ".";
		for (int i = 0; i < testData.length; i++) {
			String[] args = testData[i];

			// The JCommander Object gains state when you invoke its "parse()"
			// method,
			// so we need to keep recreating new ones in the loop.
			this.jCommander = injector.getInstance(JCommander.class);
			SupportedCommandCollection cmdCollection = injector
					.getInstance(SupportedCommandCollection.class);
			CommandLineParsingService parsingService = new CommandLineParsingService(
					this.jCommander, mainArgs, cmdCollection);
			parsingService.handleInput(args);
			AssertJUnit.assertEquals(expectedCommands[i].getName(),
					parsingService.getCommand().getName());
			AssertJUnit.assertEquals(mainArgs.getConfigurationOverride(),
					parsingService.getConfigurationOverride());
			AssertJUnit.assertEquals("", this.outputStreamFixture.toString());
		}
	}

	/**
	 * Test that a parsing error will result in a null command and showing an
	 * error/usage message to the user.
	 * @throws MisconfigurationException 
	 */
	@Test()
	public void testHandleInputWithBogusCommands() throws MisconfigurationException {
		// Set up the specifics for this test
		String[] testData = new String[] { "foobar" };
		MainArgs mainArgs = injector.getInstance(MainArgs.class);
		mainArgs.appHome = ".";
		this.jCommander = injector.getInstance(JCommander.class);
		SupportedCommandCollection cmdCollection = injector
				.getInstance(SupportedCommandCollection.class);
		CommandLineParsingService parsingService = new CommandLineParsingService(
				this.jCommander, mainArgs, cmdCollection);

		StringBuilder exepectedUsageString = new StringBuilder();
		this.jCommander.usage(exepectedUsageString);
		ICommand expectedCommand = new UsagePrinter(printWriter);

		// parse the test data
		parsingService.handleInput(testData);
		ICommand resultingCommand = parsingService.getCommand();
		AssertJUnit.assertEquals(expectedCommand.getName(),
				resultingCommand.getName());
		AssertJUnit.assertEquals(mainArgs.getConfigurationOverride(),
				parsingService.getConfigurationOverride());

		resultingCommand.run();
		String result = this.outputStreamFixture.toString();
		assert result.contains("Expected a command, got foobar");
	}
	
	/**
	 * Test that our commands are getting validated by our custom validator 
	 * classes
	 * 
	 * @TODO: Rewrite this using Mock Objects for the JCommander Object.
	 */
	@Test()
	public void testHandleInputWithInvalidCommand() throws MisconfigurationException {
//		// Set up the specifics for this test
//		String[] testData = new String[] { CommandNewProject.NAME, "-g", App.APP_NAME_UPPERCASE};
//		MainArgs mainArgs = injector.getInstance(MainArgs.class);
//		mainArgs.appHome = ".";
//		this.jCommander = injector.getInstance(JCommander.class);
//		SupportedCommandCollection cmdCollection = injector
//				.getInstance(SupportedCommandCollection.class);
//		CommandLineParsingService parsingService = new CommandLineParsingService(
//				this.jCommander, mainArgs, cmdCollection);
//
//		StringBuilder exepectedUsageString = new StringBuilder();
//		this.jCommander.usage(exepectedUsageString);
//		ICommand expectedCommand = new UsagePrinter(printWriter);
//
//		// parse the test data
//		parsingService.handleInput(testData);
//		ICommand resultingCommand = parsingService.getCommand();
//		AssertJUnit.assertEquals(expectedCommand.getName(),
//				resultingCommand.getName());
//		AssertJUnit.assertEquals(mainArgs.getConfigurationOverride(),
//				parsingService.getConfigurationOverride());
//
//		resultingCommand.run();
//		String result = this.outputStreamFixture.toString();
//		assert result.contains("-globalObjVar may not be equal to: IMAT");
	}

	@Test()
	public void testHandleInputWithCommandHelp() {
		String[][] testArgs = new String[][] {
				new String[] { UsagePrinter.NAME },
				new String[] { CommandInitEnv.NAME, "-help" },
				new String[] { CommandNewProject.NAME, "-help" },
				new String[] { CommandRunTests.NAME, "-help" }, };

		MainArgs mainArgs = injector.getInstance(MainArgs.class);

		for (int i = 0; i < testArgs.length; i++) {
			String[] args = testArgs[i];

			// The JCommander Object gains state when you invoke its "parse()"
			// method, so we need to keep recreating new ones in the loop.
			this.jCommander = injector.getInstance(JCommander.class);
			SupportedCommandCollection cmdCollection = injector
					.getInstance(SupportedCommandCollection.class);
			CommandLineParsingService parsingService = new CommandLineParsingService(
					this.jCommander, mainArgs, cmdCollection);
			parsingService.handleInput(args);

			ICommand command = parsingService.getCommand();
			assert command.getName() == UsagePrinter.NAME;
		}
	}
}