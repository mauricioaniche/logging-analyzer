package nl.tudelft.serg.la.historical;

import java.util.List;
import java.util.Optional;

import org.repodriller.domain.DiffBlock;
import org.repodriller.domain.DiffLine;
import org.repodriller.domain.DiffLineType;
import org.repodriller.domain.DiffParser;

import nl.tudelft.serg.la.LogLevel;

public class LogDiffAnalyzer {

	private static String[] logFunctions = { "LOG", "LOGGER" };
	
	public LogAnalysisResult analyze(String diff) {
		LogAnalysisResult result = new LogAnalysisResult();
		
		DiffParser parser = new DiffParser(diff);
		List<DiffBlock> blocks = parser.getBlocks();
		
		for(DiffBlock block : blocks) {
			
			for(DiffLine deletedLine : block.getLinesInOldFile()) {
				String line = deletedLine.getLine();
				
				if(isLog(line) && deletedLine.getType().equals(DiffLineType.REMOVED) && !otherIsLog(deletedLine.getLineNumber(), block.getLinesInNewFile())) {
					result.logDeleted();
				}
				else if(isLog(line) && deletedLine.getType().equals(DiffLineType.REMOVED) && otherIsLog(deletedLine.getLineNumber(), block.getLinesInNewFile())) {
					String logLineInNewFile = block.getLineInNewFile(deletedLine.getLineNumber()).get().getLine();
					result.logUpdated(logType(line), logType(logLineInNewFile));
				}
			}

			for(DiffLine addedLine : block.getLinesInNewFile()) {
				String line = addedLine.getLine();
				if(isLog(line) && addedLine.getType().equals(DiffLineType.ADDED) && !otherIsLog(addedLine.getLineNumber(), block.getLinesInOldFile())) {
					result.logAdded();
				}
			}
		}
		
		return result;
	}

	private boolean otherIsLog(int lineNumber, List<DiffLine> linesInNewFile) {
		Optional<DiffLine> found = linesInNewFile.stream().filter(x -> x.getLineNumber() == lineNumber).findFirst();
		if(!found.isPresent()) return false;
		return isLog(found.get().getLine());
	}

	private String logType(String line) {
		line = line.toUpperCase();
		for(LogLevel level : LogLevel.values()) {
			if(line.contains("." + level.toString())) return level.toString();
		}
		return "I-DONT-KNOW";
	}

	private boolean isLog(String line) {
		line = line.toUpperCase();
		
		for(LogLevel level : LogLevel.values()) {
			for(String logFunction : logFunctions) {
				String toDetect = logFunction.toUpperCase() + "." + level.toString().toUpperCase();
				if(line.contains(toDetect)) return true;
			}
		}
		
		return false;
	}

}
