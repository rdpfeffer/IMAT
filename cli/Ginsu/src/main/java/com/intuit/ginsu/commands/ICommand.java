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
package com.intuit.ginsu.commands;

/**
 * @author rpfeffer
 * @dateCreated Mar 25, 2011
 * 
 *              This defines the main interface for Commands in Ginsu. All
 *              commands should implement this interface.
 * 
 */
public interface ICommand {

	/**
	 * Execute the command. The implementer is required to return an exit status
	 * of 0 when the execution ran normally, and a positive, non-zero integer
	 * when the command exited abnormally. By contract of implementing this
	 * interface, calling getExitStatus() or getErrorMessage() a before run()
	 * has finished executing should result in an exception, therefore run
	 * should also maintain some sort of internal representation of the run
	 * state of the command as well, setting the state to return 0 when it has
	 * completed.
	 * 
	 * @return int 0, when the command has completed normally and and a positive
	 *         non-zero integer when it has completed abnormally.
	 */
	public int run();

	/**
	 * Clean up after the command has finished.
	 */
	public void cleanUp();

	/**
	 * @return true if the {@link ICommand} object is runnable, false otherwise
	 */
	public boolean isRunnable();

	/**
	 * This function is to be called on the {@link ICommand} object after it has
	 * completed. The {@link ICommand} object should return 0 when it has
	 * completed normally and a positive non-zero integer otherwise.
	 * 
	 * @return int 0, when the command has completed normally and and a positive
	 *         non-zero integer when it has completed abnormally.
	 * @throws IncompleteCommandException
	 *             when called before the command has completed.
	 */
	public int getExitStatus() throws IncompleteCommandException;

	/**
	 * In the case that an {@link ICommand} object completes abnormally, it is
	 * possible that an error message will accompany it. Call this method to get
	 * an error message from an abnormally completing {@link ICommand} object.
	 * 
	 * @return a {@link String} representing the error message from running this
	 *         command containing information as to what when wrong
	 * @throws IncompleteCommandException
	 *             when called before the command has completed.
	 */
	public String getErrorMessage() throws IncompleteCommandException;

	/**
	 * Although the run() method returns the exit status of the command, it may
	 * be possible that the runner of the command may not be the same entity
	 * that executes it (i.e. if the command is executed on another thread).
	 * This method provides an interface for monitoring that state in those
	 * instances.
	 * 
	 * @return true after the command has completed and false up until that point
	 */
	public boolean isCommandComplete();
}
