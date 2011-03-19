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

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

/**
 * @author rpfeffer
 * @dateCreated Mar 11, 2011
 * 
 *              The options factory exists to define and generate the options
 *              required by the Ginsu command line interface.
 */
public class OptionsFactory {

	/**
	 * The default constructor for this class
	 */
	public OptionsFactory() {
		// do nothing for now...
	}

	/**
	 * Generate the command options for a given command
	 * 
	 * @param command
	 *            {@link String} The string matching one of the recognized
	 *            commands
	 * @return The set of {@link Options} expected for that command
	 * @throws InvalidCommandException
	 *             when the command passed in does not match any of the
	 *             recognized commands
	 */
	public Options generateCommandOptions(String command)
			throws InvalidCommandException {
		Options options = new Options();
		if (command.equals(CommandHelper.INIT_ENV_CMD)) {
			this.getInitEnvOptions(options);
		} else if (command.equals(CommandHelper.GENERATE_CMD)) {
			this.getGenerateOptions(options);
		} else if (command.equals(CommandHelper.RUN_TEST_CMD)) {
			this.getRunTestsOptions(options);
		} else {
			throw new InvalidCommandException(command);
		}
		return options;
	}

	/**
	 * Generate the options for the core Ginsu runtime.
	 * @return The {@link Options}
	 */
	@SuppressWarnings("static-access")
	public Options generateCoreOptions() {
		Options options = new Options();
		options.addOption(new Option("debug", "print debugging information"));
		options.addOption(OptionBuilder.withArgName("[path to file]").hasArg()
				.withDescription("path to the logging directory")
				.create("logDir"));
		return options;
	}

	/**
	 * Return the options for the subcommand "init-env"
	 * @param options The {@link Options} for "init-env"
	 */
	@SuppressWarnings("static-access")
	private void getInitEnvOptions(Options options) {

		options.addOption(OptionBuilder.withArgName("[path to file]").hasArg()
				.withDescription("path to the .tracetemplate file")
				.create("traceTemplate"));
	}

	/**
	 * Return the options for the subcommand "generate"
	 * @param options The {@link Options} for "init-env"
	 */
	private void getGenerateOptions(Options options) {
		// TODO Auto-generated method stub

	}

	/**
	 * Return the options for the subcommand "generate"
	 * @param options The {@link Options} for "init-env"
	 */
	private void getRunTestsOptions(Options options) {
		// TODO Auto-generated method stub

	}
}
