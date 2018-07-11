import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TeamGenerator {

	public static void main(String[] args) throws FileNotFoundException {
		
		HashMap<String,Participant> participants = new HashMap<String,Participant>();
		
		Scanner scanner = new Scanner(new File("/Users/benjholla/Desktop/emails.csv"));
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] values = line.split(",");
			Participant participant = new Participant();
			String name = values[1];
			participant.setName(name);
			String email = values[2].toLowerCase().trim();
			participant.setEmail(email);
			String designation = values[3];
			participant.setDesignation(designation);
			String home = values[4];
			participant.setHome(home);
			participants.put(email, participant);
		}
		
		scanner = new Scanner(new File("/Users/benjholla/Desktop/prefs.csv"));
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] values = line.split(",");
			String email = values[1].toLowerCase().trim();
			Participant participant = participants.get(email);
			if(participant == null) {
				System.err.println("Email " + email + " did not complete preferences form.");
				continue;
			}
			participant.setParticipating(values[2].toLowerCase().contains("yes"));
			participant.setAttending(values[3].toLowerCase().contains("yes"));
			participant.setBinaryExploitation(Integer.parseInt(values[4].toLowerCase()));
			participant.setWebExploitation(Integer.parseInt(values[5].toLowerCase()));
			participant.setCpp(Integer.parseInt(values[6].toLowerCase()));
			participant.setJava(Integer.parseInt(values[7].toLowerCase()));
			participant.setLevel(values[8].toLowerCase());
		}
		
		int numTeams = 6;
		Team offsiteTeam = new Team();
		Team staffTeam = new Team();
		ArrayList<Team> teams = new ArrayList<Team>(numTeams);
		ArrayList<Participant> partipantsToAssign = new ArrayList<Participant>(participants.values());
		Collections.shuffle(partipantsToAssign); // shuffle
		// organized broadly into education levels
		Collections.sort(partipantsToAssign, new Comparator<Participant>() {
			@Override
			public int compare(Participant p1, Participant p2) {
				return Integer.compare(p1.getLevel(), p2.getLevel());
			}
		});
		
		while(!partipantsToAssign.isEmpty()) {
			Participant participant = partipantsToAssign.remove(0);
			if(participant.isAttending()) {
				if(participant.getLevel() == 3 && participant.getHome().toLowerCase().contains("mnit")) {
					staffTeam.addMember(participant);
				} else {
					Collections.sort(teams);
					if(teams.size() < numTeams) {
						Team team = new Team();
						team.addMember(participant);
						teams.add(team);
					} else {
						Team team = teams.get(0);
						team.addMember(participant);
					}
				}
			} else {
				offsiteTeam.addMember(participant);
			}
		}
		
		System.out.println("Staff Team: " + staffTeam.toString());
		System.out.println("Offsite Team: " + offsiteTeam.toString());
		for(int i=0; i<teams.size(); i++) {
			System.out.println("Team " + (i+1) + ": " + teams.get(i).toString());
		}
	}

	private static class Team implements Comparable<Team> {

		private Set<Participant> members = new HashSet<Participant>();
		
		public void addMember(Participant participant) {
			members.add(participant);
		}
		
		public int getWeight() {
			int result = 0;
			for(Participant member : members) {
				result += member.getWeight();
			}
			return result;
		}
		
		@Override
		public int compareTo(Team o) {
			return Integer.compare(this.getWeight(), o.getWeight());
		}
		
		public String toString() {
			return "[size: " + members.size() + ", weight: " + getWeight() + ", members: " + members.toString() + "]";
		}
	}
	
	private static class Participant implements Comparable<Participant> {
		private String name;
		private String email;
		private String home;
		private boolean participating;
		private boolean attending;
		private int binaryExploitation;
		private int webExploitation;
		private int cpp;
		private int java;
		private int level;
		
		public String toString() {
			return "\"" + name + "\" <" + email + ">";
		}
		
		public String getHome() {
			return home;
		}

		public void setHome(String home) {
			this.home = home;
		}
		
		public int getJava() {
			return java;
		}

		public int getWeight() {
			if(participating && attending) {
				return binaryExploitation + (2 * webExploitation) + cpp + (2 * java) + level;
			} else {
				return 0;
			}
		}

		public void setJava(int java) {
			this.java = java;
		}
		
		public void setName(String name) {
			this.name = name;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void setDesignation(String designation) {
			this.home = designation;
		}

		public void setParticipating(boolean participating) {
			this.participating = participating;
		}

		public void setAttending(boolean attending) {
			this.attending = attending;
		}

		public void setBinaryExploitation(int binaryExploitation) {
			this.binaryExploitation = binaryExploitation;
		}

		public void setWebExploitation(int webExploitation) {
			this.webExploitation = webExploitation;
		}

		public void setCpp(int cpp) {
			this.cpp = cpp;
		}

		public void setLevel(String level) {
			if(level.equalsIgnoreCase("Undergraduate")) {
				this.level = 1;
			} else if(level.equalsIgnoreCase("Masters")) {
				this.level = 2;
			} else if(level.equalsIgnoreCase("PhD")) {
				this.level = 3;
			} else if(level.equalsIgnoreCase("Post")) {
				this.level = 4;
			}
		}

		public String getName() {
			return name;
		}

		public String getEmail() {
			return email;
		}

		public String getDesignation() {
			return home;
		}

		public boolean isParticipating() {
			return participating;
		}

		public boolean isAttending() {
			return attending;
		}

		public int getBinaryExploitation() {
			return binaryExploitation;
		}

		public int getWebExploitation() {
			return webExploitation;
		}

		public int getCpp() {
			return cpp;
		}

		public int getLevel() {
			return level;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((email == null) ? 0 : email.hashCode());
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
			Participant other = (Participant) obj;
			if (email == null) {
				if (other.email != null)
					return false;
			} else if (!email.equals(other.email))
				return false;
			return true;
		}
		
		@Override
		public int compareTo(Participant p) {
			return Integer.compare(this.getWeight(), p.getWeight());
		}
	}
}
