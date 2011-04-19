package com.intuit.ginsu.commands;

import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CommandInitEnvTest {

	@BeforeMethod
	public void beforeMethod() {
	}

	@AfterMethod
	public void afterMethod() {
	}

	@SuppressWarnings("static-access")
	@BeforeClass
	public void beforeClass() {
		this.getClass().getClassLoader()
				.getSystemResourceAsStream("test.properties");
		File myFile = new File(".");
		System.out.println(myFile.toString());
	}

	@AfterClass
	public void afterClass() {
	}

	@Test
	public void testRun() {

	}

	@Test
	public void testCleanUp() {

	}

}
