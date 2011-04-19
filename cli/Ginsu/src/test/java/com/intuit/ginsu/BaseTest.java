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
package com.intuit.ginsu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 * @author rpfeffer
 * @dateCreated Apr 12, 2011
 * 
 *              //TODO Explain why this file exists and how it is used.
 * 
 */
public abstract class BaseTest {

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
			BufferedReader reader = new BufferedReader( new FileReader(file));
			String line = "";
			while((line = reader.readLine()) != null)
			{
				if (line.contains(substr))
				{
					stringExists = true;
					break;
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			assert false: e.getStackTrace();
		} catch (IOException e) {
			assert false: e.getStackTrace();
		}
		return stringExists;
	}
}
