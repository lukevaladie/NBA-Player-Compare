
import java.util.Hashtable;
import java.io.*;
import java.nio.file.Files;
import java.util.Set;
import java.text.Normalizer;
import java.nio.file.*;

//class used to store a table of all players in a given season

public class PlayerTable {

	Hashtable<String, Player> table;
	private int season;

	public PlayerTable(int season) {
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

	public Set<String> getKeySet() {
		Set<String> s = table.keySet();
		return s;
	}

	public boolean containsKey(String key) {
		return getKeySet().contains(key);
	}

	// this method is used to scrape the player list from a document holding all
	// per-game box stats for a given season
	public static PlayerTable populateTable(Path path, int season) {
		PlayerTable pt = new PlayerTable(season);
		try {

			// extracts the table and puts it into a string
			String playerString = Files.readString(path);

			// all of this is extremely ugly string parsing in order to extract all the
			// stats
			while (playerString.contains(",")) {

				String name = playerString.substring(playerString.indexOf(",") + 1,
						playerString.indexOf("\\", playerString.indexOf(",") + 1));
				// used to get rid of any accents or special characters for formatting purposes
				name = Normalizer.normalize(name, Normalizer.Form.NFD);
				name = name.replaceAll("[^\\p{ASCII}]", "");
				if (name.substring(name.length() - 1).equals("*")) {
					name = name.substring(0, name.length() - 1);
				}

				playerString = playerString.substring(playerString.indexOf("\\"));

				String position = playerString.substring(playerString.indexOf(",") + 1,
						playerString.indexOf(",", playerString.indexOf(",") + 1));
				playerString = playerString.substring(playerString.indexOf(position) + position.length() + 1);

				int age = Integer.parseInt(playerString.substring(0, 2));
				playerString = playerString.substring(3);

				String team = playerString.substring(0, 3);
				playerString = playerString.substring(4);

				Player p = new Player(name, team, age, position);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setGamesPlayed(Integer.parseInt(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setGamesStarted(Integer.parseInt(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setMinutesPerGame(Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setFieldGoalsPerGame(Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setFieldGoalAttemptsPerGame(
							Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setFieldGoalPercentage(Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setThreePointersPerGame(Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setThreePointAttemptsPerGame(
							Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setThreePointPercentage(Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setTwoPointersPerGame(Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setTwoPointAttemptsPerGame(
							Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setTwoPointPercentage(Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setEffectiveFieldGoalPercentage(
							Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setFreeThrowsPerGame(Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setFreeThrowAttemptsPerGame(
							Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setFreeThrowPercentage(Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setOffensiveReboundsPerGame(
							Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setDefensiveReboundsPerGame(
							Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setTotalReboundsPerGame(Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setAssistsPerGame(Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setStealsPerGame(Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setBlocksPerGame(Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setTurnoversPerGame(Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setPersonalFoulsPerGame(Double.parseDouble(playerString.substring(0, playerString.indexOf(","))));
				}

				playerString = playerString.substring(playerString.indexOf(",") + 1);

				if (!playerString.substring(0, 1).equals(",")) {
					p.setPointsPerGame(Double.parseDouble(playerString.substring(0, playerString.indexOf(".") + 2)));
				}

				playerString = playerString.substring(playerString.indexOf("."));

				pt.addPlayer(p);

			}

		} catch (IOException i) {
			System.out.println("There's an IOException!");
		}

		return pt;
	}

}
