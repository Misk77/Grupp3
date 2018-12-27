
public class PlayersTest {
	private String playerName = "None";
	
	String[][] cards = new String[10][2]; // Max 10 kort med 2 värden (suit&value)
	int drawnCards = 0; // Gör det lättare att rita upp bordet.
	int currentBet = 0;
	int currentWin = 0;
	int sumOfHand = 0;
	int sumOfHandWithAces = 0;
	
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
	
	int getCurrentWin() {
		return currentWin;
	}
	void setCurrentWin(int currentWin) {
		this.currentWin = currentWin;
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

			
//	Konstruktor
	public PlayersTest(String name, int saldo)
	{
		playerName = name;
		playerSaldo = saldo;
	}
}
