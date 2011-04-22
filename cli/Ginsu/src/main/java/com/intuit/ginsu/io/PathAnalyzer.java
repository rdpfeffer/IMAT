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
package com.intuit.ginsu.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;

/**
 * @author rpfeffer
 * @dateCreated Apr 14, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public class PathAnalyzer {

	private final Logger logger;
	private IApplicationResourceService resourceService;
	
	/**
	 * Create a new Path Analyzer
	 */
	public PathAnalyzer(Logger logger)
	{
		this.setResourceService(
				new FileSystemResourceService(
						Logger.getLogger(FileSystemResourceService.class)));
		this.logger = logger;
	}
	
	/**
	 * TODO: Finish documenting this with examples.
	 * @param fromPath 
	 * @param toPath
	 * @return
	 */
	public String getRelativePath(File fromPath, File toPath)
	{
		
		String relativePath = "";
		
		try {
			logger.debug("Getting the relativepath. fromPath="+fromPath+" toPath="+toPath);
			String[] fromPathStrArray = fromPath.getCanonicalFile().toURI().getPath().split("/");
			String[] toPathStrArray = toPath.getCanonicalFile().toURI().getPath().split("/");
			
			//First find the number of common ancestor directories between the two paths.
			int numCommonDirs = 0;
			while (numCommonDirs < fromPathStrArray.length && numCommonDirs < toPathStrArray.length)
			{
				
				if( !fromPathStrArray[numCommonDirs].equals(toPathStrArray[numCommonDirs]))
				{
					break;
				}
				numCommonDirs++;
			}
			logger.trace("When finding common ancestor of paths. " +"numCommonDirs="+
					numCommonDirs+". fromPathStrArray.length=" + fromPathStrArray.length+
					" toPathStrArray.length="+ toPathStrArray.length);
			
			//Second start to build the relative path by adding the number of parent dirs
			//the "fromPath" does not have in common with the "toPath".
			StringBuilder pathBuilder = new StringBuilder();
			int numParentDirs = fromPathStrArray.length - numCommonDirs; 
			//decrement num uncommon dirs if the fromPath is a file. 
			numParentDirs = (fromPath.isFile() ? numParentDirs - 1 : numParentDirs); 
			for(int i = 0; i < numParentDirs; i++)
			{
				pathBuilder.append("../");
			}
			
			//Finally, add the dirs to the toDir to the pathBuilder that are not common between the
			//two directories
			int j = numCommonDirs;
			for(; j < toPathStrArray.length - 1; j++ )
			{
				pathBuilder.append(toPathStrArray[j] + "/");
			}
			//if toPath is the same directory as fromPath then it is possible that
			//j will be larger.
			if(j < toPathStrArray.length) 
			{
				pathBuilder.append(toPathStrArray[j]);
			}
			
			//set the relative path 
			relativePath = pathBuilder.toString();
			
		} catch (MalformedURLException e) {
			this.logException("MalformedURLException when getting relativePath.", e);
		} catch (IOException e) {
			this.logException("IOException when getting relativePath.", e);
		}
		logger.debug("Ended with the final relativePath="+ relativePath);
		return relativePath;
	}
	
	/**
	 * TODO: Finish documenting this with examples.
	 * @param fromPath
	 * @param toGinsuResourceFile
	 * @return
	 */
	public String getRelativePath(File fromPath, String toGinsuResourceFile)
	{
		String relativePath = "";
		try {
			File toPath = this.resourceService.getAppResourceFile(toGinsuResourceFile, true);
			relativePath = this.getRelativePath(fromPath, toPath);
		} catch (FileNotFoundException e) {
			this.logException("FileNotFoundException when getting relativePath.", e);
		}
		return relativePath;
	}

	/**
	 * @param resourceService the resourceService to set
	 */
	public void setResourceService(IApplicationResourceService resourceService) {
		this.resourceService = resourceService;
	}

	/**
	 * @return the resourceService
	 */
	public IApplicationResourceService getResourceService() {
		return resourceService;
	}
	
	private void logException(String prefix, Throwable e)
	{
		logger.debug(prefix + " " + e.getMessage());
		logger.debug(e.getStackTrace());
	}
}
