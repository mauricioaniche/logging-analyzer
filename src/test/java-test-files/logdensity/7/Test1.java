package nl.tudelft.serg.la.metric;

import org.apache.log4j.Logger;

public class Test1 {

	private static Logger log = Logger.getLogger(Test1.class);
	
	public void m1() {

		int a = 10;
		
		if(a > 10) {
			if(a>20) {
				for(int i = 0 ; i < 10; i++) {
					log.info("this is a log inside of a for");
				}
			}
			
		}
	}
}
