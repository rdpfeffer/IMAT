/*******************************************************************************
* Copyright (c) 2009 Intuit, Inc.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.opensource.org/licenses/eclipse-1.0.php
* 
* Contributors:
*     Intuit, Inc - initial API and implementation
*******************************************************************************/
//declare the platform
var GINSU_TARGET_PLATFORM = "ios";

//bootstrap ginsu
#import "../bootstrap.js"

//declare any settings
GINSU.settings.logLevel = GINSU.logLevels.LOG_DEBUG;

//load the tests
#import "./MockTests.js"
#import "./MetaTests.js"
#import "./SuiteHandler.js"


//run the tests
GINSU.suiteRunner.previewAllRunnableTests();
GINSU.suiteRunner.runTests(new GINSU.SuiteHandler());