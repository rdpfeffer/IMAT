package com.intuit.ginsu.cli;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit test for simple App.
 */
public class AppTest
{
 
	//private ByteArrayOutputStream stubOutputStream;
	
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        //super( testName );
    }

    @BeforeMethod()
	public void setUp()
    {
    	//Set up the application with the fixture that we can query later
    	//AppContext.getInstance().setAppModule(new GinsuTestModuleOverride());
    	
    	
    }
    
    @AfterMethod()
	public void tearDown()
    {
    	/*TODO: Add the following block back in.
    	 * try {
    		//this.stubOutputStream.flush();
    		//this.stubOutputStream.close();
		} catch (IOException e) {
			AssertJUnit.assertTrue(e.getMessage(), false);
		}*/
    }

    /**
     * Rigourous Test :-)
     */
    @Test()
	public void testInitEnvCommandEndToEnd()
    {
        AssertJUnit.assertTrue( true );
        //TODO: Start this later
    }
    
    /**
     * Test to make sure that we can generate
     */
    @Test()
	public void testGenerateProjectCommandEndToEnd()
    {
        AssertJUnit.assertTrue( true );
        
    }
    
    /**
     * Test to make sure we can test in all of the appropriate conditions
     */
    @Test()
	public void testExecuteCommandEndToEnd()
    {
        AssertJUnit.assertTrue( true );
        //TODO: once we start running tests with ginsu this will be important!
    }
    
    /**
     * Test to make sure help comes out in the right way
     */
    @Test()
	public void testHelpCommandEndToEnd()
    {
    	
    }
    
    
}
