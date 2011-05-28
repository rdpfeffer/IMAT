package com.intuit.tools.imat.cli;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.beust.jcommander.IDefaultProvider;
import com.beust.jcommander.JCommander;
import com.google.inject.Injector;
import com.intuit.tools.imat.AppContext;
import com.intuit.tools.imat.ICommandDispatchService;
import com.intuit.tools.imat.IConfigurationService;
import com.intuit.tools.imat.IInputHandlingService;
import com.intuit.tools.imat.cli.App;
import com.intuit.tools.imat.cli.CommandLineParsingService;
import com.intuit.tools.imat.cli.MainArgs;
import com.intuit.tools.imat.cli.UsagePrinter;
import com.intuit.tools.imat.commands.CommandRunTests;
import com.intuit.tools.imat.commands.SupportedCommandCollection;
import com.intuit.tools.imat.commands.SynchronousCommandDispatchService;
import com.intuit.tools.imat.config.PropertyFileConfigurationService;

public class IMATCLIModuleTest {

	public Injector injector;

	@BeforeClass
	public void setupInjector() {
		App.initAppContext(new String[] { MainArgs.HOME, "." });
		this.injector = AppContext.INSTANCE.getInjector();
	}

	@Test
	public void testAppNameAnnotationWithJCommanderProvider() {

		StringBuilder stringBuilder = new StringBuilder();
		JCommander jCommander = injector.getInstance(JCommander.class);
		SupportedCommandCollection supportedCommands = injector
				.getInstance(SupportedCommandCollection.class);
		jCommander.addCommand(CommandRunTests.NAME,
				supportedCommands.get(CommandRunTests.NAME));
		jCommander.usage(stringBuilder);
		assert stringBuilder.toString().contains("imat");

		// Just for safe measure, also make sure that we are always getting a
		// new instance of JCommander when we ask for it.
		assert jCommander != injector.getInstance(JCommander.class);
	}

	@Test
	public void testParsingServiceBinding() {
		IInputHandlingService inputHandlingService = injector
				.getInstance(IInputHandlingService.class);
		assert inputHandlingService instanceof CommandLineParsingService;

		// the IInputParsingService is not bound as a singleton
		// so it should not equal another instance
		IInputHandlingService secondIInputParsingService = injector
				.getInstance(IInputHandlingService.class);
		assert inputHandlingService != secondIInputParsingService;
	}

	@Test
	public void testConfigurationServiceSingletonBinding() {
		IConfigurationService configService = injector
				.getInstance(IConfigurationService.class);
		assert configService instanceof PropertyFileConfigurationService;

		// the IInputParsingService is not bound as a singleton
		// so it should not equal another instance
		IConfigurationService secondConfigService = injector
				.getInstance(IConfigurationService.class);
		assert configService == secondConfigService;
	}

	@Test
	public void testCommandDispatchServiceBinding() {
		ICommandDispatchService inputParsingService = injector
				.getInstance(ICommandDispatchService.class);
		assert inputParsingService instanceof SynchronousCommandDispatchService;

		// the ICommandDispatchService is not bound as a singleton
		// so it should not equal another instance
		ICommandDispatchService secondICommandDispatchService = injector
				.getInstance(ICommandDispatchService.class);
		assert inputParsingService != secondICommandDispatchService;
	}

	@Test
	public void testIDefaultProviderIsConfigurationService() {

		// Since we mapped the IDefault provider to the
		// PropertyFileConfigurationService
		// we should get that instance back.
		IDefaultProvider defaultProvider = injector
				.getInstance(IDefaultProvider.class);
		assert defaultProvider instanceof PropertyFileConfigurationService;

		// Further validation that the Configuration Service Implementation is a
		// singleton.
		IConfigurationService secondConfigService = injector
				.getInstance(IConfigurationService.class);
		assert defaultProvider == secondConfigService;
	}

	@Test
	public void testOutputStreamBinding() {
		assert injector.getInstance(OutputStream.class) instanceof PrintStream;
	}

	@Test
	public void testMainArgsBinding() {
		assert injector.getInstance(MainArgs.class) instanceof MainArgs;
	}

	@Test
	public void testPrintWriterProvider() {
		assert injector.getInstance(PrintWriter.class) instanceof PrintWriter;
	}

	@Test
	public void testSupportedCommandProvider() {
		SupportedCommandCollection collection = injector
				.getInstance(SupportedCommandCollection.class);
		assert collection.size() > 0;
		assert collection.get(UsagePrinter.NAME) instanceof UsagePrinter;
	}
}
