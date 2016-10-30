package nl.tudelft.serg.la.historical;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.repodriller.RepoDriller;

import nl.tudelft.serg.la.LogLevel;
import nl.tudelft.serg.la.util.StringUtils;

public class HistoricalMetricsCalculator {

	private String path;
	private String outputDir;
	private String projectName;

	public HistoricalMetricsCalculator(String path, String outputDir) {
		this.path = path;
		this.outputDir = outputDir;
		this.projectName = StringUtils.extractProjectNameFromFolder(path);
	}

	public void run() {
		HistoricalLogVisitor visitor1 = new HistoricalLogVisitor();
		CommitInfoVisitor visitor2 = new CommitInfoVisitor();
		BugVisitor visitor3 = new BugVisitor();
		
		LogStudy study = new LogStudy(path, visitor1, visitor2, visitor3);
		new RepoDriller().start(study);

		Map<String, HistoricFile> files = visitor1.getFiles();
		Map<String, Calendar> dates = visitor2.getDates();
		
		printLogEvolution(files, dates);
		printLogLevelChanges(files, dates);
		printLogProcessMetrics(files);
		printAuthors(files, visitor2);
		printBugs(files, visitor2, visitor3);
		
	}

	private void printLogProcessMetrics(Map<String, HistoricFile> files) {
		try {
			PrintStream ps = new PrintStream(outputDir + projectName + "-log-process-metrics.csv");
			ps.println("project,file,logadd,logdel");
			
			for(String file : files.keySet()) {
				HistoricFile hf = files.get(file);
					
				ps.println(
					projectName + "," +
					file + "," +
					hf.logadd() + "," +
					hf.logdel()
				);
			}
			
			ps.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
	}

	private void printLogEvolution(Map<String, HistoricFile> files, Map<String, Calendar> dates) {
		try {
			PrintStream ps = new PrintStream(outputDir + projectName + "-log-evolution.csv");
			ps.println("project,file,commit,date,adds,deletions,updates");
			
			for(String file : files.keySet()) {
				HistoricFile hf = files.get(file);
				
				for(String commit : hf.getLogEvolution().keySet()) {
					LogAnalysisResult logAnalysisResult = hf.getLogEvolution().get(commit);
					
					ps.println(
							projectName + "," +
									file + "," +
									commit + "," +
									new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dates.get(commit).getTime()) + "," +
									logAnalysisResult.getLogAdds() + "," +
									logAnalysisResult.getLogDels() + "," +
									logAnalysisResult.getLogUpdates()
							);
				}
			}
			
			ps.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
	}

	private void printAuthors(Map<String, HistoricFile> files, CommitInfoVisitor visitor2) {
		try {
			PrintStream ps = new PrintStream(outputDir + projectName + "-commit-authors.csv");
			ps.println("project,commit,date,author");
			
			for(String commit : visitor2.getCommits()) {
				ps.println(
					projectName + "," +
					commit + "," +
					new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(visitor2.getDates().get(commit).getTime()) + "," +
					visitor2.getAuthors().get(commit)
				);
			}
			
			ps.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
	}

	private void printBugs(Map<String, HistoricFile> files, CommitInfoVisitor visitor2, BugVisitor bugs) {
		try {
			PrintStream ps = new PrintStream(outputDir + projectName + "-commit-bugs.csv");
			ps.println("project,commit,date,bug");
			
			for(String commit : bugs.getBugs().keySet()) {
				ps.println(
					projectName + "," +
					commit + "," +
					new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(visitor2.getDates().get(commit).getTime()) + "," +
					bugs.getBugs().get(commit)
				);
			}
			
			ps.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
	}

	private void printLogLevelChanges(Map<String, HistoricFile> files, Map<String, Calendar> dates) {
		try {
			PrintStream ps = new PrintStream(outputDir + projectName + "-log-level-changes.csv");
			ps.println("project,file,commit,date,change,direction");
			
			for(String file : files.keySet()) {
				HistoricFile hf = files.get(file);
				
				for(String commit : hf.getLogEvolution().keySet()) {
					LogAnalysisResult logAnalysisResult = hf.getLogEvolution().get(commit);

					for(String change : logAnalysisResult.getLevelChanges()) {
						ps.println(
							projectName + "," +
							file + "," +
							commit + "," +
							new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dates.get(commit).getTime()) + ","+
							change + "," +
							direction(change)
						);
					}
				}
			}
			
			ps.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
	}

	private String direction(String change) {
		String[] two = change.split(" -> ");
		return LogLevel.direction(two[0], two[1]);
	}

	
}
