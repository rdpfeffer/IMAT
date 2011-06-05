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

import java.util.ArrayList;

public class JunitTestSuiteList {
	private ArrayList<JunitTestSuite> testSuiteList;
	
	public ArrayList<JunitTestSuite> getTestSuiteList() {
		return testSuiteList;
	}
	public void setTestSuiteList(ArrayList<JunitTestSuite> testSuiteList) {
		this.testSuiteList = testSuiteList;
	}
}
