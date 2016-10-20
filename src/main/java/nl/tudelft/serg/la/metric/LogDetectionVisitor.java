package nl.tudelft.serg.la.metric;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;

import nl.tudelft.serg.la.JavaFile;
import nl.tudelft.serg.la.LogLevel;
import nl.tudelft.serg.la.LogLine;
import nl.tudelft.serg.la.jdt.JDTVisitor;

public class LogDetectionVisitor extends ASTVisitor implements JDTVisitor {

	private static Logger log = Logger.getLogger(LogDetectionVisitor.class);
	
	private Map<String, JavaFile> javaFilesRepo;
	private String logVarName = null;
	private String path;
	private List<String> importedLines;
	private Stack<String> position;

	private CompilationUnit cu;
	
	public LogDetectionVisitor(Map<String, JavaFile> javaFilesRepo) {
		this.javaFilesRepo = javaFilesRepo;
		this.importedLines = new ArrayList<>();
		this.position = new Stack<>();
	}

	@Override
	public void execute(CompilationUnit cu, String path) {
		this.cu = cu;
		this.path = path;
		cu.accept(this);
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		position.push("method");
		return true;
	}
	
	@Override
	public void endVisit(MethodDeclaration node) {
		this.position.pop();
	}
	
	@Override
	public boolean visit(IfStatement node) {
		this.position.push("if");
		
		if(node.getThenStatement()!=null) {
			node.getThenStatement().accept(this);
		}
		
		if(node.getElseStatement()!=null) { 
			position.push("else");
			node.getElseStatement().accept(this);
			position.pop();
		}
		return false;
	}

	@Override
	public void endVisit(IfStatement node) {
		this.position.pop();
	}

	@Override
	public boolean visit(ForStatement node) {
		this.position.push("for");
		return true;
	}

	@Override
	public void endVisit(ForStatement node) {
		this.position.pop();
	}

	@Override
	public boolean visit(WhileStatement node) {
		this.position.push("while");
		return true;
	}
	
	@Override
	public void endVisit(WhileStatement node) {
		this.position.pop();
	}

	@Override
	public boolean visit(DoStatement node) {
		this.position.push("do-while");
		return true;
	}
	
	@Override
	public void endVisit(DoStatement node) {
		this.position.pop();
	}
	
	@Override
	public boolean visit(EnhancedForStatement node) {
		this.position.push("foreach");
		return true;
	}

	@Override
	public void endVisit(EnhancedForStatement node) {
		this.position.pop();
	}
	
	@Override
	public boolean visit(CatchClause node) {
		this.position.push("catch");
		return true;
	}

	@Override
	public void endVisit(CatchClause node) {
		this.position.pop();
	}

	@Override
	public boolean visit(SwitchStatement node) {
		this.position.push("switch");
		return true;
	}
	
	@Override
	public void endVisit(SwitchStatement node) {
		this.position.pop();
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
			if(LogLevel.isLogLevel(logType)) {
				JavaFile javaFile = javaFilesRepo.get(path);
				
				javaFile.log(new LogLine(LogLevel.valueFor(logType), currentPosition(), lineNumber(node)));
			}
		}
		
		return true;
	}

	private int lineNumber(MethodInvocation node) {
		int lineNumber = cu.getLineNumber(node.getStartPosition());
		return lineNumber;
	}

	private String currentPosition() {
		
		String currentPosition;
		if(position.isEmpty()) { // being defensive
			log.error("can't determine the position of this log... return 'I-DONT-KNOW'");
			currentPosition = "I-DONT-KNOW";
		}
		else currentPosition = position.peek();
		
		return currentPosition;
	}

}
