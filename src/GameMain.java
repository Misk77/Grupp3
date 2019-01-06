import java.util.Scanner;


public class GameMain {



//	Settings:
	static int newPlayerBalance = 200;
	static int minBet = 10;
	static int maxBet = 2000; 
	static int numberOfDecks = 4;
	static int tableLowerMargin = 18;
	static int littlePause = 1000; // Sets the game pace. 1000mS = 1 second.
	static boolean alphaNumericOnly = true; // Concerning username input.
	static String text_ask_for_new_round = " Are ya'll keen for another round? [Y/N]";
	static String text_dealer_hits = " %s. Dealer hits.";
	static String text_dealer_stays = " Dealer stands at %s.";
	static String text_hit_or_stay =  ". [H]it or [S]tand?";
	static String text_user_bj =  "BLACKJACK! [C]ontinue";
	static String text_user_bust = " = BUST! [C]ontinue";
	static String text_dealer_bj = "Dealer got BLACKJACK!";
	static String text_dealer_bust = "Dealer got BUSTED!";
	static String text_how_much_bet = "\n %1$s, your balance is %2$s credits. How much do you want to bet this round?\n (Min bet: %3$s, Max bet: %4$s)\n";
	static String text_place_bets =  "\n Please place your bets, while the dealer is shuffling the %1$s decks!\n";
	static String text_table_full =  "\n The table is full.\n Please place your bets, while the dealer is shuffling the %1$s decks!\n";
	static String text_added_player =  "\n Welcome %1$s, you have been added to the database!\n As a welcome gift, your account has been topped up with %2$d credits!\n";
	static String text_welcome_back =  "\n Welcome back, %1$s, You have %2$d credits to your name.\n";
	static String text_add_start_cancel =  "\n [A]dd another player, [S]tart game or [C]ancel?\n";
	static String text_ask_for_name = "\n Player %1$d, please enter your name (a-z, A-Z, 0-9):\n";
	static String text_game_on = "\n The game is on, time to place your bets!\n";
	static String text_missed_key = "\n You're too drunk for gambling, honey. Just go home!\n";
	static String betOrWin = " Bet/Win: ";
//	"Graphics":	
	static String boxTopL = "╔";
	static String boxBtmL = "╚";
	static String boxTopR = "╗ ";
	static String boxBtmR = "╝ ";
	static String boxV = "║";
	static String boxH = "═";
//	static String boxTopL = "╭";
//	static String boxBtmL = "╰";
//	static String boxTopR = "╮ ";
//	static String boxBtmR = "╯ ";
//	static String boxV = "┆";
//	static String boxH = "┄";
	static String boxLong = new String(new char[25]).replace("\0", boxH);
	static String boxTop = boxTopL + boxLong + boxTopR;
	static String boxBtm = boxBtmL + boxLong + boxBtmR;



//	Non-settings:
	static int numberOfPlayers = 0;
	static int maxNumberOfPlayers = 6; // Including the dealer!
	static boolean aceHigh = false;
	static int hiHand = 0;
	static int loHand = 0;
	static int ace = 0;
	static int sum = 0;
	static String handStr = "";
	static String choice = "A";
	static String hitOrStay = "";
	static String dealerStuff = "";
	static Scanner s = new Scanner(System.in);
	public static Db Blackjackdb = new Db();
	static Deck d = new Deck(numberOfDecks);
	static Card c;
	static GameMenu theMenu = new GameMenu();
	static Player[] player = new Player[maxNumberOfPlayers];
	static void setSum(int p, int s) {player[p].setSumOfHand(s);}
	static int getSum(int p) {return player[p].getSumOfHand();}

	public static void main(String[] args) {
		
		theMenu.mainMenu(s, Blackjackdb);// Denna vi alltid ska starta med, RIKTIGA SPELE START MENU
		//ExternStart ext = new ExternStart(); ext.init();
		//menuStartGame();
		Blackjackdb.menu();
		

	
	}


