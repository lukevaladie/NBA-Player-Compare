
import java.util.Vector;

import javax.swing.*;
import java.awt.*;

import java.util.Set;

import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.*;

//This class is used to find the player most comparable to a given player in a single season

public class ClosestPlayerFinder {

	// These ints mark the bounds of the seasons loaded into the program
	private static final int EARLIEST_YEAR = 1980;
	private static final int LATEST_YEAR = 2020;
	Vector<PlayerTable> seasons;
	Scanner s;

	// on startup, load the player data into a vector of PlayerTables
	public ClosestPlayerFinder() {
		s = new Scanner(System.in);
		// used to hold all the needed NBA seasons. Each PlayerTable contains the stats
		// of every player that played
		// during that season.
		seasons = new Vector<PlayerTable>();

		// Populate seasons with a PlayerTable for every season
		for (int x = EARLIEST_YEAR; x <= LATEST_YEAR; x++) {

			PlayerTable pt = PlayerTable.populateTable(Paths.get("/Users/lukev/Documents/PlayerData/" + x + ".txt"), x);
			seasons.add(pt);

		}

		JFrame frame = new JFrame();
		frame.setTitle("NBA Player Comparison Engine");
		frame.setSize(600, 500);
		frame.setLayout(new BorderLayout());

		JPanel northPanel = new JPanel();
		northPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 45, 15));

		JLabel nameLabel = new JLabel("Player: ");
		JTextField playerInput = new JTextField(30);
		JLabel teamLabel = new JLabel("Team Abbreviation: ");
		JTextField teamInput = new JTextField(3);
		JLabel seasonLabel = new JLabel("Season: ");
		JTextField seasonInput = new JTextField(4);
		JLabel firstSeasonLabel = new JLabel("First year of search range: ");
		JTextField firstSeasonInput = new JTextField(4);
		JLabel lastSeasonLabel = new JLabel("Last year of search range: ");
		JTextField lastSeasonInput = new JTextField(4);

		JButton findComp = new JButton("Find comparable player in search range");
		findComp.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// check inputs first
				findComp(playerInput.getText(), teamInput.getText(), Integer.parseInt(seasonInput.getText()),
						Integer.parseInt(firstSeasonInput.getText()), Integer.parseInt(lastSeasonInput.getText()));
			}

		});

		northPanel.add(nameLabel);
		northPanel.add(playerInput);
		northPanel.add(teamLabel);
		northPanel.add(teamInput);
		northPanel.add(seasonLabel);
		northPanel.add(seasonInput);
		northPanel.add(firstSeasonLabel);
		northPanel.add(firstSeasonInput);
		northPanel.add(lastSeasonLabel);
		northPanel.add(lastSeasonInput);
		northPanel.add(findComp);

		JPanel westPanel = new JPanel();
		westPanel.setBorder(BorderFactory.createEmptyBorder(15, 5, 10, 5));
		JTextArea chosenPlayerStats = new JTextArea(20, 28);
		chosenPlayerStats.setEditable(false);
		westPanel.add(chosenPlayerStats);

		JPanel eastPanel = new JPanel();
		eastPanel.setBorder(BorderFactory.createEmptyBorder(15, 5, 10, 5));
		JTextArea compStats = new JTextArea(20, 28);
		compStats.setEditable(false);
		eastPanel.add(compStats);

		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(westPanel, BorderLayout.WEST);
		frame.add(eastPanel, BorderLayout.EAST);

		frame.setVisible(true);
	}

	private void findComp(String name, String team, int season, int firstYear, int lastYear) {

		// will be used to hold the user's chosen player
		Player chosen = new Player();

	}

	public static void main(String[] args) {

		while (true) {

			// will hold a particular season for the selected player
			int year = 0;

			int yearSpan = LATEST_YEAR - EARLIEST_YEAR;

			boolean validPlayerAndSeasonEntered = false;

			// loop until the user gives a valid input
			while (!validPlayerAndSeasonEntered) {

				System.out.println(
						"Enter the full name of the player you'd like to find a comparison for, followed by a dash and their team abbreviation (i.e \"Kawhi Leonard - TOR\")");
				String nameAndTeam = s.nextLine();
				System.out.println(
						"Enter a particular season your chosen player played in while on your specified team (the second year of the season, i.e. \"2019\" for the 2018-19 season)");
				year = Integer.parseInt(s.nextLine());

				// finds the index of the chosen year in the Vector
				int yearIndex = yearSpan - (LATEST_YEAR - year);

				if (seasons.get(yearIndex).containsKey(nameAndTeam)) {
					chosen = seasons.get(yearIndex).getPlayer(nameAndTeam);
					validPlayerAndSeasonEntered = true;
				} else {
					System.out.println(
							"Invalid input. You must enter a player and team in the appropriate format as well as a year in which they played on that team");
				}

			}

			boolean validYearEntered = false;
			int firstYear = 0;
			int lastYear = 0;

			// loop until the user enters a valid year
			while (!validYearEntered) {

				System.out.println(
						"Enter the first year of the timespan in which you'd like to find a comparison (1980 - 2020):");

				firstYear = Integer.parseInt(s.nextLine());

				if (firstYear <= LATEST_YEAR && firstYear >= EARLIEST_YEAR) {
					validYearEntered = true;
				} else {
					System.out.println("Invalid input. The first year must be within the range of the available data");
				}

			}

			validYearEntered = false;

			while (!validYearEntered) {

				System.out.println(
						"Enter the last year of the timespan in which you'd like to find a comparison (1980 - 2020):");

				lastYear = Integer.parseInt(s.nextLine());

				if (lastYear >= firstYear && lastYear <= LATEST_YEAR && lastYear >= EARLIEST_YEAR) {
					validYearEntered = true;
				} else {
					System.out.println(
							"Invalid input. The last year must be later than or equal to the first year and within the range of the available data");
				}

			}

			System.out.println("Loading player stats...");

			String name = chosen.getName();

			System.out.println("Your Player:");
			System.out.println(year + " season");
			System.out.println(chosen.toString());
			System.out.println("Most similar player in specified year range:");

			// Gives comp and deviation initial values. By default, they are set to the
			// first player in the first specified season.
			Set<String> firstYearKeys = seasons.get(yearSpan - (LATEST_YEAR - firstYear)).getKeySet();
			Vector<String> firstYearKeysVector = new Vector<String>();
			for (String k : firstYearKeys) {
				firstYearKeysVector.add(k);
			}

			Player comp = seasons.get(yearSpan - (LATEST_YEAR - firstYear)).getPlayer(firstYearKeysVector.get(0));
			double deviation = Player.getAvgDeviation(chosen, comp);

			// this will be used to display the season in which the comparable player played
			int compYear = firstYear;

			// A vector that will only hold the user's specified seasons
			Vector<PlayerTable> selectedSeasons = new Vector<PlayerTable>();

			// populated selectedSeasons
			for (int x = firstYear; x <= lastYear; x++) {
				selectedSeasons.add(seasons.get(yearSpan - (LATEST_YEAR - x)));
			}

			// finds the most comparable player within the specified date range
			for (PlayerTable pt : selectedSeasons) {
				Set<String> keys = pt.getKeySet();
				for (String player : keys) {
					double currentDeviation = Player.getAvgDeviation(chosen, pt.getPlayer(player));
					if (currentDeviation < deviation && !name.equals(pt.getPlayer(player).getName())) {
						comp = pt.getPlayer(player);
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
