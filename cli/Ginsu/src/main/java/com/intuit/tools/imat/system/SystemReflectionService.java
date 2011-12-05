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
package com.intuit.tools.imat.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author rpfeffer
 * @dateCreated Dec 2, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public class SystemReflectionService {

	private static final String KEY = "CFBundleShortVersionString";
	private static final String DEFAULT_INSTRUMENTS_LOCATION = File.separator+"Developer"+File.separator+"Applications"+File.separator+"Instruments.app";
	private static final String VERSION_PLIST_PATH = "Contents"+File.separator+"version.plist";
	private static final String START_TAG = "<string>";
	private static final String END_TAG = "</string>";
	
	public String getSystemInstrumentsVersion() {
		String versionString = "";
		File versionFile = new File(DEFAULT_INSTRUMENTS_LOCATION + File.separator + VERSION_PLIST_PATH);
		if (versionFile.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(versionFile));
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.contains(KEY)) {
						line = reader.readLine();
						int start = line.indexOf(START_TAG) + START_TAG.length();
						int end = line.indexOf(END_TAG);
						versionString = line.substring(start, end);
						break;
					}
				}
				reader.close();
			} catch (FileNotFoundException e) {
				//ignore the error.
			} catch (IOException e) {
				//ignore the error.
			}
		}
		//	TODO Else use the PropertyConfigurationService to get the path to 
		//	Instruments that we would like to use.
		return versionString;
	}
}
