package nl.tudelft.serg.la.metric;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.repodriller.plugin.jdt.FileUtils;
import org.repodriller.plugin.jdt.JDTRunner;

import nl.tudelft.serg.la.JavaFile;
import nl.tudelft.serg.la.LogLevel;
import nl.tudelft.serg.la.LogStatement;
import nl.tudelft.serg.la.util.StringUtils;

public class LogMetricsCalculator {

	private Map<String, JavaFile> javaFilesRepo;
	private static Logger log = Logger.getLogger(LogMetricsCalculator.class);
	private String outputDir;
	private String path;
	private String projectName;
	private String[] srcDirs;
	private String[] javaFilePaths;
	
	public LogMetricsCalculator(String path, String outputDir) {
		this.path = path;
		this.outputDir = outputDir;
		this.javaFilesRepo = new HashMap<>();

		this.projectName = StringUtils.extractProjectNameFromFolder(path);
		this.srcDirs = FileUtils.getAllDirs(path);
		this.javaFilePaths = FileUtils.getAllJavaFiles(path);
	}
	
	public void run() {

		log.info("Parsing project at " + path);
		
		calculateLoc();
		runLogMetrics();
		
		writeJavaTypesOutput();
		writeProductMetricsOutput();
		writeLogPositionOutput();
		
	}

	private void writeLogPositionOutput() {
		try {
			PrintStream ps = new PrintStream(outputDir + projectName + "-logs.csv");
			ps.println("project,file,line,level,position,strings,strings_length,variables,method_invocations,has_exception,exception_type");
			for(String filePath : javaFilesRepo.keySet()) {
				JavaFile file = javaFilesRepo.get(filePath);
				
				for(LogStatement line : file.getAllLogs()) {
					ps.println(
						projectName + "," +
						file.getFullPath() + "," +
						line.getLineNumber() + ","+
						line.getLevel() + "," +
						line.getPosition() + "," +
						line.getMessage().getQtyOfStrings() + "," +
						line.getMessage().getStringsLength() + "," +
						line.getMessage().getQtyOfVariables() + "," +
						line.getMessage().getQtyOfMethodInvocations() + "," +
						line.getMessage().hasException() + "," +
						line.getMessage().getExceptionType()
					);
				}
			}
			ps.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeJavaTypesOutput() {
		try {
			PrintStream ps = new PrintStream(outputDir + projectName + "-java-types.csv");
			ps.println("project,file,class_name,type");
			for(String filePath : javaFilesRepo.keySet()) {
				JavaFile file = javaFilesRepo.get(filePath);
				ps.println(
					projectName + "," +
					file.getFullPath() + "," +
					file.getClassName() + "," + 
					file.getType()
				);
			}
			ps.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeProductMetricsOutput() {
		try {
			PrintStream ps = new PrintStream(outputDir + projectName + "-log-product-metrics.csv");
			ps.println("project,file,loc,total_logs,trace,debug,info,warn,error,fatal,log_density,avg_logging_level");
			for(String filePath : javaFilesRepo.keySet()) {
				JavaFile file = javaFilesRepo.get(filePath);
				ps.println(
					projectName + "," +
					file.getFullPath() + "," +
					file.getLoc() + "," +
					file.totalLogs() + "," +
					file.getQtyLogs(LogLevel.TRACE) + "," +
					file.getQtyLogs(LogLevel.DEBUG) + "," +
					file.getQtyLogs(LogLevel.INFO) + "," +
					file.getQtyLogs(LogLevel.WARN) + "," +
					file.getQtyLogs(LogLevel.ERROR) + "," +
					file.getQtyLogs(LogLevel.FATAL) + "," +
					file.logDensity() + "," + 
					file.averageLoggingLevel()
				);
			}
			ps.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private void runLogMetrics() {
		new JDTRunner(true, true).run(srcDirs, javaFilePaths, 
			() -> Arrays.asList(new ClassInfo(javaFilesRepo), new LogVisitor(javaFilesRepo)));
	}

	private void calculateLoc() {
		for(String javaFilePath : javaFilePaths) {
			
			try {
				int loc = new LOCCalculator().calculate(new FileInputStream(javaFilePath));
				
				JavaFile javaFile = new JavaFile(javaFilePath, loc);
				log.info(javaFilePath + " contains " + loc + " lines of code");
				javaFilesRepo.put(javaFilePath, javaFile);
			} catch (Exception e) {
				log.error("error in " + javaFilePath, e);
			}
		}
	}
	
	public Map<String, JavaFile> getJavaFilesRepo() {
		return javaFilesRepo;
	}
}
