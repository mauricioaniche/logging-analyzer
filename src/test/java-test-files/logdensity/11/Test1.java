package nl.tudelft.serg.la.metric;

import org.apache.log4j.Logger;

public class Test1 {

	private static Logger log = Logger.getLogger(Test1.class);
	
	static {
		log.info("test 1");
	}
	
	public Test1() {
		log.info("test 2");
	}
	
}
