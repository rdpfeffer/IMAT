package com.intuit.tools.imat.reporting;

import java.util.HashMap;

public class IOSAutomationResultsReaderTests extends BaseIOSAutomationResultsTests {

	@Override
	protected void verifyAgainstResultFile(String fileName,
			HashMap<String, Object> props) {
		int sizeForFile = ((Integer)props.get(LOG_QUEUE_SIZE_KEY)).intValue();
		String sizeAsString = String.valueOf(sizeForFile);
		reader.setPlistFile(getTestResourceAsFile(SUBDIR + fileName));
		reader.run();
		assert logQueue.size() == sizeForFile : "Queue was expected to have " 
			+ sizeAsString + " results for the file named " + fileName 
			+ " instead it had: " + logQueue.size();
	}
}
