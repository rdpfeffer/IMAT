package com.intuit.tools.imat.reporting;

import java.util.HashMap;

public class IOSAutomationResultsReaderTests extends BaseIOSAutomationResultsTests {

	@Override
	protected void verifyAgainstResultFile(String fileName,
			HashMap<String, Object> props) {
		int sizeForFile = ((Integer)props.get(LOG_QUEUE_SIZE_KEY)).intValue();
		//sizeForFile++;//the log queue should include an additional closing message
		reader.setPlistFile(getTestResourceAsFile(SUBDIR + fileName));
		reader.run();
		int actual = logQueue.size() -1;
		assert actual == sizeForFile : "Queue was expected to have " 
			+ sizeForFile + " results for the file named " + fileName 
			+ " instead it had: " + actual;
	}
}
