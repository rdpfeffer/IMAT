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
 *  @namespace The IMAT namespace holds all of the formal classes of the Intuit
 *  Mobile Automation Toolkit(IMAT). If an object is part of the IMAT namespace,
 *  then it should provide some sort of common functionality applicable to all 
 *  types of consumers of this framework.
 */
var IMAT = {};

//import the default settings
#import "./settings.js"

// Declare the base inheritance framework
#import "./inheritance-2.7.js"

//Declare the constants
#import "./constants.js"

// Declare our test macros
#import "./AssertionException.js"
#import "./testMacros.js"

//declare our utilities
#import "./utils/String-extensions.js"
#import "./utils/exceptions.js"
#import "./utils/generators.js"
#import "./BaseTestSet.js"

IMAT.platforms = {
	ios:"ios"
};

//First ensure that the target platform var is declared.
if(typeof IMAT_TARGET_PLATFORM === "undefined") {
	var IMAT_TARGET_PLATFORM = IMAT.platforms.ios;
}

//Then ensure that even if it was declared, it should be set to something.
if(!IMAT_TARGET_PLATFORM) {
	IMAT_TARGET_PLATFORM = IMAT.platforms.ios;
}

if(IMAT_TARGET_PLATFORM === IMAT.platforms.ios)
{
#import "./platforms/ios/platform.js";
}

// Declare the Runner Classes
#import "./TestRunner.js"
#import "./SuiteRunner.js"

// Declare a global suite runner object that we can use to run our tests.
IMAT.suiteRunner = new IMAT.SuiteRunner();
IMAT.suiteRunner.setTestRunner(new IMAT.TestRunner());