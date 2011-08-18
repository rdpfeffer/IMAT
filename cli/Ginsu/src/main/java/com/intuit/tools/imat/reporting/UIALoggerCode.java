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

/**
 * @author rpfeffer
 * @dateCreated Aug 15, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public enum UIALoggerCode {
	// UIA Logger codes
	DEBUG			(0),
	MESSAGE			(1),
	WARNING			(2),
	ERROR			(3),
	TESTSTART		(4),
	PASS			(5),
	ISSUE			(6),
	FAIL			(7),
	SCREENSHOT		(8),
	STOPPED			(9);
	
	private int code;
	
	UIALoggerCode(int code)
	{
		this.code = code;
	}
	
	public int getCode()
	{
		return this.code;
	}
}
