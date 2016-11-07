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
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.repodriller.plugin.jdt.JDTVisitor;

import nl.tudelft.serg.la.JavaFile;
import nl.tudelft.serg.la.LogLevel;
import nl.tudelft.serg.la.LogMessage;
import nl.tudelft.serg.la.LogStatement;

public class LogDetectionVisitor extends ASTVisitor implements JDTVisitor {

	private static Logger log = Logger.getLogger(LogDetectionVisitor.class);
	
	private Map<String, JavaFile> javaFilesRepo;
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
		if(node.isConstructor()) position.push("constructor");
		else position.push("method");
		
		return true;
	}
	
	@Override
	public void endVisit(MethodDeclaration node) {
		this.position.pop();
	}

	@Override
	public boolean visit(Initializer node) {
		position.push("initializer");
		
		return true;
	}
	
	@Override
	public void endVisit(Initializer node) {
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
	
	public boolean visit(MethodInvocation node) {
		if(node.getExpression()==null) return false;
		
		String leftExpression = node.getExpression().toString();
		boolean nameRemindsLogging = leftExpression.toLowerCase().contains("log");
		boolean methodNameRemindsLogLevel = LogLevel.isLogLevel(node.getName().toString());
		
		if(methodNameRemindsLogLevel && nameRemindsLogging) {
			JavaFile javaFile = javaFilesRepo.get(path);
			
			LogMessage message = new LogMessage();
			if(!node.arguments().isEmpty()) {
				resolveArgumentsInMessage(message, node, node.arguments().get(0));
				if(node.arguments().size()>1 && node.arguments().get(1)!=null) {
					resolveArgumentsInMessage(message, node, node.arguments().get(1));
				}
			}
			javaFile.log(new LogStatement(LogLevel.valueFor(node.getName().toString()), currentPosition(), lineNumber(node), message));
		}
		
		return true;
	}

	private void resolveArgumentsInMessage(LogMessage current, MethodInvocation node, Object object) {
		
		if(object == null) return ;
		
		if(object instanceof InfixExpression) {
			InfixExpression exp = (InfixExpression) object; 
			resolveArgumentsInMessage(current, node, exp.getLeftOperand());
			resolveArgumentsInMessage(current, node, exp.getRightOperand());
			if(exp.extendedOperands()!=null) {
				for(Object o : exp.extendedOperands()) {
					resolveArgumentsInMessage(current, node, o);
				}
			}
		}
		else if(object instanceof StringLiteral) {
			StringLiteral literal = (StringLiteral) object;
			current.addString(literal.getLiteralValue().length());
		}
		else if(object instanceof SimpleName) {
			SimpleName var = (SimpleName) object;
			IBinding binding = var.resolveBinding();
			if(binding!=null) {
				String type = binding.toString().split(" ")[0];
				if(isException(type)) {
					current.addException(type);
				} else {
					current.addVar(var);
				}
						
			}
		}
	
	}

	private boolean isException(String type) {
		return type.contains("Exception") || type.contains("Error"); 
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
