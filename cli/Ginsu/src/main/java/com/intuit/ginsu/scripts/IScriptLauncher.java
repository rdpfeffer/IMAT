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
package com.intuit.ginsu.scripts;

import java.io.File;
import java.io.PrintStream;
import java.util.Hashtable;

/**
 * @author rpfeffer
 * @dateCreated Apr 12, 2011
 *
 * //TODO Explain why this file exists and how it is used.
 *
 */
public interface IScriptLauncher {

	public void setScript(String script);
	
	public String getScript();
	
	public File getScriptAsFile();
	
	//TODO: explain that it is expected that these properties will be set and used when the target script is invoked.
	public void setProperties( Hashtable<String, String> properties);
	
	public void runScript();
	
	public void setPrinStream(PrintStream stdOut);
}
