
import java.util.Vector;
import java.util.Set;

import java.util.Scanner;

import java.nio.file.*;

//This class is used to find the player most comparable to a given player in a single season

public class FindClosestPlayer {
	
	//These ints mark the bounds of the seasons loaded into the program
	private static final int EARLIEST_YEAR = 1980;
	private static final int LATEST_YEAR = 2020;

	public static void main(String[] args) {
		
		
		Scanner s = new Scanner(System.in);
		
		//used to hold all the needed NBA seasons. Each PlayerTable contains the stats of every player that played
		//during that season.
		Vector<PlayerTable> seasons = new Vector<PlayerTable>();
		
		System.out.println("Loading player stats...");
		
		//Populate seasons with a PlayerTable for every season
		for(int x = EARLIEST_YEAR; x <= LATEST_YEAR; x++) {
					
			PlayerTable pt = PlayerTable.populateTable(Paths.get("/Users/lukev/Documents/PlayerData/" + x + ".txt"), x);
			seasons.add(pt);
				
			}
		
//		System.out.println("Enter the directory where player data is stored (likely \"/Users/yourname/Documents/PlayerData/\")");
//		
//		Path folder = Paths.get(s.nextLine());
		
		
		
		
		
		while(true) {
			
			Player chosen = new Player();
			int year = 0;
			int yearSpan = LATEST_YEAR - EARLIEST_YEAR;
			
			boolean validPlayerAndSeasonEntered = false;
			
			while(!validPlayerAndSeasonEntered) {
			
			System.out.println("Enter the full name of the player you'd like to find a comparison for, followed by a dash and their team abbreviation (i.e \"Kawhi Leonard - TOR\")");
			String nameAndTeam = s.nextLine();
			System.out.println("Enter a particular season your chosen player played in while on your specified team (the second year of the season, i.e. \"2019\" for the 2018-19 season)");
			year = Integer.parseInt(s.nextLine());
			//finds the index of the chosen year in the Vector
			int yearIndex = yearSpan - (LATEST_YEAR - year);
			
			
			if(seasons.get(yearIndex).containsKey(nameAndTeam)) {
				chosen = seasons.get(yearIndex).getPlayer(nameAndTeam);
				validPlayerAndSeasonEntered = true;
			}else {
				System.out.println("Invalid input. You must enter a player and team in the appropriate format as well as a year in which they played on that team");
			}
			
			}
			
			boolean validYearEntered = false;
			int firstYear = 0;
			int lastYear = 0;
			
			while(!validYearEntered) {
			
				System.out.println("Enter the first year of the timespan in which you'd like to find a comparison (1980 - 2020):");
			
				firstYear = Integer.parseInt(s.nextLine());
				
				if(firstYear <= LATEST_YEAR && firstYear >= EARLIEST_YEAR) {
					validYearEntered = true;
				}else {
					System.out.println("Invalid input. The first year must be within the range of the available data");
				}
			
			
			
			}
			
			validYearEntered = false;
			
			while(!validYearEntered) {
			
				System.out.println("Enter the last year of the timespan in which you'd like to find a comparison (1980 - 2020):");
			
				lastYear = Integer.parseInt(s.nextLine());
				
				if(lastYear >= firstYear && lastYear <= LATEST_YEAR && lastYear >= EARLIEST_YEAR) {
					validYearEntered = true;
				}else {
					System.out.println("Invalid input. The last year must be later than or equal to the first year and within the range of the available data");
				}
			
			}
			
			System.out.println("Loading player stats...");
			
			
			
			String name = chosen.getName();
			
			System.out.println("Your Player:");
			System.out.println(year + " season");
			System.out.println(chosen.toString());
			System.out.println("Most similar player in specified year range:");
			
			
			//definitely a more efficient way to do this, but this just gives comp and deviation initial values
			Set<String> firstYearKeys = seasons.get(yearSpan - (LATEST_YEAR - firstYear)).getKeySet();
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
