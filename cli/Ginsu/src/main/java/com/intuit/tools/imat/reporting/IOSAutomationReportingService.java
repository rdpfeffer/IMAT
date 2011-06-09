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
import java.util.*;
import java.net.*;
import java.text.DateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult; 

public class IOSAutomationReportingService implements IReportingService{
	
	//tag names in plist file.
	private static final String DICT_TAG_NAME = "dict";
	private static final String STRING_TAG_NAME = "string";
	private static final String DATE_TAG_NAME = "date";
	private static final String INTEGER_TAG_NAME = "integer";
	
	//return codes for various test statuses
	private static final Integer SCRIPT_ENTER_EXCEPTION_CODE = 0;
	private static final Integer SCRIPT_RUNNING_EXCEPTION_CODE = 1;
	private static final Integer SCRIPT_COMPLETED_SUCCESSFULLY_CODE = 2;
	
	//test status messages in plist files
	private static final String SCRIPT_ENTER_EXCEPTION_MSG = "An exception occurred while trying to run the script";
	private static final String SCRIPT_RUNNING_EXCEPTION_MSG = "Exception raised while running script";
	private static final String SCRIPT_COMPLETED_SUCCESSFULLY_MSG = "Script completed";
	private static final String INIT_SUITE = "suiteHandler.initSuite()";	
	private static final String SUITE_STARTED = "setUpTestSet";	
	private static final String SUITE_ENDED = "tearDownTestSet";
	
	//UIA Logger codes
	private static final String UIA_DEBUG_CODE = "0";
	private static final String UIA_MESSAGE_CODE = "1";
	private static final String UIA_WARNING_CODE = "2";
	private static final String UIA_ERROR_CODE = "3";
	private static final String UIA_TESTSTART_CODE = "4";
	private static final String UIA_PASS_CODE = "5";
	private static final String UIA_ISSUE_CODE = "6";
	private static final String UIA_FAIL_CODE = "7";
	private static final String UIA_SCREENSHOT_CODE = "8";
	private static final String UIA_STOPPEDBYUSER_CODE = "9";
	
	//testcase result strings 
	private static final String TESTCASE_PASS_STATUS = "PASS";	
	private static final String TESTCASE_FAIL_STATUS = "FAIL";	
	
	//junit XML elements	
	private static final String JUNIT_XML_ELEMENT_TESTSUITE = "testsuite";
	private static final String JUNIT_XML_ELEMENT_TESTCASE= "testcase";
	private static final String JUNIT_XML_ELEMENT_FAILURE = "failure";
	private static final String JUNIT_XML_ELEMENT_ERROR = "error";
	
	//junit XML attributes
	private static final String JUNIT_XML_ELEMENT_ERRORS = "errors";
	private static final String JUNIT_XML_ELEMENT_FAILURES = "failures";
	private static final String JUNIT_XML_ELEMENT_HOSTNAME = "hostname";
	private static final String JUNIT_XML_ELEMENT_ID = "id";
	private static final String JUNIT_XML_ELEMENT_NAME = "name";
	private static final String JUNIT_XML_ELEMENT_PACKAGE = "package";
	private static final String JUNIT_XML_ELEMENT_TESTS = "tests";
	private static final String JUNIT_XML_ELEMENT_TIME = "time";
	private static final String JUNIT_XML_ELEMENT_TIMESTAMP = "timestamp";
	private static final String JUNIT_XML_ELEMENT_CLASSNAME = "classname";
	private static final String JUNIT_XML_ELEMENT_MESSAGE = "message";
	private static final String JUNIT_XML_ELEMENT_TYPE = "type";
	
	private ArrayList<Dict> dictList;
	private JunitTestSuiteList junitTestSuiteList;
	private File junitXMLResultPath;
	
	public void setTestOutputFile() {
		//to be implemented
	}
	
	public void convertTestOutputFileToJunitXMLResultFormat(File pList) {		
		this.convertPListToDictList (pList);
		this.convertDictListToJunitTestSuiteList ();
		this.convertJunitTestSuiteListToJunitXMLResult ();
	}
	
	public File getJunitXMLResultPath() {
		return junitXMLResultPath;
	}
	
