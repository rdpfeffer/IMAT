@GLOBAL_OBJECT@.SuiteHandler = Class.create({
	
	initialize: function()
	{
		//do nothing. All objects created using Class.create must have an initialize function.
	},
	
	/**
	 * Initializes the suite before any testSets are run
	 */
	initSuite: function()
	{
		GINSU.log_trace("Initializing Suite");
	},
	
	/**
	 * Cleans up the suite after all testSets are run
	 */
	cleanUpSuite: function()
	{
		GINSU.log_trace("Cleaning up Suite");
	}
});
