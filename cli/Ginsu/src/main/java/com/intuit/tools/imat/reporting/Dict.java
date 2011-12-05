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

public class Dict {	
	private String string = "";
	private String date = "";
	private UIALoggerCode code;	
	private ArrayList<Dict> dictList = new ArrayList<Dict>();

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public UIALoggerCode getCode() {
		return code;
	}

	public void setCode(UIALoggerCode code) {
		this.code = code;
	}
	
	public void setCode(String code) {
		this.code = UIALoggerCode.values()[Integer.valueOf(code).intValue()];
	}

	/**
	 * @param dict the dict to set
	 */
	public void addDict(Dict dict) {
		this.dictList.add(dict);
	}

	/**
	 * @return the dict
	 */
	public Dict getDictAtIndex(int index) {
		return dictList.get(index);
	}
	
	public void clearDictList() {
		this.dictList.clear();
	}
	
	public int countSubDicts()
	{
		return dictList.size();
	}
	
	public boolean isUncaughtExceptionLogEntry() {
		return this.getCode() == UIALoggerCode.UNCAUGHT_ERROR;
	}
	
	public boolean isStartLogEntry() {
		return this.getCode() == UIALoggerCode.TESTSTART;
	}
	
	public boolean isCompletionLogEntry() {
		boolean isComplete = false;
		if (	this.getCode() == UIALoggerCode.FAIL ||
				this.getCode() == UIALoggerCode.ISSUE ||
				this.getCode() == UIALoggerCode.PASS) {
			isComplete = true;
		}
		return isComplete;
	}
	
	public boolean showsScriptWasHalted() {
		return this.getCode() == UIALoggerCode.STOPPED; 
	}

}
