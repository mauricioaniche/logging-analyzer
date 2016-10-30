package nl.tudelft.serg.la;

import java.util.Calendar;

import org.apache.log4j.Logger;

import nl.tudelft.serg.la.historical.HistoricalMetricsCalculator;
import nl.tudelft.serg.la.metric.LogMetricsCalculator;

public class Runner {

	private static Logger log = Logger.getLogger(Runner.class);
	
	public static void main(String[] args) {
		
		Calendar started = Calendar.getInstance();
		
		String path = args[0];
		String outputDir = args[1];
		
		LogMetricsCalculator metrics = new LogMetricsCalculator(path, outputDir);
		metrics.run();
		
		HistoricalMetricsCalculator historical = new HistoricalMetricsCalculator(path, outputDir);
		historical.run();

		Calendar ended = Calendar.getInstance();
		
		long diff = ended.getTime().getTime() - started.getTime().getTime();
		long diffMinutes = diff / (60 * 1000);
		long diffHours = diff / (60 * 60 * 1000);                      
		log.info("It took " + diffMinutes + " minutes (~" + diffHours + "h).");         
	}
}
