package nl.tudelft.serg.la.metric;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import org.junit.Assert;

public class LOCCalculatorTest {

	@Test
	public void countNumberOfLinesInAString() {
		InputStream stream = toInputStream("l1\nl2\nl3\nl4\nl5");

		int loc = new LOCCalculator().calculate(stream);
		
		Assert.assertEquals(5, loc);
	}

	@Test
	public void understandSlashRSlashN() {
		InputStream stream = toInputStream("l1\r\nl2\r\nl3\r\nl4\r\nl5");
		
		int loc = new LOCCalculator().calculate(stream);
		
		Assert.assertEquals(5, loc);
	}

	@Test
	public void ignoreEmptyLines() {
		InputStream stream = toInputStream("l1\r\n\n\n\t\tl2\t\t\r\n\n\nl3\r\nl4\r\nl5\n\n  \t\n \n");
		
		int loc = new LOCCalculator().calculate(stream);
		
		Assert.assertEquals(5, loc);
	}

	private InputStream toInputStream(String sourceCode) {
		// snippet from http://stackoverflow.com/questions/782178/how-do-i-convert-a-string-to-an-inputstream-in-java by  3 revs, 3 users 67%
		InputStream stream = new ByteArrayInputStream(sourceCode.getBytes(StandardCharsets.UTF_8));
		return stream;
	}
}
