package nl.tudelft.serg.la.historical;

import nl.tudelft.serg.la.LogLevel;

public class LogDiffAnalyzer {

	private static String[] logFunctions = { "LOG", "LOGGER" };
	
	public LogAnalysisResult analyze(String diff) {
		int logAdds = 0;
		int logDels = 0;
		
		String[] lines = diff.replace("\r", "").split("\n");
		for(int i = 0; i < lines.length; i++) {
			if(i<5) continue;
			String line = lines[i];
			if(line.startsWith(" ")) continue;
			
			boolean logDeleted = isDel(line) && isLog(line);
			boolean nextLineIsLogAdded = i+1<lines.length && isAdd(lines[i+1]) && isLog(lines[i+1]);
			boolean logAdded = isAdd(line) && isLog(line);
			boolean priorLineIsLogDeleted = isDel(lines[i-1]) && isLog(lines[i-1]);

			if(logDeleted && !nextLineIsLogAdded) logDels++;
			else if(logAdded && !priorLineIsLogDeleted) logAdds++;
		}
		
		return new LogAnalysisResult(logAdds, logDels);
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
