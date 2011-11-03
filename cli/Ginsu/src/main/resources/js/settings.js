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
 *  @namespace The IMAT.settings namespace should hold all things related to 
 *  settings used while running tests. For example,Your automation can reference 
 *  this namespace to get or set the logging levels.
 */
IMAT.settings = {};

/**
 * Sets the default log level for all of IMAT.
 * @type String
 */
IMAT.settings.logLevel = IMAT.logLevels.LOG_INFO;

/**
 * Flag used to skip the initialization of the suite durring the Test execution 
 * lifecycle.
 * @type Boolean
 */
IMAT.settings.SKIP_INIT_SUITE = false;

/**
 * Flag used to skip the cleanup of the suite for the Test execution lifecycle.
 * @type Boolean
 */
IMAT.settings.SKIP_CLEAN_UP_SUITE = false;

/**
 * Flag used to skip the testSet setup portion of the Test execution lifecycle.
 * @type Boolean
 */
IMAT.settings.SKIP_SET_UP_TEST_SET = false;

/**
 * Flag used to skip the testSet tear down portion of the Test execution 
 * lifecycle.
 * @type Boolean
 */
IMAT.settings.SKIP_TEAR_DOWN_TEST_SET = false;

/**
 * Flag used to skip the test setup portion of the Test execution lifecycle.
 * @type Boolean
 */
IMAT.settings.SKIP_SET_UP_TEST_CASE = false;

/**
 * Flag used to skip the test tear down portion of the Test execution 
 * lifecycle.
 * @type Boolean
 */
IMAT.settings.SKIP_TEAR_DOWN_TEST_CASE = false;

/**
 * Flag used to skip the test cleanup portion of the Test execution 
 * lifecycle.
 * @type Boolean
 */
IMAT.settings.SKIP_DO_CLEANUP = false;

/**
 * Flag used to log out the state of the Application whenever an exception is
 * thrown during the test execution lifecycle. 
 * @type Boolean 
 */
IMAT.settings.LOG_STATE_ON_ERROR = true;