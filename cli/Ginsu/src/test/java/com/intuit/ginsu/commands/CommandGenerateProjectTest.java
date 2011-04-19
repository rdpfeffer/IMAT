package com.intuit.ginsu.commands;

import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.logging.Logger;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.intuit.ginsu.AppContext;
import com.intuit.ginsu.BaseTest;
import com.intuit.ginsu.cli.GinsuCLIModule;
import com.intuit.ginsu.cli.GinsuTestModuleOverride;
import com.intuit.ginsu.io.FileSystemResourceService;
import com.intuit.ginsu.scripts.AntScriptLauncher;

public class CommandGenerateProjectTest extends BaseTest{
	
	private File targetDir;
	private Injector injector;
	private Logger logger;
	private PrintWriter printWriter;
	private CommandGenerateProject command;
	
	@BeforeMethod
	public void beforeMethod() 
	{
		this.printWriter = injector.getInstance(PrintWriter.class);
		this.targetDir = new File(System.getProperty("java.io.tmpdir") 
				+ System.getProperty("file.separator") + "ginsuTestDir");
		AntScriptLauncher launcher = new AntScriptLauncher(new FileSystemResourceService());
		launcher.setProjectListener(injector.getInstance(PrintStream.class));
		this.command = new CommandGenerateProject(this.printWriter, this.logger, 
				launcher);
	}

	@AfterMethod
	public void afterMethod() 
	{
		this.targetDir.delete();
	}

	@BeforeClass
	public void beforeClass() 
	{
		AppContext appContext = AppContext.getInstance();
		appContext.setAppModule(Modules.override( new GinsuCLIModule()).with(new GinsuTestModuleOverride()));
		this.injector = Guice.createInjector(appContext.getAppModule());
		this.logger = injector.getInstance(Logger.class);
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
		this.command.run();
		
		// test to make sure that the Tokens were all matched
		File[] targetDirFiles = this.command.targetDir.listFiles();
		assert targetDirFiles.length == 4 : "The target directory did not have the right number of files. Instead it had: "
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
