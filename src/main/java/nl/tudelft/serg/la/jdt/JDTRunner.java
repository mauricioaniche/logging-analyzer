package nl.tudelft.serg.la.jdt;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;

import com.google.common.collect.Lists;

public class JDTRunner {

	private static Logger log = Logger.getLogger(JDTRunner.class);
	private boolean resolveBindings;
	private boolean bindingsRecovery;
	
	
	public JDTRunner(boolean resolveBindings, boolean bindingsRecovery) {
		this.resolveBindings = resolveBindings;
		this.bindingsRecovery = bindingsRecovery;
	}
	
	// extracted from the original Eclipse IDE source code
	private static final int MAX_AT_ONCE;
	static {
		long maxMemory= Runtime.getRuntime().maxMemory() / (1 << 20); // in MiB
		
		if      (maxMemory >= 2000) MAX_AT_ONCE= 400;
		else if (maxMemory >= 1500) MAX_AT_ONCE= 300;
		else if (maxMemory >= 1000) MAX_AT_ONCE= 200;
		else if (maxMemory >=  500) MAX_AT_ONCE= 100;
		else                        MAX_AT_ONCE=  25;
	}
	
	public void run(String[] srcDirs, String[] javaFiles, Callable<List<JDTVisitor>> metrics) {
		JDTFileRequestor storage = new JDTFileRequestor(metrics);
		
		List<List<String>> partitions = Lists.partition(Arrays.asList(javaFiles), MAX_AT_ONCE);
		log.info("Max partition size: " + MAX_AT_ONCE + ", total partitions=" + partitions.size());

		int count = 0;
		for(List<String> partition : partitions) {
			log.info("Next partition " + ++count);
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			
			parser.setResolveBindings(resolveBindings);
			parser.setBindingsRecovery(bindingsRecovery);
			
			Map<?, ?> options = JavaCore.getOptions();
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
			parser.setCompilerOptions(options);
			parser.setEnvironment(null, srcDirs, null, true);
			parser.createASTs(partition.toArray(new String[partition.size()]), null, new String[0], storage, null);
		}
		
		log.info("Finished parsing");	
	}
	
	public void run(String path, Callable<List<JDTVisitor>> metrics) {
		String[] srcDirs = FileUtils.getAllDirs(path);
		String[] javaFiles = FileUtils.getAllJavaFiles(path);
		log.info("Found " + javaFiles.length + " java files in " + path);
		
		run(srcDirs, javaFiles, metrics);

	}
}
