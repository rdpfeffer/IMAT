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

public class JunitTestCase {	
	private String classname;
	private String testcasename;
	private String time;
	private String status;
	private JunitTestError testerror;
	private JunitTestFailure testfailure;
	
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getTestcasename() {
		return testcasename;
	}
	public void setTestcasename(String testcasename) {
		this.testcasename = testcasename;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public JunitTestError getTesterror() {
		return testerror;
	}
	public void setTesterror(JunitTestError testerror) {
		this.testerror = testerror;
	}
	public JunitTestFailure getTestfailure() {
		return testfailure;
	}
	public void setTestfailure(JunitTestFailure testfailure) {
		this.testfailure = testfailure;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
