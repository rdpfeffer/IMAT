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
package com.intuit.ginsu.cli;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;

import com.beust.jcommander.IDefaultProvider;
import com.beust.jcommander.JCommander;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.intuit.ginsu.AppContext;
import com.intuit.ginsu.IApplicationResourceService;
import com.intuit.ginsu.ICommand;
import com.intuit.ginsu.ICommandDispatchService;
import com.intuit.ginsu.IInputHandlingService;
import com.intuit.ginsu.annotations.AppHome;
import com.intuit.ginsu.annotations.AppName;
import com.intuit.ginsu.annotations.ConfigFile;
import com.intuit.ginsu.annotations.UsageRenderer;
import com.intuit.ginsu.commands.SupportedCommandCollection;
import com.intuit.ginsu.commands.SynchronousCommandDispatchService;
import com.intuit.ginsu.io.FileSystemResourceService;

/**
 * @author rpfeffer
 * @dateCreated Mar 25, 2011
 * 
 *              This is the main configuration file for the Ginsu Command Line
 *              utility. This file determines what implementation types should
 *              be used for the interfaces that our application needs to use.
 *              When we need an implementation of that interface in a class, it
 *              is injected using Google Guice. For more information on Google
 *              Guice and using configuration modules to enable dependency
 *              injection see: http://code.google.com/p/google-guice/
 * 
 */
public class GinsuCLIModule extends AbstractModule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {

		AppContext appContext = AppContext.INSTANCE;
		String appHome = appContext.getProperty(AppContext.APP_HOME_KEY);
		String configFile = "config" + File.separator + "cliConfig.properties";

		URL testConfigURL = ClassLoader.getSystemResource(configFile);
		assert testConfigURL != null : "Config file not found. Could not resolve file: "
				+ configFile;
		System.setProperty("log4j.configuration", testConfigURL.toString());

		// GINSU_LOG_DIR is the log directory for Ginsu. It is used by log4j to
		// determine
		// where to place logs
		try {
			String logDir = appContext.getProperty(AppContext.APP_HOME_KEY)
					+ File.separator + "logs";
			File appHomeFile = new File(logDir).getCanonicalFile();
			System.setProperty("GINSU_LOG_DIR", appHomeFile.toString()
					+ File.separator);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Configure the App's Directory Structure based off of the
		// Application's
		// home directory.
		bind(String.class).annotatedWith(AppHome.class).toInstance(appHome);
		bind(String.class).annotatedWith(ConfigFile.class).toInstance(
				configFile);

		// Bind the app name, that is configurable too!
		bind(String.class).annotatedWith(AppName.class).toInstance("ginsu");

		// Bind the rest of the Major Service interfaces of the CLI app to their
		// implementing classes.
		bind(IInputHandlingService.class).to(CommandLineParsingService.class);
		bind(ICommandDispatchService.class).to(
				SynchronousCommandDispatchService.class);
		bind(IApplicationResourceService.class).to(
				FileSystemResourceService.class);
		bind(OutputStream.class).toInstance(System.out);
		bind(MainArgs.class);
		bind(ICommand.class).annotatedWith(UsageRenderer.class).to(
				UsagePrinter.class);

	}

	/**
	 * Provide a PrintWriter that has the desired output stream.
	 * 
	 * @param outputStream
	 *            an {@link OutputStream} to print to.
	 * @return a {@link PrintWriter} with the {@link OutputStream} passed in.
	 */
	@Provides
	PrintWriter providePrintWriter(OutputStream outputStream) {
		return new PrintWriter(outputStream, true);
	}

	@Provides
	PrintStream providePrintStream(OutputStream outputStream) {
		return new PrintStream(outputStream);
	}

	/**
	 * 
	 * @param appName
	 * @param mainArgs
	 * @param supportedCommands
	 * @return
	 */
	@Provides
	JCommander provideJCommander(@AppName String appName, MainArgs mainArgs,
			IDefaultProvider defaultProvider) {
		JCommander jCommander = new JCommander(mainArgs);
		jCommander.setProgramName(appName);
		jCommander.setDefaultProvider(defaultProvider);
		return jCommander;
	}

	@Provides
	UsagePrinter provideUsagePrinter(PrintWriter printWriter) {
		return new UsagePrinter(printWriter);
	}

	@Provides
	CommandLineParsingService provideCommandLineParsingService(
			JCommander jCommander, MainArgs mainArgs,
			SupportedCommandCollection supportedCommands) {
		return new CommandLineParsingService(jCommander, mainArgs,
				supportedCommands);
	}
}
