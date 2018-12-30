import java.lang.Integer;
import java.lang.String;
import java.util.*; // Innehåller både random och List

// Klass för att beskriva själva kortet via dess valör och värde
public class card {
  
  public Integer value;       // värde 1-13
  public Integer bjvalue;    // Black Jack värde
  public String  suit;        // valör S,H,R,K

  public card() {
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