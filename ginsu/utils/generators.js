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
GINSU.randomXToY = function(minVal, maxVal, floatVal)
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

GINSU.getTime = function()
{
	var d = new Date();
	return d.getTime();
};


GINSU.generatePhoneNumber = function()
{
	return GINSU.randomXToY(100,999) + "-" + GINSU.randomXToY(100,999) + "-" + GINSU.randomXToY(1000,9999) ;
}