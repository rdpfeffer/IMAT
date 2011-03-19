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

import com.sun.mail.iap.Argument;

/**
 * @author rpfeffer
 * @dateCreated Mar 11, 2011
 * 
 *              The Command helper class assists in parsing the ginsu subcommand
 *              from the main list of arguments fed to the main arguments list.
 *              Furthermore, it assists in loading options from that subcommand
 *              into a separate array.
 */
public class CommandHelper {

	/**
	 * This is the current list of supported commands.
	 * 
	 * TODO: We are ok with this right now, but long term, Is there a better way
	 * to organize this?
	 */
	public static final String INIT_ENV_CMD = "init-env";
	public static final String GENERATE_CMD = "generate";
	public static final String RUN_TEST_CMD = "run-tests";

	/**
	 * From the list of arguments passed from main, get the first command in
	 * that list that we recognize. Right now we only support one argument at a
	 * time.
	 * 
	 * @param args
	 *            String[] The array, most typically from the main[] args array.
	 *            This is the array where we expect the command to exist. None
	 *            of the elements in this array should be null after the first
	 *            non-null element or before the last non-null element
	 * @return The first argument passed in matching one of the supported
	 *         commands.
	 * 
	 * @throws InvalidMainArgumentArray
	 *             If <code>args</code> is empty.
	 * @throws InternalCommandParsingException
	 *             If there is a null element after the first non-null element
	 *             or before the last non-null element in <code>args</code>
	 * @throws CommandNotFoundException
	 *             If we do not find one of the supported commands in the
	 *             <code>args</code> array
	 */
	public static String getCommandFromMainArgs(String[] args)
			throws InvalidMainArgumentArray, InternalCommandParsingException,
			CommandNotFoundException {
		if (args == null || args.length < 1) {
			throw new InvalidMainArgumentArray();
		}
		return args[CommandHelper.getIndexOfFirstCommandFromMainArgs(args)];
	}

	/**
	 * Given the list of main arguments passed into ginsu, get the list of
	 * options related to the ginsu subcommand that was passed in.
	 * 
	 * @param args
	 *            String[] The array, most typically from the main[] args array.
	 *            This is the array where we expect the command to exist. None
	 *            of the elements in this array should be null after the first
	 *            non-null element or before the last non-null element
	 * @return The array of string arguments passed in after the first found
	 *         argument
	 * 
	 * @throws InternalCommandParsingException
	 *             If there is a null element after the first non-null element
	 *             or before the last non-null element in <code>args</code>
	 * @throws CommandNotFoundException
	 */
	public static String[] getCommandOptionsFromMainArgs(String[] args)
			throws InternalCommandParsingException, CommandNotFoundException {
		int commandIndex = CommandHelper
				.getIndexOfFirstCommandFromMainArgs(args);
		int optionsArrayLength = args.length - commandIndex - 1;
		String cmdArgs[] = new String[optionsArrayLength];
		if (optionsArrayLength > 0) {
			System.arraycopy(args, (commandIndex + 1), cmdArgs, 0,
					optionsArrayLength);
		}
		return cmdArgs;
	}

	private static int getIndexOfFirstCommandFromMainArgs(String[] args)
			throws InternalCommandParsingException, CommandNotFoundException {
		int ind = 0;
		boolean isValidIndex;
		while ((isValidIndex = CommandHelper.isValidIndexOfMainArgsArray(ind,
				args)) && !CommandHelper.isRegisteredCommand(args[ind])) {
			ind++;
		}
		if (!isValidIndex) {
			throw new CommandNotFoundException();
		}
		return ind;
	}

	private static boolean isValidIndexOfMainArgsArray(int index, String[] array)
			throws InternalCommandParsingException {
		boolean isValid = (0 <= index && index < array.length);
		if (index < array.length && array[index] == null) {
			throw new InternalCommandParsingException();
		}
		return isValid;
	}

	private static boolean isRegisteredCommand(String arg) {
		boolean isRegisteredCommand = false;
		if (arg != null
				&& (arg.equals(INIT_ENV_CMD) || arg.equals(GENERATE_CMD) || arg
						.equals(RUN_TEST_CMD))) {
			isRegisteredCommand = true;
		}
		return isRegisteredCommand;
	}
}
