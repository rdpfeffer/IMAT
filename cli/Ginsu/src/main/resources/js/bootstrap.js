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

//Declare the Global object
#import "./IMAT.js"

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

//import platform specific code.
#import "./platforms/ios/platform.js";

// Declare the Runner Classes
#import "./TestRunner.js"
#import "./SuiteRunner.js"