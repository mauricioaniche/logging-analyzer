package nl.tudelft.serg.la.historical;

public class HistoricFile {

	private String file;
	private int commits;
	private int logAdds;
	private int logDels;
	
	public HistoricFile(String file) {
		this.file = file;
	}
	
	public void committed() {
		commits++;
	}
}
