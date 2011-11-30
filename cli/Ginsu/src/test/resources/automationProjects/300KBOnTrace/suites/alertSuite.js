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
 * The following filter will only run tests that have the word "Alert" in the test function part of
 * of the signature.
 */
IMAT.suiteRunner.addFilters([".*\\.test.*Alert.*()$"]);

// uncomment the following two lines and comment out the call to "runTests()" to get a preview of
// what tests we are filtering for.
//IMAT.settings.logLevel = IMAT.logLevels.LOG_DEBUG;
//IMAT.suiteRunner.previewAllRunnableTests();

//run the tests
IMAT.suiteRunner.runTests(new SAMPLE.SuiteHandler());
