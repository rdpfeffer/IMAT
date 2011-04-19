package com.intuit.ginsu.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ginsu.BaseTest;

public class PathAnalyzerTest extends BaseTest{

	private static final String PLACEHOLDER_FILE = "placeholder.txt";
	private PathAnalyzer pathAnalyzer;
	private ByteArrayOutputStream outputStream;
	private File tempFile;
	
	@BeforeMethod
	public void beforeMethod() 
	{
		this.outputStream = new ByteArrayOutputStream();
		this.pathAnalyzer = new PathAnalyzer(new PrintWriter(outputStream));
		
		try {
			this.tempFile = File.createTempFile("placeholder", ".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void afterMethod() 
	{
		this.tempFile.delete();
	}

	@Test
	public void testRelativePathFromChildToDescendant() 
	{
		File fromPath = this.getTestResourceAsFile("pathTest"+File.separator
				+"grandfather"+File.separator
				+"father"+File.separator
				+"child"+File.separator
				+PLACEHOLDER_FILE);
		File toPath = this.getTestResourceAsFile("pathTest"+File.separator
				+"grandfather"+File.separator
				+PLACEHOLDER_FILE);
		
		String relativePath = this.pathAnalyzer.getRelativePath(fromPath, toPath);
		
		assert relativePath.equals("../../placeholder.txt") : "We did not get the relative path" +
				"we expected. Instead we got: " + relativePath;
	}
	
	@Test
	public void testRelativePathFromDescendantToChild() 
	{
		File toPath = this.getTestResourceAsFile("pathTest"+File.separator
				+"grandfather"+File.separator
				+"father"+File.separator
				+"child"+File.separator
				+PLACEHOLDER_FILE);
		File fromPath = this.getTestResourceAsFile("pathTest"+File.separator
				+"grandfather"+File.separator
				+PLACEHOLDER_FILE);
		
		String relativePath = this.pathAnalyzer.getRelativePath(fromPath, toPath);
		
		assert relativePath.equals("father/child/placeholder.txt") : "We did not get the relative path" +
				"we expected. Instead we got: " + relativePath;
	}
	
	@Test
	public void testRelativePathFromCousinToCousin() {
		File fromPath = this.getTestResourceAsFile("pathTest"+File.separator
				+"grandfather"+File.separator
				+"father"+File.separator
				+"child"+File.separator
				+PLACEHOLDER_FILE);
		File toPath = this.getTestResourceAsFile("pathTest"+File.separator
				+"grandfather"+File.separator
				+"uncle"+File.separator
				+"cousin"+File.separator
				+PLACEHOLDER_FILE);
		
		String relativePath = this.pathAnalyzer.getRelativePath(fromPath, toPath);
		
		assert relativePath.equals("../../uncle/cousin/placeholder.txt") : "We did not get the relative path" +
				"we expected. Instead we got: " + relativePath;  
	}
	
	@Test
	public void testRelativePathFromNephewToGreatUncle() {
		File fromPath = this.getTestResourceAsFile("pathTest"+File.separator
				+"grandfather"+File.separator
				+"father"+File.separator
				+"child"+File.separator
				+PLACEHOLDER_FILE);
		File toPath = this.getTestResourceAsFile("pathTest"+File.separator
				+"greatUncle"+File.separator
				+PLACEHOLDER_FILE);
		
		String relativePath = this.pathAnalyzer.getRelativePath(fromPath, toPath);
		
		assert relativePath.equals("../../../greatUncle/placeholder.txt") : "We did not get the relative path" +
				"we expected. Instead we got: " + relativePath;  
	}
	
	@Test
	public void testRelativePathFromPathToSibling() {
		File fromPath = this.getTestResourceAsFile("pathTest"+File.separator
				+"grandfather"+File.separator
				+"father"+File.separator
				+"child"+File.separator
				+PLACEHOLDER_FILE);
		File toPath = this.getTestResourceAsFile("pathTest"+File.separator
				+"grandfather"+File.separator
				+"father"+File.separator
				+"child"+File.separator
				+PLACEHOLDER_FILE);
		
		String relativePath = this.pathAnalyzer.getRelativePath(fromPath, toPath);
		
		assert relativePath.equals("") : "We did not get the relative path" +
				"we expected. Instead we got: " + relativePath;  
	}
	
	@Test
	public void testRelativePathThroughRootDir() throws IOException 
	{
		File fromPath = this.getTestResourceAsFile("pathTest"+File.separator
				+"grandfather"+File.separator
				+"father"+File.separator
				+"child"+File.separator
				+PLACEHOLDER_FILE);
		
		String relativePath = this.pathAnalyzer.getRelativePath(fromPath, this.tempFile);
		
		assert relativePath.contains(this.tempFile.getCanonicalPath()) : "When going through the root dir" +
				" we did not get the full path. Instead we got: " + relativePath; 
		assert relativePath.contains("../") : "When going through the root dir, we did not see any relative paths";
	}
	
}
