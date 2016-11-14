package nl.tudelft.serg.la.historical;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

public class CommitInfoVisitor implements CommitVisitor{

	private Map<String, Calendar> dates;
	private Map<String, String> authors;
	private List<String> commits;
	private Calendar maxDate;
	
	public CommitInfoVisitor() {
		dates = new HashMap<>();
		authors = new HashMap<>();
		commits = new ArrayList<>();
		maxDate = new GregorianCalendar(1970, Calendar.JANUARY, 1);
	}
	
	@Override
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		dates.put(commit.getHash(), commit.getDate());
		authors.put(commit.getHash(), commit.getAuthor().getEmail());
		commits.add(commit.getHash());
		
		if(commit.getDate().after(maxDate))
			maxDate = commit.getDate();
	}

	@Override
	public String name() {
		return "info";
	}
	
	public Map<String, Calendar> getDates() {
		return dates;
	}

	public Map<String, String> getAuthors() {
		return authors;
	}
	
	public List<String> getCommits() {
		return commits;
	}
	
	public Calendar getMaxDate() {
		return maxDate;
	}
}
