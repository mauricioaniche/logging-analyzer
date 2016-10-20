package nl.tudelft.serg.la;

public class LogLine {

	private LogLevel level;
	private String position;
	private int lineNumber;
	
	public LogLine(LogLevel level, String position, int lineNumber) {
		this.level = level;
		this.position = position;
		this.lineNumber = lineNumber;
	}

	public LogLevel getLevel() {
		return level;
	}

	public String getPosition() {
		return position;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	@Override
	public String toString() {
		return "LogLine [level=" + level + ", position=" + position + ", lineNumber=" + lineNumber + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + lineNumber;
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
		if (lineNumber != other.lineNumber)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
	
}
