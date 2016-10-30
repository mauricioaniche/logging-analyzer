package nl.tudelft.serg.la.util;

public class StringUtils {


	public static String extractProjectNameFromFolder(String path) {
		if(path.endsWith("/")) path = path.substring(0, path.length()-1);
		path = "/" + path;
		return path.substring(path.lastIndexOf("/")+1);
	}

	
}
