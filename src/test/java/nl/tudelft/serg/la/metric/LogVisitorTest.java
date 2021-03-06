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
import org.repodriller.plugin.jdt.JDTRunner;

import nl.tudelft.serg.la.JavaFile;
import nl.tudelft.serg.la.LogLevel;
import nl.tudelft.serg.la.LogStatement;

public class LogVisitorTest {

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
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
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
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		Assert.assertEquals(7, result1.totalLogs());
		Assert.assertEquals(7, result2.totalLogs());
	}
	
	@Test
	public void shouldIgnoreLogWhenNotLogEvenThoughNameIsLog_2() throws IOException {
		String path = path("logdensity", "2");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		Assert.assertEquals(0, result1.totalLogs());
	}

	@Test
	public void shouldUnderstandMapOfExceptions_14() throws IOException {
		String path = path("logdensity", "14");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		Assert.assertEquals(1, result1.totalLogs());
		Assert.assertTrue(result1.getAllLogs().get(0).getMessage().hasException());
		Assert.assertEquals("Map<java.lang.String Errors>", result1.getAllLogs().get(0).getMessage().getExceptionType());
	}

	@Test
	public void loggingViaMethod_15() throws IOException {
		String path = path("logdensity", "15");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		Assert.assertEquals(1, result1.totalLogs());
		Assert.assertEquals(11, result1.getAllLogs().get(0).getLineNumber());
	}

	@Test
	public void shouldNotCrashWithSomeConstructions_3() throws IOException {
		String path = path("logdensity", "3");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		Assert.assertEquals(0, result1.totalLogs());
	}
	
	
	
	@Test
	public void shouldGetThePositionOfTheLog_6() throws IOException {
		String path = path("logdensity", "6");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));

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
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		List<LogStatement> allLogs = result1.getAllLogs();
		Assert.assertEquals(1, allLogs.size());
		Assert.assertEquals("for", allLogs.get(0).getPosition());
	}

	@Test
	public void constructorsAndInitializers_11() throws IOException {
		String path = path("logdensity", "11");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		List<LogStatement> allLogs = result1.getAllLogs();
		Assert.assertEquals(2, allLogs.size());
		Assert.assertEquals("initializer", allLogs.get(0).getPosition());
		Assert.assertEquals("constructor", allLogs.get(1).getPosition());
	}

	@Test
	public void lineNumbers_8() throws IOException {
		String path = path("logdensity", "8");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
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
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		List<LogStatement> allLogs = result1.getAllLogs();
		Assert.assertEquals(2, allLogs.size());
		
		Assert.assertEquals(2, allLogs.get(0).getMessage().getQtyOfStrings());
		Assert.assertEquals(11, allLogs.get(0).getMessage().getStringsLength());
		Assert.assertEquals(2, allLogs.get(0).getMessage().getQtyOfVariables());
		Assert.assertFalse(allLogs.get(0).getMessage().hasException());
		
		Assert.assertEquals(4, allLogs.get(1).getMessage().getQtyOfStrings());
		Assert.assertEquals(20, allLogs.get(1).getMessage().getStringsLength());
		Assert.assertEquals(3, allLogs.get(1).getMessage().getQtyOfVariables());
		Assert.assertTrue(allLogs.get(1).getMessage().hasException());
	}

	@Test
	public void mightBeMoreThanOneVariable_CodeFromActivemq_12() throws IOException {
		String path = path("logdensity", "12");
		JavaFile result1 = new JavaFile(path + "/TransportConnection.java", 100);
		javaFilesRepo.put(path + "/TransportConnection.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		List<LogStatement> allLogs = result1.getAllLogs();
		Assert.assertEquals(45, allLogs.size());
	}

	@Test
	public void logsViaConstructor_CodeFromHttpCore_13() throws IOException {
		String path = path("logdensity", "13");
		JavaFile result1 = new JavaFile(path + "/LoggingIOSession.java", 100);
		javaFilesRepo.put(path + "/LoggingIOSession.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		List<LogStatement> allLogs = result1.getAllLogs();
		Assert.assertEquals(9, allLogs.size());
	}

	@Test
	public void extractException() throws IOException {
		String path = path("logdensity", "10");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		List<LogStatement> allLogs = result1.getAllLogs();
		Assert.assertEquals(1, allLogs.size());
		Assert.assertEquals(2, allLogs.get(0).getMessage().getQtyOfStrings());
		Assert.assertEquals(11, allLogs.get(0).getMessage().getStringsLength());
		Assert.assertEquals(2, allLogs.get(0).getMessage().getQtyOfVariables());
		Assert.assertTrue(allLogs.get(0).getMessage().hasException());
		Assert.assertEquals("IOException", allLogs.get(0).getMessage().getExceptionType());
	}
	

	@Test
	public void logMethodWithPriority_16() throws IOException {
		String path = path("logdensity", "16");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		Assert.assertEquals(3, result1.totalLogs());
		
		Assert.assertEquals(LogLevel.DEBUG, result1.getAllLogs().get(0).getLevel());
		Assert.assertEquals(LogLevel.INFO, result1.getAllLogs().get(1).getLevel());
		Assert.assertEquals(LogLevel.TRACE, result1.getAllLogs().get(2).getLevel());
		Assert.assertEquals("java.lang.Exception", result1.getAllLogs().get(2).getMessage().getExceptionType());
	}

	@Test
	public void ignoreMethodsThatYouDoNotUnderstand_17() throws IOException {
		String path = path("logdensity", "17");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		Assert.assertEquals(0, result1.totalLogs());
	}

	@Test
	public void countMethodInvocationInLogMessage_18() throws IOException {
		String path = path("logdensity", "18");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		Assert.assertEquals(1, result1.totalLogs());
		Assert.assertEquals(1, result1.getAllLogs().get(0).getMessage().getQtyOfMethodInvocations());
	}
	
	@Test
	public void javaUtilLogging_19() throws IOException {
		String path = path("logdensity", "19");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		Assert.assertEquals(1, result1.totalLogs());
		Assert.assertEquals(LogLevel.FINE, result1.getAllLogs().get(0).getLevel());
		Assert.assertEquals(3, result1.getAllLogs().get(0).getMessage().getStringsLength());
		Assert.assertEquals(1, result1.getAllLogs().get(0).getMessage().getQtyOfVariables());
	}

	@Test
	public void javaUtilLoggingNoFullLevelName_21() throws IOException {
		String path = path("logdensity", "21");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		Assert.assertEquals(1, result1.totalLogs());
		Assert.assertEquals(LogLevel.FINER, result1.getAllLogs().get(0).getLevel());
	}
	
	
	@Test
	public void javaUtilLoggingInSuperClass_20() throws IOException {
		String path = path("logdensity", "20");
		JavaFile result1 = new JavaFile(path + "/Test1.java", 100);
		javaFilesRepo.put(path + "/Test1.java", result1);
		
		new JDTRunner(true, true).run(path, () -> Arrays.asList(new LogVisitor(javaFilesRepo)));
		
		Assert.assertEquals(1, result1.totalLogs());
		Assert.assertEquals(LogLevel.DEBUG, result1.getAllLogs().get(0).getLevel());
		Assert.assertEquals(2, result1.getAllLogs().get(0).getMessage().getStringsLength());
	}
	
	
}
