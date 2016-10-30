package nl.tudelft.serg.la.historical;

import java.util.HashMap;
import java.util.Map;

import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.domain.ModificationType;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.GitRepository;
import org.repodriller.scm.SCMRepository;

public class HistoricalLog implements Study, CommitVisitor {

	private String path;
	private Map<String, HistoricFile> files;

	public HistoricalLog(String path) {
		this.path = path;
		this.files = new HashMap<>();
	}

	@Override
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		for(Modification m : commit.getModifications()) {
			if(files.containsKey(m.getOldPath()) && m.getType().equals(ModificationType.RENAME)) {
				HistoricFile file = files.get(m.getOldPath());
				files.remove(m.getOldPath());
				files.put(m.getNewPath(), file);
			}
			
			if(!files.containsKey(m.getNewPath())) {
				files.put(m.getNewPath(), new HistoricFile(m.getNewPath()));
			}
			
			HistoricFile file = files.get(m.getNewPath());
			file.committed();
			
		}
	}

	@Override
	public String name() {
		return "historical";
	}

	@Override
	public void execute() {
		new RepositoryMining()
			.in(GitRepository.singleProject(path))
			.through(Commits.all())
			.process(this)
			.mine();
	}

}
