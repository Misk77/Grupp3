
public class Player {
	private String playerName = "None";
	
	String[][] cards = new String[10][2]; // Max 10 kort med 2 värden (suit&value)
	int drawnCards = 0; 
	int currentBet = 0;
	double winFactor = 0.00;
	int sumOfHand = 0; 				
	int sumOfHandWithAces = 0;  	// Kommer ryka snart.
	int numberOfAces = 0; 
	int lastBet = 0;
	int lastResult = 0;
	int finalHand = 0;
	private int playerSaldo = 200;

	// Setters / Getters:
	
	String getPlayerName() {
		return playerName;
	}

	void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	int getPlayerSaldo() {
		return playerSaldo;
	}

	void setPlayerSaldo(int playerSaldo) {
		this.playerSaldo = playerSaldo;
	}
	
	int getDrawnCards() {
		return drawnCards;
	}

	void setDrawnCards(int drawnCards) {
		this.drawnCards = drawnCards;
	}
	
	int getCurrentBet() {
		return currentBet;
	}

	void setCurrentBet(int currentBet) {
		this.currentBet = currentBet;
	}
	
	double getWinFactor() {
		return winFactor;
	}
	void setWinFactor(double winFactor) {
		this.winFactor = winFactor;
	}
	
	int getSumOfHand() {
		return sumOfHand;
	}

	void setSumOfHand(int sumOfHand) {
		this.sumOfHand = sumOfHand;
	}
	
	int getSumOfHandWithAces() {
		return sumOfHandWithAces;
	}

	void setSumOfHandWithAces(int sumOfHandWithAces) {
		this.sumOfHandWithAces = sumOfHandWithAces;
	}
	
	int getNumberOfAces() {
		return numberOfAces;
	}

	void setNumberOfAces(int numberOfAces) {
		this.numberOfAces = numberOfAces;
	}
	int getLastBet() {
		return lastBet;
	}
	void setLastBet(int lastBet) {
		this.lastBet = lastBet;
	}
	int getFinalHand() {
		return finalHand;
	}
	void setFinalHand(int finalHand) {
		this.finalHand = finalHand;
	}
	int getLastResult() {
		return lastResult;
	}
	void setLastResult(int lastResult) {
		this.lastResult = lastResult;
	}

	

			
//	Konstruktor
	public Player(String name, int saldo)
	{
		playerName = name;
		playerSaldo = saldo;
	}
}
