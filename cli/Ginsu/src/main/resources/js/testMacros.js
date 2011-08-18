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
 * Checks for Strict equality. Depending on if you are using objects or 
 * primitives, this will work differently. JS is pass by reference for objects, 
 * so if you are testing that two objects are equal, this will only work if they 
 * are the same reference. For literals, this is simply checking that they 
 * evaluate to the same value.
 *
 * @param expected The expected value we are testing
 * @param received The value we expected to see
 * @param message (optional) The message to throw if expected did not match 
 * received
 *
 * @throws An exception string with the given parameter message if expected was 
 * not strictly equal to received.
 */
 /*
 * TODO: Create an assertEqualObjects function that calls isEqual on the 
 * expected object. Consider underscore.js
 */
function assertEquals(received, expected, message) {
  if (received !== expected) {
    if (!message) {
    	message = "Expected " + expected + " (type:" + typeof expected + ")" + 
    		" but received " + received + " (type:" + typeof received + 
    		"). Check for value and type.";
    }
    throw new IMAT.AssertionException(message);
  }
}

/**
 * Checks for Strict inequality. Depending on if you are using objects or 
 * primitives, this will work differently. JS is pass by reference for objects, 
 * so if you are testing that two objects are not equal, this will throw an 
 * exception if they are the same reference. For literals, this is strictly 
 * checking to make sure they are different values.
 *
 * @param expected The expected value we are testing
 * @param received The value we expected to see
 * @param message (optional) The message to throw if expected matched received
 *
 * @throws An exception string with the given parameter message if expected  
 * was strictly equal to received.
 */
function assertNotEquals(received, expected, message) {
  if (received === expected) {
    if (!message) {
    	message = "Expected " + expected + " (type:" + typeof expected + ")" + 
    		" but received " + received + " (type:" + typeof received + 
    		"). Check for value and type.";
    }
    throw new IMAT.AssertionException(message);
  }
}



/**
 * Checks that the expression evaluates to true.
 *
 * @param expression The expression value we are checking
 * @param message (optional) The message to throw if expression does not 
 * evaluate to true
 *
 * @throws an error message if expression does not evaluate to true.
 */
function assertTrue(expression, message) {
  if (!expression) {
    if (!message){
    	message = "Assertion failed when expression was: " + expression + 
    		" type:" + typeof expression;
    } 
    throw new IMAT.AssertionException(message);
  }
}

/**
 * Checks that the expression evaluates to false.
 *
 * @param expression The expression value we are checking
 * @param message (optional) The message to throw if expression does not 
 * evaluate to false
 *
 * @throws an error message if expression does not evaluate to false.
 */
function assertFalse(expression, message) {
  assertTrue(!expression, message);
}

/**
 * Checks that thingie does not evaluate to null or any of its equivalents 
 * (i.e. undefined). Note that this also handles cases where UIAElementNil is 
 * returned. However, it does not consider the value false to be an equivalent 
 * of null. For that use the assertFalse() function.
 *
 * @param thingie The expected value we are testing
 * @param message (optional) The message to throw if thingie evaluated to a null 
 * equivalents
 *
 * @throws an error message if expected did not match received
 */
function assertNotNull(thingie, message) {
  if (thingie === null || typeof thingie === "undefined" || thingie instanceof UIAElementNil) {
    if (!message) {
    	message = "Expected a non null object. Object was " + thingie;
    }
    throw new IMAT.AssertionException(message);
  }
}

/**
 * Checks that two values from a sort are in an ascending relationship.
 *
 * @param lowValue The value that should be the lower of the two values.
 * @param highValue The value that should be the higher of the two values.
 *
 * @throws an error if the values are not in ascending order.
 */
function assertAscending(lowValue, highValue, msg)
{
	if (lowValue >= highValue)
	{
		var message = msg + " Sorted values are not in ascending order. highValue " + 
			highValue + " is not higher than lowValue " + lowValue; 
		throw new IMAT.AssertionException(message);
	}
}

/**
 * Checks that two values from a sort are in an descending relationship.
 *
 * @param highValue The value that should be the higher of the two values.
 * @param lowValue The value that should be the lower of the two values.
 *
 * @throws an error if the values are not in descending order.
 */
function assertDescending(highValue, lowValue, msg)
{
	if (lowValue >= highValue)
	{
		var message = msg + " Sorted values are not in descending order. lowValue " + 
			lowValue + " is not lower than highValue " + highValue;
		throw new IMAT.AssertionException(message);
	}
}

/**
 * For keeping track of manual tests that have not been automated yet, this will
 * log a message stating that a given test has passed manually. This is meant to
 * be called directly within a test.
 *
 *  @param {string} message
 * 					An optional message to log with the manual pass message. 
 */
function manualPass()
{
	var message = "MANUAL: This test is marked as Passing during manual execution.";
	if (arguments.length > 0) {
		message = message + " " + arguments[0];
	} 
	IMAT.log_info(message);
}

/**
 * For keeping track of manual tests that have not been automated yet, this will
 * log a message stating that a given test has failed manually. This is meant to
 * be called directly within a test.
 *
 *  @param {string} message
 * 					An optional message to log with the manual fail message. 
 */
function manualFail()
{
	var message = "MANUAL: This test is marked as failing during manual execution.";
	if (arguments.length > 0) {
		message = message + " " + arguments[0]; 
	} 
	throw new IMAT.AssertionException(message);
}

/**
 * For keeping track of manual tests that have not been automated yet, and still
 * need to be run manually. This is meant to be called directly within a test.
 * Logs a warning message since this test is neither a passing for a failing 
 * test.
 */
function toBeExecutedManually()
{
	IMAT.log_warning("MANUAL: To Be Executed.");
}