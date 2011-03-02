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
 * This class is a mock class used for testing that the TestRunner can accurately find the right
 * test methods.
 */
GINSU.MockTests = Class.extend(GINSU.BaseTest, {
	
	initialize: function(){},
	myTests: ["test1", "testA", "testa", "test_a", "testAtest", "testNeedle", "test4LeafClover"],
	myNonTests: ["test", "Atest", "foo", "TestA", "testB"],
	test1: function(){},
	testA: function(){},
	testa: function(){},
	test_a: function(){},
	testAtest: function(){},
	testNeedle: function(){},
	test4LeafClover: function(){},
	test: function(){},
	Atest: function(){},
	foo: function(){},
	TestA: function(){},
	testB: true
});