package nl.tudelft.serg.la.jdt;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

public class JDTFileRequestor extends FileASTRequestor {

	private static Logger log = Logger.getLogger(JDTFileRequestor.class);
	private Callable<List<JDTVisitor>> metrics;
	
	public JDTFileRequestor(Callable<List<JDTVisitor>> metrics) {
		this.metrics = metrics;
	}

	@Override
	public void acceptAST(String sourceFilePath, 
			CompilationUnit cu) {
		
		try {
			for(JDTVisitor visitor : metrics.call()) {
				visitor.execute(cu, sourceFilePath);
			}
		} catch(Exception e) {
			// just ignore... sorry!
			log.error("error in " + sourceFilePath, e);
		}
	}
	
}
