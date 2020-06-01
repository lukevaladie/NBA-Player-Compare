
import java.util.Hashtable;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.*;
import java.util.Set;
import java.text.Normalizer;

//class used to store a table of all players in a given season

public class PlayerTable {
	
	Hashtable<String, Player> table;
	private int season;

	public PlayerTable(int season){
		table = new Hashtable<String, Player>();
		this.season = season;
	}
	
	public int getSeason() {
		return season;
	}
	
	public void addPlayer(Player p) {
		String nameAndTeam = p.getName() + " - " + p.getTeam();
		table.put(nameAndTeam, p);
	}
	
	public Player getPlayer(String nameAndTeam) {
		return table.get(nameAndTeam);
	}
	
	public Set<String> getKeySet(){
		Set<String> s = table.keySet();
		return s;
	}
	

	//this method is used to scrape the player list from BBRef
	public static PlayerTable populateTable(String page, int season) {
		PlayerTable pt = new PlayerTable(season);
		try {
			//extracts the table and puts it into a string
			Document doc = Jsoup.connect(page).get();
			Elements playerTableHTML = doc.getElementsByClass("full_table");
			String playersHTML = playerTableHTML.toString();
			
			
			//for each player in the table, creates a Player object and adds to pt
			while(playersHTML.contains("<tr class=\"full_table\">")) {
				//extracts basic info
				String name = playersHTML.substring(playersHTML.indexOf(".html\">") + 7, playersHTML.indexOf("<", playersHTML.indexOf(".html\">") + 7));
				name = Normalizer.normalize(name, Normalizer.Form.NFD);
				name = name.replaceAll("[^\\p{ASCII}]", "");
				String pos = playersHTML.substring(playersHTML.indexOf("pos\">") + 5, playersHTML.indexOf("<", playersHTML.indexOf("pos\">") + 5));
				int age = Integer.parseInt(playersHTML.substring(playersHTML.indexOf("age\">") + 5, playersHTML.indexOf("<", playersHTML.indexOf("age\">") + 5)));
				String team = playersHTML.substring(playersHTML.indexOf("teams/") + 6, playersHTML.indexOf("/", playersHTML.indexOf("teams/") + 6));
				
				Player p = new Player(name, team, age, pos);
				
				
				//the rest is used to extract the per game stats for each player
				p.setGamesPlayed(Integer.parseInt(playersHTML.substring(playersHTML.indexOf("\"g\">") + 4, playersHTML.indexOf("<", playersHTML.indexOf("\"g\">") + 4))));
				p.setGamesStarted(Integer.parseInt(playersHTML.substring(playersHTML.indexOf("\"gs\">") + 5, playersHTML.indexOf("<", playersHTML.indexOf("\"gs\">") + 5))));
				p.setMinutesPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("mp_per_g\">") + 10, playersHTML.indexOf("<", playersHTML.indexOf("mp_per_g\">") + 10 ))));
				p.setFieldGoalsPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("fg_per_g\">") + 10, playersHTML.indexOf("<", playersHTML.indexOf("fg_per_g\">") + 10))));
				p.setFieldGoalAttemptsPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("fga_per_g\">") + 11, playersHTML.indexOf("<", playersHTML.indexOf("fga_per_g\">") + 11))));
				
				//the percentage fields are sometimes empty, so this has to be checked
				String fgpString = playersHTML.substring(playersHTML.indexOf("fg_pct\">") + 8, playersHTML.indexOf("<", playersHTML.indexOf("fg_pct\">") + 8));
				if(!fgpString.equals("")) {
					p.setFieldGoalPercentage(Double.parseDouble(fgpString));
				}
				
				p.setThreePointersPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("fg3_per_g\">") + 11, playersHTML.indexOf("<", playersHTML.indexOf("fg3_per_g\">") + 11))));
				p.setThreePointAttemptsPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("fg3a_per_g\">") + 12, playersHTML.indexOf("<", playersHTML.indexOf("fg3a_per_g\">") + 12))));
				
				String thppString = playersHTML.substring(playersHTML.indexOf("fg3_pct\">") + 9, playersHTML.indexOf("<", playersHTML.indexOf("fg3_pct\">") + 9));
				if(!thppString.equals("")) {
					p.setThreePointPercentage(Double.parseDouble(thppString));
				}
				
				p.setTwoPointersPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("fg2_per_g\">") + 11, playersHTML.indexOf("<", playersHTML.indexOf("fg2_per_g\">") + 11))));
				p.setTwoPointAttemptsPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("fg2a_per_g\">") + 12, playersHTML.indexOf("<", playersHTML.indexOf("fg2a_per_g\">") + 12))));
				
				String tppString = playersHTML.substring(playersHTML.indexOf("fg2_pct\">") + 9, playersHTML.indexOf("<", playersHTML.indexOf("fg2_pct\">") + 9));
				if(!tppString.contentEquals("")) {
					p.setTwoPointPercentage(Double.parseDouble(tppString));
				}
				
				String efgpString = playersHTML.substring(playersHTML.indexOf("efg_pct\">") + 9, playersHTML.indexOf("<", playersHTML.indexOf("efg_pct\">") + 9));
				if(!efgpString.equals("")) {
					p.setEffectiveFieldGoalPercentage(Double.parseDouble(efgpString));
				}
				
				
				p.setFreeThrowsPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("ft_per_g\">") + 10, playersHTML.indexOf("<", playersHTML.indexOf("ft_per_g\">") + 10))));
				p.setFreeThrowAttemptsPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("fta_per_g\">") + 11, playersHTML.indexOf("<", playersHTML.indexOf("fta_per_g\">") + 11))));
				
				String ftpString = playersHTML.substring(playersHTML.indexOf("ft_pct\">") + 8, playersHTML.indexOf("<", playersHTML.indexOf("ft_pct\">") + 8));
				if(!ftpString.equals("")) {
					p.setFreeThrowPercentage(Double.parseDouble(ftpString));
				}
				
				p.setOffensiveReboundsPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("orb_per_g\">") + 11, playersHTML.indexOf("<", playersHTML.indexOf("orb_per_g\">") + 11))));
				p.setDefensiveReboundsPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("drb_per_g\">") + 11, playersHTML.indexOf("<", playersHTML.indexOf("drb_per_g\">") + 11))));
				p.setTotalReboundsPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("trb_per_g\">") + 11, playersHTML.indexOf("<", playersHTML.indexOf("trb_per_g\">") + 11))));
				p.setAssistsPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("ast_per_g\">") + 11, playersHTML.indexOf("<", playersHTML.indexOf("ast_per_g\">") + 11))));
				p.setStealsPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("stl_per_g\">") + 11, playersHTML.indexOf("<", playersHTML.indexOf("stl_per_g\">") + 11))));
				p.setBlocksPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("blk_per_g\">") + 11, playersHTML.indexOf("<", playersHTML.indexOf("blk_per_g\">") + 11))));
				p.setTurnoversPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("tov_per_g\">") + 11, playersHTML.indexOf("<", playersHTML.indexOf("tov_per_g\">") + 11))));
				p.setPersonalFoulsPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("pf_per_g\">") + 10, playersHTML.indexOf("<", playersHTML.indexOf("pf_per_g\">") + 10))));
				p.setPointsPerGame(Double.parseDouble(playersHTML.substring(playersHTML.indexOf("pts_per_g\">") + 11, playersHTML.indexOf("<", playersHTML.indexOf("pts_per_g\">") + 11))));
				
				pt.addPlayer(p);
				
				playersHTML = playersHTML.substring(playersHTML.indexOf("</tr>", playersHTML.indexOf("<tr class=\"full_table\">")));
				
			}
			
			}catch(IOException i) {
				System.out.println("There's an IOException!");
			}
		
		return pt;
	}
	
	
	
	public static void main(String[] args) {
		PlayerTable t = populateTable("https://www.basketball-reference.com/leagues/NBA_2020_per_game.html", 2020);
		Set<String> keySet = t.getKeySet();
		System.out.println(keySet.size());
		for(String s : keySet) {
			System.out.println(s);
			Player p = t.getPlayer(s);
			System.out.print(p.getName() + " - ");
			System.out.println(p.getThreePointPercentage());
		}
	}
	
	
	
	
	
	
	
}
