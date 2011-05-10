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

import java.util.HashSet;
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
public enum AppContext {

	/* Our Singleton */
	INSTANCE;

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

	/**
	 * The main project key for the application to be used when getting the Home
	 * directory of the target project.
	 * <p>
	 * To get the path to the project directory of the applicaiton, the user
	 * should call
	 * <code>AppContext.getInstance().getProperty(AppContext.PROJECT_HOME_KEY);</code>
	 * </p>
	 */
	public static final String PROJECT_HOME_KEY = "project_key";

	/**
	 * This is Key to the property used to determine if the application should
	 * exit returning the command's exit status. NOTE: if a property is not
	 * stored in the app context, it will come back as the empty string "".
	 */
	public static final String SKIP_EXIT_STATUS = "skip_exit_status_key";
	private final HashSet<Module> appModules = new HashSet<Module>();
	private final Hashtable<String, String> properties = new Hashtable<String, String>();

	/**
	 * @return the {@link HashSet} of Modules currently added to the app
	 *         context.
	 */
	public HashSet<Module> getAppModules() {
		return appModules;
	}

	/**
	 * @param module
	 *            the {@link Module} to add
	 */
	public void addAppModule(Module module) {
		this.appModules.add(module);
	}

	/**
	 * Get an instance of the Guice Dependency Injector.
	 * <p>
	 * NOTE: This returns a new instance of the dependency injector every time.
	 * Repeated calls to this method will not return the same injector
	 * </p>
	 * 
	 * @return {@link Injector} an instance of the dependency injector
	 *         configured to use the currently set application module
	 */
	public Injector getInjector() {
		return Guice.createInjector(this.getAppModules());
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
	public String getProperty(String key) {
		String value = this.properties.get(key);
		return (value == null ? "" : value);
	}

	/**
	 * Set an application property on the App Context.
	 * 
	 * @param key
	 *            String the key by which the property can be retrieved.
	 * @param value
	 *            The value of the property.
	 */
	public void setProperty(String key, String value) {
		this.properties.put(key, value);
	}

	/**
	 * Override a set of properties already set on the Application context.
	 * 
	 * @param properties
	 *            The {@link Hashtable} of Properties which will override what
	 *            is already set in the applicaiton context.
	 */
	public void overrideProperties(Hashtable<String, String> properties) {
		this.properties.putAll(properties);
	}

	/**
	 * Clear the Application Context to an empty state. This function clears all
	 * previously set modules and properties and leaves the app context in a
	 * clean state as if it were a new instance.
	 */
	public void clear() {
		appModules.clear();
		properties.clear();
	}

}
