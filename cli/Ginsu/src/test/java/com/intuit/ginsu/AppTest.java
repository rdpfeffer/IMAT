package com.intuit.ginsu;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import com.intuit.ginsu.cli.HelpTextGenerator;
import com.intuit.ginsu.cli.InvalidMainArgumentArray;


/**
 * Unit test for simple App.
 */
public class AppTest
{
 
	private ByteArrayOutputStream stubOutputStream;
	private HelpTextGenerator helpTextGenerator;
	
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
    	this.stubOutputStream = new ByteArrayOutputStream();
    	PrintWriter printWriter = new PrintWriter(this.stubOutputStream, true);
    	AppContext.getInstance().setPrintWriter(printWriter);
    	
    	//set up the other fixtures
    	//NOTE: We do not actually print in our tests with the help
    	//text generator. We just use it to get the String value 
    	this.helpTextGenerator = new HelpTextGenerator(new PrintWriter(System.out, true));
    	
    }
    
    @AfterMethod()
	public void tearDown()
    {
    	try {
    		this.stubOutputStream.flush();
    		this.stubOutputStream.close();
    		this.helpTextGenerator = null;
		} catch (IOException e) {
			AssertJUnit.assertTrue(e.getMessage(), false);
		}
    }

    /**
     * Rigourous Test :-)
     */
    @Test()
	public void testInitEnvCommand()
    {
        AssertJUnit.assertTrue( true );
        //TODO: Start this later
    }
    
    /**
     * Test to make sure that we can generate
     */
    @Test()
	public void testGenerateCommand()
    {
        AssertJUnit.assertTrue( true );
        
    }
    
    /**
     * Test to make sure we can test in all of the appropriate conditions
     */
    @Test()
	public void testExecuteCommand()
    {
        AssertJUnit.assertTrue( true );
        //TODO: once we start running tests with ginsu this will be important!
    }
    
    /**
     * Test to make sure help comes out in the right way
     */
    @Test()
	public void testHelpCommand()
    {
    	
    }
    
    /**
     * Test all of the 
     */
    @Test()
	public void testIncorrectUsageNoArguments()
    {
    	//Test no arguments (empty array)
    	//shell$	ginsu
    	String args[] = new String[0];
    	String expectedMessage = (new InvalidMainArgumentArray()).getMessage() + "\n" 
    		+ this.helpTextGenerator.getUsage();

    	App.main(args);
    	AssertJUnit.assertEquals(expectedMessage, this.stubOutputStream.toString());
    }
    
    @Test()
	public void testIncorrectUsageNullArguments()
    {
    	//Test null arguments
    	//shell$	ginsu
    	String expectedMessage = (new InvalidMainArgumentArray()).getMessage() + "\n" 
    		+ this.helpTextGenerator.getUsage();
    	App.main(null);
    	AssertJUnit.assertEquals(expectedMessage, this.stubOutputStream.toString());
    }
    
    
    @Test()
	public void testIncorrectUsageBogusArguments()
    {
    	//Test Bogus arguments
    	//shell$	ginsu foobar
    }
    
    
}
