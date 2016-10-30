package nl.tudelft.serg.la.historical;

import java.util.HashMap;
import java.util.Map;

import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.domain.ModificationType;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

public class HistoricalLogVisitor implements CommitVisitor {

	private Map<String, HistoricFile> files;

	public HistoricalLogVisitor() {
		this.files = new HashMap<>();
	}

	@Override
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		for(Modification m : commit.getModifications()) {
			if(!m.fileNameEndsWith("java")) continue;
			
			if(files.containsKey(m.getOldPath()) && m.getType().equals(ModificationType.RENAME)) {
				HistoricFile file = files.get(m.getOldPath());
				files.remove(m.getOldPath());
				files.put(m.getNewPath(), file);
			}
			
			if(!files.containsKey(m.getNewPath())) {
				files.put(m.getNewPath(), new HistoricFile(m.getNewPath()));
			}
			
			HistoricFile file = files.get(m.getNewPath());
			
			LogDiffAnalyzer analyzer = new LogDiffAnalyzer();
			LogAnalysisResult result = analyzer.analyze(m.getDiff());
			file.committed(commit.getHash(), commit.getDate(), result);
			
		}
	}

	@Override
	public String name() {
		return "historical";
	}

	public Map<String, HistoricFile> getFiles() {
		return files;
	}

}
