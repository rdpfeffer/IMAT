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

/**
 * @author rpfeffer
 * @dateCreated Mar 11, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public class CommandHelper {

	public static final String INIT_ENV_CMD = "init-env";
	public static final String GENERATE_CMD = "generate";
	public static final String RUN_TEST_CMD = "run-tests";
	
	/**
	 * TODO: Fill this in
	 * @param args
	 * @return
	 * @throws InvalidMainArgumentArray
	 * @throws InternalCommandParsingException
	 * @throws CommandNotFoundException 
	 */
	public static String getCommandFromMainArgs(String[] args) throws InvalidMainArgumentArray, InternalCommandParsingException, CommandNotFoundException
	{
		if(args == null || args.length < 1)
		{
			throw new InvalidMainArgumentArray();
		}
		return args[CommandHelper.getIndexOfFirstCommandFromMainArgs(args)];
	}
	
	/**
	 * TODO: Fill this in
	 * @param args
	 * @return
	 * @throws InternalCommandParsingException
	 * @throws CommandNotFoundException 
	 */
	public static String[] getCommandOptionsFromMainArgs(String[] args) throws InternalCommandParsingException, CommandNotFoundException 
    {
    	int commandIndex = CommandHelper.getIndexOfFirstCommandFromMainArgs(args);
    	int optionsArrayLength = args.length - commandIndex - 1;
		String cmdArgs[] = new String[optionsArrayLength];
		if(optionsArrayLength > 0)
		{
			System.arraycopy(args, (commandIndex + 1), cmdArgs, 0, optionsArrayLength);
		}
    	return cmdArgs;
    }
	
	private static int getIndexOfFirstCommandFromMainArgs(String[] args) throws InternalCommandParsingException, CommandNotFoundException
	{
		int ind = 0;
		boolean isValidIndex;
		while ((isValidIndex = CommandHelper.isValidIndexOfMainArgsArray(ind, args))  
				&& !CommandHelper.isRegisteredCommand(args[ind]))
		{
			ind++;
		}
		if (!isValidIndex)
		{
			throw new CommandNotFoundException(); 
		}
		return ind;
	}
	
	private static boolean isValidIndexOfMainArgsArray(int index, String[] array) throws InternalCommandParsingException
	{
		boolean isValid = (0 <= index && index < array.length);
		if (index < array.length && array[index] == null)
		{
			throw new InternalCommandParsingException();
		}
		return isValid;
	}
	
	private static boolean isRegisteredCommand(String arg)
	{
		boolean isRegisteredCommand = false;
		if (arg != null && (arg.equals(INIT_ENV_CMD) || arg.equals(GENERATE_CMD) ||
				arg.equals(RUN_TEST_CMD)))
		{
			isRegisteredCommand = true;
		}
		return isRegisteredCommand;
	}
}
