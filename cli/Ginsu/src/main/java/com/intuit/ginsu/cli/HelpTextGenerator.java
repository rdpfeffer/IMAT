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
package com.intuit.ginsu.cli;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * @author rpfeffer
 * @dateCreated Mar 12, 2011
 * 
 *              This class is used to Generate and Print help text for all of
 *              the different ways in which ginsu can be run.
 * 
 */
public class HelpTextGenerator {

	private HelpFormatter helpFormatter;
	private PrintWriter printWriter;
	private static final int HELP_TEXT_WIDTH = 80;
	private static final String USAGE_SYNTAX = "ginsu [OPTION]... [SUBCOMMAND] [OPTION1 [OPTION2] [OPTION3] ...]]";

	/**
	 * Default Constructor
	 * 
	 * @param printWriter
	 *            {@link PrintWriter} object to print help text to.
	 */
	public HelpTextGenerator(PrintWriter printWriter) {
		this.printWriter = printWriter;
		this.helpFormatter = new HelpFormatter();
	}

	/**
	 * Print the correct usage after an exception is thrown. Prepends the
	 * exceptions message before printing usage
	 * 
	 * @param e
	 *            The {@link Exception} that was thrown
	 */
	public void printUsage(Exception e) {
		this.printWriter.println(e.getMessage());
		this.printUsage();
	}

	/**
	 * Print the correct usage after an exception is thrown.
	 */
	public void printUsage() {
		OptionsFactory optionsFactory = new OptionsFactory();
		this.usageHelper(this.printWriter, optionsFactory.generateCoreOptions());
	}

	/**
	 * Get the usage in string format
	 * @return @String 
	 */
	public String getUsage() {
		OptionsFactory optionsFactory = new OptionsFactory();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintWriter printWriter = new PrintWriter(outputStream, true);
		this.usageHelper(printWriter, optionsFactory.generateCoreOptions());
		return outputStream.toString();
	}

	/**
	 * TODO: Implement this
	 */
	public void printUsageForCommand() {

	}

	/**
	 * TODO: Implement this
	 */
	public void printOptionsForCommand(String cmd) {

	}

	/**
	 * TODO: Implement this
	 */
	public void printOptionsForAllCommands() {

	}

	/**
	 * TODO: Implement this
	 */
	public void printHelp() {

	}

	private void usageHelper(PrintWriter printWriter, Options options) {
		this.helpFormatter.printUsage(printWriter, HELP_TEXT_WIDTH,
				USAGE_SYNTAX);
	}
}
