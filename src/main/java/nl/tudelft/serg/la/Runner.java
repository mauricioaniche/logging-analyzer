package nl.tudelft.serg.la;

import nl.tudelft.serg.la.historical.HistoricalMetricsCalculator;
import nl.tudelft.serg.la.metric.LogMetricsCalculator;

public class Runner {

	public static void main(String[] args) {
		String path = args[0];
		String outputDir = args[1];
		
//		String path = "/Users/mauricioaniche/log-study/spring-framework";
//		String outputDir = "/Users/mauricioaniche/log-study/";
		
		LogMetricsCalculator metrics = new LogMetricsCalculator(path, outputDir);
		metrics.run();
		
		HistoricalMetricsCalculator historical = new HistoricalMetricsCalculator(path, outputDir);
		historical.run();
	}
}
