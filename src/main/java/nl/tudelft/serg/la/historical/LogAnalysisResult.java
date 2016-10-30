package nl.tudelft.serg.la.historical;

public class LogAnalysisResult {

	private int logAdds;
	private int logDels;
	
	public LogAnalysisResult(int logAdds, int logDels) {
		this.logAdds = logAdds;
		this.logDels = logDels;
	}
	public int getLogAdds() {
		return logAdds;
	}
	public int getLogDels() {
		return logDels;
	}
	
}
