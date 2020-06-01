public class Player {
	
	//basic attributes of player. Cannot be changed.
	private final String name;
	private final String team;
	private final int age;
	private final String pos;
	
	//player performance statistics
	private int gamesPlayed = 0;
	private int gamesStarted = 0;
	private double minutesPerGame = 0.0;
	private double fieldGoalsPerGame = 0.0;
	private double fieldGoalAttemptsPerGame = 0.0;
	private double fieldGoalPercentage = 0.0;
	private double threePointersPerGame = 0.0;
	private double threePointAttemptsPerGame = 0.0;
	private double threePointPercentage = 0.0;
	private double twoPointersPerGame = 0.0;
	private double twoPointAttemptsPerGame = 0.0;
	private double twoPointPercentage = 0.0;
	private double effectiveFieldGoalPercentage = 0.0;
	private double freeThrowsPerGame = 0.0;
	private double freeThrowAttemptsPerGame = 0.0;
	private double freeThrowPercentage = 0.0;
	private double offensiveReboundsPerGame = 0.0;
	private double defensiveReboundsPerGame = 0.0;
	private double totalReboundsPerGame = 0.0;
	private double assistsPerGame = 0.0;
	private double stealsPerGame = 0.0;
	private double blocksPerGame = 0.0;
	private double turnoversPerGame = 0.0;
	private double personalFoulsPerGame = 0.0;
	private double pointsPerGame;
	
	//creates Player object with given name, team, age, and position
	public Player(String playerName, String teamAbv, int age, String position) {
		name = playerName;
		team = teamAbv;
		this.age = age;
		pos = position;
	}
	
	//methods for getting basic player info
	
	public String getName() {
		return name;
	}
	
	public String getTeam() {
		return team;	
	}
	
	public int getAge() {
		return age;
	}
	
	public String getPosition() {
		return pos;
	}
	
	//methods for setting player statistics
	
	public void setGamesPlayed(int games) {
		gamesPlayed = games;
	}
	
	public void setGamesStarted(int games) {
		gamesStarted = games;
	}
	
	public void setMinutesPerGame(double minutes) {
		minutesPerGame = minutes;
	}
	
	
	public void setFieldGoalsPerGame (double fieldGoals) {
		fieldGoalsPerGame = fieldGoals;
	}
	
	public void setFieldGoalAttemptsPerGame(double fga) {
		fieldGoalAttemptsPerGame = fga;
	}
	
	public void setFieldGoalPercentage(double fgp) {
		fieldGoalPercentage = fgp;
	}
	
	public void setThreePointersPerGame (double tpg) {
		threePointersPerGame = tpg;
	}
	
	public void setThreePointAttemptsPerGame (double tpapg) {
		threePointAttemptsPerGame = tpapg;
	}
	
	public void setThreePointPercentage (double tpp) {
		threePointPercentage = tpp;
	}
	
	public void setTwoPointersPerGame(double tppg) {
		twoPointersPerGame = tppg;
	}
	
	public void setTwoPointAttemptsPerGame(double tpapg) {
		twoPointAttemptsPerGame = tpapg;
	}
	
	public void setTwoPointPercentage(double tpp) {
		twoPointPercentage = tpp;
	}
	
	public void setEffectiveFieldGoalPercentage(double efgp) {
		effectiveFieldGoalPercentage = efgp;
	}
	
	public void setFreeThrowsPerGame(double ftpg) {
		freeThrowsPerGame = ftpg;
	}
	
	public void setFreeThrowAttemptsPerGame(double ftapg) {
		freeThrowAttemptsPerGame = ftapg;
	}
	
	public void setFreeThrowPercentage(double ftp) {
		freeThrowPercentage = ftp;
	}
	
	public void setOffensiveReboundsPerGame(double orpg) {
		offensiveReboundsPerGame = orpg;
	}
	
	public void setDefensiveReboundsPerGame(double drpg) {
		defensiveReboundsPerGame = drpg;
	}
	
	public void setTotalReboundsPerGame(double trpg) {
		totalReboundsPerGame = trpg;
	}
	
	public void setAssistsPerGame(double apg) {
		assistsPerGame = apg;
	}
	
	public void setStealsPerGame(double spg) {
		stealsPerGame = spg;
	}
	
	public void setBlocksPerGame(double bpg) {
		blocksPerGame = bpg;
	}
	
	public void setTurnoversPerGame(double tovpg) {
		turnoversPerGame = tovpg;
	}
	
	public void setPersonalFoulsPerGame(double pfpg) {
		personalFoulsPerGame = pfpg;
	}
	
	public void setPointsPerGame(double ppg) {
		pointsPerGame = ppg;
	}
	
	//methods for retrieving player statistics
	
	public int getGamesPlayed() {
		return gamesPlayed;
	}
	
	public int getGamesStarted() {
		return gamesStarted;
	}
	
	public double getMinutesPerGame() {
		return minutesPerGame;
	}
	
	
	public double getFieldGoalsPerGame () {
		return fieldGoalsPerGame;
	}
	
	public double getFieldGoalAttemptsPerGame() {
		return fieldGoalAttemptsPerGame;
	}
	
	public double getFieldGoalPercentage() {
		return fieldGoalPercentage;
	}
	
	public double getThreePointersPerGame () {
		return threePointersPerGame;
	}
	
	public double getThreePointAttemptsPerGame () {
		return threePointAttemptsPerGame;
	}
	
	public double getThreePointPercentage () {
		return threePointPercentage;
	}
	
	public double getTwoPointersPerGame() {
		return twoPointersPerGame;
	}
	
	public double getTwoPointAttemptsPerGame() {
		return twoPointAttemptsPerGame;
	}
	
	public double getTwoPointPercentage() {
		return twoPointPercentage;
	}
	
	public double getEffectiveFieldGoalPercentage() {
		return effectiveFieldGoalPercentage;
	}
	
	public double getFreeThrowsPerGame() {
		return freeThrowsPerGame;
	}
	
	public double getFreeThrowAttemptsPerGame() {
		return freeThrowAttemptsPerGame;
	}
	
	public double getFreeThrowPercentage() {
		return freeThrowPercentage;
	}
	
	public double getOffensiveReboundsPerGame() {
		return offensiveReboundsPerGame;
	}
	
	public double getDefensiveReboundsPerGame() {
		return defensiveReboundsPerGame;
	}
	
	public double getTotalReboundsPerGame() {
		return totalReboundsPerGame;
	}
	
	public double getAssistsPerGame() {
		return assistsPerGame;
	}
	
	public double getStealsPerGame() {
		return stealsPerGame;
	}
	
	public double getBlocksPerGame() {
		return blocksPerGame;
	}
	
	public double getTurnoversPerGame() {
		return turnoversPerGame;
	}
	
	public double getPersonalFoulsPerGame() {
		return personalFoulsPerGame;
	}
	
	public double getPointsPerGame() {
		return pointsPerGame;
	}
	
	public String toString() {
		String display = name + " - " + team + " - " + age + " - " + pos + "\n";
		display += "GP: " + getGamesPlayed() + ", GS: " + getGamesStarted() + ", MPG: " + getMinutesPerGame() + "\n";
		display += "FG/G: " + getFieldGoalsPerGame() + ", FGA/G: " + getFieldGoalAttemptsPerGame() + ", FG%: " + getFieldGoalPercentage() + "\n";
		display += "3P/G: " + getThreePointersPerGame() + ", 3PA/G: " + getThreePointAttemptsPerGame() + ", 3P%: " + getThreePointPercentage() + "\n";
		display += "2P/G: " + getTwoPointersPerGame() + ", 2PA/G: " + getTwoPointAttemptsPerGame() + ", 2P%: " + getTwoPointPercentage() + "\n";
		display += "eFG%: " + getEffectiveFieldGoalPercentage() + "\n";
		display += "FT/G: " + getFreeThrowsPerGame() + ", FTA/G: " + getFreeThrowAttemptsPerGame() + ", FT%: " + getFreeThrowPercentage() + "\n";
		display += "ORB/G" + getOffensiveReboundsPerGame() + ", DRB/G: " + getDefensiveReboundsPerGame() + ", TRB/G: " + getTotalReboundsPerGame() + "\n";
		display += "AST/G: " + getAssistsPerGame() + ", STL/G: " + getStealsPerGame() + ", BLK/G: " + getBlocksPerGame() + "\n";
		display += "TOV/G: " + getTurnoversPerGame() + ", PF/G" + getPersonalFoulsPerGame() + "\n";
		display += "PTS/G: " + getPointsPerGame();
		return display;
	}
	
	public static double calculatePercentDiff(double chosen, double comp) {
		
		if(chosen == 0.0 && comp == 0.0) {
			return 0.0;
		}else {
			return Math.abs(chosen - comp)/((chosen + comp) / 2);
		}
		
	}
	
	//the average deviation between the two players is returned (i.e. if there is an average of a 50% difference from comp to chosen, .5 is returned)
	//it does not matter whether the deviation is positive or negative.
	public static double getAvgDeviation (Player chosen, Player comp) {
		
		double total = 0;
		total += calculatePercentDiff(chosen.getGamesPlayed(), comp.getGamesPlayed());
		total += calculatePercentDiff(chosen.getGamesStarted(), comp.getGamesStarted());
		total += calculatePercentDiff(chosen.getMinutesPerGame(), comp.getMinutesPerGame());
		total += calculatePercentDiff(chosen.getFieldGoalsPerGame(), comp.getFieldGoalsPerGame());
		total += calculatePercentDiff(chosen.getFieldGoalAttemptsPerGame(), comp.getFieldGoalAttemptsPerGame());
		total += calculatePercentDiff(chosen.getFieldGoalPercentage(), comp.getFieldGoalPercentage());
		total += calculatePercentDiff(chosen.getThreePointersPerGame(), comp.getThreePointersPerGame());
		total += calculatePercentDiff(chosen.getThreePointAttemptsPerGame(), comp.getThreePointAttemptsPerGame());
		total += calculatePercentDiff(chosen.getThreePointPercentage(), comp.getThreePointPercentage());
		total += calculatePercentDiff(chosen.getTwoPointersPerGame(), comp.getTwoPointersPerGame());
		total += calculatePercentDiff(chosen.getTwoPointAttemptsPerGame(), comp.getTwoPointAttemptsPerGame());
		total += calculatePercentDiff(chosen.getTwoPointPercentage(), comp.getTwoPointPercentage());
		total += calculatePercentDiff(chosen.getEffectiveFieldGoalPercentage(), comp.getEffectiveFieldGoalPercentage());
		total += calculatePercentDiff(chosen.getFreeThrowsPerGame(), comp.getFreeThrowsPerGame());
		total += calculatePercentDiff(chosen.getFreeThrowAttemptsPerGame(), comp.getFreeThrowAttemptsPerGame());
		total += calculatePercentDiff(chosen.getFreeThrowPercentage(), comp.getFreeThrowPercentage());
		total += calculatePercentDiff(chosen.getOffensiveReboundsPerGame(), comp.getOffensiveReboundsPerGame());
		total += calculatePercentDiff(chosen.getDefensiveReboundsPerGame(), comp.getDefensiveReboundsPerGame());
		total += calculatePercentDiff(chosen.getTotalReboundsPerGame(), comp.getTotalReboundsPerGame());
		total += calculatePercentDiff(chosen.getAssistsPerGame(), comp.getAssistsPerGame());
		total += calculatePercentDiff(chosen.getStealsPerGame(), comp.getStealsPerGame());
		total += calculatePercentDiff(chosen.getBlocksPerGame(), comp.getBlocksPerGame());
		total += calculatePercentDiff(chosen.getTurnoversPerGame(), comp.getTurnoversPerGame());
		total += calculatePercentDiff(chosen.getPersonalFoulsPerGame(), comp.getPersonalFoulsPerGame());
		total += calculatePercentDiff(chosen.getPointsPerGame(), comp.getPointsPerGame());
		
		return total/24;
		
	}
	
	

	

}
