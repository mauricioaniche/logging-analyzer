package nl.tudelft.serg.la.metric;

import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import nl.tudelft.serg.la.JavaFile;
import nl.tudelft.serg.la.jdt.JDTVisitor;

public class LogDensityVisitor extends ASTVisitor implements JDTVisitor {

	private Map<String, JavaFile> javaFilesRepo;
	private String logVarName = null;
	private String path;

	public LogDensityVisitor(Map<String, JavaFile> javaFilesRepo) {
		this.javaFilesRepo = javaFilesRepo;
	}

	@Override
	public void execute(CompilationUnit cu, String path) {
		this.path = path;
		cu.accept(this);
	}
	
	public boolean visit(FieldDeclaration node) {
		
		String varType = node.getType().toString();
		if(varType.endsWith("Logger")) {
			logVarName = ((VariableDeclarationFragment)node.fragments().get(0)).getName().toString();
		}
		return true;
	}
	
	public boolean visit(MethodInvocation node) {
		String leftExpression = node.getExpression().toString();
		
		if(leftExpression.equals(logVarName)) {
			String invokedMethodName = node.getName().toString();
			
			JavaFile javaFile = javaFilesRepo.get(path);
			javaFile.log(invokedMethodName);
		}
		
		return true;
	}

}
