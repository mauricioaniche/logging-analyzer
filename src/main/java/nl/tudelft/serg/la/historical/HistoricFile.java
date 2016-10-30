package nl.tudelft.serg.la.historical;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HistoricFile {

	private String file;
	private int commits;
	private Map<String, LogAnalysisResult> logEvolution;
	private Map<String, String> authors;
	
	public HistoricFile(String file) {
		this.file = file;
		this.logEvolution = new HashMap<>();
		this.authors = new HashMap<>();
	}
	
	public void committed(String commit, Calendar date, String author, LogAnalysisResult result) {
		commits++;
		
		this.authors.put(commit, author);
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

	public Map<String, String> getAuthors() {
		return authors;
	}
	
	
}
