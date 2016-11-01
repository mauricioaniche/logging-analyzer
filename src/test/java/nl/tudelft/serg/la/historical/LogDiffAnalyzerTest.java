package nl.tudelft.serg.la.historical;

import org.junit.Test;
import org.junit.Assert;

public class LogDiffAnalyzerTest {

	@Test
	public void countLogAdditions() {
		String diff = 
			"diff --git a/A.java b/A.java\n"+
			"index aa1aca0..25fd00d 100644\n"+
			"--- a/A.java\n"+
			"+++ b/A.java\n"+
			"@@ -1,3 +1,4 @@\n"+
			" a\n"+
			" log.info(\"aa\");\n"+
			"+log.info(\"bb\");\n"+
			" b\n";
		
		LogAnalysisResult result = new LogDiffAnalyzer().analyze(diff);
		
		Assert.assertEquals(1, result.getLogAdds());
		Assert.assertEquals(0, result.getLogDels());
	}
	
	@Test
	public void addsAndDels() {
		String diff = 
				"diff --git a/A.java b/A.java\n"+
				"index c06867f..a1e3870 100644\n"+
				"--- a/A.java\n"+
				"+++ b/A.java\n"+
				"@@ -1,3 +1,13 @@\n"+
				" a\n"+
				"-log.info(\"aaa\");\n"+
				"+a\n"+
				"+a\n"+
				"+a\n"+
				"+a\n"+
				"+a\n"+
				"+log.info(\"aaaa\");\n"+
				"+b\n"+
				"+b\n"+
				"+b\n"+
				"+b\n"+
				"+b\n"+
				" b";
		
		LogAnalysisResult result = new LogDiffAnalyzer().analyze(diff);
		
		Assert.assertEquals(1, result.getLogAdds());
		Assert.assertEquals(0, result.getLogUpdates());
		Assert.assertEquals(1, result.getLogDels());
		
	}

	@Test
	public void logUpdated() {
		String diff = 
				"diff --git a/A.java b/A.java\n"+
				"index a1e3870..4d02d66 100644\n"+
				"--- a/A.java\n"+
				"+++ b/A.java\n"+
				"@@ -4,7 +4,7 @@ a\n"+
				" a\n"+
				" a\n"+
				" a\n"+
				"-log.info(\"aaaa\");\n"+
				"+log.info(\"baaaa\");\n"+
				" b\n"+
				" b\n"+
				" b";
		
		LogAnalysisResult result = new LogDiffAnalyzer().analyze(diff);
		
		Assert.assertEquals(0, result.getLogDels());
		Assert.assertEquals(0, result.getLogAdds());
		Assert.assertEquals(1, result.getLogUpdates());
		
	}

	@Test
	public void logUpdatedAndChangedLevel() {
		String diff = 
				"diff --git a/A.java b/A.java\n"+
						"index a1e3870..4d02d66 100644\n"+
						"--- a/A.java\n"+
						"+++ b/A.java\n"+
						"@@ -4,7 +4,7 @@ a\n"+
						" a\n"+
						" a\n"+
						" a\n"+
						"-log.info(\"aaaa\");\n"+
						"+log.debug(\"baaaa\");\n"+
						" b\n"+
						" b\n"+
						" b";
		
		LogAnalysisResult result = new LogDiffAnalyzer().analyze(diff);
		
		Assert.assertEquals(1, result.getLogUpdates());
		Assert.assertEquals("INFO -> DEBUG", result.getLevelChanges().get(0));
		
	}

	@Test
	public void differentLogNames() {
		String diff = 
				"diff --git a/A.java b/A.java\n"+
						"index a1e3870..4d02d66 100644\n"+
						"--- a/A.java\n"+
						"+++ b/A.java\n"+
						"@@ -4,6 +4,5 @@ a\n"+
						"-log.info(\"aaaa\");\n"+ // 4
						" a\n"+ // 5 4
						" a\n"+ // 6 4
						"+LOGGER.debug(\"aaaa\");\n"+ // 6 5
						" a\n"+  // 7 6
						"-_LOGGER.trace(\"aaaa\");\n"+ // 8 6
						" b"; // 9 7
		
		LogAnalysisResult result = new LogDiffAnalyzer().analyze(diff);
		
		Assert.assertEquals(0, result.getLogUpdates());
		Assert.assertEquals(2, result.getLogDels());
		Assert.assertEquals(1, result.getLogAdds());
		
	}
	
	@Test
	public void detectSameLineInLargeDiff() {
		String diff =
				"diff --git a/A b/A\r\n"+
				"index ca17226..ae43afd 100644\r\n"+
				"--- a/A\r\n"+
				"+++ b/A\r\n"+
				"@@ -1,8 +1,8 @@\r\n"+
				"-a\r\n"+
				"-b\r\n"+
				"-c\r\n"+
				"-log.info(\"a\")\r\n"+
				"-d\r\n"+
				"-e\r\n"+
				"-f\r\n"+
				"+aa\r\n"+
				"+bb\r\n"+
				"+cc\r\n"+
				"+log.info(\"aa\")\r\n"+
				"+dd\r\n"+
				"+ee\r\n"+
				"+ff\r\n"+
				" ";
		
		LogAnalysisResult result = new LogDiffAnalyzer().analyze(diff);
		
		Assert.assertEquals(0, result.getLogDels());
		Assert.assertEquals(1, result.getLogUpdates());
		Assert.assertEquals(0, result.getLogAdds());
		
		
	}
}
