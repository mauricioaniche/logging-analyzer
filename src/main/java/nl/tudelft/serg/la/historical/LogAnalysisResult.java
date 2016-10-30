package nl.tudelft.serg.la.historical;

import java.util.ArrayList;
import java.util.List;

public class LogAnalysisResult {

	private int logAdds;
	private int logDels;
	private int logUpdates;
	private List<String> levelChanges;
	
	public LogAnalysisResult() {
		this.levelChanges = new ArrayList<>();
	}
	
	public int getLogAdds() {
		return logAdds;
	}
	public int getLogDels() {
		return logDels;
	}

	public int getLogUpdates() {
		return logUpdates;
	}
	public void logDeleted() {
		logDels++;
	}
	public void logAdded() {
		logAdds++;
	}
	public void logUpdated(String from, String to) {
		logUpdates++;
		levelChanges.add(from + " -> " + to);
	}
	
	public List<String> getLevelChanges() {
		return levelChanges;
	}
}
