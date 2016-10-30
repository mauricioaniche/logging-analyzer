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
		file.log(new LogStatement(LogLevel.INFO, "method", 1));
		file.log(new LogStatement(LogLevel.WARN, "method", 2));
		file.log(new LogStatement(LogLevel.WARN, "method", 3));
		file.log(new LogStatement(LogLevel.DEBUG, "method", 4));
		file.log(new LogStatement(LogLevel.DEBUG, "method", 5));
		file.log(new LogStatement(LogLevel.DEBUG, "method", 6));
		file.log(new LogStatement(LogLevel.FATAL, "method", 7));
		file.log(new LogStatement(LogLevel.FATAL, "method", 8));
		file.log(new LogStatement(LogLevel.FATAL, "method", 9));
		file.log(new LogStatement(LogLevel.FATAL, "method", 10));
		file.log(new LogStatement(LogLevel.TRACE, "method", 11));
		file.log(new LogStatement(LogLevel.TRACE, "method", 12));
		file.log(new LogStatement(LogLevel.TRACE, "method", 13));
		file.log(new LogStatement(LogLevel.TRACE, "method", 14));
		file.log(new LogStatement(LogLevel.TRACE, "method", 15));
		file.log(new LogStatement(LogLevel.ERROR, "method", 16));
		file.log(new LogStatement(LogLevel.ERROR, "method", 17));
		file.log(new LogStatement(LogLevel.ERROR, "method", 18));
		file.log(new LogStatement(LogLevel.ERROR, "method", 19));
		file.log(new LogStatement(LogLevel.ERROR, "method", 20));
		file.log(new LogStatement(LogLevel.ERROR, "method", 21));
		return file;
	}
}
