package nl.tudelft.serg.la;

import nl.tudelft.serg.la.historical.HistoricalLog;
import nl.tudelft.serg.la.metric.LogMetricsCalculator;

public class Runner {

	public static void main(String[] args) {
		LogMetricsCalculator metrics = new LogMetricsCalculator(args[0], args[1]);
		metrics.run();
		
		HistoricalLog historic = new HistoricalLog(args[0]);
	}
}
