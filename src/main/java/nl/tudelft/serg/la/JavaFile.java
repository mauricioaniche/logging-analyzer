package nl.tudelft.serg.la;

import java.util.HashMap;
import java.util.Map;

public class JavaFile {

	private String fullPath;
	private int loc;
	private Map<LogLevel, Integer> qtyOfLogs;
	
	public JavaFile(String fullPath, int loc) {
		this.fullPath = fullPath;
		this.loc = loc;
		this.qtyOfLogs = new HashMap<>();
		
		for(LogLevel level : LogLevel.values()) {
			qtyOfLogs.put(level, 0);
		}
	}
	
	public String getFullPath() {
		return fullPath;
	}

	public int getLoc() {
		return loc;
	}

	public int getQtyLogs(LogLevel level) {
		return qtyOfLogs.get(level);
	}

	public void log(LogLevel level) {
		qtyOfLogs.put(level, qtyOfLogs.get(level)+1);
	}

	public double logDensity() {
		return totalLogs() / (double) loc;
	}

	public int totalLogs() {
		return qtyOfLogs.values().stream().mapToInt(i -> i.intValue()).sum();
	}

	public double averageLoggingLevel() {
		int weight = 0;
		for(LogLevel logLevel : qtyOfLogs.keySet()) {
			weight += qtyOfLogs.get(logLevel) * logLevel.getWeight();
		}
		
		return weight / (double) totalLogs();
		
	}	
	
}
