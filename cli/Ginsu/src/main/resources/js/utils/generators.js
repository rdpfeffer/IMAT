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
 * Generate a random number between minVal and maxVal.
 * @param {number} minVal 
 * 					The minimum allowed value in the range of randomly generated 
 * 					numbers.
 * @param {number} maxVal 
 * 					The maximum allowed value in the range of randomly generated 
 * 					numbers.
 * @param {boolean} floatVal 
 * 					true if the randomly generated number should be a fload 
 * 					value. If true, will return an integer.
 * 
 * @return {number} a random number between minVal and maxVal.
 */
IMAT.randomXToY = function(minVal, maxVal, floatVal)
{
  var randVal = minVal + (Math.random() * (maxVal - minVal));
  if(floatVal)
  {
  	randVal = Math.round(randVal);
  }
  else
  {
  	randVal = randVal.toFixed(floatVal);
  }
  return randVal;
};

/**
 * Get the current time in miliseconds.
 * @return {number} The number of miliseconds since Jan 1, 1970 (Unix Epoch) 
 */
IMAT.getTime = function()
{
	var d = new Date();
	return d.getTime();
};

/**
 * Generate a psuedo random phone number.
 * @return {string} A random phone number of the form NNN-NNN-NNNN 
 */
IMAT.generatePhoneNumber = function()
{
	return IMAT.randomXToY(100,999) + "-" + IMAT.randomXToY(100,999) + "-" + IMAT.randomXToY(1000,9999) ;
};