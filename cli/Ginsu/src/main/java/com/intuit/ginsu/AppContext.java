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
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.intuit.ginsu.cli.GinsuCLIModule;

/**
 * @author rpfeffer
 * @dateCreated Mar 12, 2011
 * 
 *              The Application context is used to store runtime application
 *              configurations modules. Combined with the Google Guice injection
 *              framework, it provides a consistent interface across the
 *              application to set and get the app-wide shared configurations.
 * 
 */
public class AppContext {

	private static AppContext instance;
	private Module appModule;

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
	public Module getAppModule() {
		return appModule;
	}

	/**
	 * @param module
	 *            the appModule to set
	 */
	public void setAppModule(Module module) {
		this.appModule = module;
	}
	
	public Injector getInjector()
	{
		return Guice.createInjector(this.getAppModule());
	}

}
