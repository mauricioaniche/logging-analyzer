package nl.tudelft.serg.la.metric;

import org.apache.log4j.Logger;
import static org.junit.Assert.assertEquals;

public class Test2 {

	private static OtherThing log;
	
	public void m1() {
		
		log.info("info hi");
		log.info("info hi again");
		log.warn("warn hi");
		log.fatal("fatal hi");
		log.error("error hi");
		log.debug("debug hi");
		log.trace("trace hi");
		assertEquals("yeah, static invocation here", "yeah, static invocation here");
		
	}
}
