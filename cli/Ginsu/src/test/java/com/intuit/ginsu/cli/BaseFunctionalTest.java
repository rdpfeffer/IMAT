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
package com.intuit.ginsu.cli;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.intuit.ginsu.AppContext;
import com.intuit.ginsu.commands.GinsuCommandsModule;
import com.intuit.ginsu.config.GinsuConfigModule;
import com.intuit.ginsu.io.FileSystemResourceService;
import com.intuit.ginsu.io.FileSystemTestResourceService;
import com.intuit.ginsu.io.GinsuIOModule;
import com.intuit.ginsu.io.PathAnalyzer;
import com.intuit.ginsu.logging.BindLog4JWithClassNameModule;
import com.intuit.ginsu.monitor.GinsuMonitorModule;
import com.intuit.ginsu.scripts.AntScriptLauncher;
import com.intuit.ginsu.scripts.AntScriptLauncherForTesting;
import com.intuit.ginsu.scripts.GinsuScriptsModule;

/**
 * @author rpfeffer
 * @dateCreated Apr 12, 2011
 * 
 *              This class provides base methods commonly used in functional
 *              test classes.
 * 
 */
public abstract class BaseFunctionalTest {

	private static final String TARGET_DIR = "target" + File.separator
			+ "test-classes" + File.separator;
	private static final String DEV_RESOURCE_DIR = "src" + File.separator
			+ "test" + File.separator + "resources" + File.separator;

	@SuppressWarnings("static-access")
	protected File getTestResourceAsFile(String resourceFileName) {
		File resourceFile;
		URL fileUrl = this.getClass().getClassLoader()
				.getSystemResource(resourceFileName);
		if (fileUrl == null) {
			// if we don't get a url back, just defult to what maven does out of
			// the box.
			resourceFile = new File(TARGET_DIR + resourceFileName);

			if (!resourceFile.exists()) {
				// if we still do not get what we want, try to get it from the
				// project's development directory structure.
				resourceFile = new File(DEV_RESOURCE_DIR + resourceFileName);
			}
		} else {
			resourceFile = new File(fileUrl.getPath());
		}
		return resourceFile;
	}

	/**
	 * Reads each line in the given Text File, testing for sbstr. This method
	 * does not search accross more than one line at a time for substr
	 * 
	 * @param file
	 * @param substr
	 * @return
	 */
	protected boolean checkEachLineInFileForSubstring(File file, String substr) {
		boolean stringExists = false;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (line.contains(substr)) {
					stringExists = true;
					break;
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			assert false : e.getStackTrace();
		} catch (IOException e) {
			assert false : e.getStackTrace();
		}
		return stringExists;
	}

	/**
	 * Return the Google Guice injector to created especially for functional
	 * testing
	 * 
	 * @param outputStream
	 * @return
	 */
	protected Injector getFunctionalTestInjector(
			ByteArrayOutputStream outputStream) {
		{
			AppContext appContext = AppContext.INSTANCE;
			appContext.setProperty(AppContext.APP_HOME_KEY, ".");
			return Guice.createInjector(Modules.override(new GinsuCLIModule())
					.with(new GinsuCLIModuleOverrideForTesting(outputStream)),
					new BindLog4JWithClassNameModule(),
					new GinsuCommandsModule(), new GinsuIOModule(),
					new GinsuConfigModule(), new GinsuScriptsModule(),
					new GinsuMonitorModule());
		}
	}

	/**
	 * Delete all project files created at targetPath to clean up after
	 * functional testing.
	 * 
	 * @param targetPath
	 *            the path where the project exists.
	 */
	protected void deleteAllFilesInGeneratedProject(String targetPath) {
		PathAnalyzer mockPathAnalyzer = EasyMock.createMock(PathAnalyzer.class);
		FileSystemTestResourceService resourceService = new FileSystemTestResourceService(
				Logger.getLogger(FileSystemResourceService.class),
				mockPathAnalyzer);
		AntScriptLauncher scriptLauncher = new AntScriptLauncherForTesting(
				resourceService, Logger.getLogger(AntScriptLauncher.class));
		File cleanupScript = getTestResourceAsFile("deleteProjectFiles.xml");
		scriptLauncher.setScript(cleanupScript.getPath());
		LinkedHashMap<String, String> properties = new LinkedHashMap<String, String>();
		properties.put("target.dir", targetPath);
		scriptLauncher.setProperties(properties);
		scriptLauncher.setProjectListener(System.out);
		scriptLauncher.runScript();
	}
}