	static void cleanForNewRound(){
		for(int i=0; i<=numberOfPlayers; i++){
			for(int g=0; g<10; g++){
				player[i].cards[g][0] = null;
				player[i].cards[g][1] = null;
			}
			player[i].setDrawnCards(0);
			choice = "S";
			hitOrStay = "";
			dealerStuff = "";
			d.resetAndShuffleDeck();
		}
	}
	static void closeTheBar(){
		player = new Player[maxNumberOfPlayers];
		choice = "A";
		numberOfPlayers = 0;
		hitOrStay = "";
		dealerStuff = "";
		d.resetAndShuffleDeck();
		nline(30);
		return;
	}
	static void menuStartGame(){
		player[0] = new Player("Dealer", 0); 
		while (choice.equalsIgnoreCase("A")){
			if (numberOfPlayers >= 5){
				System.out.printf(text_table_full, numberOfDecks); 
				placeBets(player, numberOfPlayers);
				break;
			}
			numberOfPlayers++;
			System.out.printf(text_ask_for_name, numberOfPlayers); 
			String pname = s.nextLine();


			if (alphaNumericOnly) {
				pname = pname.replaceAll("[å]", "a");
				pname = pname.replaceAll("[Å]", "A");
				pname = pname.replaceAll("[ä]", "a");
				pname = pname.replaceAll("[Ä]", "A");
				pname = pname.replaceAll("[ö]", "o");
				pname = pname.replaceAll("[Ö]", "O");
				pname = pname.replaceAll("[^A-Za-z0-9]", "");
			}
			if (pname.length() < 2){
				System.out.println("Name is too short, make sure it's at least 2 characters!");
				numberOfPlayers--;
				continue;
			}
			if (Blackjackdb.playerNameExists(pname)){
				int saldo = Blackjackdb.inGameGetSaldo(pname);
				player[numberOfPlayers] = new Player(pname, saldo);
				System.out.printf(text_welcome_back, pname, saldo); 
				System.out.printf(text_add_start_cancel);
				choice = s.next();
				s.nextLine(); 
				if (choice.equalsIgnoreCase("S")){
					System.out.printf(text_place_bets, numberOfDecks); 
					placeBets(player, numberOfPlayers);
				}
				else if (choice.equalsIgnoreCase("C")){ // Go back to Game Menu
					theMenu.mainMenu(s, Blackjackdb);
					return;
				}
				else if (!choice.equalsIgnoreCase("A") && !choice.equalsIgnoreCase("S") && !choice.equalsIgnoreCase("C")){
					System.out.println("You're too drunk for gambling, honey. Just go home!\n");
					closeTheBar();
					theMenu.mainMenu(s, Blackjackdb);
					return;
				}
			}
			else{
				Blackjackdb.inGameAddPlayer(pname, newPlayerBalance);
				player[numberOfPlayers] = new Player(pname, newPlayerBalance);
				System.out.printf(text_added_player, pname, newPlayerBalance);
				System.out.printf(text_add_start_cancel);  
				choice = s.next();
				s.nextLine();
				if (choice.equalsIgnoreCase("S")){
					System.out.printf(text_game_on);  
					placeBets(player, numberOfPlayers);
				}
				else if (choice.equalsIgnoreCase("C")){ // Go back to Game Menu
					return;
				}
				else if (!choice.equalsIgnoreCase("A") && !choice.equalsIgnoreCase("S") && !choice.equalsIgnoreCase("C")){
					System.out.printf(text_missed_key);
					closeTheBar();
					return;
				}
			}
		}
	}
	
	static void placeBets(Player[] player, int numberOfPlayers){
		for(int i=1; i<=numberOfPlayers; i++){
			int saldo = Blackjackdb.inGameGetSaldo(player[i].getPlayerName());
			System.out.printf(text_how_much_bet, player[i].getPlayerName(), Integer.toString(saldo), Integer.toString(minBet), Integer.toString(maxBet));  
			boolean okBet = false;
			while (okBet == false) {
				int betSet = s.nextInt();
				s.nextLine();
				
				if ( ( (betSet > saldo) || (betSet < minBet) || (betSet > maxBet) ) && (betSet != 0) ) {
					System.out.println(" Try again, or bet 0 to leave!");
				}
				else if (betSet > 0){
					okBet = true;
					System.out.println(" " + player[i].getPlayerName() + " is in the game!");
					player[i].setCurrentBet(betSet);
					Blackjackdb.inGameDecrBalance(player[i].getPlayerName(), player[i].getCurrentBet());
				}
				if (betSet == 0){
					okBet = true;
					System.out.println("Later, " + player[i].getPlayerName() + "!");
					break;
				}
			}
		}
		numberOfPlayers++;
		startGame(player, numberOfPlayers);
	}
	
	
	
