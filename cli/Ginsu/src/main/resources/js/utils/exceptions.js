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

#import "String-extensions.js"

/**
 * This is a helper function to handle the fact that JavaScript will sometimes throw strings as 
 * exceptions while at other times throw full fledged exception objects. It determines if a message
 * is part of a given exception.
 *
 * @param e mixed - The exception we are handling.
 * @param message string - The message
 * 
 * @return boolean - true if message is part of the thrown exception.
 */
GINSU.exceptionStartsWithMessage = function(e, message)
{
	var isPartofException = false;
	if(typeof e == "string")
	{
		isPartofException = e.startsWith(message);
	}
	else if (e.message)
	{
		isPartofException = e.message.startsWith(message);
	}
	return isPartofException;
};