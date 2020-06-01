
import java.util.Vector;
import java.util.Set;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.*;

//This class is used to find the player most comparable to a given player in a single season

public class FindClosestPlayer {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		//used to hold all the needed NBA seasons. Each PlayerTable contains the stats of every player that played
		//during that season.
		Vector<PlayerTable> seasons = new Vector<PlayerTable>();
		
//		System.out.println("Enter the directory where player data is stored (likely \"/Users/yourname/Documents/PlayerData/\")");
//		
//		Path folder = Paths.get(s.nextLine());
		
		System.out.println("Enter the first year you'd like to load (1980 - 2020):");
		
		int firstYear = Integer.parseInt(s.nextLine());
		
		System.out.println("Enter the last year you'd like to load (1980 - 2020):");
		
		int lastYear = Integer.parseInt(s.nextLine());
		
		System.out.println("Loading player stats...");
		
		for(int x = firstYear; x <= lastYear; x++) {
			
			PlayerTable pt = PlayerTable.populateTable(Paths.get("/Users/lukev/Documents/PlayerData/" + x + ".txt"), x);
			seasons.add(pt);
		
		}
		
		while(true) {
			System.out.println("Enter the full name of the player you'd like to find a comparison for, followed by a dash and their team abbreviation (i.e \"Kawhi Leonard - TOR\")");
			String nameAndTeam = s.nextLine();
			System.out.println("Enter a particular season your chosen player played in (i.e. \"2020\")");
			int year = Integer.parseInt(s.nextLine());
			int yearSpan = lastYear - firstYear;
			//finds the index of the chosen year in the Vector
			int yearIndex = yearSpan - (lastYear - year);
			
			Player chosen = seasons.get(yearIndex).getPlayer(nameAndTeam);
			
			String name = chosen.getName();
			
			System.out.println("Your Player:");
			System.out.println(year + " season");
			System.out.println(chosen.toString());
			System.out.println("Finding closest player in specified year range...");
			
			
			//definitely a more efficient way to do this, but this just gives comp and deviation initial values
			Set<String> firstYearKeys = seasons.get(0).getKeySet();
			Vector<String> firstYearKeysVector = new Vector<String>();
			for(String k : firstYearKeys) {
				firstYearKeysVector.add(k);
			}
			
			Player comp = seasons.get(0).getPlayer(firstYearKeysVector.get(0));
			double deviation = Player.getAvgDeviation(chosen, comp);
			int compYear = firstYear;
			
			for(PlayerTable pt : seasons) {
				Set<String> keys = pt.getKeySet();
				for(String player : keys) {
					double currentDeviation = Player.getAvgDeviation(chosen, pt.getPlayer(player));
					if(currentDeviation < deviation && !name.equals(pt.getPlayer(player).getName())) {
						comp =  pt.getPlayer(player);
						deviation = currentDeviation;
						compYear = pt.getSeason();
					}
				}
			}
			
			System.out.println("Average statistical deviation: " + deviation);
			System.out.println(compYear + " season");
			System.out.println(comp.toString());
		}
		

	}

}