	public void convertPListToDictList (File pList){		
		try {		
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(pList);
		    doc.getDocumentElement().normalize();		    
		    NodeList nList = doc.getElementsByTagName(DICT_TAG_NAME);	    
		    dictList = new ArrayList<Dict>();
		    
		    //start the loop from 1 instead of 0. Otherwise the first node will be added twice.
		    for (int temp = 1; temp < nList.getLength() ; temp++) {		 
		       Node nNode = nList.item(temp);	    
		       if (nNode.getNodeType() == Node.ELEMENT_NODE) {		 
		          Element eElement = (Element) nNode;		          
		          String str = getTagValue(STRING_TAG_NAME,eElement);
		          String date = getTagValue(DATE_TAG_NAME,eElement);
		          String integer = getTagValue(INTEGER_TAG_NAME,eElement);		          
		          Dict d = new Dict(str,date,integer);
		          dictList.add(d);
		        }
		    }	    
		 } catch (Exception e) {
			e.printStackTrace();
		 }		
	}
	
	public void convertDictListToJunitTestSuiteList () {
		if (getSuiteStatus () != SCRIPT_ENTER_EXCEPTION_CODE){
			generateSuiteListForScriptCompleted ();
		}	
	}	
	public void convertJunitTestSuiteListToJunitXMLResult () {		
		if  (getSuiteStatus () == SCRIPT_ENTER_EXCEPTION_CODE){
			generateXMLForScriptEnterException ();			
		}else {
			generateXMLForScriptCompleted ();
		}		
	}	
	
	public void generateSuiteListForScriptCompleted () {
		String currentTestCase = "";
		String currentSuite = "";
		String testCaseStartTime = "";
		String testCaseEndTime = "";
		long totalSuiteExecutionTime = 0;
		int testSuiteCount=0;
		boolean testCaseCompleted = false;
		junitTestSuiteList = new JunitTestSuiteList();		
		JunitTestSuite testSuite = new JunitTestSuite ();
		JunitTestCase currentJunitTestCase = new JunitTestCase ();
		JunitTestError testError = new JunitTestError ();
		ArrayList<JunitTestCase> testCaseList = new ArrayList<JunitTestCase> ();	
		ArrayList<JunitTestSuite> testSuiteList = new ArrayList<JunitTestSuite> ();		
		
		
		for (int i=0;i<dictList.size();i++){			
	    	Dict dict = dictList.get(i);
	    	
	    	 //Skip suiteHandler.initSuite() strings
	        if (dict.getString().contains(INIT_SUITE)) {
	        	continue;
	        }
	              
	        //Found SUITE_STARTED pattern
	        if (dict.getString().contains(SUITE_STARTED)){
	        	//Intialize suite execution time.
	        	totalSuiteExecutionTime=0;
	        	String[] tokens = dict.getString().split("\\.");
	        	currentSuite= tokens[0];
	        	testSuite = new JunitTestSuite ();        	
	        	testSuite.setName(currentSuite);
	        	testCaseList = new ArrayList<JunitTestCase> ();        	 
	        }
	        
	        //Found TestCase start pattern
	        if (dict.getString().contains(currentSuite) && dict.getInteger().equalsIgnoreCase(UIA_TESTSTART_CODE)){
	        	testCaseCompleted = false;
	        	testCaseStartTime=dict.getDate();
	        	String[] tokens = dict.getString().split("\\.");	        	
	        	currentTestCase = tokens[1];
	        	currentJunitTestCase = new JunitTestCase();
	        	currentJunitTestCase.setClassname(currentSuite);
	        	currentJunitTestCase.setTestcasename(currentTestCase);        	
	        }
	        
	      //check if the testcase has any error message
	        if ( (!currentTestCase.isEmpty() &&  (dict.getInteger().equalsIgnoreCase(UIA_ERROR_CODE)) || dict.getString().contains(SCRIPT_RUNNING_EXCEPTION_MSG))){	        		
	        		testError = new JunitTestError();
	        		testError.setMessage(dict.getString());
	        		currentJunitTestCase.setTesterror(testError);   		
	        }        
	        
	        //Check if TestCase end pattern is found
	        if (dict.getString().contains(SCRIPT_RUNNING_EXCEPTION_MSG) || (dict.getString().contains(currentTestCase) && (dict.getInteger().equalsIgnoreCase(UIA_FAIL_CODE) || dict.getInteger().equalsIgnoreCase(UIA_PASS_CODE)) )) {
	        	
	        	
	        	//check if the testcase failed
	        	if (dict.getInteger().equalsIgnoreCase(UIA_FAIL_CODE) || dict.getString().contains(SCRIPT_RUNNING_EXCEPTION_MSG)){
	        		testCaseCompleted = true;
	        		testCaseEndTime=dict.getDate();
	        		currentJunitTestCase.setStatus(TESTCASE_FAIL_STATUS);	        		
	        		testSuite.incrementErrorCount();
	        	}
	        	
	        	//check if the testcase passed
	        	else if (dict.getInteger().equalsIgnoreCase(UIA_PASS_CODE)){
	        		testCaseCompleted = true;
	        		testCaseEndTime=dict.getDate();
	        		currentJunitTestCase.setStatus(TESTCASE_PASS_STATUS);	        		
	        	}        	
	        	
	        	if (testCaseCompleted){
	        		
	        		DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	        		long testCaseDuration=0;
		    		try {
		    			Date startTime = df.parse(testCaseStartTime);
		    			Date endTime = df.parse(testCaseEndTime);
		    			testCaseDuration = (endTime.getTime() - startTime.getTime()) / 1000;
		    		}catch (Exception e) {
		    			e.printStackTrace();
		    		}   		
		    		totalSuiteExecutionTime += testCaseDuration;
		    		currentJunitTestCase.setTime(String.valueOf(testCaseDuration));	
		    		testCaseList.add(currentJunitTestCase);	        		
	        		testSuite.setTestcaseList(testCaseList);
	        	}
	    		
	        		
	        	//check if suite is completed.
	        	if ( (dict.getString().contains(SUITE_ENDED) && dict.getInteger().equalsIgnoreCase(UIA_PASS_CODE)) || dict.getString().contains(SCRIPT_RUNNING_EXCEPTION_MSG)){	        		
	        			try {
	    	                InetAddress addr = InetAddress.getLocalHost();	                
	    	                String hostName = addr.getHostName();
	    	                testSuite.setHostname(hostName);
	    	                
	    	            } catch (UnknownHostException e) {
	    	            	e.printStackTrace();
	    	            }
	    	            
	    	            testSuite.setTime(String.valueOf(totalSuiteExecutionTime));
	    	            testSuite.setId(Integer.toString(testSuiteCount));
	    	            testSuite.setPackagename("");
	    	            int inumTests = testSuite.getTestCaseList().size();
	    	            testSuite.setTests(Integer.toString(inumTests));
	    	            testSuite.setTimestamp(dict.getDate());	    	            
	    	            testSuiteList.add(testSuite);   	        	
	    	        	testSuiteCount++;    	        	
	    	      }
	        
	        
	        }
	        //Check if SCRIPT_COMPLETED pattern is found.
	        if (dict.getString().contains(SCRIPT_COMPLETED_SUCCESSFULLY_MSG)){	        	    	
	            junitTestSuiteList.setTestSuiteList(testSuiteList);	            
	        	break;
	        }
	        
		}	   
	}
	
