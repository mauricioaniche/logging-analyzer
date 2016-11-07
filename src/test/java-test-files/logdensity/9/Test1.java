package nl.tudelft.serg.la.metric;

import org.apache.log4j.Logger;

public class Test1 {

	private static Logger log = Logger.getLogger(Test1.class);
	
	public void m1() {
		
//		log.info("simple message");
		
		int m = 0;
		String x = "hi";
		int y = 10;
		
		log.info("info " + m + " then " + x);
		try {
		} catch(Exception e) {
			log.debug("info " + m + " then " + x + " then " + y + "end", e);
		}
	}
}
