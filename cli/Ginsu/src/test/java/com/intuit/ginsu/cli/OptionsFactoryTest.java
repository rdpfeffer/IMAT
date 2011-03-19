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
import org.apache.commons.cli.Options;

/**
 * @author rpfeffer
 * @dateCreated Mar 16, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public class OptionsFactoryTest {

	/**
	 * @param name
	 */
	public OptionsFactoryTest(String name) {
		//super(name);
	}

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
	
	@Test()
	public void testGenerateCommandOptions()
	{
		
		OptionsFactory optionsFactory = new OptionsFactory();
		Options options;
		try {
			options = optionsFactory.generateCommandOptions(CommandHelper.INIT_ENV_CMD);
			options = optionsFactory.generateCommandOptions(CommandHelper.INIT_ENV_CMD);
		} catch (InvalidCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
