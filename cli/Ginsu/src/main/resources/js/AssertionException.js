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
 * 	Constructs an IMAT.AssertionException object which inherits from the 
 *  JavaScript ERROR object. 
 * @class The {@link IMAT.AssertionException} object is thrown any time the a
 *  test failure is thrown due to an assertion API. Throwing this object will 
 *  cause the current phase in the test execution lifecycle to be marked as a 
 *  failure instead of an issue.
 * @extends <a href=https://developer.mozilla.org/en/JavaScript/Reference/Global_Objects/Error>Error</a>
 * @memberOf IMAT 
 */
IMAT.AssertionException = function(message)
{
	this.prototype = Error.prototype;
	this.name = "AssertionException";
	this.message = (message) ? message : "The assertion was not valid.";
};