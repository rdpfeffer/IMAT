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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.util.Arrays;

/**
 * @author rpfeffer
 * @dateCreated Mar 13, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public class CommandHelperTest {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@BeforeMethod()
	protected void setUp() throws Exception {
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@AfterMethod()
	protected void tearDown() throws Exception {
	}
	
	/**
	 * TODO: Fill this in
	 */
	@Test()
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
				AssertJUnit.assertTrue("We expected an exception when parsing args: "
						+ Arrays.toString(args), false);
			} catch (InvalidMainArgumentArray e) {
				//This what we want, some of the above should
				//be caught here.
				AssertJUnit.assertTrue("The following was successfully "
						+ "detected as an invalid arry of args: "
						+ Arrays.toString(args), true);
			} catch (CommandNotFoundException e) {
				//This is what we want as well, some of the above should
				//be caught here.
				AssertJUnit.assertTrue("The following was successfully "
						+ "detected as an invalid arry of args: "
						+ Arrays.toString(args), true);
			} catch (InternalCommandParsingException e) {
				e.printStackTrace();
				AssertJUnit.assertTrue("Internal error when parsing args: "
						+ Arrays.toString(args), false);
			}
		}
	}
	
	/**
	 * TODO: Fill this in
	 */
	@Test()
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
				AssertJUnit.assertTrue("The following command resulted in an unexpected exception: "
						+ Arrays.toString(args) + "\n"
						+ e.getMessage(), false);
			}
			AssertJUnit.assertEquals("The expected command did not match what was found for command: "
					+ Arrays.toString(args),
					CommandHelper.RUN_TEST_CMD, cmd);
		}
	}
	
	/**
	 * TODO: fill this in
	 */
	@Test()
	public void testCommandOptionsFromMainArgsWithValidCommands()
	{
		String[] options = null;
		String[][] testData = new String[][]  {
				new String[] {CommandHelper.RUN_TEST_CMD},
				new String[] {"-arg1", "-arg2", CommandHelper.RUN_TEST_CMD},
				new String[] {"-arg1", "", CommandHelper.RUN_TEST_CMD,  "val1"},
				new String[] {"-arg1", "", CommandHelper.RUN_TEST_CMD,  "val1", "val2"},
				new String[] {"-arg1", "", CommandHelper.RUN_TEST_CMD,  "val1", ""}
		};
		
		String[][] expected = new String[][]  {
				new String[0],
				new String[0],
				new String[] {"val1"},
				new String[] {"val1", "val2"},
				new String[] {"val1", ""}
		};
		for (int i = 0; i < testData.length; i++)
		{
			String[] args = testData[i];
			try
			{
				options = CommandHelper.getCommandOptionsFromMainArgs(args);
			} catch (Exception e) {
				AssertJUnit.assertTrue("The following args resulted in an unexpected exception: "
						+ Arrays.toString(args) + "\n"
						+ e.getMessage(), false);
			}
			if (expected[i].length == 0)
			{
				AssertJUnit.assertTrue("we did not get an empty array for the args: " 
						+ Arrays.toString(args), options.length == 0);
			}
			else
			{
				for (int j = 0; j < expected[i].length; j++)
				{
					AssertJUnit.assertEquals("we did not get matching option for the args: "
							+ Arrays.toString(args), expected[i][j], options[j]);
				}
			}	
		}
	}
	
	@Test()
	public void testCommandOptionsFromMainArgsWithInvalidCommands()
	{
		
	}
}