package nl.tudelft.serg.la.metric;

import org.apache.log4j.Logger;

public class Test1 {

	private static Logger logger = Logger.getLogger(Test1.class);
	
	public void m1() {
		
		logger.info("info hi");
		logger.info("info hi again");
		logger.warn("warn hi");
		logger.fatal("fatal hi");
		logger.error("error hi");
		logger.debug("debug hi");
		logger.trace("trace hi");
		
	}
}
