package nl.tudelft.serg.la;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaFile {

	private String fullPath;
	private int loc;
	private Map<LogLevel, List<LogStatement>> qtyOfLogs;
	private String className;
	private String type;
	private List<String> logLibrary;
	
	public JavaFile(String fullPath, int loc) {
		this.fullPath = fullPath;
		this.loc = loc;
		this.qtyOfLogs = new HashMap<>();
		this.logLibrary = new ArrayList<>();
		
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
	
	public List<LogStatement> getAllLogs() {
		List<LogStatement> combined = new ArrayList<>();
		qtyOfLogs.values().forEach(list -> combined.addAll(list));
		Collections.sort(combined, new Comparator<LogStatement>() {
			@Override
			public int compare(LogStatement o1, LogStatement o2) {
				return o1.getLineNumber() - o2.getLineNumber();
			}
		});
		return combined;
	}

	public void log(LogStatement logLine) {
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

	public void setClassInfo(String className, String type) {
		this.className = className;
		this.type = type;
	}
	
	public String getClassName() {
		return className;
	}

	public String getType() {
		return type;
	}

	public List<String> getLogLibrary() {
		return logLibrary;
	}
	
	public void setLogLibrary(List<String> logLibrary) {
		this.logLibrary = logLibrary;
	}

	@Override
	public String toString() {
		return "JavaFile [fullPath=" + fullPath + ", loc=" + loc + ", qtyOfLogs=" + qtyOfLogs + ", className="
				+ className + ", type=" + type + ", logLibrary=" + logLibrary + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((fullPath == null) ? 0 : fullPath.hashCode());
		result = prime * result + loc;
		result = prime * result + ((logLibrary == null) ? 0 : logLibrary.hashCode());
		result = prime * result + ((qtyOfLogs == null) ? 0 : qtyOfLogs.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JavaFile other = (JavaFile) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (fullPath == null) {
			if (other.fullPath != null)
				return false;
		} else if (!fullPath.equals(other.fullPath))
			return false;
		if (loc != other.loc)
			return false;
		if (logLibrary == null) {
			if (other.logLibrary != null)
				return false;
		} else if (!logLibrary.equals(other.logLibrary))
			return false;
		if (qtyOfLogs == null) {
			if (other.qtyOfLogs != null)
				return false;
		} else if (!qtyOfLogs.equals(other.qtyOfLogs))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
}
