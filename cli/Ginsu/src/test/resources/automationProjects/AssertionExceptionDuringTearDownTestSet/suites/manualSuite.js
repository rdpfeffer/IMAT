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
#import "../project.js"

/**
 * This suite will run all of the manual tests. Since manual tests really don't interact with the 
 * it is more expedient to turn off parts of the test lifecycle so that you don't have to wait for
 * setup and teardown functions to run in order to get the results.
 */
IMAT.suiteRunner.addFilters(["testManually"]);
IMAT.suiteRunner.previewAllRunnableTests();

//This is how you turn off parts of the testing lifecycle. 
//Note: it is not recommended you do this for anything other than manual tests.
IMAT.settings.SKIP_INIT_SUITE = true;
IMAT.settings.SKIP_CLEAN_UP_SUITE = true;
IMAT.settings.SKIP_SET_UP_TEST_SET = true;
IMAT.settings.SKIP_TEAR_DOWN_TEST_SET = true;
IMAT.settings.SKIP_SET_UP_TEST_CASE = true;
IMAT.settings.SKIP_TEAR_DOWN_TEST_CASE = true;
IMAT.settings.SKIP_DO_CLEANUP = true;

//"run" the tests. NOTE: since these are all manual, it will just log out the last known status.
IMAT.suiteRunner.runTests(new SAMPLE.SuiteHandler());