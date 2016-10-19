package nl.tudelft.serg.la.jdt;

import org.eclipse.jdt.core.dom.CompilationUnit;

public interface JDTVisitor {
	void execute(CompilationUnit cu, String path);
}
