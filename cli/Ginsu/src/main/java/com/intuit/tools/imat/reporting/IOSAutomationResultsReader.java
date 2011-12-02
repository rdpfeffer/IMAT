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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author rpfeffer
 * @dateCreated Aug 14, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public class IOSAutomationResultsReader implements Runnable{

	private final Logger logger;
	private final BlockingQueue<Dict> buffer;
	private  File plistFile;
	
	IOSAutomationResultsReader(Logger logger, BlockingQueue<Dict> buffer) {
		this.logger = logger;
		this.buffer = buffer;
	}
	
	// tag names in plist file.
	private static final String DICT_XPATH = "/plist/dict/array/dict";
	private static final String STRING_TAG_NAME = "string";
	private static final String DATE_TAG_NAME = "date";
	private static final String INTEGER_TAG_NAME = "integer";
	
	public void run() {
		try {
			logger.debug("Converting Plist to DictList.");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(getPlistFile());
			doc.getDocumentElement().normalize();
			
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();
			XPathExpression expr = xPath.compile(DICT_XPATH);
			NodeList nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			logger.debug("The number of Dictionary nodes parsed is: " + nList.getLength());
			
			for (int numDictionariesParsed = 0; numDictionariesParsed < nList.getLength(); numDictionariesParsed++) {
				Node nNode = nList.item(numDictionariesParsed);
				Dict dictionaryEntry = parseDictionaryFromNode(nNode);
				if (dictionaryEntry != null)
				{
					buffer.put(dictionaryEntry);
				}
			}
			
			sendReadingCompleteToBuffer();
		} catch (Exception e) {
			logger.error("Error while converting pList to DictList", e);
		}
	}

	private Dict parseDictionaryFromNode(Node node) {
		Dict dictionary = null;
		try {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;	
				dictionary = new Dict();
				dictionary.setString(getTagValue(STRING_TAG_NAME, element, 1));	//The message of the log entry
				dictionary.setCode(getTagValue(INTEGER_TAG_NAME, element, 0));	//The Type of the log entry
				dictionary.setDate(getTagValue(DATE_TAG_NAME, element, 0));		//The Timestamp of the log entry
			}
		} catch (Exception e) {
			logger.warn("Errors while pasring log entry. Skipping.", e);
		}
		return dictionary;
	}
	
	private String getTagValue(String sTag, Element eElement, int index ) {
		String value = "";
		NodeList tagNodeList = eElement.getElementsByTagName(sTag);
		if (tagNodeList.getLength() > 0) {
			NodeList nlList = tagNodeList.item(index).getChildNodes();
			if (nlList.getLength() > 0){
				value = nlList.item(0).getNodeValue().trim();
			}
		}
		return value;
	}
	
	private void sendReadingCompleteToBuffer()
	{
		Dict lastDict = new Dict();
		lastDict.setCode(UIALoggerCode.STOPPED);
		try {
			this.buffer.put(lastDict);
		} catch (InterruptedException e) {
			logger.error(e); 
		}
	}

	/**
	 * @param plistFile the plistFile to set
	 */
	public void setPlistFile(File plistFile) {
		this.plistFile = plistFile;
	}

	/**
	 * @return the plistFile
	 */
	public File getPlistFile() {
		return plistFile;
	}
	
}
