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
import java.io.FileNotFoundException;
import java.util.Hashtable;

import com.intuit.ginsu.IScriptLauncher;

/**
 * @author rpfeffer
 * @dateCreated Apr 22, 2011
 * 
 *              This class provides an abstract implementation for all
 *              externally scripted processes, like Applescript
 * 
 */
public abstract class ExternallyScriptedProcessLauncher implements IScriptLauncher {

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.scripts.IScriptLauncher#setScript(java.lang.String)
	 */
	public void setScript(String script) {
		// TODO RP: We still need to implement this class. Will be done with the run tests feature

	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.scripts.IScriptLauncher#getScript()
	 */
	public String getScript() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.scripts.IScriptLauncher#getScriptAsFile()
	 */
	public File getScriptAsFile() throws FileNotFoundException {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.scripts.IScriptLauncher#setProperties(java.util.Hashtable)
	 */
	public void setProperties(Hashtable<String, String> properties) {

	}

	/* (non-Javadoc)
	 * @see com.intuit.ginsu.scripts.IScriptLauncher#runScript()
	 */
	public int runScript() {
		return 0;
	}

}
