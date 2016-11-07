package nl.tudelft.serg.la.metric;

import org.apache.log4j.Logger;
import static org.junit.Assert.assertEquals;

public class Test2 {

	private static OtherThing other;
	
	public void m1() {
		
		other.info("info hi");
		other.info("info hi again");
		other.warn("warn hi");
		other.fatal("fatal hi");
		other.error("error hi");
		other.debug("debug hi");
		other.trace("trace hi");
		assertEquals("yeah, static invocation here", "yeah, static invocation here");
		
	}
}
