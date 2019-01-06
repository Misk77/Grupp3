import java.lang.Integer;
import java.util.*; // Innehåller både random och List


public class Deck {
  
  private Integer currentTurn;              // Dragning nr X , denna variabel håller reda på vilken dragning vi gjort ur kortleken.
  private Integer numOfDecks;               // Anger antalet kortlekar
  private Random rnd = new Random();        // För slumpgeneratorn

  private ArrayList<Card> cardlist = new ArrayList<Card>(); // kortlekshög

  // Man kan välja hur många kortlekar man vill ha i leken
  public Deck(Integer numberOfDecks) {

    // Spar antalet kortlekar
    this.numOfDecks = numberOfDecks;
    this.resetAndShuffleDeck();
  }
  
  public Card getNewCard() {

    Card c = new Card();

    if (currentTurn < this.numOfDecks*4*13)
       c = cardlist.get(currentTurn); 


     this.currentTurn++;

     // returnera sedan sist själva kortet
     return c;
  }



  public void resetAndShuffleDeck() {

    // Nollställ även vilken dragning vi behöver oss på
    this.currentTurn = 0;

    // Ta bort alla de gamla korten från kortleken i arrayen
    // dvs rensa arrayen på nytt
    cardlist.clear();


    for (Integer c = 0; c < numOfDecks; c++) { // max antal kortlekar
      for (Integer s = 1; s <= 4; s++) { // 1 - 4 olika typer av färger
        //for (Integer v = 1; v <= 4; v++) { // 1 till 13 kort i varje valör
          for (Integer v = 1; v <= 13; v++) { // 1 till 13 kort i varje valör
          // Skapa det nya kortet
          Card newcard = new Card();
          
          newcard.value = v; // spar värdet på kortet

          
          
          // bestäm här via ifsatser vilken valörtyp
//          if (s == 1) { newcard.suit = "Spader"; }   // spader
//          if (s == 2) { newcard.suit = "Hjärter"; } // hjärter
//          if (s == 3) { newcard.suit = "Klöver"; } // klöver
//          if (s == 4) { newcard.suit = "Ruter"; } // ruter
          
          //J's Tillägg:
          String[] suits = {"♡", "♠", "♢", "♣"};
          newcard.suit = suits[s-1];
          //Slut tillägg
          
          if (v == 11) { newcard.bjvalue = 10;}
          if (v == 12) { newcard.bjvalue = 10;}
          if (v == 13) { newcard.bjvalue = 10;}
          // Sist spar vi detta kort i vår kortlekslista
          cardlist.add(newcard);
        }
      }
    }

    // Slumpa nu alla dessa kort vi har i listan.
    // genom att använda en metod som heter shuffle och finns i Collections
    Collections.shuffle(cardlist);

    // nu är en ny shuffle ruffle kortblandning utförd, klar för spel!!
  }

  // Om vi behöver så kan vi returnera antalet kortlekar vi använder i detta spel
  public Integer getNumberOfDecks() {
    return this.numOfDecks;
  }

  // returnera tillbaks vilken dragning vi gjorde från kortleken
  public Integer getCurrentTurn() {
    return this.currentTurn;
  }

}