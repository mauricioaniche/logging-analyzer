package nl.tudelft.serg.la.historical;

import java.io.FileNotFoundException;
import java.io.PrintStream;
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
		
		LogStudy study = new LogStudy(path, visitor1, visitor2);
		new RepoDriller().start(study);

		Map<String, HistoricFile> files = visitor1.getFiles();
		Map<String, Calendar> dates = visitor2.getDates();
		
		printLogMetrics(files, dates);
		printLogLevelChanges(files, dates);
		
	}

	private void printLogMetrics(Map<String, HistoricFile> files, Map<String, Calendar> dates) {
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
						dates.get(commit) + "," +
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
							dates.get(commit) + ","+
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
