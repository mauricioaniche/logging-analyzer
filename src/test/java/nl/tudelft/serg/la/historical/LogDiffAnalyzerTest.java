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
			"@@ -1,4 +1,5 @@\n"+
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
				"@@ -1,4 +1,14 @@\n"+
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
						"@@ -4,7 +4,7 @@ a\n"+
						"-log.info(\"aaaa\");\n"+
						" a\n"+
						"-LOGGER.warn(\"aaaa\");\n"+
						" a\n"+
						"+log.debug(\"aaaa\");\n"+
						" a\n"+
						"-_LOGGER.trace(\"aaaa\");\n"+
						" b";
		
		LogAnalysisResult result = new LogDiffAnalyzer().analyze(diff);
		
		Assert.assertEquals(3, result.getLogDels());
		Assert.assertEquals(1, result.getLogAdds());
		
	}
}
