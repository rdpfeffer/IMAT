package com.intuit.tools.imat.cli;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.tools.imat.AppContext;
import com.intuit.tools.imat.cli.App;
import com.intuit.tools.imat.cli.MainArgs;
import com.intuit.tools.imat.commands.CommandNewProject;

/**
 * Unit test for simple App.
 */
public class AppTest extends BaseFunctionalTest {

	@AfterMethod()
	public void tearDown() throws IOException {
		AppContext.INSTANCE.clear();
	}

	/**
	 * This is a big EndToEnd Test to validate that we can generate a project as
	 * well as ensure that the environment specific files can be re-initialized
	 * to simulate initialization
	 * 
	 * @throws IOException
	 */
	@Test()
	public void testProjectSetupEndToEnd() throws IOException {
		// Createa temp file so that we can get a path to the temp dir
		File tempFile = File.createTempFile("testInitEnvCommandEndToEnd",
				".txt");
		String targetPath = tempFile.getParent() + File.separator
				+ "com.intuit.tools.imat.testInitEnvCommandEndToEndTargetDir";

		String envDirPath = targetPath + File.separator + "env";
		String envJSPath = envDirPath + File.separator + "env.js";
		String templateName = "TestTemplate.tracetemplate";
		String templatePath = getTestResourceAsFile(templateName).getPath();

		// this is the template that will get copied into the project directory
		String templateTargetPath = envDirPath + File.separator + templateName;

		// First generate the project
		App.main(new String[] { MainArgs.HOME, ".", MainArgs.SKIP_EXIT_STATUS,
				CommandNewProject.NAME, "-t", targetPath, "-g", "TEST" });
		(new File(envJSPath)).delete();

		// Clear the app context before we try to run things again.
		AppContext.INSTANCE.clear();

		// Delete the env dir so we can create it with the next call to
		// App.main(args[])
		assert !(new File(envJSPath)).exists() : "environment js file still exists when it should have been deleted. Dir: "
				+ envDirPath;

		// Clear the app context before we try to run the init-env command.
		AppContext.INSTANCE.clear();

		// Make the call to init env.
		App.main(new String[] { MainArgs.HOME, ".", MainArgs.PROJECT_DIR,
				targetPath, MainArgs.SKIP_EXIT_STATUS, "init-env", "-t",
				templatePath });

		// Check that it recreated all of the stuff within projectDir/env
		assert (new File(envDirPath)).exists() : "environment directory did not exist after making a call to init-env. Dir: "
				+ envDirPath;
		assert (new File(envJSPath)).exists() : "environment javascript file did not exist after making a call to init-env. File: "
				+ envJSPath;
		assert (new File(templateTargetPath)).exists() : "template file did not exist after making a call to init-env. File: "
				+ templateTargetPath;

		// clean up after we are done.
		tempFile.delete();
		deleteAllFilesInGeneratedProject(targetPath);
		assert !(new File(targetPath)).exists() : "The target directory still exists even after we tried to delete it! Dir: "
				+ targetPath;
	}

	/**
	 * Test to make sure that asking for help end to end does not throw any
	 * uncaught exceptions.
	 */
	@Test()
	public void testHelpCommandEndToEnd() {
		App.main(new String[] { MainArgs.HOME, ".", MainArgs.SKIP_EXIT_STATUS,
				"help" });
	}

}
