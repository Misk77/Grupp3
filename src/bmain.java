import java.lang.Boolean;

public class main {

  public static void main(String[] args) {

		
	    deck d = new deck(1); // Välj 10 kortlekar från start
	    
	    System.out.println("Antalet korthögar vi har här : " + d.getNumberOfDecks());
	
	    card c;
	
	   
	    c = d.getNewCard();
	    System.out.println("Kort " + d.getCurrentTurn() + " är " + c.suit + " " + c.value + ", Black Jack värde: " + c.bjvalue);
	    c = d.getNewCard();
	    System.out.println("Kort " + d.getCurrentTurn() + " är " + c.suit + " " + c.value + ", Black Jack värde: " + c.bjvalue);
	
	
	    System.out.println("");
	
	    
	 // blanda på nytt 
	  //  d.resetAndShuffleDeck();
	    
	    
  }}

