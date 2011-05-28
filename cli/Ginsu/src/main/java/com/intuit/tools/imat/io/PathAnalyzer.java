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
package com.intuit.tools.imat.io;

import java.io.File;
import java.net.URI;

import org.apache.log4j.Logger;

/**
 * @author rpfeffer
 * @dateCreated Apr 14, 2011
 * 
 *              This class analyzes one or more paths in order to generate
 *              relative path information. It computes the relative path from
 *              the perspective of one URI to the location of the other.
 * 
 */
public class PathAnalyzer {

	private final Logger logger;

	/**
	 * Create a new Path Analyzer
	 */
	PathAnalyzer(Logger logger) {
		this.logger = logger;
	}

	/**
	 * TODO RP: Figure out if we really need this, or if we should just use
	 * URI.relativize(); If so, we should just get rid of this class.
	 * 
	 * @param fromURI
	 * @param toURI
	 * @return
	 */
	public String getRelativePath(URI fromURI, URI toURI) {

		String relativePath = "";
		logger.debug("Getting the relativepath. fromPath=" + fromURI
				+ " toPath=" + toURI);
		String[] fromPathStrArray = fromURI.getPath().split("/");
		String[] toPathStrArray = toURI.getPath().split("/");

		// First find the number of common ancestor directories between the two
		// paths.
		int numCommonDirs = 0;
		while (numCommonDirs < fromPathStrArray.length
				&& numCommonDirs < toPathStrArray.length) {

			if (!fromPathStrArray[numCommonDirs]
					.equals(toPathStrArray[numCommonDirs])) {
				break;
			}
			numCommonDirs++;
		}
		logger.trace("When finding common ancestor of paths. "
				+ "numCommonDirs=" + numCommonDirs
				+ ". fromPathStrArray.length=" + fromPathStrArray.length
				+ " toPathStrArray.length=" + toPathStrArray.length);

		// Second start to build the relative path by adding the number of
		// parent dirs the "fromPath" does not have in common with the "toPath".
		StringBuilder pathBuilder = new StringBuilder();
		int numParentDirs = fromPathStrArray.length - numCommonDirs;
		// decrement num uncommon dirs if the fromPath is a file.
		numParentDirs = (isFile(fromURI) ? numParentDirs - 1 : numParentDirs);
		for (int i = 0; i < numParentDirs; i++) {
			pathBuilder.append("../");
		}

		// Finally, add the dirs to the toDir to the pathBuilder that are not
		// common between the two directories
		int j = numCommonDirs;
		for (; j < toPathStrArray.length - 1; j++) {
			pathBuilder.append(toPathStrArray[j] + "/");
		}
		// if toPath is the same directory as fromPath then it is possible that
		// j will be larger.
		if (j < toPathStrArray.length) {
			pathBuilder.append(toPathStrArray[j]);
		}

		// set the relative path
		relativePath = pathBuilder.toString();
		logger.debug("Ended with the final relativePath=" + relativePath);
		return relativePath;
	}

	/**
	 * @param uri
	 * @return
	 */
	private boolean isFile(URI uri) {
		File path = new File(uri);
		return path.isFile();
	}
}
