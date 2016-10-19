package nl.tudelft.serg.la;

public enum LogLevel {

	TRACE(1),
	DEBUG(2),
	INFO(3),
	WARN(4),
	ERROR(5),
	FATAL(6);

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
}
