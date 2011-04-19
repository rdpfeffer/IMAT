/**
 * This class handles things common to all Functional Tests
 */
@GLOBAL_OBJECT@.BaseFunctionalTests = Class.extend(GINSU.BaseTest, {
	
	alertManager : {
	
		handleDefaultAlertWithTitle : function(title) {
			UIATarget.onAlert = function onAlert(alert) {
				
				@GLOBAL_OBJECT@.log_debug("Handling alert named: '" + title + "'");
				
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
				
				@GLOBAL_OBJECT@.log_debug("Tapping Alert Button: '" + buttonName + "'");
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
		this.viewContext = new @GLOBAL_OBJECT@.StarterView();
		@GLOBAL_OBJECT@.log_trace("Setup Complete.");
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
		
		this.viewContext = new @GLOBAL_OBJECT@.StarterView();
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
					@GLOBAL_OBJECT@.log_debug("Escaping from view: " + this.viewContext.viewName);
					this.viewContext = this.viewContext.escapeAction();
				}
				while(this.viewContext && this.viewContext.viewName != this.getBaseStateViewName());
				
				//we should now be at the login view. If we are not, trying to instantiate one here
				// will throw an exception and cause us to try again.
				this.viewContext = this.getBaseView();
			}
			catch (e)
			{
				@GLOBAL_OBJECT@.log_debug("Escape failed when attempt count was " + attemptCount);
				stemView = new @GLOBAL_OBJECT@.StemView();
				this.viewContext = stemView.getSpecializedView();
				//make a recursive call to doCleanup and increment the attemptCount
				this.doCleanup(++attemptCount);
			}
		}
		else
		{
			@GLOBAL_OBJECT@.log_error("Automation failed cleanup more than 5 times in a row.");
		}
	},
	
	getBaseStateViewName: function()
	{
		return	"StarterView";
	},
	
	getBaseView: function()
	{
		return new @GLOBAL_OBJECT@.StarterView();
	}
});