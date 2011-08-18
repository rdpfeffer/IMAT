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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * @author rpfeffer
 * @dateCreated Jun 4, 2011
 * 
 *              This class configures the reporting interface for test results.
 */
public class IMATReportingModule extends AbstractModule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(IReportingService.class).to(IOSAutomationReportingService.class);
//		bind(BlockingQueue.class).annotatedWith(LogResultsBuffer.class)
//				.toInstance(new LinkedBlockingQueue<Dict>());
//		bind(BlockingQueue.class).annotatedWith(JUnitReportsBuffer.class)
//				.toInstance(new LinkedBlockingQueue<JunitTestSuite>());
	}

	@Provides
	IOSAutomationReportingService provideReportingService(
			IOSAutomationResultsReader reader,
			IOSLogEntryToJUnitTranslator translator, JUnitReportWriter writer) {
		Logger logger = Logger.getLogger(IOSAutomationReportingService.class);
		return new IOSAutomationReportingService(logger, reader, translator,
				writer);
	}

	@Provides
	IOSAutomationResultsReader provideResultsReader(
			@LogResultsBuffer BlockingQueue<Dict> buffer) {

		Logger logger = Logger.getLogger(IOSAutomationResultsReader.class);
		return new IOSAutomationResultsReader(logger, buffer);
	}

	@Provides
	IOSLogEntryToJUnitTranslator provideTranslator(
			@LogResultsBuffer BlockingQueue<Dict> buffer,
			@JUnitReportsBuffer BlockingQueue<JunitTestSuite> reportBuffer) {
		Logger logger = Logger.getLogger(IOSLogEntryToJUnitTranslator.class);
		return new IOSLogEntryToJUnitTranslator(logger, buffer, reportBuffer);
	}
	
	@Provides
	JUnitReportWriter provideWriter(
			@JUnitReportsBuffer BlockingQueue<JunitTestSuite> reportBuffer) {
		
		Logger logger = Logger.getLogger(JUnitReportWriter.class);
		return new JUnitReportWriter(logger, reportBuffer);
	}

	@Provides
	@LogResultsBuffer
	@Singleton
	BlockingQueue<Dict> provideLogResultsBuffer() {
		return new LinkedBlockingQueue<Dict>();
	}

	@Provides
	@JUnitReportsBuffer
	@Singleton
	BlockingQueue<JunitTestSuite> provideReportsBuffer() {
		return new LinkedBlockingQueue<JunitTestSuite>();
	}

//	private void bindBlockingQueuePrameterizedWith(Class<?> clazz) {
//		ParameterizedType ifcType = Types.newParameterizedType(
//				BlockingQueue.class, clazz);
//		ParameterizedType implType = Types.newParameterizedType(
//				LinkedBlockingQueue.class, clazz);
//		// bind
//	}
}