	static void startGame(Player[] player, int numberOfPlayers){
		// Deal two cards each:
		for (int i=1;i<numberOfPlayers;i++){
			c = d.getNewCard();
			player[i].cards[0][0] = c.suit;
			player[i].cards[0][1] = String.valueOf(c.value);
			player[i].setDrawnCards(1);
			drawBoard(player, numberOfPlayers, false);
			nline(tableLowerMargin);
			try{Thread.sleep(1000);}
			catch(InterruptedException ex) {Thread.currentThread().interrupt();}
		}
		c = d.getNewCard();
		player[0].cards[0][0] = c.suit;
		player[0].cards[0][1] = String.valueOf(c.value);
		player[0].setDrawnCards(1);
		drawBoard(player, numberOfPlayers, false);
		nline(tableLowerMargin);
		try{Thread.sleep(1000);}
		catch(InterruptedException ex) {Thread.currentThread().interrupt();}
		for (int i=1;i<numberOfPlayers;i++){
			c = d.getNewCard();
			player[i].cards[1][0] = c.suit;
			player[i].cards[1][1] = String.valueOf(c.value);
			player[i].setDrawnCards(2);
			drawBoard(player, numberOfPlayers, false);
			nline(tableLowerMargin);
			try{Thread.sleep(1000);}
			catch(InterruptedException ex) {Thread.currentThread().interrupt();}
		}
		c = d.getNewCard();
		player[0].cards[1][0] = c.suit;
		player[0].cards[1][1] = String.valueOf(c.value);
		player[0].setDrawnCards(2);
		drawBoard(player, numberOfPlayers, false);
		nline(tableLowerMargin);
		try{Thread.sleep(1000);}
		catch(InterruptedException ex) {Thread.currentThread().interrupt();}
		for (int i=1;i<numberOfPlayers;i++){
			countPlayerHand(player, i);
			aceHigh = false;
		}
		aceHigh = false;
		dealersTurn(player);
	}

	
	static void countPlayerHand(Player[] player, int playerNumber){
		int i = playerNumber;
		int theCard = 0;
		int drawn = player[i].getDrawnCards();
		handStr = "";
		loHand = 0; 
		hiHand = 0;
		setSum(i,0);
		
		for (int d=0; d < drawn; d++){ // Summarizing the players card values.	
			theCard = Integer.parseInt( player[i].cards[d][1] );
			if (theCard == 1){
				if ( (getSum(i) + 11) < 22 ){
					setSum(i, getSum(i)+11 );
					aceHigh = true;
				} else {setSum(i, (getSum(i)+1) );}
			}
			else if (theCard > 9){setSum(i,( getSum(i)+10) );}
			else {setSum(i, (getSum(i)+theCard) );}
		}
		if ( getSum(i) > 21 ){
			if (aceHigh){ // If you had an ace counted as 11, say goodbye.
				setSum(i,(getSum(i) -10));
				aceHigh = false;
			}
		}
		if ( getSum(i) > 21 ){ // We better check again.
			drawBoard(player, numberOfPlayers+1, false);
			handStr = Integer.toString(getSum(i));
			System.out.print(space(28*(i-1)) + "  " + handStr + text_user_bust);
			nline(tableLowerMargin);
			player[i].setWinFactor(0.00);
			//player[i].setCurrentBet(0);
			s.next();
			s.nextLine();
		}
		else if (getSum(i)==21){
			printBJ(player[i].getPlayerName());
			drawBoard(player, numberOfPlayers+1, false);
			System.out.print(space(28*(i-1)) + "  " + text_user_bj);
			nline(tableLowerMargin);
			player[i].setWinFactor(2.50);
			s.next();
			s.nextLine();
		}else{
			if (aceHigh){
				handStr = Integer.toString(getSum(i)) + " or " + Integer.toString(getSum(i)-10);
			}else{
				handStr = Integer.toString(getSum(i));
			}
			drawBoard(player, numberOfPlayers+1, false);
			System.out.print(space(28*(i-1)) + " " + handStr + text_hit_or_stay);
	
			nline(tableLowerMargin);
			
			player[i].setWinFactor(2.00);
			player[i].setFinalHand(getSum(i));
			hitOrStay = s.next();
			s.nextLine();
			if (hitOrStay.equalsIgnoreCase("H")){
				c = d.getNewCard();
				player[i].cards[player[i].getDrawnCards()][0] = c.suit;
				player[i].cards[player[i].getDrawnCards()][1] = String.valueOf(c.value);
				player[i].setDrawnCards(player[i].getDrawnCards()+1);
				countPlayerHand(player, i);
			}
		}

	}
	
	
		
	
	
