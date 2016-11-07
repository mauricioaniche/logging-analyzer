package nl.tudelft.serg.la.metric;

import org.apache.log4j.Logger;

public class Test2 {

	private static OtherThing otherStuff;
	
	public void m1() {
		
		otherStuff.info("info hi");
		otherStuff.info("info hi again");
		otherStuff.warn("warn hi");
		otherStuff.fatal("fatal hi");
		otherStuff.error("error hi");
		otherStuff.debug("debug hi");
		otherStuff.trace("trace hi");
		
	}
}
