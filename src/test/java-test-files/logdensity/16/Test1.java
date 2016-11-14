package nl.tudelft.serg.la.metric;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Test1 {

	private static Logger log = Logger.getLogger(Test1.class);
	
	public void m1() {
		
		Exception e;
		
		log.log(Level.DEBUG, "hi hi hi");
		log.log(Level.TRACE, "hi hi hi", e);
		
		log.nothing(Level.DEBUG, "hihihi");
		a.b(Level.TRACE, "hihihi");
	}
}
