package nl.tudelft.serg.la.metric;

import static a.b.c;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class Test1 {

	private static final Log log = LogFactory.getLog("AspectJ Weaver");
	
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
