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

//run the tests
IMAT.settings.SKIP_INIT_SUITE = true;
IMAT.settings.SKIP_CLEAN_UP_SUITE = true;
IMAT.settings.SKIP_SET_UP_TEST_SET = true;
IMAT.settings.SKIP_TEAR_DOWN_TEST_SET = true;
IMAT.settings.SKIP_SET_UP_TEST_CASE = true;
IMAT.settings.SKIP_TEAR_DOWN_TEST_CASE = true;
IMAT.suiteRunner.addFilters(["testManually"]);
IMAT.suiteRunner.previewAllRunnableTests();
IMAT.suiteRunner.runTests(new SAMPLE.SuiteHandler());