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

import java.util.Arrays;

import junit.framework.TestCase;

/**
 * @author rpfeffer
 * @dateCreated Mar 13, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public class CommandHelperTest extends TestCase {

	/**
	 * @param name
	 */
	public CommandHelperTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * TODO: Fill this in
	 */
	public void testGetCommandFromMainArgsWithInvalidCommands()
	{
		String[][] tests = new String[][]  {
				new String[] {},
				new String[] {""},
				new String[] {"-arg1"},
				new String[] {"-arg1", "val1"},
				new String[] {"-arg1", "val1", "-arg2"},
				new String[] {"-arg1", "val1", "-arg2", "val2"},
		};
		for (int i = 0; i < tests.length; i++)
		{
			String[] args = tests[i];
			try
			{
				CommandHelper.getCommandFromMainArgs(args);

				//fail the test if we get here.
				assertTrue("We expected an exception when parsing args: "
						+ Arrays.toString(args), false);
			} catch (InvalidMainArgumentArray e) {
				//This what we want, some of the above should
				//be caught here.
				assertTrue("The following was successfully "
						+ "detected as an invalid arry of args: "
						+ Arrays.toString(args), true);
			} catch (CommandNotFoundException e) {
				//This is what we want as well, some of the above should
				//be caught here.
				assertTrue("The following was successfully "
						+ "detected as an invalid arry of args: "
						+ Arrays.toString(args), true);
			} catch (InternalCommandParsingException e) {
				e.printStackTrace();
				assertTrue("Internal error when parsing args: "
						+ Arrays.toString(args), false);
			}
		}
	}
	
	/**
	 * TODO: Fill this in
	 */
	public void testGetCommandFromMainArgsWithValidCommands()
	{
		String cmd = "";
		String[][] tests = new String[][]  {
				new String[] {CommandHelper.RUN_TEST_CMD},
				new String[] {"-arg1", "-arg2", CommandHelper.RUN_TEST_CMD},
				new String[] {"-arg1", "", CommandHelper.RUN_TEST_CMD,  "val1"}
		};
		for (int i = 0; i < tests.length; i++)
		{
			String[] args = tests[i];
			try
			{
				cmd = CommandHelper.getCommandFromMainArgs(args);
			} catch (Exception e) {
				assertTrue("The following command resulted in an unexpected exception: "
						+ Arrays.toString(args) + "\n"
						+ e.getMessage(), false);
			}
			assertEquals("We successfully found a command when parsing args: "
					+ Arrays.toString(args),
					CommandHelper.RUN_TEST_CMD, cmd);
		}
	}
	
	/**
	 * TODO: fill this in
	 */
	public void testCommandOptionsFromMainArgs()
	{
		
	}
}