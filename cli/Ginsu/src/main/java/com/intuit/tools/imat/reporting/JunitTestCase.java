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
	private String classname = "";
	private String testcasename = "";
	private String time = "";
	private STATUS status;
	private String startTime = "";
	private String endTime = "";
	private JunitTestCaseInnerMessage message = new JunitTestCaseInnerMessage();
	private boolean isIncomplete = true;
	
	public enum STATUS {PASS, FAIL, ERROR}
	
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
	public JunitTestCaseInnerMessage getInnerMessage() {
		return message;
	}
	public void setInnerMessage(JunitTestCaseInnerMessage message) {
		this.message = message;
	}
	
	public STATUS getStatus() {
		return status;
	}
	public void setStatus(STATUS status) {
		this.status = status;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param hasBeenCaptured the hasBeenCaptured to set
	 */
	public void setIsIncomplete(boolean isIncomplete) {
		this.isIncomplete = isIncomplete;
	}
	/**
	 * @return the hasBeenCaptured
	 */
	public boolean isIncomplete() {
		return isIncomplete;
	}	
}
