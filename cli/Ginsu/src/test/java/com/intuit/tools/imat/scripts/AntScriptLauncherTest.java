package com.intuit.tools.imat.scripts;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.tools.imat.AppContext;
import com.intuit.tools.imat.cli.BaseFunctionalTest;
import com.intuit.tools.imat.io.FileSystemResourceService;
import com.intuit.tools.imat.io.FileSystemTestResourceService;
import com.intuit.tools.imat.io.PathAnalyzer;
import com.intuit.tools.imat.scripts.AntScriptLauncher;

public class AntScriptLauncherTest extends BaseFunctionalTest {

	private AntScriptLauncher scriptLauncher;
	private ByteArrayOutputStream outputStream;
	private PathAnalyzer mockPathAnalyzer;
	
	
	@BeforeMethod
	public void beforeMethod() {
		mockPathAnalyzer = EasyMock.createMock(PathAnalyzer.class);
		FileSystemTestResourceService resourceService = 
			new FileSystemTestResourceService(
					Logger.getLogger(FileSystemResourceService.class), mockPathAnalyzer);
		scriptLauncher = new AntScriptLauncher(resourceService,
				Logger.getLogger(AntScriptLauncher.class));
		outputStream = new ByteArrayOutputStream();
		scriptLauncher.setProjectListener(new PrintStream(outputStream, true));
		AppContext.INSTANCE.setProperty(AppContext.APP_HOME_KEY, "");
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
		//App.initAppContext(new String[] {});
	}

	@AfterClass
	public void afterClass() {
		AppContext.INSTANCE.clear();
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
		scriptWithoutProps.setReadable(true);
		scriptWithoutProps.setExecutable(true);
		scriptLauncher.setScript(scriptWithoutProps.getPath());
		scriptLauncher.runScript();
		String output = this.outputStream.toString(); 
		assert output.contains("Hello, world!") : "Output from invoking ant script did not" +
				"contain the expected results. Instead output was: " + output;
	}
	
	@Test
	public void testRunScript() {
		File scriptWithProps = getTestResourceAsFile("helloWorldWithProps.xml");
		scriptWithProps.setExecutable(true);
		scriptWithProps.setReadable(true);
		scriptLauncher.setScript(scriptWithProps.getPath());
		LinkedHashMap<String, String> properties = new LinkedHashMap<String, String>();
		properties.put("name", "Johnny Utah");
		scriptLauncher.setProperties(properties);
		scriptLauncher.runScript();
		String output = this.outputStream.toString(); 
		assert output.contains("Johnny Utah") : "Output from invoking ant script did not" +
				"contain the expected results. Instead output was: " + output;
	}
}
