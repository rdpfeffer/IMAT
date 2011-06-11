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