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
 * The following filter will only run automated tests in the Events test set. Also notice that the
 * filter will run the tests in this demo that were written to fail on purpose. This will show off 
 * how IMAT will recover to a known base state using the doCleanup() implementation in 
 * IMAT.BaseFunctionalTestSet.
 */
IMAT.suiteRunner.addFilters(["^InfoTestSet\\.test(?!Manually)"]);

// uncomment the following two lines and comment out the call to "runTests()" to get a preview of
// what tests we are filtering for.
//IMAT.settings.logLevel = IMAT.logLevels.LOG_DEBUG;
//IMAT.suiteRunner.previewAllRunnableTests();

//run the tests
IMAT.suiteRunner.runTests(new SAMPLE.SuiteHandler());
jones.foo();
