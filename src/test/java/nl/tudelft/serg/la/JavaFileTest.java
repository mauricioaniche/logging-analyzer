package nl.tudelft.serg.la;

import org.junit.Assert;
import org.junit.Test;

public class JavaFileTest {

	@Test
	public void calculateLogDensity() {
		JavaFile file = fileWithManyLogs();
		
		Assert.assertEquals(0.21, file.logDensity(), 0.0001);
	}

	@Test
	public void calculateLogDensityWhenNoLog() {
		JavaFile file = new JavaFile("SomeClass.java", 100);
		
		Assert.assertEquals(0, file.logDensity(), 0.0001);
	}
	
	@Test
	public void calculateAverageLoggingLevel() {
		JavaFile file = fileWithManyLogs();
		
		// weight=76, logs=21 => 76/21
		Assert.assertEquals(76/21.0, file.averageLoggingLevel(), 0.0001);
	}
	
	private JavaFile fileWithManyLogs() {
		JavaFile file = new JavaFile("SomeClass.java", 100);
		file.log("info");
		file.log("warn");
		file.log("warn");
		file.log("debug");
		file.log("debug");
		file.log("debug");
		file.log("fatal");
		file.log("fatal");
		file.log("fatal");
		file.log("fatal");
		file.log("trace");
		file.log("trace");
		file.log("trace");
		file.log("trace");
		file.log("trace");
		file.log("error");
		file.log("error");
		file.log("error");
		file.log("error");
		file.log("error");
		file.log("error");
		return file;
	}
}
