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

/**
 * Logs an error if the log level matches. Will take a screenshot if the tests are running on the
 * device.
 *
 * @param message string - The message to be logged
 */
GINSU.log_error = function(message)
{
	if (GINSU.settings.logLevel >= GINSU.logLevels.LOG_ERROR)
	{
		//NOTE: this will take a screenshot as well.
		UIALogger.logError(message);
	}
};

/**
 * Logs a warning if the log level matches. Will take a screenshot if the tests are running on the
 * device.
 *
 * @param message string - The message to be logged
 */
GINSU.log_warning = function(message)
{
	if (GINSU.settings.logLevel >= GINSU.logLevels.LOG_WARN)
	{
		//NOTE: this will take a screenshot as well.
		UIALogger.logWarning(message);
	}
};

/**
 * Logs an info message if the log level matches.
 *
 * @param message string - The message to be logged
 */
GINSU.log_info = function(message)
{
	if (GINSU.settings.logLevel >= GINSU.logLevels.LOG_INFO)
	{
		UIALogger.logMessage(message);
	}
};

/**
 * Logs a debug message if the log level matches.
 *
 * @param message string - The message to be logged
 */
GINSU.log_debug = function(message)
{
	if (GINSU.settings.logLevel >= GINSU.logLevels.LOG_DEBUG)
	{
		UIALogger.logDebug("DEBUG: " + message);
	}
};

/**
 * Logs a trace message if the log level matches.
 *
 * @param message string - The message to be logged
 */
GINSU.log_trace = function(message)
{
	if(GINSU.settings.logLevel >=  GINSU.logLevels.LOG_TRACE)
	{
		UIALogger.logDebug("TRACE: " + message);
	}
};

/**
 * Logs a trace message if the log level matches.
 *
 * @param message string - The message to be logged
 */
GINSU.log_start = function(message)
{
	UIALogger.logStart(message);
};

/**
 * Log a pass message
 *
 * @param message string - The message to be logged
 */
GINSU.log_pass = function(message)
{
	UIALogger.logPass(message);
};

/**
 * Log a failure message
 *
 * @param message string - The message to be logged
 */
GINSU.log_fail = function(message)
{
	UIALogger.logFail(message);
};

/**
 * Log the state of the application under test (AUT)
 *
 * @param message string - The message to be logged
 */
GINSU.log_state = function()
{
	UIATarget.localTarget().logElementTree();
};