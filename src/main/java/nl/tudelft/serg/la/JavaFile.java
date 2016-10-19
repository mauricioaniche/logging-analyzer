package nl.tudelft.serg.la;

import java.util.HashMap;
import java.util.Map;

public class JavaFile {

	private String fullPath;
	private int loc;
	private Map<String, Integer> qtyOfLogs;
	
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

	private int totalLogs() {
		return qtyOfLogs.values().stream().mapToInt(i -> i.intValue()).sum();
	}	
	
}
