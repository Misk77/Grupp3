
public class Card {
	
	public static String[] getCard() {
		String[] suits = {"♡", "♠", "♢", "♣"};
		String suit = suits[(int) (Math.random() * 4)];
		String[] values = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
		String value = values[(int) (Math.random() * 13)];
		String[] cardString = {suit, value};

		return cardString;
	}

}
