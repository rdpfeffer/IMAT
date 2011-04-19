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
import java.io.PrintWriter;
import java.net.MalformedURLException;

/**
 * @author rpfeffer
 * @dateCreated Apr 14, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public class PathAnalyzer {

	private final PrintWriter printWriter;
	private IApplicationResourceService resourceService;
	
	/**
	 * Create a new Path Analyzer
	 * @param printWriter
	 */
	public PathAnalyzer(PrintWriter printWriter)
	{
		this.printWriter = printWriter;
		this.setResourceService(new FileSystemResourceService());
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
			//TODO throw a more appropriate exception.
			this.printException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.printException(e);
		}
		
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
			// TODO throw a more appropriate exception.
			this.printException(e);
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
	
	private void printException(Throwable e)
	{
		printWriter.println("An error occured while trying to get a relative path. "+ e.getMessage());
		e.printStackTrace();
	}
}
