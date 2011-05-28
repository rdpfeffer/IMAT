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

/**
 * 	@namespace Holds log level constants for the framework. Set 
 * {@link IMAT.settings.logLevel} to one of the Constants to throttle the 
 * logging threashold.
 */
IMAT.logLevels = {
	/**
	 * When {@link IMAT.settings.logLevel} is set to LOG_OFF, nothing is logged.
	 * @constant
	 * @type number 
	 */
	LOG_OFF: 0,
	
	/**
	 * When {@link IMAT.settings.logLevel} is set to LOG_ERROR, only errors are
	 * logged. 
	 * @constant 
	 * @type number 
	 */
	LOG_ERROR: 1,
	
	/**
	 * When {@link IMAT.settings.logLevel} is set to LOG_WARN, only errors and
	 * warning messages are logged. 
	 * @constant
	 * @type number 
	 */
	LOG_WARN: 2,
	
	/**
	 * When {@link IMAT.settings.logLevel} is set to LOG_INFO, only errors, 
	 * warnings, and info messages are logged. This is good for running your 
	 * tests in an unattended state, because it provides a good level of 
	 * granularoty in order to adequately triage your tests when things go 
	 * wrong.
	 * @constant
	 * @type number 
	 */
	LOG_INFO: 3,
	
	/**
	 * When {@link IMAT.settings.logLevel} is set to LOG_DEBUG, only errors, 
	 * warnings, info, and debug messages are logged. This is good for 
	 * developing your tests.
	 * @constant
	 * @type number 
	 */
	LOG_DEBUG: 4,
	
	/**
	 * When {@link IMAT.settings.logLevel} is set to LOG_TRACE, log everything.
	 * It is recommended you ony use this when you are running into problems
	 * that you cannot track down.  
	 * @constant
	 * @type number 
	 */
	LOG_TRACE: 5
};

/**
 * 	@namespace Holds state constants for the framework to be used when validating
 *  common states of a given view class. 
 * 	@see {@link IMAT.BaseView.validate}
 */
IMAT.states = {
	/**
	 * String constant signifying the initial state of a view class. By 
	 * convention this state should be used in the state validator called within
	 * the constructor (i.e. initialize()). 
	 * @constant
	 * @type String 
	 */
	INITIAL: "initial"
};

/**
 * 	@namespace Used to determine which platform to run against. This is purely
 * 	forward looking in case we support more than one platform in the future. 
 */
IMAT.platforms = {
	/**
	 * Support the iOS platform 
	 * @constant
	 * @type number. 
	 */
	IOS: 1
};