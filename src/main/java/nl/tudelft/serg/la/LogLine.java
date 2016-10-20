package nl.tudelft.serg.la;

public class LogLine {

	private LogLevel level;
	private String position;
	
	public LogLine(LogLevel level, String position) {
		this.level = level;
		this.position = position;
	}

	public LogLevel getLevel() {
		return level;
	}

	public String getPosition() {
		return position;
	}

	@Override
	public String toString() {
		return "LogLine [level=" + level + ", position=" + position + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogLine other = (LogLine) obj;
		if (level != other.level)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
	
}
