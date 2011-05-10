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
package com.intuit.ginsu;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author rpfeffer
 * @dateCreated Apr 26, 2011
 * 
 *              This is a generic interface for all path analysis and
 *              computation.
 * 
 */
public interface IPathAnalysis {

	/**
	 * Returns the relative path from the given path to the directory where the
	 * application is installed.
	 * 
	 * @param fromPath
	 *            {@link File} the file representing the perspective from which
	 *            the relative path will be generated.
	 * @return The relative path as a string.
	 * @throws FileNotFoundException
	 *             when either fromPath or the App's home directory cannot be
	 *             found.
	 */
	public String getRelativePathToAppHome(File fromPath)
			throws FileNotFoundException;
}
