package com.intuit.tools.imat.reporting;

import java.util.HashMap;

public class JunitReportWriterTests extends BaseIOSAutomationResultsTests{
	
	@Override
	protected void verifyAgainstResultFile(String fileName,
			HashMap<String, Object> props) {
		reader.setPlistFile(getTestResourceAsFile(SUBDIR + fileName));
		reader.run();
		translator.run();
		writer.write();
	}
}
