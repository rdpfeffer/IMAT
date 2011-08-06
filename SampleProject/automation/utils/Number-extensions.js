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
 * Extend the number object to also include a function called isBetween, which
 * determines if a number object is between two numbers, x and y.
 *
 * @param x {Number} The first boundary number. x may be larger or smaller than 
 * 	the parameter y.
 *
 * @param y {Number} The second boundary number. y may be larger or smaller than
 *	the parameter x.
 *
 * @return {boolean} true if this number is between x and y. Note this is inclusive 
 *	to both x and y. (i.e. if this === y === x then this will return true)
 */
Number.prototype.isBetween = function(x, y) {
	if (x < y) {
		return this >= x && this <= y;
	} else {
		return this >= y && this <= x;
	}
}