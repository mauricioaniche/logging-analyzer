package nl.tudelft.serg.la.historical;

import java.util.HashMap;
import java.util.Map;

import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

public class BugVisitor implements CommitVisitor{

	private Map<String, Boolean> bugs;
	
	public BugVisitor() {
		bugs = new HashMap<>();
	}
	
	@Override
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		bugs.put(commit.getHash(), hasBug(commit.getMsg()));
	}

	private Boolean hasBug(String msg) {
		String message = msg.toLowerCase();
		return message.contains("error") || message.contains("bug") || message.contains("fix")
				|| message.contains("issue") || message.contains("mistake") || message.contains("incorrect")
				|| message.contains("fault") || message.contains("defect") || message.contains("flaw")
				|| message.contains("typo");
	}

	@Override
	public String name() {
		return "bug";
	}

	public Map<String, Boolean> getBugs() {
		return bugs;
	}
}
