package nl.tudelft.serg.la.metric;

import org.apache.log4j.Logger;

public class Test1 {

	private static Logger log = Logger.getLogger(Test1.class);
	
	public void m1() {
		int m = 0;
		String x = "hi";
		try {
		} catch(IOException e) {
			log.info("info " + m + " then " + x, e);
		}
	}
}
