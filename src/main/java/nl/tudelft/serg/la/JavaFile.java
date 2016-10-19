package nl.tudelft.serg.la;

import java.util.HashMap;
import java.util.Map;

public class JavaFile {

	private String fullPath;
	private int loc;
	private Map<String, Integer> qtyOfLogs;
	
	private static Map<String, Integer> weights;
	static {
		weights = new HashMap<>();
		weights.put("TRACE", 1);
		weights.put("DEBUG", 2);
		weights.put("INFO", 3);
		weights.put("WARN", 4);
		weights.put("ERROR", 5);
		weights.put("FATAL", 6);
	}
	
	public JavaFile(String fullPath, int loc) {
		this.fullPath = fullPath;
		this.loc = loc;
		this.qtyOfLogs = new HashMap<>();
		qtyOfLogs.put("INFO", 0);
		qtyOfLogs.put("ERROR", 0);
		qtyOfLogs.put("DEBUG", 0);
		qtyOfLogs.put("TRACE", 0);
		qtyOfLogs.put("WARN", 0);
		qtyOfLogs.put("FATAL", 0);
	}
	
	public String getFullPath() {
		return fullPath;
	}

	public int getLoc() {
		return loc;
	}

	public int getQtyOfTraceLogs() {
		return qtyOfLogs.get("TRACE");
	}

	public int getQtyOfDebugLogs() {
		return qtyOfLogs.get("DEBUG");
	}

	public int getQtyOfInfoLogs() {
		return qtyOfLogs.get("INFO");
	}

	public int getQtyOfWarnLogs() {
		return qtyOfLogs.get("WARN");
	}

	public int getQtyOfErrorLogs() {
		return qtyOfLogs.get("ERROR");
	}

	public int getQtyOfFatalLogs() {
		return qtyOfLogs.get("FATAL");
	}

	public void log(String logType) {
		logType = logType.toUpperCase();
		if(!qtyOfLogs.containsKey(logType))
			throw new IllegalArgumentException("type of log not recognizable: " + logType);

		qtyOfLogs.put(logType, qtyOfLogs.get(logType)+1);
	}

	public double logDensity() {
		return totalLogs() / (double) loc;
	}

	public int totalLogs() {
		return qtyOfLogs.values().stream().mapToInt(i -> i.intValue()).sum();
	}

	public double averageLoggingLevel() {
		int weight = 0;
		for(String logLevel : qtyOfLogs.keySet()) {
			weight += qtyOfLogs.get(logLevel) * weights.get(logLevel);
		}
		
		return weight / (double) totalLogs();
		
	}	
	
}
