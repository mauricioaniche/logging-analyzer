package nl.tudelft.serg.la;

public class JavaFile {

	private String fullPath;
	private int loc;
	private int qtyOfTraceLogs;
	private int qtyOfDebugLogs;
	private int qtyOfInfoLogs;
	private int qtyOfWarnLogs;
	private int qtyOfErrorLogs;
	private int qtyOfFatalLogs;
	
	public JavaFile(String fullPath, int loc) {
		super();
		this.fullPath = fullPath;
		this.loc = loc;
	}
	
	public String getFullPath() {
		return fullPath;
	}

	public int getLoc() {
		return loc;
	}

	public int getQtyOfTraceLogs() {
		return qtyOfTraceLogs;
	}

	public int getQtyOfDebugLogs() {
		return qtyOfDebugLogs;
	}

	public int getQtyOfInfoLogs() {
		return qtyOfInfoLogs;
	}

	public int getQtyOfWarnLogs() {
		return qtyOfWarnLogs;
	}

	public int getQtyOfErrorLogs() {
		return qtyOfErrorLogs;
	}

	public int getQtyOfFatalLogs() {
		return qtyOfFatalLogs;
	}

	public void log(String logType) {
		logType = logType.toUpperCase();
		if(logType.equals("TRACE")) qtyOfTraceLogs++;
		else if(logType.equals("DEBUG")) qtyOfDebugLogs++;
		else if(logType.equals("INFO")) qtyOfInfoLogs++;
		else if(logType.equals("WARN")) qtyOfWarnLogs++;
		else if(logType.equals("ERROR")) qtyOfErrorLogs++;
		else if(logType.equals("FATAL")) qtyOfFatalLogs++;
		else throw new IllegalArgumentException("type of log not recognizable: " + logType);
		
	}

	public double logDensity() {
		return totalLogs() / (double) loc;
	}

	private int totalLogs() {
		return qtyOfTraceLogs + qtyOfDebugLogs + qtyOfInfoLogs + qtyOfWarnLogs + qtyOfErrorLogs + qtyOfFatalLogs;
	}	
	
}
