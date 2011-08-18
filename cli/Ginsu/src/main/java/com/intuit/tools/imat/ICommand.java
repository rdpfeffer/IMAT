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
package com.intuit.tools.imat;

import com.intuit.tools.imat.cli.ExitStatus;

/**
 * @author rpfeffer
 * @dateCreated Mar 25, 2011
 * 
 *              This defines the main interface for Commands in IMAT. All
 *              commands should implement this interface.
 * 
 */
public interface ICommand {

	/**
	 * Execute the command. The implementer is required to return an
	 * {@link ExitStatus#SUCCESS} when the execution ran normally, and an
	 * appropriate {@link ExitStatus} when the command exited abnormally. By
	 * contract of implementing this interface, calling getExitStatus() or
	 * getErrorMessage() a before run() has finished executing should result in
	 * an exception, therefore run should also maintain some sort of internal
	 * representation of the run state of the command as well, setting the state
	 * to return 0 when it has completed.
	 * 
	 * @return ExitStatus SUCCESS, when the command has completed normally.
	 *         Abnormal status codes can be found in the {@link ExitStatus}
	 *         enumeration
	 * 
	 * @throws MisconfigurationException
	 *             When the command cannot run due to misconfiguration of either
	 *             the application or the target project.
	 */
	public ExitStatus run() throws MisconfigurationException;

	/**
	 * Allows the Application to conditionally run different logic if
	 * implemented {@link ICommand} takes any action.
	 * 
	 * An example of this would be objects that give help instructions to the
	 * user, but are not actually going to take any actions. The suposed "help"
	 * command could return false from the method and still be enabled to run
	 * even though the application may not allow runnable commands in certain
	 * configurations.
	 * 
	 * Inversely, a command that returns true from this method could enable
	 * other logic in the app to handle things like first time initialization,
	 * cleanup, etc.
	 * 
	 * @return true if the {@link ICommand} object is runnable, false otherwise.
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
	 * NOTE: This method is really not used in version 1.0 of IMAT, but is
	 * reserved for future functionality depending on how the project moves
	 * forward.
	 * 
	 * @return a {@link String} representing the error message from running this
	 *         command containing information as to what when wrong
	 * @throws IncompleteCommandException
	 *             when called before the command has completed.
	 */
	public String getErrorMessage() throws IncompleteCommandException;

	/**
	 * Returns the name of the command.
	 * 
	 * @return {@link String} the name of the command.
	 */
	public String getName();

	/**
	 * Although the run() method returns the exit status of the command, it may
	 * be possible that the runner of the command may not be the same entity
	 * that executes it (i.e. if the command is executed on another thread).
	 * This method provides an interface for monitoring that state in those
	 * instances.
	 * 
	 * @return true after the command has completed and false up until that
	 *         point
	 */
	public boolean isCommandComplete();

	/**
	 * This hook is called before a command is run. It will give the command a
	 * chance to return usage information back to the user. For example when
	 * this command returns true, the CommandDispatchServiceImpl class will not
	 * run the command, but rather print its usage out to the console. In other
	 * implementations of the ICommandDispatchService it may cause the help to
	 * be displayed in other ways.
	 * 
	 * @return true when the Command usage information should be rendered.
	 */
	public boolean shouldRenderCommandUsage();

	/**
	 * Determines whether or not this command expects project configuration to
	 * be loaded. NOTE: if this method returns false it does not mean the
	 * Application will not load project properties, only that does not rely on
	 * them to complete. If set to true, it may cause the application to behave
	 * differently if the project configuration cannot be found.
	 * 
	 * @return true if the command expects a project configuration to be loaded,
	 *         false otherwise.
	 */
	public boolean expectsProject();
}
