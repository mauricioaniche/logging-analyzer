package nl.tudelft.serg.la.metric;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import nl.tudelft.serg.la.JavaFile;
import nl.tudelft.serg.la.jdt.JDTVisitor;

public class LogDetectionVisitor extends ASTVisitor implements JDTVisitor {

	private Map<String, JavaFile> javaFilesRepo;
	private String logVarName = null;
	private String path;
	private List<String> importedLines;
	
	private static Logger log = Logger.getLogger(LogDetectionVisitor.class);

	public LogDetectionVisitor(Map<String, JavaFile> javaFilesRepo) {
		this.javaFilesRepo = javaFilesRepo;
		this.importedLines = new ArrayList<>();
	}

	@Override
	public void execute(CompilationUnit cu, String path) {
		this.path = path;
		cu.accept(this);
	}
	
	public boolean visit(ImportDeclaration node) {
		importedLines.add(node.getName().toString());
		return true;
	}
	
	public boolean visit(FieldDeclaration node) {
		
		String varType = node.getType().toString();
		if(varTypeIsLogging(varType)) {
			logVarName = ((VariableDeclarationFragment)node.fragments().get(0)).getName().toString();
		}
		return true;
	}

	private boolean varTypeIsLogging(String varType) {
		if(importedLines.contains("org.apache.log4j.Logger"))
			return varType.endsWith("Logger");
		if(importedLines.contains("org.apache.commons.logging.Log"))
			return varType.endsWith("Log");
		if(importedLines.contains("org.slf4j.Logger"))
			return varType.endsWith("Logger");
		
		return false;
	}
	
	public boolean visit(MethodInvocation node) {
		if(node.getExpression()==null) return false;
		
		String leftExpression = node.getExpression().toString();
		
		if(leftExpression.equals(logVarName)) {
			String logType = node.getName().toString();
			
			JavaFile javaFile = javaFilesRepo.get(path);
			try {
				log.debug("Found a " + logType);
				javaFile.log(logType);
			} catch (Exception e) {
				log.error("this doesnt look like a log: " + logType);
			}
		}
		
		return true;
	}

}
