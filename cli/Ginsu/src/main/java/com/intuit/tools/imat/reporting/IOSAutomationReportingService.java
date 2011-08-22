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
package com.intuit.tools.imat.reporting;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class IOSAutomationReportingService implements IReportingService {

	private ArrayList<Dict> dictList;
	private JunitTestSuiteList junitTestSuiteList;
	private File junitXMLResultPath;

	private final Logger logger;
	private final IOSAutomationResultsReader reader;
	private final IOSLogEntryToJUnitTranslator translator;
	private final JUnitReportWriter writer;

	public IOSAutomationReportingService(Logger logger,
			IOSAutomationResultsReader reader,
			IOSLogEntryToJUnitTranslator translator, JUnitReportWriter writer) {
		this.logger = logger;
		this.reader = reader;
		this.translator = translator;
		this.writer = writer;
	}

	public void setTestOutputFile() {
		// to be implemented
	}

	public void convertTestOutputFileToJunitXMLResultFormat(File pList) {
		logger.info("Starting conversion process of plist file: " + pList.toString());
		reader.setPlistFile(pList);
		writer.setTargetDir(getJunitXMLResultPath());
		(new Thread(reader)).start();
		(new Thread(translator)).start();
		writer.write();
	}

	public ArrayList<Dict> getDictList() {
		return dictList;
	}

	public void setDictList(ArrayList<Dict> dictList) {
		this.dictList = dictList;
	}

	public JunitTestSuiteList getJunitTestSuiteList() {
		return junitTestSuiteList;
	}

	public void setJunitTestSuiteList(JunitTestSuiteList junitTestSuiteList) {
		this.junitTestSuiteList = junitTestSuiteList;
	}

	public File getJunitXMLResultPath() {
		return junitXMLResultPath;
	}
	
	public void setJunitXMLResultPath(File junitXMLResultPath) {
		this.junitXMLResultPath = junitXMLResultPath;
	}

}
