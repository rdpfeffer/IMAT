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
package com.intuit.ginsu;

/**
 * @author rpfeffer
 * @dateCreated Apr 20, 2011
 *
 * 
 *
 */
public class MisconfigurationException extends Exception {

	private static final long serialVersionUID = 1L;

	public MisconfigurationException(String message)
	{
		super(message + " An internal configuration error occured.");
	}
}
