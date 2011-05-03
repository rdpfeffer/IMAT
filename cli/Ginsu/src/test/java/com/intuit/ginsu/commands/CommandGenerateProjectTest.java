package com.intuit.ginsu.commands;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.inject.Injector;
import com.intuit.ginsu.MisconfigurationException;
import com.intuit.ginsu.cli.BaseFunctionalTest;

public class CommandGenerateProjectTest extends BaseFunctionalTest{
	
	private File targetDir;
	private Injector injector;
	private CommandGenerateProject command;
	
	@BeforeMethod
	public void beforeMethod() 
	{
		this.targetDir = new File(System.getProperty("java.io.tmpdir") 
				+ System.getProperty("file.separator") + "ginsuTestDir");
		this.command = injector.getInstance(CommandGenerateProject.class);
	}

	@AfterMethod
	public void afterMethod() 
	{
		this.targetDir.delete();
	}

	@BeforeClass
	public void beforeClass() 
	{
		ByteArrayOutputStream outputStreamFixture = new ByteArrayOutputStream();
		this.injector = getFunctionalTestInjector(outputStreamFixture);
	}

	@AfterClass
	public void afterClass() 
	{
		
	}

	@BeforeTest
	public void beforeTest() 
	{
		
	}

	@AfterTest
	public void afterTest() 
	{
		this.targetDir.delete();
	}
	
	@Test
	public void testRunWithValidOptions() 
	{
		this.command.targetDir = this.targetDir;
		this.command.globalObjectVar = "FOO"; 
		try {
			this.command.run();
		} catch (MisconfigurationException e) {
			assert false : e.getMessage();
		}
		
		// test to make sure that the Tokens were all matched
		File[] targetDirFiles = this.command.targetDir.listFiles();
		assert targetDirFiles.length == 6 : "The target directory did not have the right number of files. Instead it had: "
				+ String.valueOf(targetDirFiles.length);
		for (int i = 0; i < targetDirFiles.length; i++) {
			if (targetDirFiles[i].getName() == "project.js") {
				assert false == this.checkEachLineInFileForSubstring(
						targetDirFiles[i], "@PATH_TO_GINSU@") : "@PATH_TO_GINSU@ found in file when it should not have existed";
				assert false == this.checkEachLineInFileForSubstring(
						targetDirFiles[i], "@GLOBAL_OBJECT@") : "@GLOBAL_OBJECT@ found in file when it should not have existed";
			}
		}
	}
	
	@Test
	public void testRunWithDefaultOptions() 
	{
		//this.command.run();
		//assert false;
	}
	
	@Test
	public void testRunWithInvalidGlobalObj() 
	{
		//assert false;
	}
	
	@Test
	public void testRunningTwiceToTheSameDirectory() 
	{
		//assert false;
	}
}