	static void dealersTurn(Player[] player){
		// Nollställ alla räknar-variabler:
		int i = 0;
		int theCard = 0;
		int drawn = player[i].getDrawnCards();
		handStr = "";
		loHand = 0; 
		hiHand = 0;
		setSum(i,0);

		try{Thread.sleep(littlePause*2);}
		catch(InterruptedException ex) {Thread.currentThread().interrupt();}

		drawBoard(player, numberOfPlayers+1, true);
		nline(tableLowerMargin);
		for (int d=0; d < drawn; d++){ // Summarizing the dealer's card values.	
			theCard = Integer.parseInt( player[i].cards[d][1] );
			if (theCard == 1){
				if ( (getSum(i) + 11) < 22 ){
					setSum(i, getSum(i)+11 );
					aceHigh = true;
				} else {setSum(i, (getSum(i)+1) );}
			}
			else if (theCard > 9){setSum(i,( getSum(i)+10) );}
			else {setSum(i, (getSum(i)+theCard) );}
		}

		if ( getSum(i) > 21 ){
			if (aceHigh){ // If you had an ace counted as 11, say goodbye.
				setSum(i,(getSum(i) -10));
				aceHigh = false;
			}
		}
		if ( getSum(i) > 21 ){ // We better check again.
			dealerStuff = text_dealer_bust;
			drawBoard(player, numberOfPlayers+1, true);
			nline(tableLowerMargin);
			try{Thread.sleep(littlePause*2);}
			catch(InterruptedException ex) {Thread.currentThread().interrupt();}
			giveOrTake(0);
		}
		else if (getSum(i)==21){
			dealerStuff = text_dealer_bj;
			drawBoard(player, numberOfPlayers+1, true);
			nline(tableLowerMargin);
			try{Thread.sleep(littlePause*2);}
			catch(InterruptedException ex) {Thread.currentThread().interrupt();}
			giveOrTake(1);
		}
		else{
			if (aceHigh){
				handStr = Integer.toString(getSum(i)) + " or " + Integer.toString(getSum(i)-10);
				setSum(i,(getSum(i) -10));
				aceHigh = false; // No soft 17's for this dealer!
			}else{
				handStr = Integer.toString(getSum(i));
			}
			
			if (getSum(i) >= 17){
				hitOrStay = "S";
				dealerStuff = String.format(text_dealer_stays, handStr);
				drawBoard(player, numberOfPlayers+1, true);
				nline(tableLowerMargin);
				try{Thread.sleep(littlePause*2);}
				catch(InterruptedException ex) {Thread.currentThread().interrupt();}
				giveOrTake(getSum(i));
			}
			else{
				hitOrStay = "H";
				dealerStuff = String.format(text_dealer_hits, handStr);
				drawBoard(player, numberOfPlayers+1, true);
				nline(tableLowerMargin);
				try{Thread.sleep(littlePause);}
				catch(InterruptedException ex) {Thread.currentThread().interrupt();}

			}
			if (hitOrStay.equalsIgnoreCase("H")){
				c = d.getNewCard();
				player[0].cards[player[0].getDrawnCards()][0] = c.suit;
				player[0].cards[player[0].getDrawnCards()][1] = String.valueOf(c.value);
				player[0].setDrawnCards(player[0].getDrawnCards()+1);
				drawBoard(player, numberOfPlayers+1, true);
				nline(tableLowerMargin);
				try{Thread.sleep(littlePause);}
				catch(InterruptedException ex) {Thread.currentThread().interrupt();}
				dealersTurn(player);
			}
		}
		drawBoard(player, numberOfPlayers+1, true);
		nline(tableLowerMargin);
		System.out.print(text_ask_for_new_round);
		choice = s.next();
		s.nextLine();
		if (choice.equalsIgnoreCase("Y")){
			cleanForNewRound();
			placeBets(player, numberOfPlayers);
		}
		else if (choice.equalsIgnoreCase("N")){
			closeTheBar();
			theMenu.mainMenu(s, Blackjackdb);
		}
	}
	
