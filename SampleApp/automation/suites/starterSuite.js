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

//declare the platform this suite will be testing
var IMAT_TARGET_PLATFORM = "ios";

#import "../project.js"

//run the tests
IMAT.suiteRunner.previewAllRunnableTests();
IMAT.suiteRunner.runTests(new SAMPLE.SuiteHandler());
