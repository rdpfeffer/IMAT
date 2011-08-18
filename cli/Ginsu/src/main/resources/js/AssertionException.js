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

IMAT.AssertionException = function(message)
{
	this.prototype = Error.prototype;
	this.name = "AssertionException";
	this.message = (message) ? message : "The assertion was not valid.";
};