package com.intuit.ginsu;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import com.intuit.ginsu.cli.HelpTextGenerator;
import com.intuit.ginsu.cli.InvalidMainArgumentArray;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
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
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }
    
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
    
    public void tearDown()
    {
    	try {
    		this.stubOutputStream.flush();
    		this.stubOutputStream.close();
    		this.helpTextGenerator = null;
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		}
    }

    /**
     * Rigourous Test :-)
     */
    public void testInitEnvCommand()
    {
        assertTrue( true );
        //TODO: Start this later
    }
    
    /**
     * Test to make sure that we can generate
     */
    public void testGenerateCommand()
    {
        assertTrue( true );
        
    }
    
    /**
     * Test to make sure we can test in all of the appropriate conditions
     */
    public void testExecuteCommand()
    {
        assertTrue( true );
        //TODO: once we start running tests with ginsu this will be important!
    }
    
    /**
     * Test to make sure help comes out in the right way
     */
    public void testHelpCommand()
    {
    	
    }
    
    /**
     * Test all of the 
     */
    public void testIncorrectUsageNoArguments()
    {
    	//Test no arguments (empty array)
    	//shell$	ginsu
    	String args[] = new String[0];
    	String expectedMessage = (new InvalidMainArgumentArray()).getMessage() + "\n" 
    		+ this.helpTextGenerator.getUsage();

    	App.main(args);
    	assertEquals(expectedMessage, this.stubOutputStream.toString());
    }
    
    public void testIncorrectUsageNullArguments()
    {
    	//Test null arguments
    	//shell$	ginsu
    	String expectedMessage = (new InvalidMainArgumentArray()).getMessage() + "\n" 
    		+ this.helpTextGenerator.getUsage();
    	App.main(null);
    	assertEquals(expectedMessage, this.stubOutputStream.toString());
    }
    
    
    public void testIncorrectUsageBogusArguments()
    {
    	//Test Bogus arguments
    	//shell$	ginsu foobar
    }
    
    
}