	static void giveOrTake(int dealersHand) {
		// Your cashout depends on the winFactor. 0.00 = Lost, 1.00 = Tie, 2.00 = Chance of betx2, 2.50 = BJ.
		// First the sums are compared, and the value of currentBet is later used as a var for the wins.


		// Gather some info for the info line under the player's name, before it changes:
		for (int i=1;i<numberOfPlayers+1;i++){
			player[i].setLastBet(player[i].getCurrentBet());
		}
		

		if (dealersHand == 0) {
			for (int i=1;i<numberOfPlayers+1;i++){
				
				player[i].setCurrentBet( player[i].getCurrentBet() * (int) player[i].getWinFactor() );
				Blackjackdb.inGameIncrBalance(player[i].getPlayerName(), player[i].getCurrentBet() );
				drawBoard(player, numberOfPlayers+1, true);
				nline(tableLowerMargin);
				try{Thread.sleep(littlePause);}
				catch(InterruptedException ex) {Thread.currentThread().interrupt();}
			}
		}
		else if (dealersHand == 1) {
			for (int i=1;i<numberOfPlayers+1;i++)
			{
				if (player[i].getWinFactor() != 2.50) {
					player[i].setWinFactor(0);
					player[i].setCurrentBet(0);
				}else {
					player[i].setWinFactor(1);
					Blackjackdb.inGameIncrBalance(player[i].getPlayerName(), player[i].getCurrentBet());
					}
				drawBoard(player, numberOfPlayers+1, true);
				nline(tableLowerMargin);
				try{Thread.sleep(littlePause);}
				catch(InterruptedException ex) {Thread.currentThread().interrupt();}
			}   
		}
		else {
			for (int i=1;i<numberOfPlayers+1;i++)
			{
				if (player[i].getWinFactor() == 2.50) {player[i].setCurrentBet( (int) (player[i].getCurrentBet() * 2.50));}
				if (player[i].getWinFactor() == 2){
					if (player[i].getFinalHand() > dealersHand){
						player[i].setCurrentBet( (int) (player[i].getCurrentBet() * player[i].getWinFactor()));
					}
					else if (player[i].getFinalHand() < dealersHand){
						player[i].setCurrentBet(0);
						player[i].setWinFactor(0);
					}
					else {
						player[i].setWinFactor(1);
						player[i].setCurrentBet( (int) (player[i].getCurrentBet() * player[i].getWinFactor()));
					 	
					}
				}
				if (player[i].getWinFactor() == 0) {player[i].setCurrentBet(0);}
				else {Blackjackdb.inGameIncrBalance(player[i].getPlayerName(), player[i].getCurrentBet());}
				drawBoard(player, numberOfPlayers+1, true);
				nline(tableLowerMargin);
				try{Thread.sleep(littlePause);}
				catch(InterruptedException ex) {Thread.currentThread().interrupt();}
			}

		}
		
		// Gather the rest of the info now that all is settled:
		for (int i=1;i<numberOfPlayers+1;i++){
			
			if (player[i].getWinFactor() == 0.00) {
				player[i].setLastResult(-player[i].getLastBet());
			}
			else if (player[i].getWinFactor() == 1.00){
				player[i].setLastResult(0);
			}
			else {player[i].setLastResult( (int)(player[i].getLastBet() * player[i].getWinFactor()));}
		}
	}

