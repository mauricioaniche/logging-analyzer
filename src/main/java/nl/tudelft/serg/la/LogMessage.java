package nl.tudelft.serg.la;

import org.eclipse.jdt.core.dom.SimpleName;

public class LogMessage {

	private int qtyOfStrings;
	private int stringsLength;
	private int qtyOfVariables;
	private boolean hasException;
	private String exceptionType;
	
	public void addString(int length) {
		qtyOfStrings++;
		stringsLength += length;
	}

	public void addVar(SimpleName var) {
		qtyOfVariables++;
	}

	public int getQtyOfStrings() {
		return qtyOfStrings;
	}

	public int getStringsLength() {
		return stringsLength;
	}

	public int getQtyOfVariables() {
		return qtyOfVariables;
	}

	public boolean hasException() {
		return hasException;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	@Override
	public String toString() {
		return "LogMessage [qtyOfStrings=" + qtyOfStrings + ", stringsLength=" + stringsLength + ", qtyOfVariables="
				+ qtyOfVariables + ", hasException=" + hasException + ", exceptionType=" + exceptionType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exceptionType == null) ? 0 : exceptionType.hashCode());
		result = prime * result + (hasException ? 1231 : 1237);
		result = prime * result + qtyOfStrings;
		result = prime * result + qtyOfVariables;
		result = prime * result + stringsLength;
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
		LogMessage other = (LogMessage) obj;
		if (exceptionType == null) {
			if (other.exceptionType != null)
				return false;
		} else if (!exceptionType.equals(other.exceptionType))
			return false;
		if (hasException != other.hasException)
			return false;
		if (qtyOfStrings != other.qtyOfStrings)
			return false;
		if (qtyOfVariables != other.qtyOfVariables)
			return false;
		if (stringsLength != other.stringsLength)
			return false;
		return true;
	}

	public void addException(String type) {
		this.hasException = true;
		this.exceptionType = type;
	}
	
	
	
	
}
