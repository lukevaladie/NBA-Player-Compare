import javax.swing.*;
import java.awt.*;

//A GUI for running the comparison engine

public class PlayerComparisonEngine {
	
	public PlayerComparisonEngine() {
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

	public static void main(String[] args) {
		
		PlayerComparisonEngine pce = new PlayerComparisonEngine();
		
	}
	
	
	
	
	
	
}