	static void drawBoard(Player[] player, int numberOfPlayers, boolean revealDealersSecond) {

		// Dealerns andra kort ska inte visas öppet förrän det är dennes tur:
		String[] faceDown = {" ", " "};
		// Rita upp dealerns del av bordet:
		System.out.println();
		System.out.println(space(56)+boxTop+"\n"+space(56)+boxV+" Dealer"+space(18)+boxV);
		//Första kortraden:
		for (int p=0;p<4;p++){
			if(player[0].getDrawnCards() == 2){
				System.out.print(space(56)+boxV);
				System.out.print(buildCard(player[0].cards[0])[p]);
				if (revealDealersSecond == false){
					System.out.print(buildCard(faceDown)[p]);
					System.out.print(space(25-(player[0].getDrawnCards()*5)) + boxV+" ");
				}
				else {
					System.out.print(buildCard(player[0].cards[1])[p]);
					System.out.print(space(25-(player[0].getDrawnCards()*5)) + boxV+" ");
					if (p==2) {System.out.print(dealerStuff);}
				}
			}
			if ((player[0].getDrawnCards() < 6) && (player[0].getDrawnCards() != 2)){
				if (player[0].getDrawnCards() > 2) {revealDealersSecond = true;}
				System.out.print(space(56)+boxV);
				for (int x=0;x<player[0].getDrawnCards();x++){System.out.print(buildCard(player[0].cards[x])[p]);}
				System.out.print(space(25-(player[0].getDrawnCards()*5)) + boxV+" ");
				if (p==2) {System.out.print(dealerStuff);}
			}
			else if (player[0].getDrawnCards() != 2){
				System.out.print(space(56)+boxV);
				for (int x=0;x<5;x++){System.out.print(buildCard(player[0].cards[x])[p]);}
				System.out.print(boxV+" ");
				if (p==2) {System.out.print(dealerStuff);}
			}
			System.out.println();
		} //Andra kortraden:
		for (int p=0;p<4;p++){
			if(player[0].getDrawnCards() > 5){
				System.out.print(space(56)+boxV);
				for (int x=5;x<player[0].getDrawnCards();x++){System.out.print(buildCard(player[0].cards[x])[p]);}
				System.out.print(space(50-(player[0].getDrawnCards()*5)) + boxV + " ");
				if (p==0) {System.out.print(dealerStuff);}
			}
			else{
				System.out.print(space(56)+boxV+space(25)+boxV + " ");

			}
			System.out.println();
		}
		System.out.print(space(56)+boxBtm);
		System.out.println();

		// Rita upp spelarnas del av bordet:
		for (int i=1;i<6;i++){System.out.print(boxTop);} 
		System.out.println();
		for (int i=1;i<numberOfPlayers;i++){System.out.print(boxV + " " + player[i].getPlayerName() + space(24-(player[i].getPlayerName().length())) + boxV + " ");}
		for (int i=1;i<(6-numberOfPlayers+1);i++){System.out.print(boxV + space(25) + boxV + " ");} //Fyller ut de tomma platserna...
		System.out.println();
		for (int i=1;i<numberOfPlayers;i++){System.out.print(boxV + infoLine(i) + boxV + " ");}
		for (int i=1;i<(6-numberOfPlayers+1);i++){System.out.print(boxV + space(25) + boxV + " ");} //Fyller ut de tomma platserna...
		System.out.println();

		//Första kortraden:
		for (int p=0;p<4;p++){
			for (int i=1;i<numberOfPlayers;i++){
				if(player[i].getDrawnCards() < 6){
					System.out.print(boxV);
					for (int x=0;x<player[i].getDrawnCards();x++){System.out.print(buildCard(player[i].cards[x])[p]);}
					System.out.print(space(25-(player[i].getDrawnCards()*5)) + boxV + " ");
				}
				else{
					System.out.print(boxV);
					for (int x=0;x<5;x++){System.out.print(buildCard(player[i].cards[x])[p]);}
					System.out.print(boxV + " ");
				}
			}
			for (int x=1;x<(6-numberOfPlayers+1);x++){System.out.print(boxV + space(25) + boxV + " ");} //Fyller ut de tomma platserna...
			System.out.println();
		}
		//Andra kortraden:
		for (int p=0;p<4;p++){
			for (int i=1;i<numberOfPlayers;i++){
				if(player[i].getDrawnCards() > 5){
					System.out.print(boxV);
					for (int x=5;x<player[i].getDrawnCards();x++){System.out.print(buildCard(player[i].cards[x])[p]);}
					System.out.print(space(50-(player[i].getDrawnCards()*5)) + boxV + " ");
				}
				else{
					System.out.print(boxV+space(25)+boxV + " ");
				}
			}
			for (int x=1;x<(6-numberOfPlayers+1);x++){System.out.print(boxV + space(25) + boxV + " ");} //Fyller ut de tomma platserna...
			System.out.println();
		}
		for (int i=1;i<numberOfPlayers;i++){
			if(player[i].getDrawnCards() < 6){
				System.out.print(boxBtm);
			}
			else{
				System.out.print(boxBtm);
			}
		}
		for (int x=1;x<(6-numberOfPlayers+1);x++){System.out.print(boxBtm);} //Fyller ut de tomma platserna...
		System.out.println();
	}



