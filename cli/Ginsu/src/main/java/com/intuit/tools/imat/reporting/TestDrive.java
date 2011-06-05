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

import java.io.*;

public class TestDrive {	
	public static void main(String[] args) {
		File pList = new File("C:\\testplist\\three_failed_tests.plist");
		File junitXML = new File("C:\\xmloutput\\junitreport.xml");
		IReportingService reportingService = new IOSAutomationReportingService ();
		reportingService.setJunitXMLResultFile(junitXML);
		reportingService.convertTestOutputFileToJunitXMLResultFormat(pList);		
		System.out.println ("JunitXML file location is " + reportingService.getJunitXMLResultFile());		
	}
}
