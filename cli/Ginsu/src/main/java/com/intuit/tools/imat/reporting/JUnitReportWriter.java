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
import java.util.concurrent.BlockingQueue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.intuit.tools.imat.reporting.JunitTestCase.STATUS;

/**
 * @author rpfeffer
 * @dateCreated Aug 15, 2011
 * 
 *              //TODO Explain why this file exists and how it is used.
 * 
 */
public class JUnitReportWriter {

	// junit XML elements
	private static final String TESTSUITE = "testsuite";
	private static final String TESTCASE = "testcase";
	private static final String FAILURE = "failure";
	private static final String ERROR = "error";

	// junit XML attributes
	private static final String ERRORS = "errors";
	private static final String FAILURES = "failures";
	private static final String HOSTNAME = "hostname";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String PACKAGE = "package";
	private static final String TESTS = "tests";
	private static final String TIME = "time";
	private static final String TIMESTAMP = "timestamp";
	private static final String CLASSNAME = "classname";
	// private static final String TYPE = "type";

	private final Logger logger;
	private final BlockingQueue<JunitTestSuite> suiteQueue;
	private File targetDir;
	private Document doc;

	JUnitReportWriter(Logger logger, BlockingQueue<JunitTestSuite> suiteQueue) {
		this.logger = logger;
		this.suiteQueue = suiteQueue;
	}

	public void write() {
		logger.debug("generateXMLForScriptCompleted");
		JunitTestSuite testSuite;
		try {
			while (suiteIsNotEmpty(testSuite = suiteQueue.take())) {
				logger.trace("Building document for test suite: "
						+ testSuite.getName());
				DocumentBuilderFactory docFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				// testsuite root element
				doc = docBuilder.newDocument();

				Element suite = initSuiteElement(testSuite);
				for (int j = 0; j < testSuite.getTestCaseList().size(); j++) {
					JunitTestCase junittestCase = testSuite.getTestCaseList()
							.get(j);
					suite.appendChild(convertToTestCaseElement(junittestCase));
				}
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				String junitXMLFile = targetDir.toString() + File.separator
						+ "TEST-" + testSuite.getName() + ".xml";
				StreamResult result = new StreamResult(junitXMLFile);
				transformer.transform(source, result);
			}
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}
	}

	private boolean suiteIsNotEmpty(JunitTestSuite suite) {
		return suite.getName() == "";
	}

	private Element initSuiteElement(JunitTestSuite suite) {
		Element elem = doc.createElement(TESTSUITE);
		doc.appendChild(elem);
		createAttrForElem(ERRORS, suite.getErrors(), elem);
		createAttrForElem(FAILURES, suite.getFailures(), elem);
		createAttrForElem(HOSTNAME, suite.getHostname(), elem);
		createAttrForElem(ID, suite.getId(), elem);
		createAttrForElem(NAME, suite.getName(), elem);
		createAttrForElem(PACKAGE, suite.getPackagename(), elem);
		createAttrForElem(TESTS, suite.getTests(), elem);
		createAttrForElem(TIME, suite.getTime(), elem);
		createAttrForElem(TIMESTAMP, suite.getTimestamp(), elem);
		return elem;
	}

	private Element convertToTestCaseElement(JunitTestCase junitTestCase) {
		logger.trace("Building document nodes for test case: "
				+ junitTestCase.getClassname() + "."
				+ junitTestCase.getTestcasename());
		Element testcase = doc.createElement(TESTCASE);
		createAttrForElem(CLASSNAME, junitTestCase.getClassname(), testcase);
		createAttrForElem(NAME, junitTestCase.getTestcasename(), testcase);
		createAttrForElem(TIME, junitTestCase.getTime(), testcase);
		
		if (junitTestCase.getStatus() != STATUS.PASS) {
			testcase.appendChild(getInnerMessage(junitTestCase));
		}
		return testcase;
	}
	
	private Element getInnerMessage(JunitTestCase junitTestCase) {
		Element innerMessageElement;
		switch (junitTestCase.getStatus()) {
		case FAIL:
			innerMessageElement = doc.createElement(FAILURE);
		case ERROR:
		default:
			innerMessageElement = doc.createElement(ERROR);
		}
		String innerMessage = "Error message not found for this testcase";
		if (junitTestCase.getInnerMessage() != null) {
			innerMessage = junitTestCase.getInnerMessage().getMessage();
		}
		innerMessageElement.setNodeValue(innerMessage);
		return innerMessageElement;
	}

	private void createAttrForElem(String attrName, String value, Element elem) {
		Attr attr = doc.createAttribute(attrName);
		attr.setValue(value);
		elem.setAttributeNode(attr);
	}
}
