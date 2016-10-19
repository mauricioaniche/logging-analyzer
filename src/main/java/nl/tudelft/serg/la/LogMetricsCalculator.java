package nl.tudelft.serg.la;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import nl.tudelft.serg.la.jdt.FileUtils;
import nl.tudelft.serg.la.jdt.JDTRunner;
import nl.tudelft.serg.la.metric.LOCCalculator;
import nl.tudelft.serg.la.metric.LogDensityVisitor;

public class LogMetricsCalculator {

	private Map<String, JavaFile> javaFilesRepo;
	private static Logger log = Logger.getLogger(LogMetricsCalculator.class);
	
	public LogMetricsCalculator() {
		this.javaFilesRepo = new HashMap<>();
	}
	
	public void run(String path) {

		String[] srcDirs = FileUtils.getAllDirs(path);
		String[] javaFilePaths = FileUtils.getAllJavaFiles(path);
		
		calculateLoc(javaFilePaths);
		runLogMetrics(srcDirs, javaFilePaths);
		
	}

	private void runLogMetrics(String[] srcDirs, String[] javaFilePaths) {
		new JDTRunner(true, true).run(srcDirs, javaFilePaths, 
			() -> Arrays.asList(new LogDensityVisitor(javaFilesRepo)));
	}

	private void calculateLoc(String[] javaFilePaths) {
		for(String javaFilePath : javaFilePaths) {
			
			try {
				int loc = new LOCCalculator().calculate(new FileInputStream(javaFilePath));
				
				JavaFile javaFile = new JavaFile(javaFilePath, loc);
				javaFilesRepo.put(javaFilePath, javaFile);
			} catch (Exception e) {
				log.error("error in " + javaFilePath, e);
			}
		}
	}
}
