package com.intuit.ginsu.scripts;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Hashtable;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ginsu.AppContext;
import com.intuit.ginsu.BaseTest;
import com.intuit.ginsu.io.FileSystemTestResourceService;

public class AntScriptLauncherTest extends BaseTest {

	private AntScriptLauncher scriptLauncher;
	private ByteArrayOutputStream outputStream;
	@BeforeMethod
	public void beforeMethod() {
		scriptLauncher = new AntScriptLauncher(new FileSystemTestResourceService());
		outputStream = new ByteArrayOutputStream();
		scriptLauncher.setProjectListener(new PrintStream(outputStream, true));
		scriptLauncher.setPrinStream(new PrintStream(outputStream, true));
		AppContext.getInstance().setProperty(AppContext.APP_HOME_KEY, "");
	}

	@AfterMethod
	public void afterMethod() {
		try {
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeClass
	public void beforeClass() {
	}

	@AfterClass
	public void afterClass() {
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testRunScriptThatDoesNotExist() {
		scriptLauncher.setScript("nonExistant.xml");
		scriptLauncher.runScript();
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testRunScriptWithoutPermissions() {
		File scriptWithoutPerms = getTestResourceAsFile("fileWithoutPerms.xml");
		scriptWithoutPerms.setReadable(false);
		scriptLauncher.setScript(scriptWithoutPerms.getPath());
		scriptLauncher.runScript();
		scriptWithoutPerms.setReadable(true);
	}
	
	@Test()
	public void testRunScriptWithUnsetProperties() {
		File scriptWithoutProps = getTestResourceAsFile("helloWorldWithProps.xml");
		scriptLauncher.setScript(scriptWithoutProps.getPath());
		scriptLauncher.runScript();
		String output = this.outputStream.toString(); 
		assert output.contains("[echo] Hello, ${name}") : "Output from invoking ant script did not" +
		"contain the expected results. Instead output was: " + output;
	}

	@Test()
	public void testRunScriptWithoutProperties() {
		File scriptWithoutProps = getTestResourceAsFile("helloWorldNoProps.xml");
		scriptLauncher.setScript(scriptWithoutProps.getPath());
		scriptLauncher.runScript();
		String output = this.outputStream.toString(); 
		assert output.contains("Hello, world!") : "Output from invoking ant script did not" +
				"contain the expected results. Instead output was: " + output;
	}
	
	@Test
	public void testRunScript() {
		File scriptWithoutProps = getTestResourceAsFile("helloWorldWithProps.xml");
		scriptLauncher.setScript(scriptWithoutProps.getPath());
		Hashtable<String, String> properties = new Hashtable<String, String>();
		properties.put("name", "Johnny Utah");
		scriptLauncher.setProperties(properties);
		scriptLauncher.runScript();
		String output = this.outputStream.toString(); 
		assert output.contains("Johnny Utah") : "Output from invoking ant script did not" +
				"contain the expected results. Instead output was: " + output;
	}
}
