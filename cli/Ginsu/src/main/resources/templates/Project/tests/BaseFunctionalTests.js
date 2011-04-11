/**
 * BaseFunctionalTests.js
 *
 * @creator Ryan Pfeffer. 09/28/2010
 * @version $Author: wshu $ $DateTime: 2011/02/02 11:03:10 $ $Revision: #15 $
 *
 * Copyright 2010 Intuit Inc. All rights reserved. 
 * Unauthorized reproduction is a violation of applicable law. 
 * This material contains certain confidential and proprietary information and trade secrets of Intuit Inc.
 */

//The inherited test


/**
 * This class handles things common to all Functional Tests
 */
QBM.BaseFunctionalTests = Class.extend(QBM.BaseTest, {
	
	alertManager : {
	
		handleDefaultAlertWithTitle : function(title) {
			UIATarget.onAlert = function onAlert(alert) {
				
				QBM.log_debug("Handling alert named: '" + title + "'");
				
				// if the name field exists then check that
				var alertTitle = null;
				if(alert.name()) {
					alertTitle = alert.name();
				// if not then look for a text field with the title we are looking for
				} else {
					alertTitle = alert.elements().firstWithName(title).name();
				}
				
				assertEquals(alertTitle, title);
				return true;
			}
		},
	
		handleAlertWithTitleAndButtonClick : function(title, buttonName) {
			// need an internal reference to pass to the callback method ('this' changes context)
			var ref = this;
			UIATarget.onAlert = function onAlert(alert) {
				ref.handleDefaultAlertWithTitle(title);
				
				QBM.log_debug("Tapping Alert Button: '" + buttonName + "'");
				// tap tap taparoo that button with the given name
				alert.buttons()[buttonName].tap();
				return true;
			}
		},
		
		/**
		 * Set the handler back to the default one after we are done.
		 */
		resetAlertHandler : function()
		{
			UIATarget.onAlert = this.defaultAlertHandler;
		},
		
		/**
		 * Set the handler back to the default one after we are done. Pass the contextView
		 */
		resetAlertHandler : function(viewContext)
		{
			UIATarget.onAlert = this.defaultAlertHandler;
			//wait for the next view coming up after the alert is handled.
			viewContext.target.delay(10);
		}
	},
	
	/**
	 * Initializes the base functional test object.
	 */
	initialize: function()
	{
		//this.parent();
		this.viewContext = null;
	},
	
	/**
	 * setup the App to normalize the way tests are run. Initialize the view context and do whatever
	 * we can to clear out the old set of test data and set the right target server environment
	 */
	setUp: function()
	{
		this.viewContext = new QBM.LoginView();
		QBM.log_trace("Setup Complete.");
	},
	
	/**
	 * tearDown the App to normalize the way tests finish. Clear the cache if possible.
	 */
	tearDown: function()
	{
		//do nothing.
	},
	
	setUpTestSet: function()
	{
		
		this.viewContext = new QBM.LoginView();
	},
	
	tearDownTestSet: function()
	{
		//do nothing.
	},
	
	/**
	 * Do any cleanup needed to make sure that subsequent tests run in a known good state. This 
	 * function iteratively calls escapeAction on the current view context until the returned
	 * context is not set. After that it will push the app to the background for 1 second. 
	 *
	 * If the escapeAction ever throws an exception, we attempt to discover what view we are on and
	 * try the escape process again. This will happen up to 5 times before we give up.
	 */
	doCleanup: function(attemptCount)
	{ 
		var stemView;
		//initialize the attempt
		if (typeof attemptCount == "undefined")
		{
			attemptCount = 0;
		}
		if (attemptCount < 5) //attempt the whole escape process up to 5 times
		{
			try
			{
				do
				{
					QBM.log_debug("Escaping from view: " + this.viewContext.viewName);
					this.viewContext = this.viewContext.escapeAction();
				}
				while(this.viewContext && this.viewContext.viewName != this.getBaseStateViewName());
				
				//we should now be at the login view. If we are not, trying to instantiate one here
				// will throw an exception and cause us to try again.
				this.viewContext = this.getBaseView();
				
				//Deactivate the app for 1 second before we begin the next test.
				// Not sure if we need this, but It might be a good fallback.
				// 
				// Upon further investigation I am almost certainly sure this is not needed.
				// lets leave it commented out until we are entirely sure it is needed.
				//UIATarget.localTarget().deactivateAppForDuration(1);
			}
			catch (e)
			{
				QBM.log_debug("Escape failed when attempt count was " + attemptCount);
				//TODO try to recover the viewContext using the QBM.StemView class
				stemView = new QBM.StemView();
				this.viewContext = stemView.getSpecializedView();
				//make a recursive call to doCleanup and increment the attemptCount
				this.doCleanup(++attemptCount);
			}
		}
		else
		{
			QBM.log_error("Automation failed cleanup more than 5 times in a row.");
		}
	},
	
	getBaseStateViewName: function()
	{
		return	"LoginView";
	},
	
	getBaseView: function()
	{
		return new QBM.LoginView();
	}
});