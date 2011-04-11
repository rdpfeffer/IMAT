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
 
/**
 * Checks that the given element is valid.
 *
 * @param elem The element value we are checking.
 * @param description (optional) The description to log if the element is not valid.
 */
function assertValid(elem, description) {
	assertTrue(elem.checkIsValid(), (description ? description : "") + " was not valid: " + elem + " type: " + typeof elem);
};

/**
 * Check that the given element is not valid.
 * 
 * @param elem The element value we are checking.
 * @param description (optional) The description to log if the element is valid.
 */
function assertInvalid(elem, description) {
	assertFalse(elem.checkIsValid(), (description ? description : "") + " was valid: " + elem + " type: " + typeof elem);
};