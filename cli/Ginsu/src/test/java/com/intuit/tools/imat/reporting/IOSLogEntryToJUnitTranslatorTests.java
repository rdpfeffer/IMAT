package com.intuit.tools.imat.reporting;

import java.util.HashMap;

public class IOSLogEntryToJUnitTranslatorTests extends BaseIOSAutomationResultsTests{
	
	@Override
	protected void verifyAgainstResultFile(String fileName,
			HashMap<String, Object> props) {
		reader.setPlistFile(getTestResourceAsFile(SUBDIR + fileName));
		reader.run();
		translator.run();
		int suiteCount = ((Integer)props.get(REPORT_QUEUE_SIZE_KEY)).intValue();
		String sizeAsString = String.valueOf(suiteCount);
		assert reportQueue.size() == suiteCount : "Queue was expected to have " 
			+ sizeAsString + " results for the file named " + fileName 
			+ " instead it had: " + reportQueue.size();
	}
}
