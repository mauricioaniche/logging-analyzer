package nl.tudelft.serg.la.historical;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HistoricFile {

	private String file;
	private int commits;
	private Map<String, LogAnalysisResult> logEvolution;
	
	public HistoricFile(String file) {
		this.file = file;
		this.logEvolution = new HashMap<>();
	}
	
	public void committed(String commit, Calendar date, LogAnalysisResult result) {
		commits++;
		
		this.logEvolution.put(commit, result);
	}

	public String getFile() {
		return file;
	}

	public int getCommits() {
		return commits;
	}

	public Map<String, LogAnalysisResult> getLogEvolution() {
		return logEvolution;
	}
	
	public double logadd() {
		if(commits==0) return 0;
		int totalLogsAdded = logEvolution.values().stream().mapToInt(x -> x.getLogAdds()).sum();
		return totalLogsAdded / (double) commits;
	}

	public double logdel() {
		if(commits==0) return 0;
		int totalLogsAdded = logEvolution.values().stream().mapToInt(x -> x.getLogDels()).sum();
		return totalLogsAdded / (double) commits;
	}

}
