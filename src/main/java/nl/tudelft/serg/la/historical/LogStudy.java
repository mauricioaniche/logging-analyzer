package nl.tudelft.serg.la.historical;

import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.scm.GitRepository;

public class LogStudy implements Study {

	private String path;
	private HistoricalLogVisitor visitor1;
	private CommitInfoVisitor visitor2;
	
	public LogStudy(String path, HistoricalLogVisitor visitor1, CommitInfoVisitor visitor2) {
		this.path = path;
		this.visitor1 = visitor1;
		this.visitor2 = visitor2;
		
	}
	@Override
	public void execute() {
		new RepositoryMining()
			.in(GitRepository.singleProject(path))
			.through(Commits.all())
			.process(visitor1)
			.process(visitor2)
			.mine();
	}
	
}