	static String[] buildCard(String[] card) {
		// Metod som tar emot en array med suit och value för ett kort och returnerar fyra "slices",
		// som sedan printas som en del av "bordet".

		String suit = card[0];
		String value = card[1];
		String[] pieces = new String[4];
		if (value != null) {
			switch (value) {
			case "1":  value = " A ";
			break;

			case "10":  value = "1 0";
			break;

			case "11":  value = " J ";
			break;

			case "12":  value = " Q ";
			break;

			case "13":  value = " K ";
			break;

			default: value = (" " + value + " ");
			}

			pieces[0] = "╭───╮";
			pieces[1] = "│ " + suit + " │";
			pieces[2] = "│" + value + "│";
			pieces[3] = "╰───╯";

		}
		else {
			pieces[0] = "";
			pieces[1] = "";
			pieces[2] = "";
			pieces[3] = "";

		}
		return pieces;
	}

	
	
	static String infoLine(int playerNum) {
		// This method formats the info seen in the player's squares.
		// The numbers are assigned in method giveOrTake().
		String posNeg;
		String part1;
		String part3;
		String part2;
		if (player[playerNum].getLastBet()>0) {
			if (player[playerNum].getLastResult()>0) {posNeg = "+";}
			else if (player[playerNum].getLastResult()==0) {posNeg = "+/-";}
			else {posNeg = "";}
			
		part1 = " Bet:"+Integer.toString(player[playerNum].getLastBet());
		part3 = "Result:"+posNeg+Integer.toString(player[playerNum].getLastResult() ) + " ";
		part2 = space( 25-( (part1+part3).length()) );
		}
		else {
			part1 = " Bet: " + player[playerNum].getCurrentBet();
			part3 = "";
			part2 = space( 25- ( (part1+part3).length() ) );
		}
		return part1+part2+part3;
	}
	

	static String space(int number) {
		if (number<0) {number = 1;}
		// Metod som returnerar ut ett antal blanksteg.
		String spaces = new String(new char[number]).replace("\0", " ");
		return spaces;
	}



	static void nline(int number) {
		// Metod som printar ut ett antal newlines.
		for (int l=0; l<number; l++) {
			System.out.print("\n");
		}
	}

	static void printBJ(String pName) {
		final String[] bkjk = new String[5];
		bkjk[1] = "                       888 88P' 888        d8b Y8b     d888  'Y 888 8P      888    d8b Y8b     d888  'Y 888 8P "; 
		bkjk[0] = "                       888 88b, 888         e Y8b       e88'Y88 888 88P     888     e Y8b       e88'Y88 888 88P";  
		bkjk[4] = "                       888 88P' 888,d88 d8888888b Y8b   \"88,d88 888 88b \"8\",P'  d8888888b Y8b   \"88,d88 888 88b";  
		bkjk[1] = "                       888 88P' 888        d8b Y8b     d888  'Y 888 8P      888    d8b Y8b     d888  'Y 888 8P ";                 
		bkjk[2] = "                       888 8K   888       d888b Y8b   C8888     888 K       888   d888b Y8b   C8888     888 K  ";  
		bkjk[4] = "                       888 88P' 888,d88 d8888888b Y8b   \"88,d88 888 88b \"8\",P'  d8888888b Y8b   \"88,d88 888 88b";  
		bkjk[1] = "                       888 88P' 888        d8b Y8b     d888  'Y 888 8P      888    d8b Y8b     d888  'Y 888 8P "; 
		bkjk[3] = "                       888 88b, 888  ,d  d888888888b   Y888  ,d 888 8b   e  88P  d888888888b   Y888  ,d 888 8b ";                 
		bkjk[4] = "                       888 88P' 888,d88 d8888888b Y8b   \"88,d88 888 88b \"8\",P'  d8888888b Y8b   \"88,d88 888 88b";  
		for (int w=0; w<3; w++) {
			for (int l=0; l<6; l++) {System.out.print("\n");}
			for (int l=0; l<5; l++) {System.out.print(bkjk[l] + "\n");
		}
			try{Thread.sleep(200);
			}
			catch(InterruptedException ex)
			{Thread.currentThread().interrupt();
			}
		}
		System.out.print(("\n\n\n\n\n                                                            FOR " + pName).toUpperCase());
		for (int w=0; w<50; w++) 
		{
			System.out.print("\n");
			try{Thread.sleep(80);}
			catch(InterruptedException ex)
			{Thread.currentThread().interrupt();}
		}

	}

}












