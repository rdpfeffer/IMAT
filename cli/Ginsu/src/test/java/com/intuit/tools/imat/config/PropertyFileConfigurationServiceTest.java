package com.intuit.tools.imat.config;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.tools.imat.AppContext;
import com.intuit.tools.imat.IApplicationResourceService;
import com.intuit.tools.imat.IProjectResourceService;
import com.intuit.tools.imat.MisconfigurationException;
import com.intuit.tools.imat.ProjectConfigurationNotFoundException;
import com.intuit.tools.imat.config.PropertyFileConfigurationService;

public class PropertyFileConfigurationServiceTest {

	private static String APP_CONFIG_FILE = "cliConfig.properties";
	private static String PROJECT_CONFIG_FILE = "project.properties";
	private IApplicationResourceService mockAppResourceService;
	private IProjectResourceService mockProjectResourceService;
	private Properties appProperties = new Properties();
	private Properties projectProperties = new Properties();

	@BeforeClass
	public void setupProperties() {
		// for an exception not to be thrown when loading app properties, we
		// just need one property to be set.
		appProperties.setProperty("foo", "bar");
		projectProperties.setProperty("monkey", "gorilla");
	}

	@AfterMethod
	public void clearAppContext() {
		AppContext.INSTANCE.clear();
	}

	@AfterMethod
	public void resetMocks() {
		EasyMock.reset(mockAppResourceService);
		EasyMock.reset(mockProjectResourceService);
	}

	@BeforeMethod
	public void createMocks() {
		mockAppResourceService = EasyMock
				.createMock(IApplicationResourceService.class);
		mockProjectResourceService = EasyMock
				.createMock(IProjectResourceService.class);
	}

	@Test
	public void testLoadConfigExpectingProjectWithProject()
			throws MisconfigurationException,
			ProjectConfigurationNotFoundException {
		// expect a call to get the config files
		EasyMock.expect(
				mockAppResourceService.getAppProperties(APP_CONFIG_FILE))
				.andReturn(appProperties);
		EasyMock.expect(
				mockProjectResourceService
						.getProjectProperties(PROJECT_CONFIG_FILE)).andReturn(
				projectProperties);
		EasyMock.replay(mockAppResourceService);
		EasyMock.replay(mockProjectResourceService);
		PropertyFileConfigurationService configService = new PropertyFileConfigurationService(
				mockAppResourceService, mockProjectResourceService,
				APP_CONFIG_FILE, EasyMock.createNiceMock(Logger.class));
		configService.loadConfiguration(true);
		EasyMock.verify(mockAppResourceService);
		EasyMock.verify(mockProjectResourceService);
		assert AppContext.INSTANCE.getProperty("foo") == "bar" : "Application properties were not set.";
		assert AppContext.INSTANCE.getProperty("monkey") == "gorilla" : "Project properties were not set.";
	}

	@Test(expectedExceptions = ProjectConfigurationNotFoundException.class)
	public void testLoadConfigExpectingProjectWithoutProject()
			throws MisconfigurationException,
			ProjectConfigurationNotFoundException {
		// expect a call to get the config files
		EasyMock.expect(
				mockAppResourceService.getAppProperties(APP_CONFIG_FILE))
				.andReturn(appProperties);
		EasyMock.expect(
				mockProjectResourceService
						.getProjectProperties(PROJECT_CONFIG_FILE)).andReturn(
				new Properties());
		EasyMock.replay(mockAppResourceService);
		EasyMock.replay(mockProjectResourceService);
		PropertyFileConfigurationService configService = new PropertyFileConfigurationService(
				mockAppResourceService, mockProjectResourceService,
				APP_CONFIG_FILE, EasyMock.createNiceMock(Logger.class));
		configService.loadConfiguration(true);
	}

	@Test
	public void testLoadConfigExpectingNoProjectWitProject()
			throws MisconfigurationException,
			ProjectConfigurationNotFoundException {
		// expect a call to get the config files
		EasyMock.expect(
				mockAppResourceService.getAppProperties(APP_CONFIG_FILE))
				.andReturn(appProperties);
		EasyMock.expect(
				mockProjectResourceService
						.getProjectProperties(PROJECT_CONFIG_FILE)).andReturn(
				new Properties());

		// Expect at least one call
		Logger mockLogger = EasyMock.createMock(Logger.class);
		mockLogger.debug(EasyMock.contains("Loading"));
		EasyMock.expectLastCall().times(3);
		mockLogger.debug(EasyMock.contains("Loaded"));
		mockLogger
				.debug(EasyMock
						.contains("unable to load any properties from the project configuration file"));

		EasyMock.replay(mockAppResourceService);
		EasyMock.replay(mockProjectResourceService);
		EasyMock.replay(mockLogger);
		PropertyFileConfigurationService configService = new PropertyFileConfigurationService(
				mockAppResourceService, mockProjectResourceService,
				APP_CONFIG_FILE,
				Logger.getLogger(PropertyFileConfigurationService.class));
		configService.loadConfiguration(false);
		EasyMock.verify(mockAppResourceService);
		EasyMock.verify(mockProjectResourceService);
		assert AppContext.INSTANCE.getProperty("foo") == "bar" : "Application properties were not set.";
		assert AppContext.INSTANCE.getProperty("monkey").isEmpty() : "Project property was not empty: "
				+ AppContext.INSTANCE.getProperty("monkey");
	}

	@Test
	public void testLoadConfigExpectingNoProjectWithoutProject()
			throws MisconfigurationException,
			ProjectConfigurationNotFoundException {
		// expect a call to get the config files
		EasyMock.expect(
				mockAppResourceService.getAppProperties(APP_CONFIG_FILE))
				.andReturn(appProperties);
		EasyMock.expect(
				mockProjectResourceService
						.getProjectProperties(PROJECT_CONFIG_FILE)).andReturn(
				new Properties());
		EasyMock.replay(mockAppResourceService);
		EasyMock.replay(mockProjectResourceService);
		PropertyFileConfigurationService configService = new PropertyFileConfigurationService(
				mockAppResourceService, mockProjectResourceService,
				APP_CONFIG_FILE,
				Logger.getLogger(PropertyFileConfigurationService.class));
		configService.loadConfiguration(false);
		EasyMock.verify(mockAppResourceService);
		EasyMock.verify(mockProjectResourceService);
		assert AppContext.INSTANCE.getProperty("foo") == "bar" : "Application properties were not set.";
		assert AppContext.INSTANCE.getProperty("monkey").isEmpty() : "Project property was not empty: "
				+ AppContext.INSTANCE.getProperty("monkey");
	}

	@Test(expectedExceptions = MisconfigurationException.class)
	public void testLoadConfigWithoutAppConfg()
			throws MisconfigurationException,
			ProjectConfigurationNotFoundException {
		EasyMock.expect(
				mockAppResourceService.getAppProperties(APP_CONFIG_FILE))
				.andReturn(new Properties());
		EasyMock.expect(
				mockProjectResourceService
						.getProjectProperties(PROJECT_CONFIG_FILE)).andReturn(
				projectProperties);
		EasyMock.replay(mockAppResourceService);
		EasyMock.replay(mockProjectResourceService);
		PropertyFileConfigurationService configService = new PropertyFileConfigurationService(
				mockAppResourceService, mockProjectResourceService,
				APP_CONFIG_FILE, EasyMock.createNiceMock(Logger.class));
		configService.loadConfiguration(true);
	}
}
