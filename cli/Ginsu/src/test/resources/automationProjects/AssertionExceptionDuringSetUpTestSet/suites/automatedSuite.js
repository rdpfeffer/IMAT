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
 * The following filter will only run tests that do not start with the name "testManually" or 
 * "testFail". By following the convention of prefixing all non-automated tests as such, you can 
 * create suites that only run automated tests.
 *
 * Note, the "|Fail" portion below is for demo purposes. You would never need this filter 
 * modification in a real project.
 */
IMAT.suiteRunner.addFilters(["test(?!Manually|Fail)"]);


// uncomment the following two lines and comment out the call to "runTests()" to get a preview of
// what tests we are filtering for.
//IMAT.settings.logLevel = IMAT.logLevels.LOG_DEBUG;
//IMAT.suiteRunner.previewAllRunnableTests();

//run the tests
IMAT.suiteRunner.runTests(new SAMPLE.SuiteHandler());
