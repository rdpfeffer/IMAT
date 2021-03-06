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

//bootstrap IMAT
#import "../bootstrap.js"

//load the tests
#import "./MockView.js"
#import "./MetaBaseViewTests.js"

#import "./MockTests.js"
#import "./MetaTests.js"
#import "./SuiteHandler.js"

/**
 * declare any settings
 * @ignore
 */
IMAT.settings.logLevel = IMAT.logLevels.LOG_DEBUG;

//run the tests
//IMAT.suiteRunner.previewAllRunnableTests();
IMAT.suiteRunner.runTests(new IMAT.SuiteHandler());