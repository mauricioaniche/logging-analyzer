package nl.tudelft.serg.la;

public class LogStatement {

	private LogLevel level;
	private String position;
	private int lineNumber;
	private LogMessage message;
	
	public LogStatement(LogLevel level, String position, int lineNumber, LogMessage message) {
		this.level = level;
		this.position = position;
		this.lineNumber = lineNumber;
		this.message = message;
	}

	public LogStatement(LogLevel level, String position, int lineNumber) {
		this(level, position, lineNumber, new LogMessage());
	}

	public LogMessage getMessage() {
		return message;
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
		return "LogStatement [level=" + level + ", position=" + position + ", lineNumber=" + lineNumber + ", message="
				+ message + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + lineNumber;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		LogStatement other = (LogStatement) obj;
		if (level != other.level)
			return false;
		if (lineNumber != other.lineNumber)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}


	
}
