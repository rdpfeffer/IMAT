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

//Core test framework
/**
 * This class handles things common to all Functional Tests
 */
GINSU.MetaTests = Class.extend(GINSU.BaseTest, {
	
	title: "MetaTests",
	/**
	 * Initializes the MetaTest Class object.
	 */
	initialize: function()
	{
		this.types = [undefined, null, 0, 1, true, false, "", "val", {key:"val"}, [1,2,3], 
			function(){}];
		
		this.trueTypes = [1, true, "val", {key:"val"}, [1,2,3], function(){}, [], {}];
		this.falseTypes = [undefined, null, 0,  false, "", 10/"foo"];
		
		this.anEqualObj = {key:"val"};
		this.otherEqualObj = {key:"val"};
		
		var nilObj = UIATarget.localTarget().frontMostApp().mainWindow().elements().firstWithName("foobarMonkeyTaco");
		this.nullTypes = [undefined, null, nilObj];
		this.nonNullTypes = [ 0, 1, true, false, "", "val", {key:"val"}, [1,2,3], function(){}];
		
		this.empty = "";
		this.whiteSpace = " ";
		this.needle = "<needle>";
		this.needleByItself = "<needle>";
		this.needleAtStart = "<needle>haystack";
		this.needleAtEnd = "haystack<needle>";
		this.needleInMiddle = "haystack<needle>haystack";
		
		this.exceptionWithoutMessage = new TypeError("no message here!");
		this.exceptionWithMessage = new TypeError("<needle> haystack");
	},
	
	/**
	 * setup the App to normalize the way tests are run. Initialize the view context and do whatever
	 * we can to clear out the old set of test data and set the right target server environment
	 */
	setUp: function()
	{
		//Do nothing
	},
	
	/**
	 * tearDown the App to normalize the way tests finish. Clear the cache if possible.
	 */
	tearDown: function()
	{
		//Do nothing
	},
	
	setUpTestSet: function()
	{
		//Do nothing.
	},
	
	tearDownTestSet: function()
	{
		//Do nothing
	},
	
	doCleanup: function()
	{
		//Do nothing
	},
	
	/**
	 * Test all of the macros so that we have a clear definition of what they will allow.
	 */
	testMacros: function()
	{
		//---------------------------------assertEquals-------------------------------------------//
		//-----------positive cases--------------//
		var i;
		
		GINSU.log_debug("testing positive cases for assertEquals");
		for(i = 0; i < this.types.length; i++)
		{
			GINSU.log_trace("this.types[i] is :" + this.types[i]);
			assertEquals(this.types[i], this.types[i]);
		}
		
		//test that references to the same object are equals
		assertEquals(this.anEqualObj, this.anEqualObj);
		
		//-----------negative cases--------------//
		// (non-comprehensive)
		GINSU.log_debug("testing negative cases for assertEquals");
		for(i = 0; i < this.types.length; i++)
		{
			GINSU.log_trace("this.types[i] is :" + this.types[i]);
			var message = "The two unequal objects, " + this.types[i] + " and "+ 
					this.types[(i+1) % this.types.length] + " evaluated as equals when they should" 
					+ " not have";
			
			try
			{
				assertEquals(this.types[i], this.types[(i+1) % this.types.length]);
				
				//if we get here, that means one of these things evaluated as equals. log a failure.
				UIALogger.logFail(message);
			}
			catch (e)
			{
				//do nothing. We Expect to fail.
			}
		}		
		try
		{
			assertEquals(10/"foo", 10/"foo");
			
			//if we get here, that means one of these things evaluated as equals. log a failure.
			UIALogger.logFail("NaN is never equal to itself!");
		}
		catch (e)
		{
			//do nothing. We Expect to fail.
		}
		try
		{
			//other identical objects will not be equal since this is equality by reference.
			GINSU.log_debug("Checking two objects of equal value");
			assertEquals(this.anEqualObj, this.otherEqualObj);
		}
		catch (e)
		{
			//do nothing. We Expect to fail.
		}
		
		//---------------------------------assertTrue---------------------------------------------//
		//-----------positive cases--------------//
		GINSU.log_trace("Testing positive cases for assertTrue");
		for(i = 0; i < this.trueTypes.length; i++)
		{
			GINSU.log_trace("this.trueTypes[i] is :" + this.trueTypes[i]);
			assertTrue(this.trueTypes[i]);
		}
		
		//-----------negative cases--------------//
		GINSU.log_trace("Testing negative cases for assertTrue");
		for(i = 0; i < this.falseTypes.length; i++)
		{
			GINSU.log_trace("this.falseTypes[i] is :" + this.falseTypes[i]);
			try
			{
				assertTrue(this.falseTypes[i]);
				
				//if we get here, that means one of these things evaluated as equals. log a failure.
				UIALogger.logFail("Something was true when it should have been false. Type was " +
					this.falseTypes[i]);
			}
			catch (e)
			{
				//do nothing. We Expect to fail.
			}
		}
		
		//---------------------------------assertFalse--------------------------------------------//
		//-----------positive cases--------------//
		GINSU.log_trace("Testing positive cases for assertFalse");
		for(i = 0; i < this.falseTypes.length; i++)
		{
			GINSU.log_trace("this.falseTypes[i] is :" + this.falseTypes[i]);
			assertFalse(this.falseTypes[i]);
		}
		
		//-----------negative cases--------------//
		GINSU.log_trace("Testing negative cases for assertFalse");
		for(i = 0; i < this.trueTypes.length; i++)
		{
			GINSU.log_trace("this.trueTypes[i] is :" + this.trueTypes[i]);
			try
			{
				assertFalse(this.trueTypes[i]);
				
				//if we get here, that means one of these things evaluated as equals. log a failure.
				UIALogger.logFail("Something was false when it should have been true. Type was " +
					this.trueTypes[i]);
			}
			catch (e)
			{
				//do nothing. We Expect to fail.
			}
		}

		//---------------------------------assertNotNull------------------------------------------//
		//-----------positive cases--------------//
		GINSU.log_trace("Testing positive cases for assertNotNull");
		for(i = 0; i < this.nonNullTypes.length; i++)
		{
			GINSU.log_trace("this.nonNullTypes[i] is :" + this.nonNullTypes[i]);
			assertNotNull(this.nonNullTypes[i]);
		}
		
		//-----------negative cases--------------//
		GINSU.log_trace("Testing negative cases for assertNotNull");
		for(i = 0; i < this.nullTypes.length; i++)
		{
			GINSU.log_trace("this.nullTypes[i] is :" + this.nullTypes[i]);
			try
			{
				assertNotNull(this.nullTypes[i]);
				
				//if we get here, that means one of these things evaluated as equals. log a failure.
				UIALogger.logFail("Something is null when it should have been not null. Type was " +
					this.nullTypes[i]);
			}
			catch (e)
			{
				//do nothing. We Expect to fail.
			}
		}
	},
	
	/**
	 * Make sure that we are finding all the functions we care about in a test test and ignoring
	 * the ones we don't.
	 */
	testIsPropValidTestCase: function()
	{
		var myRunner = new GINSU.TestRunner();
		var mockTests = new GINSU.MockTests();
		var i;
		
		//-----------positive cases--------------//
		GINSU.log_debug("testing positive cases for IsPropValidTestCase");
		for(i = 0; i < mockTests.myTests.length; i++)
		{
			GINSU.log_trace("mockTests.myTests[i] is :" + mockTests.myTests[i]);
			assertTrue(myRunner.isPropValidTestCase(mockTests.myTests[i], mockTests));
		}
		
		//-----------negative cases--------------//
		GINSU.log_debug("testing negative cases for IsPropValidTestCase");
		for(i = 0; i < mockTests.myNonTests.length; i++)
		{
			GINSU.log_trace("mockTests.myNonTests[i] is :" + mockTests.myNonTests[i]);
			assertFalse(myRunner.isPropValidTestCase(mockTests.myNonTests[i], mockTests));
		}
	},
	
	/**
	 * Make sure that we are filtering out all the tests we don't care about in a test set
	 */
	testTestCaseMatchesFilter: function()
	{
		var myRunner = new GINSU.TestRunner();
		var mockTests = new GINSU.MockTests();
		var i;
		
		//-----------zero filter cases--------------//
		GINSU.log_debug("testing positive cases for IsPropValidTestCase");
		for(i = 0; i < mockTests.myTests.length; i++)
		{
			GINSU.log_trace("mockTests.myTests[i] is :" + mockTests.myTests[i]);
			assertTrue(myRunner.testCaseMatchesFilter(mockTests.myTests[i], mockTests));
		}
		
		//-----------single filter cases--------------//
		myRunner.addFilters(["Needle"]);
		GINSU.log_debug("testing positive cases for IsPropValidTestCase");
		for(i = 0; i < mockTests.myTests.length; i++)
		{
			GINSU.log_trace("mockTests.myTests[i] is :" + mockTests.myTests[i]);
			if(mockTests.myTests[i] == "testNeedle")
			{
				assertTrue(myRunner.testCaseMatchesFilter(mockTests.myTests[i], mockTests));
			}
			else
			{
				assertFalse(myRunner.testCaseMatchesFilter(mockTests.myTests[i], mockTests));
			}
		}
		
		//-----------multi filter cases--------------//
		myRunner.addFilters(["4LeafClover"]);
		GINSU.log_debug("testing positive cases for IsPropValidTestCase");
		for(i = 0; i < mockTests.myTests.length; i++)
		{
			GINSU.log_trace("mockTests.myTests[i] is :" + mockTests.myTests[i]);
			if(mockTests.myTests[i] == "testNeedle" || mockTests.myTests[i] == "test4LeafClover")
			{
				assertTrue(myRunner.testCaseMatchesFilter(mockTests.myTests[i], mockTests));
			}
			else
			{
				assertFalse(myRunner.testCaseMatchesFilter(mockTests.myTests[i], mockTests));
			}
		}
		
		
	},
	
	/**
	 * Make sure we are able to decipher one exception from the next.
	 */
	testExceptions: function()
	{
		//-----------positive cases--------------//
		GINSU.log_debug("testing positive cases for Exceptions");
		assertTrue(GINSU.exceptionStartsWithMessage(this.needleAtStart, this.needle));
		assertTrue(GINSU.exceptionStartsWithMessage(this.exceptionWithMessage, this.needle));

		//-----------negative cases--------------//
		GINSU.log_debug("testing negative cases for Exceptions");
		assertFalse(GINSU.exceptionStartsWithMessage(this.exceptionWithoutMessage, this.needle));
		assertFalse(GINSU.exceptionStartsWithMessage("", this.needle));
	},
	
	/**
	 * Make sure that the extensions functions for strings work as expected.
	 */
	testStringExtensions: function()
	{
		//-----------positive cases--------------//
		GINSU.log_debug("testing positive cases for stringExtensions");
		assertTrue(this.needleByItself.startsWith(this.needle));
		assertTrue(this.needleAtStart.startsWith(this.needle));

		//-----------negative cases--------------//
		GINSU.log_debug("testing negative cases for stringExtensions");
		assertFalse(this.needleAtEnd.startsWith(this.needle));
		assertFalse(this.needleInMiddle.startsWith(this.needle));
		assertFalse(this.empty.startsWith(this.needle));
		assertFalse(this.whiteSpace.startsWith(this.needle));
	}
	
});

GINSU.suiteRunner.addTestSet( new GINSU.MetaTests());