	public void generateXMLForScriptEnterException () {		
		try {		
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		 
			//root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(JUNIT_XML_ELEMENT_TESTSUITE);
			doc.appendChild(rootElement);
			  
			//testsuite elements
			Element error = doc.createElement(JUNIT_XML_ELEMENT_ERROR);
			rootElement.appendChild(error);
		 
			//set message attribute to error element
			Attr attr = doc.createAttribute(JUNIT_XML_ELEMENT_MESSAGE);
			attr.setValue(SCRIPT_ENTER_EXCEPTION_MSG);
			error.setAttributeNode(attr);
			
			//set timestamp attribute to error element
			attr = doc.createAttribute(JUNIT_XML_ELEMENT_TIMESTAMP);
			attr.setValue(dictList.get(0).getDate());
			error.setAttributeNode(attr);		
			
			
			//write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			File junitXML = getJunitXMLResultPath ();
			StreamResult result =  new StreamResult(new File(junitXML.toString()));			
			transformer.transform(source, result);
			
		}catch(ParserConfigurationException pce){
	    	 pce.printStackTrace();
	     }catch(TransformerException tfe){
	    	 tfe.printStackTrace();
	     }
		  
	}
	
	public void generateXMLForScriptCompleted () {
		try{	 
		      
			  for (int i=0;i<junitTestSuiteList.getTestSuiteList().size();i++ ){
				  
				  JunitTestSuite testSuite = junitTestSuiteList.getTestSuiteList ().get(i);
				  
				  DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				  DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			 
				  //testsuite root element
				  Document doc = docBuilder.newDocument();				  
				  Element suite = doc.createElement(JUNIT_XML_ELEMENT_TESTSUITE);
				  doc.appendChild(suite);
			 
				  //set attributes to testsuite element
				  Attr attr = doc.createAttribute(JUNIT_XML_ELEMENT_ERRORS);
				  attr.setValue(testSuite.getErrors());
				  suite.setAttributeNode(attr);
				  
				  attr = doc.createAttribute(JUNIT_XML_ELEMENT_HOSTNAME);
				  attr.setValue(testSuite.getHostname());
				  suite.setAttributeNode(attr);
				  
				  attr = doc.createAttribute(JUNIT_XML_ELEMENT_ID);
				  attr.setValue(testSuite.getId());
				  suite.setAttributeNode(attr);
				  
				  attr = doc.createAttribute(JUNIT_XML_ELEMENT_NAME);
				  attr.setValue(testSuite.getName());
				  suite.setAttributeNode(attr);
				  
				  attr = doc.createAttribute(JUNIT_XML_ELEMENT_PACKAGE);
				  attr.setValue(testSuite.getPackagename());
				  suite.setAttributeNode(attr);
				  
				  attr = doc.createAttribute(JUNIT_XML_ELEMENT_TESTS);
				  attr.setValue(testSuite.getTests());
				  suite.setAttributeNode(attr);
				  
				  attr = doc.createAttribute(JUNIT_XML_ELEMENT_TIME);
				  attr.setValue(testSuite.getTime());
				  suite.setAttributeNode(attr);
				  
				  attr = doc.createAttribute(JUNIT_XML_ELEMENT_TIMESTAMP);
				  attr.setValue(testSuite.getTimestamp());
				  suite.setAttributeNode(attr);	
				  
				  for (int j=0;j<testSuite.getTestCaseList().size();j++) {
					  JunitTestCase junittestCase = testSuite.getTestCaseList().get(j);
					  Element testcase = doc.createElement(JUNIT_XML_ELEMENT_TESTCASE);
					  suite.appendChild(testcase);
					  
					  attr = doc.createAttribute(JUNIT_XML_ELEMENT_CLASSNAME);
					  attr.setValue(junittestCase.getClassname());
					  testcase.setAttributeNode(attr);
					  
					  attr = doc.createAttribute(JUNIT_XML_ELEMENT_NAME);
					  attr.setValue(junittestCase.getTestcasename());
					  testcase.setAttributeNode(attr);
					  
					  attr = doc.createAttribute(JUNIT_XML_ELEMENT_TIME);
					  attr.setValue(junittestCase.getTime());
					  testcase.setAttributeNode(attr);
					  
					  if (junittestCase.getStatus()==TESTCASE_FAIL_STATUS){
						  Element error = doc.createElement(JUNIT_XML_ELEMENT_ERROR);
						  testcase.appendChild(error);
						  
						  Attr attr1 = doc.createAttribute(JUNIT_XML_ELEMENT_MESSAGE);
						  
						  if (junittestCase.getTesterror() == null){
							   
							  //Insert a sample error message if UIALogger.logFail() < logError()
							  //meaning if plist file does not have matching <integer>3</integer> for <integer>7</integer>
							  attr1.setValue("Error message not found for this testcase");
						  }
						  else {
							  attr1.setValue(junittestCase.getTesterror().getMessage());
						  }
						  //attr1.setValue("Sample Error Message");
						  error.setAttributeNode(attr1);
					  }
					  
					//write the content into xml file
					  TransformerFactory transformerFactory = TransformerFactory.newInstance();
					  Transformer transformer = transformerFactory.newTransformer();
					  DOMSource source = new DOMSource(doc);
					  File junitXMLPath = getJunitXMLResultPath ();
					  String junitXMLFile =  junitXMLPath.toString() + "\\TEST-" + testSuite.getName() + ".xml";
					  StreamResult result =  new StreamResult(junitXMLFile);
					  transformer.transform(source, result); 
				  }
			  }
				  
			  		  
			  
			  }catch(ParserConfigurationException pce){
		    	 pce.printStackTrace();
		    	 
			  }catch(TransformerException tfe){
		    	 tfe.printStackTrace();
			  }
		
	}
	//change the access to private later.
	public Integer getSuiteStatus () {			
		int dictListSize = dictList.size(),suiteStatus = 0; 
		if (dictListSize == 1 && dictList.get(dictListSize-1).getString().contains(SCRIPT_ENTER_EXCEPTION_MSG)) {
			suiteStatus = SCRIPT_ENTER_EXCEPTION_CODE;
		}else if (dictList.get(dictListSize-2).getString().contains(SCRIPT_RUNNING_EXCEPTION_MSG)) {
			suiteStatus = SCRIPT_RUNNING_EXCEPTION_CODE;
		}else if (dictList.get(dictListSize-1).getString().contains(SCRIPT_COMPLETED_SUCCESSFULLY_MSG)) {
			suiteStatus = SCRIPT_COMPLETED_SUCCESSFULLY_CODE;
		}
		return suiteStatus;
	}
	
	private static String getTagValue(String sTag, Element eElement){
	    NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0); 	 
	    return nValue.getNodeValue().trim();    
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

	public void setJunitXMLResultPath(File junitXMLResultPath) {
		this.junitXMLResultPath = junitXMLResultPath;
	}
	

}
