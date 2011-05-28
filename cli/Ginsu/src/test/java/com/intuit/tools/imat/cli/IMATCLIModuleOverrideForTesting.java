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
package com.intuit.tools.imat.cli;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * @author rpfeffer
 * @dateCreated Mar 25, 2011
 * 
 *              This module overrides the OutputStream interfaces of the
 *              IMATCLIModule class for functional testing.
 * 
 */
public class IMATCLIModuleOverrideForTesting extends AbstractModule {

	private ByteArrayOutputStream outputStream;

	IMATCLIModuleOverrideForTesting(ByteArrayOutputStream outputStream) {
		this.outputStream = outputStream;
	}

	/**
	 * Provide a PrintWriter that has the desired output stream.
	 * 
	 * @param outputStream
	 *            an {@link OutputStream} to print to.
	 * @return a {@link PrintWriter} with the {@link OutputStream} passed in.
	 */
	@Provides
	PrintWriter providePrintWriter() {
		PrintWriter pw = new PrintWriter(this.outputStream, true);
		return pw;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(OutputStream.class).toInstance(outputStream);
	}

}
