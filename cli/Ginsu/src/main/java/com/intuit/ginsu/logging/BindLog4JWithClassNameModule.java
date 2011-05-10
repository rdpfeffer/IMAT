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
package com.intuit.ginsu.logging;

import static com.google.common.collect.Sets.filter;
import static com.google.inject.matcher.Matchers.any;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.ProvisionException;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

/**
 * @author Adrian Cole ( adrian@jclouds.org ) With permission, taken from:
 *         http://pastie.org/558544
 * @dateCreated Apr 19, 2011
 * 
 * 
 *              <p>
 *              Any class injected from a guice {@link Injector injector} using
 *              this module will have a log4j Logger if either of the following
 *              conditions are met.
 *              </p>
 * 
 *              <ol>
 *              <li>A constructor is present with a Logger parameter annotated
 *              with @Inject</li>
 * 
 *              <pre>
 * public static class A {
 * 	private final Logger logger;
 * 
 * 	&#064;Inject
 * 	public A(Logger logger) {
 * 		this.logger = logger;
 * 	}
 * }
 * </pre>
 * 
 *              <li>A field is present of type Logger annotated with @Inject</li>
 * 
 *              <pre>
 * public static class B {
 * 	&#064;Inject
 * 	private Logger logger;
 * }
 * </pre>
 * 
 *              </ol>
 * 
 *              <h3>Example Usage</h3>
 * 
 *              <pre>
 * Injector i = Guice.createInjector(new BindLog4JLoggersNameClassNameModule());
 * A instance = i.getInstance(A.class);
 * </pre>
 * 
 */
public class BindLog4JWithClassNameModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bindListener(any(), new BindLoggers());
	}

	/**
	 * @return
	 */
	@Provides
	Logger provideDefaultLoggerToSatisfyGuiceProvisionCheck() {
		return Logger.getRootLogger();
	}

	@VisibleForTesting
	static class BindLoggers implements TypeListener {

		static class AssignLoggerToField<I> implements InjectionListener<I> {
			private final Logger logger;
			private final Field field;

			/**
			 * @param logger
			 * @param field
			 */
			AssignLoggerToField(Logger logger, Field field) {
				this.logger = logger;
				this.field = field;
			}

			/* (non-Javadoc)
			 * @see com.google.inject.spi.InjectionListener#afterInjection(java.lang.Object)
			 */
			public void afterInjection(I injectee) {
				try {
					field.setAccessible(true);
					field.set(injectee, logger);
				} catch (IllegalAccessException e) {
					throw new ProvisionException(e.getMessage(), e);
				}
			}
		}

		/* (non-Javadoc)
		 * @see com.google.inject.spi.TypeListener#hear(com.google.inject.TypeLiteral, com.google.inject.spi.TypeEncounter)
		 */
		public <I> void hear(TypeLiteral<I> injectableType,
				TypeEncounter<I> encounter) {
			Class<? super I> type = injectableType.getRawType();
			Set<Field> loggerFields;
			if (hasInjectableConstructorWithLoggerParameter(type))
				loggerFields = getAllLoggerFieldsFrom(type);
			else
				loggerFields = getInjectableLoggerFieldsFrom(type);

			if (loggerFields.size() == 0)
				return;
			// assign the correct scope to the logger based on the classname
			Logger logger = getCorrectLoggerForType(type);

			assignLoggerAfterInjection(encounter, loggerFields, logger);
		}

		/**
		 * @param type
		 * @return
		 */
		@VisibleForTesting
		static Set<Field> getInjectableLoggerFieldsFrom(Class<?> type) {
			return onlyInjectableFields(onlyLoggerFields(allFieldsFrom(type)));
		}

		/**
		 * @param type
		 * @return
		 */
		@VisibleForTesting
		static Set<Field> getAllLoggerFieldsFrom(Class<?> type) {
			return onlyLoggerFields(allFieldsFrom(type));
		}

		/**
		 * @param type
		 * @return
		 */
		private static Logger getCorrectLoggerForType(Class<?> type) {
			return Logger.getLogger(type.getName());
		}

		/**
		 * @param <I>
		 * @param encounter
		 * @param loggerFields
		 * @param logger
		 */
		private static <I> void assignLoggerAfterInjection(
				TypeEncounter<I> encounter, Set<Field> loggerFields,
				Logger logger) {
			for (Field field : loggerFields) {
				encounter.register(new AssignLoggerToField<I>(logger, field));
			}
		}

		/**
		 * @param declaredType
		 * @return
		 */
		@VisibleForTesting
		static boolean hasInjectableConstructorWithLoggerParameter(
				Class<?> declaredType) {
			// iterate through class and superclass constructors.
			Class<?> type = declaredType;
			while (type != null) {
				for (Constructor<?> constructor : type.getConstructors()) {
					// only inject guiced constructors
					if (!constructor.isAnnotationPresent(Inject.class))
						continue;
					if (hasLoggerParameter(constructor))
						return true;
				}
				type = type.getSuperclass();
			}
			return false;
		}

		private static boolean hasLoggerParameter(Constructor<?> constructor) {
			return filter(Sets.newHashSet(constructor.getParameterTypes()),
					new Predicate<Class<?>>() {
						public boolean apply(Class<?> input) {
							return input.isAssignableFrom(Logger.class);
						}
					}).size() > 0;
		}

		/**
		 * @param declaredType
		 * @return
		 */
		private static Set<Field> allFieldsFrom(Class<?> declaredType) {
			Set<Field> fields = new HashSet<Field>();
			// find all the fields for this type.
			Class<?> type = declaredType;
			while (type != null) {
				fields.addAll(Arrays.asList(type.getDeclaredFields()));
				type = type.getSuperclass();
			}
			return fields;
		}

		/**
		 * @param fields
		 * @return
		 */
		private static Set<Field> onlyInjectableFields(Set<Field> fields) {
			return filter(fields, new Predicate<Field>() {
				public boolean apply(Field input) {
					return input.isAnnotationPresent(Inject.class);
				}
			});
		}

		/**
		 * @param fields
		 * @return
		 */
		private static Set<Field> onlyLoggerFields(Set<Field> fields) {
			return filter(fields, new Predicate<Field>() {
				public boolean apply(Field input) {
					return input.getType().isAssignableFrom(Logger.class);
				}
			});
		}
	}
}
