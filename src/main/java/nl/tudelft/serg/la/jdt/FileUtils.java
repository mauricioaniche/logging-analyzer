package nl.tudelft.serg.la.jdt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtils {

	public static String[] getAllDirs(String path) {
		ArrayList<String> dirs = new ArrayList<String>();
		getAllDirs(path, dirs);
		
		String[] ar = new String[dirs.size()];
		ar = dirs.toArray(ar);
		return ar;
	}
	
	private static void getAllDirs(String path, ArrayList<String> dirs) {
		
		File f = new File(path);
		if(f.getName().equals(".git")) return;
		
		for(File inside : f.listFiles()) {
			if(inside.isDirectory()) {
				String newDir = getCanonicalPath(inside);
				dirs.add(newDir);
				getAllDirs(newDir, dirs);
			}
		}
	}

	private static String getCanonicalPath(File inside) {
		try {
			String newDir = inside.getCanonicalPath();
			return newDir;
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String[] getAllJavaFiles(String path) {
		ArrayList<String> files = new ArrayList<String>();
		getAllJavaFiles(path, files);
		
		String[] ar = new String[files.size()];
		ar = files.toArray(ar);
		return ar;
	}
	
	private static void getAllJavaFiles(String path, ArrayList<String> files) {
		
		File f = new File(path);
		if(f.getName().equals(".git")) return;
		
		for(File inside : f.listFiles()) {
			if(inside.isDirectory()) {
				String newDir = getCanonicalPath(inside);
				getAllJavaFiles(newDir, files);
			} else if(inside.getAbsolutePath().endsWith(".java")) {
				files.add(inside.getAbsolutePath());
			}
		}
	}
	
	
}
