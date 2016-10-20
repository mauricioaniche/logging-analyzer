package nl.tudelft.serg.la;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaFile {

	private String fullPath;
	private int loc;
	private Map<LogLevel, List<LogLine>> qtyOfLogs;
	
	public JavaFile(String fullPath, int loc) {
		this.fullPath = fullPath;
		this.loc = loc;
		this.qtyOfLogs = new HashMap<>();
		
		for(LogLevel level : LogLevel.values()) {
			qtyOfLogs.put(level, new ArrayList<>());
		}
	}
	
	public String getFullPath() {
		return fullPath;
	}

	public int getLoc() {
		return loc;
	}

	public int getQtyLogs(LogLevel level) {
		return qtyOfLogs.get(level).size();
	}
	
	public List<LogLine> getAllLogs() {
		List<LogLine> combined = new ArrayList<>();
		qtyOfLogs.values().forEach(list -> combined.addAll(list));
		return combined;
	}

	public void log(LogLine logLine) {
		LogLevel level = logLine.getLevel();
		qtyOfLogs.get(level).add(logLine);
	}

	public double logDensity() {
		return totalLogs() / (double) loc;
	}

	public int totalLogs() {
		return qtyOfLogs.values().stream().mapToInt(i -> i.size()).sum();
	}

	public double averageLoggingLevel() {
		int weight = 0;
		for(LogLevel logLevel : qtyOfLogs.keySet()) {
			weight += qtyOfLogs.get(logLevel).size() * logLevel.getWeight();
		}
		
		return weight / (double) totalLogs();
	}	
	
}
