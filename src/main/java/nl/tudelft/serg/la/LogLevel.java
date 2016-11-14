package nl.tudelft.serg.la;

public enum LogLevel {

	// all logging tools...
	TRACE(1),
	DEBUG(2),
	INFO(3),
	WARN(4),
	ERROR(5),
	FATAL(6),

	// ... but java.util.Logging.Level is different!
	SEVERE(5),
	WARNING(4),
	CONFIG(2),
	FINE(1),
	FINER(0),
	FINEST(-1);

	private int weight;
	
	LogLevel(int weight) {
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}
	
	public static boolean isLogLevel(String name) {
		for(LogLevel level : LogLevel.values()) {
			String nameInCaps = name.toUpperCase();
			if(level.name().toUpperCase().equals(nameInCaps)) return true;
		}
		
		return false;
	}

	public static LogLevel valueFor(String name) {
		for(LogLevel level : LogLevel.values()) {
			String nameInCaps = name.toUpperCase();
			if(level.name().toUpperCase().equals(nameInCaps)) return level;
		}
		return null;
	}
	
	public static String direction(String from, String to) {
		LogLevel levelFrom = Enum.valueOf(LogLevel.class, from);
		LogLevel levelTo = Enum.valueOf(LogLevel.class, to);
		
		if(levelFrom.getWeight() > levelTo.getWeight()) return "increased";
		else if(levelFrom.getWeight() < levelTo.getWeight()) return "reduced";
		else return "same";
	}
}
