/**
 * This file is really more for running reports on our manual tests.
 *
 * @creator Ryan Pfeffer. 06/08/2010
 *
 * Copyright 2011 Intuit Inc. All rights reserved. 
 * Unauthorized reproduction is a violation of applicable law. 
 * This material contains certain confidential and proprietary information and trade secrets of Intuit Inc.
 */
//declare the platform this suite will be testing
var IMAT_TARGET_PLATFORM = "ios";

#import "../project.js"

/**
 * This suite will run all of the manual tests. Since manual tests really don't interacti with the 
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

//"run" the tests. NOTE: since these are all manual, it will just log out the last known status.
IMAT.suiteRunner.runTests(new SAMPLE.SuiteHandler());