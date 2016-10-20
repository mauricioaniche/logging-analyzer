package nl.tudelft.serg.la.metric;

import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import nl.tudelft.serg.la.JavaFile;
import nl.tudelft.serg.la.jdt.JDTVisitor;

public class ClassInfo extends ASTVisitor implements JDTVisitor {

	private String path;
	private Map<String, JavaFile> javaFilesRepo;

	public ClassInfo(Map<String, JavaFile> javaFilesRepo) {
		this.javaFilesRepo = javaFilesRepo;
	}

	@Override
	public boolean visit(TypeDeclaration node) {

		String className = null;
		
		ITypeBinding binding = node.resolveBinding();
		if(binding!=null)
			className = binding.getBinaryName();
		if(className==null) className = node.getName().toString();
		
		String type = null;
		if(node.isInterface()) type = "interface";
		else type = "class";
		
		javaFilesRepo.get(path).setClassInfo(className, type);
		
		return false;
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		String className = null;
		String type = "enum";
		
		ITypeBinding binding = node.resolveBinding();
		if(binding!=null)
			className = binding.getBinaryName();
		if(className==null) className = node.getName().toString();
		
		javaFilesRepo.get(path).setClassInfo(className, type);
		return false;
	}
	
	@Override
	public void execute(CompilationUnit cu, String path) {
		this.path = path;
		cu.accept(this);
		
	}
	
	
}
