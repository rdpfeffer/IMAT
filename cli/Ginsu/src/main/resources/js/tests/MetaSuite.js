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
 * declare the platform
 * @ignore
 */
var IMAT_TARGET_PLATFORM = "ios";

//bootstrap IMAT
#import "../bootstrap.js"


/**
 * declare any settings
 * @ignore
 */
IMAT.settings.logLevel = IMAT.logLevels.LOG_DEBUG;

//load the tests
#import "./MockTests.js"
#import "./MetaTests.js"
#import "./SuiteHandler.js"


//run the tests
IMAT.suiteRunner.previewAllRunnableTests();
IMAT.suiteRunner.runTests(new IMAT.SuiteHandler());