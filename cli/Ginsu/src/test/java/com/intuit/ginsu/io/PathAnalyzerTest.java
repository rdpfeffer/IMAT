package com.intuit.ginsu.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ginsu.cli.BaseFunctionalTest;

public class PathAnalyzerTest extends BaseFunctionalTest {

	private static final String PLACEHOLDER_FILE = "placeholder.txt";
	private PathAnalyzer pathAnalyzer;
	private URI tempFile;

	/**
	 * @TODO DocMe
	 */
	@BeforeMethod
	public void beforeMethod() {
		new ByteArrayOutputStream();
		this.pathAnalyzer = new PathAnalyzer(
				Logger.getLogger(PathAnalyzer.class));

		try {
			this.tempFile = convertToURI(File.createTempFile("placeholder",
					".txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @TODO DocMe
	 */
	@AfterMethod
	public void afterMethod() {
		File file = new File(this.tempFile);
		file.delete();
	}

	/**
	 * @TODO DocMe
	 */
	@Test()
	public void testRelativePathFromChildToDescendant() {
		URI toPath = convertToURI(this.getTestResourceAsFile("pathTest"
				+ File.separator + "grandfather" + File.separator
				+ PLACEHOLDER_FILE));
		URI fromPath = convertToURI(this.getTestResourceAsFile("pathTest"
				+ File.separator + "grandfather" + File.separator + "father"
				+ File.separator + "child" + File.separator + PLACEHOLDER_FILE));
		String relativePath = this.pathAnalyzer.getRelativePath(fromPath,
				toPath);
		assert relativePath.equals("../../placeholder.txt") : "We did not get the relative path"
				+ "we expected. Instead we got: " + relativePath;
	}

	/**
	 * @TODO DocMe
	 */
	@Test
	public void testRelativePathFromDescendantToChild() {
		URI toPath = convertToURI(this.getTestResourceAsFile("pathTest"
				+ File.separator + "grandfather" + File.separator + "father"
				+ File.separator + "child" + File.separator + PLACEHOLDER_FILE));
		URI fromPath = convertToURI(this.getTestResourceAsFile("pathTest"
				+ File.separator + "grandfather" + File.separator
				+ PLACEHOLDER_FILE));
		String relativePath = this.pathAnalyzer.getRelativePath(fromPath,
				toPath);
		assert relativePath.equals("father/child/placeholder.txt") : "We did not get the relative path"
				+ "we expected. Instead we got: " + relativePath;
	}

	@Test
	public void testRelativePathFromCousinToCousin() {
		URI fromPath = convertToURI(this.getTestResourceAsFile("pathTest"
				+ File.separator + "grandfather" + File.separator + "father"
				+ File.separator + "child" + File.separator + PLACEHOLDER_FILE));
		URI toPath = convertToURI(this
				.getTestResourceAsFile("pathTest" + File.separator
						+ "grandfather" + File.separator + "uncle"
						+ File.separator + "cousin" + File.separator
						+ PLACEHOLDER_FILE));
		String relativePath = this.pathAnalyzer.getRelativePath(fromPath,
				toPath);
		assert relativePath.equals("../../uncle/cousin/placeholder.txt") : "We did not get the relative path"
				+ "we expected. Instead we got: " + relativePath;
	}

	/**
	 * @TODO DocMe
	 */
	@Test
	public void testRelativePathFromNephewToGreatUncle() {
		URI fromPath = convertToURI(this.getTestResourceAsFile("pathTest"
				+ File.separator + "grandfather" + File.separator + "father"
				+ File.separator + "child" + File.separator + PLACEHOLDER_FILE));
		URI toPath = convertToURI(this.getTestResourceAsFile("pathTest"
				+ File.separator + "greatUncle" + File.separator
				+ PLACEHOLDER_FILE));
		String relativePath = this.pathAnalyzer.getRelativePath(fromPath,
				toPath);
		assert relativePath.equals("../../../greatUncle/placeholder.txt") : "We did not get the relative path"
				+ "we expected. Instead we got: " + relativePath;
	}

	/**
	 * @TODO DocMe
	 */
	@Test
	public void testRelativePathFromPathToSibling() {
		URI fromPath = convertToURI(this.getTestResourceAsFile("pathTest"
				+ File.separator + "grandfather" + File.separator + "father"
				+ File.separator + "child" + File.separator + PLACEHOLDER_FILE));
		URI toPath = convertToURI(this.getTestResourceAsFile("pathTest"
				+ File.separator + "grandfather" + File.separator + "father"
				+ File.separator + "child" + File.separator + PLACEHOLDER_FILE));
		String relativePath = this.pathAnalyzer.getRelativePath(fromPath,
				toPath);
		assert relativePath.equals("") : "We did not get the relative path"
				+ "we expected. Instead we got: " + relativePath;
	}

	/**
	 * @TODO DocMe
	 * @throws IOException
	 */
	@Test
	public void testRelativePathThroughRootDir() throws IOException {
		URI fromPath = convertToURI(this.getTestResourceAsFile("pathTest"
				+ File.separator + "grandfather" + File.separator + "father"
				+ File.separator + "child" + File.separator + PLACEHOLDER_FILE));

		String relativePath = this.pathAnalyzer.getRelativePath(fromPath,
				this.tempFile);
		assert relativePath.contains(this.tempFile.getPath()) : "When going through the root dir we did not get the full path. Instead we got: "
				+ relativePath;
		assert relativePath.contains("../") : "When going through the root dir, we did not see any relative paths";
	}

	/**
	 * @TODO DocMe
	 * @param file
	 * @return
	 */
	private URI convertToURI(File file) {
		try {
			file = file.getCanonicalFile();
		} catch (IOException e) {
			assert false : e.getMessage();
		}
		return file.toURI();
	}

}
