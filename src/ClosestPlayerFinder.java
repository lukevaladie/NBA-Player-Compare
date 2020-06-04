
import java.util.Vector;

import javax.swing.*;

import java.util.Set;

import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.*;

//This class is used to find the player most comparable to a given player in a single season

public class ClosestPlayerFinder {

	// These ints mark the bounds of the seasons loaded into the program
	private static final int EARLIEST_YEAR = 1980;
	private static final int LATEST_YEAR = 2020;
	Vector<PlayerTable> seasons;
	Scanner s;

	JTextArea chosenPlayerStats;
	JTextArea compStats;
	JFrame frame;

	// on startup, load the player data into a vector of PlayerTables
	public ClosestPlayerFinder() {

		frame = new JFrame();
		frame.setTitle("NBA Comparison Finder");
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

		JButton findComp = new JButton("Loading data, please wait...");
		findComp.setEnabled(false);
		findComp.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				boolean yearsOnlyDigits = true;
				
				for(int x = 0; x < seasonInput.getText().length(); x++) {
					if(!Character.isDigit(seasonInput.getText().charAt(x))) {
						yearsOnlyDigits = false;
					}
				}
				
				for(int x = 0; x < firstSeasonInput.getText().length(); x++) {
					if(!Character.isDigit(firstSeasonInput.getText().charAt(x))) {
						yearsOnlyDigits = false;
					}
				}
				
				for(int x = 0; x < firstSeasonInput.getText().length(); x++) {
					if(!Character.isDigit(firstSeasonInput.getText().charAt(x))) {
						yearsOnlyDigits = false;
					}
				}
				
				if(yearsOnlyDigits) {
				findComp(playerInput.getText(), teamInput.getText(), Integer.parseInt(seasonInput.getText()),
						Integer.parseInt(firstSeasonInput.getText()), Integer.parseInt(lastSeasonInput.getText()));
				}else {
					chosenPlayerStats.setText("Please ensure that all of your inputs are valid");
				}
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
		westPanel.setBorder(BorderFactory.createEmptyBorder(15, 2, 10, 5));
		chosenPlayerStats = new JTextArea(20, 28);
		chosenPlayerStats.setEditable(false);
		westPanel.add(chosenPlayerStats);

		JPanel eastPanel = new JPanel();
		eastPanel.setBorder(BorderFactory.createEmptyBorder(15, 1, 10, 5));
		compStats = new JTextArea(20, 28);
		compStats.setEditable(false);
		eastPanel.add(compStats);

		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(westPanel, BorderLayout.WEST);
		frame.add(eastPanel, BorderLayout.EAST);
		
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
			
			//These lines are used to get the data from the text files, which are contained in the classpath
			InputStream in = getClass().getClassLoader().getResourceAsStream(x + ".txt");
			String data = readTextFromInputStream(in);

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

	//method used to convert an input stream to a String so that data can be extracted
	private String readTextFromInputStream(InputStream i) {
		//will hold the text that is to be returned
		String text = "";
		//used to read in the file line by line
		BufferedReader reader = new BufferedReader(new InputStreamReader(i));
		String currentLine;
		try {
			//this awkward structure is necessary because the line isn't recoverable after nextLine() is called
			while ((currentLine = reader.readLine()) != null) {
				text = text + currentLine + "\n";
			}
			return text;
		} catch (IOException e) {
			System.out.println("IOException found!");
			return null;
		}
	}

	private void findComp(String name, String team, int season, int firstYear, int lastYear) {

		// will be used to hold the user's chosen player
		Player chosen = new Player();
		int yearSpan = LATEST_YEAR - EARLIEST_YEAR;
		boolean validPlayerAndSeasonEntered = false;

		String playerAndTeam = name + " - " + team;

		int yearIndex = yearSpan - (LATEST_YEAR - season);

		if (season <= LATEST_YEAR && season >= EARLIEST_YEAR) {
			if (seasons.get(yearIndex).containsKey(playerAndTeam)) {
				chosen = seasons.get(yearIndex).getPlayer(playerAndTeam);
				validPlayerAndSeasonEntered = true;
			}
		}

		boolean validFirstYearEntered = false;

		if (firstYear >= EARLIEST_YEAR && firstYear <= LATEST_YEAR) {
			validFirstYearEntered = true;
		}

		boolean validLastYearEntered = false;

		if (firstYear >= EARLIEST_YEAR && firstYear <= LATEST_YEAR && lastYear >= firstYear) {
			validLastYearEntered = true;
		}

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

			chosenPlayerStats.setText("Your Player:\n" + season + " season\n" + chosen.toString());
			compStats.setText("Most comparable player:\n" + compYear + " season\n" + comp.toString());

		} else {
			chosenPlayerStats.setText(
					"Invalid input. Ensure that you have entered valid inputs in every field. \n A season is denoted by the second"
							+ " year in which it took place. \n Ensure that you have entered seasons between 1980 and 2020 to be included in the"
							+ " search, \n and that your chosen player, team, and season are correct.");
		}

	}

}
