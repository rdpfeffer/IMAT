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
package com.intuit.tools.imat.reporting;

import java.io.File;

/**
 * @author ssathyamoorthy
 * @dateCreated Apr 25, 2011
 * 
 *              This Interface defines how test output file should be 
 *              converted to Ginsu XML result format.
 * 
 */

public interface IReportingService {
	
	/**
	 * Set the test output file created after the test run.	
	 */
	public void setTestOutputFile();
	
	/**
	 * Convert the test output file to the JUnit XML result format.
	 * @param test output file created after the test run.	
	 */
	public void convertTestOutputFileToJunitXMLResultFormat(File testOutputFile);
	
	/**
	 * Get the Junit XML result format file.	
	 */
	public File getJunitXMLResultFile();
	
	/**
	 * Get the Junit XML result format file.	
	 */
	public void setJunitXMLResultFile(File junitXMLResultFile);

}
