@GLOBAL_OBJECT@.StarterTestSet = Class.extend(IMAT.BaseFunctionalTestSet, {
	
	//All tests
	title: "StarterTestSet",
	/**
	 * Initializes the MetaTest Class object.
	 */
	initialize: function()
	{
		IMAT.log_debug("Initializing the Starter Tests.");
		IMAT.log_debug("Note: This is where you could declare your test fixtures.");
	},
	
	/**
	 * setup the App to normalize the way tests are run. Initialize the view context and do whatever
	 * we can to make sure that the next test runs in the right state.
	 */
	setUp: function()
	{
		//Do nothing
		IMAT.log_debug("setUp was called");
		//This is where you would invoke actions to setup the right viewContext before each test in
		//this test set
	},
	
	/**
	 * tearDown the App to normalize the way tests finish. Reset anything that might cause the next
	 * test to fail.
	 */
	tearDown: function()
	{
		//Do nothing
		IMAT.log_debug("tearDown was called");
		//This is where you would invoke actions to reset the viewContext after each test in this
		//test Set
	},
	
	setUpTestSet: function()
	{
		IMAT.log_debug("setUpTestSet was called");
		//This is where you would invoke actions to setup the right viewContext before any of the 
		//tests in this test set are run.
		
		//A good thing to do here would be to initialize this.viewContext
		this.viewContext = new @GLOBAL_OBJECT@.StarterView();
	},
	
	tearDownTestSet: function()
	{
		IMAT.log_debug("tearDownTestSet was called");		
		//This is where you would invoke actions to setup the right viewContext after all of the 
		//tests in this test set are run.
	},
	
	doCleanup: function()
	{
		//Do nothing
		IMAT.log_debug("doCleanup was called");		
	},
	
	/**
	 * Return the base view which we consider to be the "Base State" when recovering from a test
	 * failure. This implementation does nothing and returns undefined. You must override this
	 * in order to get test recovery working correctly. 
	 *
	 * @return IMAT.LoginView which is considered the base view for all functional tests
	 */
	getBaseView: function() {
		return new @GLOBAL_OBJECT@.StarterView();
	},
	
	/**
	 * Test all of the macros so that we have a clear definition of what they will allow.
	 */
	testSomething: function()
	{
		IMAT.log_debug("Running the testSomething() test");
		
		//In IMAT, a test is defined as a set of actions taken against a view context.
		this.viewContext
		
	},
	
});

//After a test is defined, add an instance of it to the global suiteRunner object.
IMAT.suiteRunner.addTestSet( new @GLOBAL_OBJECT@.StarterTestSet());