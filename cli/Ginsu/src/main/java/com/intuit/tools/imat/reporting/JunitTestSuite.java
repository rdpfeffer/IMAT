/*******************************************************************************
0 * Copyright (c) 2009 Intuit, Inc.
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

public class JunitTestSuite {	
	private String errors = "0";
	private String failures = "0";
	private int errorCount;
	private int failureCount;
	private String hostName = "";
	private String id = "";
	private String name = "";
	private String packageName = "";
	private String tests = "";
	private String time = "";
	private String timeStamp = "";
	private ArrayList<JunitTestCase> testCaseList = new ArrayList<JunitTestCase>();
	private boolean moreReportsExist = true;
	private boolean isSuiteCaptured = false;
	
	void incrementErrorCount () {
		errorCount++;		
		errors = Integer.toString(errorCount);		
	}
	
	void incrementFailureCount() {
		failureCount++;
		failures = Integer.toString(failureCount);
	}
	
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public String getFailures() {
		return failures;
	}
	public void setFailures(String failures) {
		this.failures = failures;
	}
	public String getHostname() {
		return hostName;
	}
	public void setHostname(String hostName) {
		this.hostName = hostName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPackagename() {
		return packageName;
	}
	public void setPackagename(String packageName) {
		this.packageName = packageName;
	}
	public String getTests() {
		return tests;
	}
	public void setTests(String tests) {
		this.tests = tests;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTimestamp() {
		return timeStamp;
	}
	public void setTimestamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public ArrayList<JunitTestCase> getTestCaseList() {
		return testCaseList;
	}
	public void setTestcaseList(ArrayList<JunitTestCase> testCaseList) {
		this.testCaseList = testCaseList;
	}

	/**
	 * @param moreReportsExist the moreReportsExist to set
	 */
	public void setMoreReportsExist(boolean moreReportsExist) {
		this.moreReportsExist = moreReportsExist;
	}

	/**
	 * @return the moreReportsExist
	 */
	public boolean moreReportsExist() {
		return moreReportsExist;
	}

	/**
	 * @param isSuiteCaptured the isSuiteCaptured to set
	 */
	public void setSuiteCaptured(boolean isSuiteCaptured) {
		this.isSuiteCaptured = isSuiteCaptured;
	}

	/**
	 * @return the isSuiteCaptured
	 */
	public boolean isSuiteCaptured() {
		return isSuiteCaptured;
	}

}
