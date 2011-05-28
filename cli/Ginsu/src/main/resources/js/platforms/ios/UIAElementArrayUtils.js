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
 * Returns the index within the array of the first element with its name
 * matching the supplied elementName.
 *
 * @param {UIAElementArray} elementArray 
 * 						The array containing the elements to search.
 * @param {string} name 
 * 						The name of the element to find. NOTE: This is case 
 * 						sensitive.
 *
 * @return {number} The whole number representing the index of the element 
 * within the array. If not found, this element will be undefined.
 */
IMAT.indexOfFirstWithName = function(elementArray, name)
{
	var i = 0;
	var index = undefined;
	for (i = 0; i < elementArray.length; i++)
	{
		var currentElement = elementArray[i];
		var elementIsNull = (currentElement === null || typeof currentElement === "undefined" || currentElement instanceof UIAElementNil);
		if (!elementIsNull && currentElement.name() == name)
		{
			index = i;
			break;
		}
	}
	return index;
};