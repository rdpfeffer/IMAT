/*******************************************************************************
 * Copyright (c) 2011 Intuit, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.opensource.org/licenses/eclipse-1.0.php
 * 
 * Contributors:
 *     Intuit, Inc - initial API and implementation
 *******************************************************************************/
package com.intuit.ginsu;

import com.google.inject.AbstractModule;

/**
 * @author rpfeffer
 * @dateCreated Mar 12, 2011
 * 
 *              The Application context is used to store runtime applicaiton
 *              configurations. It provides a consistent interface accross the
 *              cli applicaiton to set and get the app wide shared objects and
 *              configurations.
 * 
 *              Examples: The Printwriter object we use to write to the console
 *              is set in the app context.
 * 
 *              Note: This also allows us to send to a byte array while we unit
 *              test
 * 
 */
public class AppContext {

	private static AppContext instance;
	private AbstractModule appModule;

	/**
	 * Default Constructor for the Application Context
	 */
	protected AppContext() {
		this.setAppModule(new GinsuModule());
	}

	/**
	 * Get an instance of the AppContext singleton object
	 * 
	 * @return the singleton instance of {@link AppContext}
	 */
	public static AppContext getInstance() {
		if (instance == null) {
			instance = new AppContext();
		}
		return instance;
	}

	/**
	 * @return the appModule
	 */
	public AbstractModule getAppModule() {
		return appModule;
	}

	/**
	 * @param appModule
	 *            the appModule to set
	 */
	public void setAppModule(AbstractModule appModule) {
		this.appModule = appModule;
	}

}
