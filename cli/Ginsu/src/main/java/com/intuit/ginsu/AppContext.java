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

import java.util.Hashtable;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

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

	/**
	 * The main home key for the application to be used when getting the Home
	 * directory of the application.
	 * <p>
	 * To get the path to the home directory of the applicaiton, the user should
	 * call
	 * <code>AppContext.getInstance().getProperty(AppContext.APP_HOME_KEY);</code>
	 * </p>
	 */
	public static final String APP_HOME_KEY = "home_key";
	private static AppContext instance;
	private Module appModule;
	private Hashtable<String,String> properties = new Hashtable<String, String>();

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
	
	/**
	 * Get an instance of the Dependency Injector.
	 * <p>
	 * NOTE: This returns a new instance of the dependency injector every time.
	 * </p>
	 * 
	 * @return {@link Injector} an instance of the dependency injector
	 *         configured to use the currently set application module
	 */
	public Injector getInjector()
	{
		return Guice.createInjector(this.getAppModule());
	}
	
	/**
	 * Get a non-null property from the application context. If the key for the
	 * property does not exist, return the empty string.
	 * 
	 * @param key
	 *            The {@link String} mapped to the property string we want to
	 *            get.
	 * @return {@link String} - The value matching key, or the empty string if
	 *         the key has not been set
	 */
	public String getProperty(String key)
	{
		String value = this.properties.get(key);
		return (value == null ? "" : value);
	}
	
	/**
	 * Set an application property on the App Context.
	 * @param key String the key by which the property can be retrieved.
	 * @param value The value of the property.
	 */
	public void setProperty(String key, String value)
	{
		this.properties.put(key, value);
	}
	
	/**
	 * Override a set of properties already set on the Application context.
	 * 
	 * @param properties
	 *            The {@link Hashtable} of Properties which will override what
	 *            is already set in the applicaiton context.
	 */
	public void overrideProperties(Hashtable<String, String> properties)
	{
		this.properties.putAll(properties);
	}

}
