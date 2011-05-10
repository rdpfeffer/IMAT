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
package com.intuit.ginsu.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

/**
 * @author rpfeffer
 * @dateCreated Mar 26, 2011
 * 
 *              This is a BindingAnnotation for Guice. It tells the injection
 *              framework what value to use when something needs to inject the
 *              app's name.
 * 
 */
@BindingAnnotation
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER, METHOD })
public @interface AppName {
}
