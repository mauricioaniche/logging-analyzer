package nl.tudelft.serg.la.metric;

import org.apache.log4j.Logger;

public class Test1 {

	private static Logger log = Logger.getLogger(Test1.class);
	
	public void m1() {

		int a = 10;
		log.info("inside a method");
		
		if(log.isInfoEnabled())
			log.info("inside an if without brackets");
		else {
			log.info("inside an else");
		}
		
		for(int i = 0; i < 10; i++) {
			log.info("inside a for");
		}
		
		switch(a) {
		case 10:
			log.info("inside a case");
		default:
			log.info("inside a case default");
		}
		
		int b[] = { 1, 2, 3} ;
		
		for(int bb : b) {
			log.info("inside a for each");
		}
		
		while(true) {
			log.info("inside a while");
		}
		
		do {
			log.info("inside a do");
		} while(true);
	}
}
