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
package com.intuit.tools.imat;

/**
 * @author rpfeffer
 * @dateCreated May 7, 2011
 * 
 *              //TODO Explain why this file exists and how it is used.
 * 
 */
public interface ITestMonitor extends IFileListener {

	/**
	 * @return @TODO DocMe
	 */
	public boolean isExecutionComplete();

	/**
	 * @return @TODO DocMe
	 */
	public boolean testsDidRunToCompletion();

}
