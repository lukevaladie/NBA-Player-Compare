
import java.util.Vector;

import javax.swing.*;

import java.util.Set;

import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//This class is used to find the player most comparable to a given player in a single season

public class ClosestPlayerFinder {

	// These ints mark the bounds of the seasons loaded into the program
	private static final int EARLIEST_YEAR = 1980;
	private static final int LATEST_YEAR = 2020;
	Vector<PlayerTable> seasons;
	Scanner s;

	// Declarations for GUI elements that are modified by multiple methods
	JTextArea chosenPlayerStats;
	JTextArea compStats;
	JFrame frame;
	JTextArea deviationDisplay;
	JTextField playerInput;
	JTextField teamInput;
	JTextField seasonInput;
	JTextField firstSeasonInput;
	JTextField lastSeasonInput;

	// on startup, load the player data into a vector of PlayerTables
	public ClosestPlayerFinder() {

		// creates the window
		frame = new JFrame();
		frame.setTitle("NBA Comparison Finder");
		frame.setSize(600, 500);
		frame.setLayout(new BorderLayout());

		// creates a panel that holds all input fields and the button
		JPanel northPanel = new JPanel();
		northPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 45, 15));

		// declarations and assignments for GUI components
		JLabel nameLabel = new JLabel("Player: ");
		playerInput = new JTextField(30);
		JLabel teamLabel = new JLabel("Team Abbreviation: ");
		teamInput = new JTextField(3);
		JLabel seasonLabel = new JLabel("Season: ");
		seasonInput = new JTextField(4);
		JLabel firstSeasonLabel = new JLabel("First year of search range: ");
		firstSeasonInput = new JTextField(4);
		JLabel lastSeasonLabel = new JLabel("Last year of search range: ");
		lastSeasonInput = new JTextField(4);

		// creates a button and tells it what to do when clicked
		JButton findComp = new JButton("Loading data, please wait...");
		findComp.setEnabled(false);
		findComp.addActionListener(new ActionListener() {

			// when the button is clicked, check that the inputs are of a valid form
			// before passing their content to findComp.
			public void actionPerformed(ActionEvent e) {

				// makes sure that the year inputs don't contains any non-digit characters to
				// avoid
				// errors when Integer.parseInt() is called
				boolean yearsOnlyDigits = true;

				for (int x = 0; x < seasonInput.getText().length(); x++) {
					if (!Character.isDigit(seasonInput.getText().charAt(x))) {
						yearsOnlyDigits = false;
					}
				}

				for (int x = 0; x < firstSeasonInput.getText().length(); x++) {
					if (!Character.isDigit(firstSeasonInput.getText().charAt(x))) {
						yearsOnlyDigits = false;
					}
				}

				for (int x = 0; x < firstSeasonInput.getText().length(); x++) {
					if (!Character.isDigit(firstSeasonInput.getText().charAt(x))) {
						yearsOnlyDigits = false;
					}
				}

				// makes sure none of the fields are left blank
				boolean anyEmptyField = false;

				if (playerInput.getText().isEmpty() || teamInput.getText().isEmpty() || seasonInput.getText().isEmpty()
						|| firstSeasonInput.getText().isEmpty() || lastSeasonInput.getText().isEmpty()) {
					anyEmptyField = true;
				}

				if (yearsOnlyDigits && !anyEmptyField) {
					findComp(playerInput.getText(), teamInput.getText(), Integer.parseInt(seasonInput.getText()),
							Integer.parseInt(firstSeasonInput.getText()), Integer.parseInt(lastSeasonInput.getText()));
				} else {
					chosenPlayerStats.setText("Please ensure that all of your inputs are valid");
				}
			}

		});

		// add all of the components to the north panel
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

		// create a panel on the left side of the window to hold the chosen player's
		// stats
		JPanel westPanel = new JPanel();
		westPanel.setBorder(BorderFactory.createEmptyBorder(15, 2, 10, 5));
		chosenPlayerStats = new JTextArea(20, 28);
		chosenPlayerStats.setEditable(false);
		westPanel.add(chosenPlayerStats);

		// a panel on the right side for the most comparable player's stats
		JPanel eastPanel = new JPanel();
		eastPanel.setBorder(BorderFactory.createEmptyBorder(15, 1, 10, 5));
		compStats = new JTextArea(20, 28);
		compStats.setEditable(false);
		eastPanel.add(compStats);

		// a panel that just holds a small display for the average deviation
		JPanel southPanel = new JPanel();
		southPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		deviationDisplay = new JTextArea(1, 35);
		deviationDisplay.setEditable(false);
		southPanel.add(deviationDisplay);

		// add all of the panels to the window
		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(westPanel, BorderLayout.WEST);
		frame.add(eastPanel, BorderLayout.EAST);
		frame.add(southPanel, BorderLayout.SOUTH);

		// close the program when the window is closed
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeProgram();
			}

		});

		frame.setVisible(true);

		// used to hold all the needed NBA seasons. Each PlayerTable contains the stats
		// of every player that played
		// during that season.
		seasons = new Vector<PlayerTable>();

		// Populate seasons with a PlayerTable for every season
		for (int x = EARLIEST_YEAR; x <= LATEST_YEAR; x++) {

			// These lines are used to get the data from the text files, which are contained
			// in the classpath
			InputStream in = getClass().getClassLoader().getResourceAsStream(x + ".txt");
			// will hold the text that is to be returned
			String data = "";
			// used to read in the file line by line
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String currentLine;
			try {
				// this awkward structure is necessary because the line isn't recoverable after
				// nextLine() is called
				while ((currentLine = reader.readLine()) != null) {
					data = data + currentLine + "\n";
				}
			} catch (IOException e) {
				System.out.println("IOException found!");
			}

			PlayerTable pt = PlayerTable.populateTable(data, x);
			seasons.add(pt);

		}

		findComp.setText("Find most comparable player in search range");
		findComp.setEnabled(true);
	}

	private void closeProgram() {
		frame.dispose();
		System.exit(0);
	}

	private void findComp(String name, String team, int season, int firstYear, int lastYear) {

		// will be used to hold the user's chosen player
		Player chosen = new Player();
		// will be used to caluculate indices of PlayerTables in the vector
		int yearSpan = LATEST_YEAR - EARLIEST_YEAR;
		boolean validPlayerAndSeasonEntered = false;

		// formats keys for search within a table
		String playerAndTeam = name + " - " + team;

		int yearIndex = yearSpan - (LATEST_YEAR - season);

		// makes sure that the chosen player actually exists for that given team and
		// season before
		// assigning chosen
		if (season <= LATEST_YEAR && season >= EARLIEST_YEAR) {
			if (seasons.get(yearIndex).containsKey(playerAndTeam)) {
				chosen = seasons.get(yearIndex).getPlayer(playerAndTeam);
				validPlayerAndSeasonEntered = true;
			}
		}

		// a few checks follow to ensure the validity of the inputs

		boolean validFirstYearEntered = false;

		if (firstYear >= EARLIEST_YEAR && firstYear <= LATEST_YEAR) {
			validFirstYearEntered = true;
		}

		boolean validLastYearEntered = false;

		if (lastYear >= EARLIEST_YEAR && lastYear <= LATEST_YEAR && lastYear >= firstYear) {
			validLastYearEntered = true;
		}

		// the following block of code is used to give comp and deviation default values
		if (validPlayerAndSeasonEntered && validFirstYearEntered && validLastYearEntered) {
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

			// updates the text displays
			chosenPlayerStats.setText("Your Player:\n" + season + " season\n" + chosen.toString());
			compStats.setText("Most comparable player:\n" + compYear + " season\n" + comp.toString());
			deviationDisplay.setText("Average statistical deviation: " + deviation);

			// if any of the inputs are invalid, give an error message
		} else {
			chosenPlayerStats.setText(
					"Invalid input. Ensure that you have entered \n valid inputs in every field. A season is \n denoted by the second"
							+ " year in which it \n took place. Ensure that you have entered \n seasons between 1980 and 2020 to be included \n in the"
							+ " search, and that your chosen \n player, team, and season are correct.");
		}

	}

}
