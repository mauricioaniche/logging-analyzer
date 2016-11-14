package nl.tudelft.serg.la.metric;

import java.util.ArrayList;
import java.util.List;

public class LogLibraryImportFinder {

	public List<String> find(String sourceCode) {

		ArrayList<String> result = new ArrayList<>();
		if(sourceCode.contains("import org.apache.log4j.Logger"))		
 			result.add("org.apache.log4j.Logger");		
 		if(sourceCode.contains("import org.apache.commons.logging.Log"))		
 			result.add("org.apache.commons.logging.Log");		
 		if(sourceCode.contains("import org.slf4j.Logger"))
 			result.add("org.slf4j.Logger");		
 		if(sourceCode.contains("import java.util.Logging.Logger"))		
 			result.add("java.util.Logging.Logger");
 		if(sourceCode.contains("import org.apache.juli.logging.Log"))		
 			result.add("org.apache.juli.logging.Log");		
 		if(sourceCode.contains("import org.apache.logging.log4j.Logger"))		
 			result.add("org.apache.logging.log4j.Logger");		
 		if(sourceCode.contains("import org.codehaus.plexus.logging.Logger"))		
 			result.add("org.codehaus.plexus.logging.Logger");
		
		return result;
	}


}
