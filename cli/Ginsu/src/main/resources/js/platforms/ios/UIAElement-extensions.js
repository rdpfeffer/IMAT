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
 * Wait for a given element to be invisible. Uses the local target's timeout value checks for 
 * isVisible until the timeout time is reached. This is an extension to the UIAElement object of the 
 * UI Automation framework.
 * 
 * @param softTimeout boolean, Optional - true if we should log the timeout at the debug level rather
 * 	than log a warning.
 */
UIAElement.prototype.waitForInvisible = function(softTimeout)
{
	//Since it is possible for the timeout to be verrrry long, 30 seconds or more, we need to store
	//its value and replace the top timeout with a 1 second timeout value.
	var timeout = UIATarget.localTarget().timeout();
	UIATarget.localTarget().pushTimeout(1);
	
	var endDate, startDate;
	startDate = endDate = new Date();
	var millisecondsPerSecond = 1000;
	var iAmVisible = true;
	GINSU.log_trace("waitForInvisible: " + this);
	
	//Internal convenience function to get the time since we started waiting for the element
	//to be invisible.
	var timeSinceWaitStart = function()
	{
		return (endDate.getTime() - startDate.getTime())/millisecondsPerSecond;
	};
	
	while (iAmVisible && timeSinceWaitStart() < timeout)
	{
		iAmVisible = this && this.checkIsValid() && this.isVisible();
		endDate = new Date();
		if(timeSinceWaitStart() >= timeout)
		{
			var message = "Timeout Exceeded: waitForInvisible | timeout: " + timeout + " sec | " + this;
			if(softTimeout)
			{
				GINSU.log_debug(message);
			}
			else
			{
				GINSU.log_warning(message);
			}
			
		}
	}
	//Reset the timeout stack back to its original state once we are done waiting.
	UIATarget.localTarget().popTimeout();
};
