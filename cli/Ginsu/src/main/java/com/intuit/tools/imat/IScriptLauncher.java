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
package com.intuit.tools.imat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

import com.intuit.tools.imat.cli.ExitStatus;

/**
 * @author rpfeffer
 * @dateCreated Apr 12, 2011
 * 
 *              This interface defines how all script launcher implementations
 *              should interact.
 * 
 *              <p>
 *              <b>Note</b>, this interface assumes nothing about how the script
 *              will return its output other than the fact that it should return
 *              a non-zero value when the script exits abnormally.
 *              </p>
 * 
 *              <p>
 *              In general, to run a script, you should invoke the following
 *              pseudocode...
 *              </p>
 * 
 *              <pre>
 * Hashtable&lt;String, String&gt; props = new Hashtable&lt;String, String&gt;();
 * props.set(&quot;foo&quot;, &quot;bar&quot;); // set the property foo to the value &quot;bar&quot;
 * 
 * // assuming you already have an instance that implements IScriptLauncher
 * scriptLauncher.setScript(&quot;someScript.script&quot;);
 * 
 * // enables the property set above to be available to the script
 * scriptLauncher.setProperties(props);
 * 
 * // finally, now that everything is set, we can run the script
 * int exitVal = scriptLauncher.runScript();
 * </pre>
 */
public interface IScriptLauncher {

	/**
	 * Set the script to be run by the launcher.
	 * 
	 * @param script
	 *            a {@link String} representing the path to the file to be run.
	 */
	public void setScript(String script);

	/**
	 * Get the currently set script.
	 * 
	 * @return a {@link String} representing the path to the file to be run.
	 */
	public String getScript();

	/**
	 * Get the currently set script returned as {@link File}
	 * 
	 * @return the {@link File} representation of the script
	 * @throws FileNotFoundException
	 *             when the script string does not map to an actual file.
	 */
	public File getScriptAsFile() throws FileNotFoundException;

	/**
	 * Set values defined in properties to a configuration store which will be
	 * accessed by the script when it runs. Note, this store could be anything
	 * from an in-memory set, to a file, and environment variable or otherwise.
	 * Depending on the implementation of the scripted process, it could be any
	 * one or more of those just mentioned. Regardless of the implementation,
	 * these properties must be set before
	 * {@link com.intuit.tools.imat.IScriptLauncher#runScript()} is called.
	 * 
	 * @param properties
	 */
	public void setProperties(LinkedHashMap<String, String> properties);

	/**
	 * Run the script.
	 * 
	 * @return a non-zero positive integer when the script exits abnormally, and
	 *         zero when it exits normally.
	 */
	public ExitStatus runScript();
}
