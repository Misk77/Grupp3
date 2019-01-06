
public class Card {
	
	
	
	  
	  public Integer value;       // värde 1-13
	  public Integer bjvalue;    // Black Jack värde
	  public String  suit;        // valör S,H,R,K

	  public Card() {
	    // Vi börjar alltid med utgå från att detta kort när den skapas som objekt är ett s.k "garbage" kort
	    // eller jokern osv Det är sedan när vi ändrar på value och suit i deck
	    // som detta INTE längre är ett skräpkort. 
	    value = 0;
	    suit = "JOKER";
	  }

	  // Om vi vill skriva ut värdet och valören som en sträng istället för att behöva
	  // använda bara värdet eller suit när vi ska presentera vilket kort det är
	  // kan vi använda denna metod
	  public String getInfo() {
	    return this.suit + " " + this.value + " " + this.bjvalue;
	  }

}
