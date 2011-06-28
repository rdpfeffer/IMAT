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

#import "env/env.js"

//declare any settings
IMAT.settings.logLevel = IMAT.logLevels.LOG_DEBUG;

var AUTO = {};

//load the tests and their respective views
#import "tests/tests_inc.js"
#import "views/views_inc.js"
#import "suites/SuiteHandler.js"

