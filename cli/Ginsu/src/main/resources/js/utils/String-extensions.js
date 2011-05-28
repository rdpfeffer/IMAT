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
 * Determine if this string object starts with the given string. This is an extension to the base
 * String object of the JavaScript language.
 * 
 * @param {String} str 
 * 					The string to compare this against
 * 
 * @return {boolean} True if this string object starts with str, false otherwise.
 * @memberOf String
 */
String.prototype.startsWith = function(str)
{
	return (this.match( "^" + str ) == str);
};