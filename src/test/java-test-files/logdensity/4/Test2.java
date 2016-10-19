package nl.tudelft.serg.la.metric;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test1 {

	private static final Logger log = LoggerFactory.getLogger(HelloWorld.class);
	
	public void m1() {
		
		log.info("info hi");
		log.info("info hi again");
		log.warn("warn hi");
		log.fatal("fatal hi");
		log.error("error hi");
		log.debug("debug hi");
		log.trace("trace hi");
		
	}
}
