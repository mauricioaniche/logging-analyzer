package nl.tudelft.serg.la.historical;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

public class CommitInfoVisitor implements CommitVisitor{

	private Map<String, Calendar> dates;
	
	public CommitInfoVisitor() {
		dates = new HashMap<>();
	}
	
	@Override
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		dates.put(commit.getHash(), commit.getDate());
	}

	@Override
	public String name() {
		return "info";
	}
	
	public Map<String, Calendar> getDates() {
		return dates;
	}

}
