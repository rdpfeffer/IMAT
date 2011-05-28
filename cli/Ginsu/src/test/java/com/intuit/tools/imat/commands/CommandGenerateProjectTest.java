package com.intuit.tools.imat.commands;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.inject.Injector;
import com.intuit.tools.imat.MisconfigurationException;
import com.intuit.tools.imat.cli.BaseFunctionalTest;
import com.intuit.tools.imat.commands.CommandNewProject;

public class CommandGenerateProjectTest extends BaseFunctionalTest {

	private File targetDir;
	private Injector injector;
	private CommandNewProject command;

	@BeforeMethod
	public void beforeMethod() {
		this.targetDir = new File(System.getProperty("java.io.tmpdir")
				+ System.getProperty("file.separator") + "imatTestDir");
		this.command = injector.getInstance(CommandNewProject.class);
	}

	@AfterMethod
	public void afterMethod() {
		this.targetDir.delete();
	}

	@BeforeClass
	public void beforeClass() {
		ByteArrayOutputStream outputStreamFixture = new ByteArrayOutputStream();
		this.injector = getFunctionalTestInjector(outputStreamFixture);
	}

	@AfterTest
	public void afterTest() {
		this.targetDir.delete();
	}

	@Test
	public void testRunWithValidOptions() throws MisconfigurationException {
		this.command.targetDir = this.targetDir;
		this.command.globalObjectVar = "FOO";

		this.command.run();

		// test to make sure that the Tokens were all matched
		File[] targetDirFiles = this.command.targetDir.listFiles();
		assert targetDirFiles.length == 6 : "The target directory did not have the right number of files. Instead it had: "
				+ String.valueOf(targetDirFiles.length);
		for (int i = 0; i < targetDirFiles.length; i++) {
			if (targetDirFiles[i].getName() == "project.js") {
				assert false == this.checkEachLineInFileForSubstring(
						targetDirFiles[i], "@PATH_TO_APP@") : "@PATH_TO_APP@ found in file when it should not have existed";
				assert false == this.checkEachLineInFileForSubstring(
						targetDirFiles[i], "@GLOBAL_OBJECT@") : "@GLOBAL_OBJECT@ found in file when it should not have existed";
			}
		}
	}

	@Test
	public void testRunWithDefaultOptions() {
		// this.command.run();
		// assert false;
	}

	@Test
	public void testRunningTwiceToTheSameDirectory() {
		// assert false;
	}
}
