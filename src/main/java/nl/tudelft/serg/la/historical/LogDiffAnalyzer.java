package nl.tudelft.serg.la.historical;

import nl.tudelft.serg.la.LogLevel;

public class LogDiffAnalyzer {

	private static String[] logFunctions = { "LOG", "LOGGER" };
	
	public LogAnalysisResult analyze(String diff) {
		LogAnalysisResult result = new LogAnalysisResult();
		
		String[] lines = diff.replace("\r", "").split("\n");
		for(int i = 0; i < lines.length; i++) {
			if(i<5) continue;
			String line = lines[i];
			if(line.startsWith(" ")) continue;
			
			boolean logDeleted = isDel(line) && isLog(line);
			boolean nextLineIsLogAdded = i+1<lines.length && isAdd(lines[i+1]) && isLog(lines[i+1]);
			boolean logAdded = isAdd(line) && isLog(line);
			boolean priorLineIsLogDeleted = isDel(lines[i-1]) && isLog(lines[i-1]);

			if(logDeleted && !nextLineIsLogAdded) result.logDeleted();
			else if(logAdded && !priorLineIsLogDeleted) result.logAdded();
			else if(logAdded && priorLineIsLogDeleted) {
				result.logUpdated(logType(lines[i-1]), logType(line));
			}
		}
		
		return result;
	}

	private String logType(String line) {
		line = line.toUpperCase();
		for(LogLevel level : LogLevel.values()) {
			if(line.contains("." + level.toString())) return level.toString();
		}
		return "I-DONT-KNOW";
	}

	private boolean isDel(String line) {
		return line.startsWith("-");
	}

	private boolean isAdd(String line) {
		return line.startsWith("+");
	}

	private boolean isLog(String line) {
		line = line.toUpperCase();
		
		for(LogLevel level : LogLevel.values()) {
			for(String logFunction : logFunctions) {
				String toDetect = logFunction.toUpperCase() + "." + level.toString().toUpperCase();
				if(line.contains(toDetect)) return true;
			}
		}
		
		return false;
	}

}
