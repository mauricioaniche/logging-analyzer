package nl.tudelft.serg.la.metric;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.tudelft.serg.la.JavaFile;
import nl.tudelft.serg.la.LogLevel;
import nl.tudelft.serg.la.LogStatement;
import nl.tudelft.serg.la.jdt.JDTRunner;

public class LogDetectionVisitorTest {

	private Map<String, JavaFile> javaFilesRepo;

	@Before
	public void setUp() throws IOException {
		this.javaFilesRepo = new HashMap<>();
	}

	private String path(String test, String subfolder) throws IOException {
		return new File(this.getClass().getResource("/").getPath() + "../../src/test/java-test-files/" + test + "/" + subfolder).getCanonicalPath();
	}
	
	@Test
	public void shouldCountLogInstructions_1() throws IOException {
		String path = path("logdensity", "1");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		JavaFile result2 = new JavaFile(path + "/Test2.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		javaFilesRepo.put(path + "/Test2.java", result2);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogDetectionVisitor(javaFilesRepo)));
		
		Assert.assertEquals(2, result1.getQtyLogs(LogLevel.INFO));
		Assert.assertEquals(1, result1.getQtyLogs(LogLevel.WARN));
		Assert.assertEquals(1, result1.getQtyLogs(LogLevel.TRACE));
		Assert.assertEquals(1, result1.getQtyLogs(LogLevel.DEBUG));
		Assert.assertEquals(1, result1.getQtyLogs(LogLevel.FATAL));
		Assert.assertEquals(1, result1.getQtyLogs(LogLevel.ERROR));

		Assert.assertEquals(2, result2.getQtyLogs(LogLevel.INFO));
		Assert.assertEquals(1, result2.getQtyLogs(LogLevel.WARN));
		Assert.assertEquals(1, result2.getQtyLogs(LogLevel.TRACE));
		Assert.assertEquals(1, result2.getQtyLogs(LogLevel.DEBUG));
		Assert.assertEquals(1, result2.getQtyLogs(LogLevel.FATAL));
		Assert.assertEquals(1, result2.getQtyLogs(LogLevel.ERROR));
	}

	@Test
	public void shouldWorkForApacheLogAndSLF4J_4() throws IOException {
		String path = path("logdensity", "4");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		JavaFile result2 = new JavaFile(path + "/Test2.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		javaFilesRepo.put(path + "/Test2.java", result2);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogDetectionVisitor(javaFilesRepo)));
		
		Assert.assertEquals(7, result1.totalLogs());
		Assert.assertEquals(7, result2.totalLogs());
	}
	
	@Test
	public void shouldIgnoreLogWhenNotLogEvenThoughNameIsLog_2() throws IOException {
		String path = path("logdensity", "2");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogDetectionVisitor(javaFilesRepo)));
		
		Assert.assertEquals(0, result1.totalLogs());
	}

	@Test
	public void shouldNotCrashWithSomeConstructions_3() throws IOException {
		String path = path("logdensity", "3");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogDetectionVisitor(javaFilesRepo)));
		
		Assert.assertEquals(0, result1.totalLogs());
	}
	
	
	
	@Test
	public void shouldGetThePositionOfTheLog_6() throws IOException {
		String path = path("logdensity", "6");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogDetectionVisitor(javaFilesRepo)));

		List<LogStatement> allLogs = result1.getAllLogs();
		Assert.assertEquals(9, allLogs.size());
		Assert.assertEquals(1, allLogs.stream().filter(l -> l.getPosition().equals("if")).count());
		Assert.assertEquals(1, allLogs.stream().filter(l -> l.getPosition().equals("else")).count());
		Assert.assertEquals(1, allLogs.stream().filter(l -> l.getPosition().equals("for")).count());
		Assert.assertEquals(1, allLogs.stream().filter(l -> l.getPosition().equals("method")).count());
		Assert.assertEquals(1, allLogs.stream().filter(l -> l.getPosition().equals("foreach")).count());
		Assert.assertEquals(2, allLogs.stream().filter(l -> l.getPosition().equals("switch")).count());
		Assert.assertEquals(1, allLogs.stream().filter(l -> l.getPosition().equals("while")).count());
		Assert.assertEquals(1, allLogs.stream().filter(l -> l.getPosition().equals("do-while")).count());
	}
	
	@Test
	public void shouldCaptureTheDeeperStructure_7() throws IOException {
		String path = path("logdensity", "7");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogDetectionVisitor(javaFilesRepo)));
		
		List<LogStatement> allLogs = result1.getAllLogs();
		Assert.assertEquals(1, allLogs.size());
		Assert.assertEquals("for", allLogs.get(0).getPosition());
	}

	@Test
	public void lineNumbers_8() throws IOException {
		String path = path("logdensity", "8");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogDetectionVisitor(javaFilesRepo)));
		
		List<LogStatement> allLogs = result1.getAllLogs();
		Assert.assertEquals(2, allLogs.size());
		Assert.assertTrue(allLogs.stream().anyMatch(l -> l.getLineNumber() == 11));
		Assert.assertTrue(allLogs.stream().anyMatch(l -> l.getLineNumber() == 12));
	}
	
	@Test
	public void extractQtyOfVariables_9() throws IOException {
		String path = path("logdensity", "9");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogDetectionVisitor(javaFilesRepo)));
		
		List<LogStatement> allLogs = result1.getAllLogs();
		Assert.assertEquals(1, allLogs.size());
		Assert.assertEquals(2, allLogs.get(0).getMessage().getQtyOfStrings());
		Assert.assertEquals(11, allLogs.get(0).getMessage().getStringsLength());
		Assert.assertEquals(2, allLogs.get(0).getMessage().getQtyOfVariables());
	}

	@Test
	public void extractException() throws IOException {
		String path = path("logdensity", "10");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogDetectionVisitor(javaFilesRepo)));
		
		List<LogStatement> allLogs = result1.getAllLogs();
		Assert.assertEquals(1, allLogs.size());
		Assert.assertEquals(2, allLogs.get(0).getMessage().getQtyOfStrings());
		Assert.assertEquals(11, allLogs.get(0).getMessage().getStringsLength());
		Assert.assertEquals(2, allLogs.get(0).getMessage().getQtyOfVariables());
		Assert.assertTrue(allLogs.get(0).getMessage().hasException());
		Assert.assertEquals("IOException", allLogs.get(0).getMessage().getExceptionType());
	}
	
	
	
}
