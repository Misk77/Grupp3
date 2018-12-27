import java.util.Scanner;


public class GameMain {
	
	/*
	 * TO-DO:
	 * 
	 * - inGameIncrBalance(amount) - Metod i Db.java
	 * - inGameDecrBalance(amount) - Metod i Db.java
	 * - gameMenu - Egen klass?
	 * 
	 * I GameMain.java:
	 * - i placeBets(): dra bet från användarens saldo i db:n. Se till att det är inom min/max bet, och att användaren har råd.
	 * - dealersTurn() - metod med dealerns logik (stanna vid >=17, annars ta kort)
	 * - Metod som kontrollerar spelarnas händer mot dealern när dealersTurn är klar. Hoppa över tjocka händer och blackjack, som redan är avklarade...
	 * - Illustrera hur mkt man vunnit. Byta ut "Bet: 20" mot "Win: 20 * 2 = 40", "Blackjack: 20 * 2.5 = 50", och "Busted" kort och gott?
	 * - 
	 */

	//	Settings:
	static int newPlayerBalance = 200;
	static int minBet = 10;
	static int maxBet = 2000; 

	// Don't change:
	static int numberOfPlayers = 0;
	static int maxNumberOfPlayers = 6; // Including the dealer!
	static String choice = "A";
	static String hitOrStay = "";
	static PlayersTest[] player = new PlayersTest[maxNumberOfPlayers];
	static Db Blackjackdb = new Db();
	static Scanner s = new Scanner(System.in);
	public static void main(String[] args) {

		/*
		 * Händelseförlopp (efter att spelare är valda):
		 * 1. Fråga varje spelare hur mycket de vill satsa, och dra det från deras hämtade saldo (om de har råd).
		 *    Behöver inte uppdatera databasen i detta läge va?
		 *    Min/max-satsning? Jämna 10/20kr? Kan köra modulus = 0 där.
		 *    
		 * 2. Ge först ett kort var till varje spelare och dealern, och sen det andra till spelarna och ett facedown till dealern.
		 * 
		 * 3. Kolla en spelares kort i taget. Har någon fått 21, Skriv "Blackjack!" Ge pengar direkt (2.5*bet tillbaka). Över 21 = "Busted!"
		 *    
		 * 4. En spelare i taget: visa summan (t.ex. "13" eller "9 or 19" vid ess) och fråga "Hit or Stay?"
		 * 
		 * 5. När alla spelare är klara visas dealerns andra kort, och blir det under 17 tar dealern nytt kort tills det blir 17 eller mer.
		 * 
		 * 6. Jämför dealerns hand mot spelarnas. Dela ut ev. vinster.
		 * 
		 * 7. Fråga om det ska bli en till omgång.
		 */

		player[0] = new PlayersTest("Dealer", 0); 

		menuStartGame();


	}


	static void menuStartGame()
	{
		while (choice.equalsIgnoreCase("A"))
		{
			if (numberOfPlayers >= 5)
			{
				System.out.println("\nThe table is full, the game is on!\nTime to place your bets...\n");

				placeBets(player, numberOfPlayers);
				break;
			}
			Blackjackdb.inGameOpenConn();

			numberOfPlayers++;

			System.out.println("Player " + numberOfPlayers + ", please enter your name (a-z, A-Z, 0-9):");
			String pname = s.nextLine();
			pname = pname.replaceAll("[^A-Za-z0-9]", "");
			if (pname.length() < 2)
			{
				System.out.println("Name is too short, make sure it's at least 2 characters!");
				numberOfPlayers--;
				continue;
			}


			if (Blackjackdb.playerNameExists(pname))
			{
				int saldo = Blackjackdb.inGameGetSaldo(pname);
				player[numberOfPlayers] = new PlayersTest(pname, saldo);
				System.out.println("\nWelcome back, " + pname + "!" + "\nYou have " + saldo + " credits to your name.\n");


				System.out.println("[A]dd another player, [S]tart game or [C]ancel?");
				choice = s.next();
				s.nextLine(); // Need to consume the linebreak to be able to use nextLine() next loop iteration...
				if (choice.equalsIgnoreCase("S"))
				{
					System.out.println("\nThe game is on!\nTime to place your bets...\n");
					placeBets(player, numberOfPlayers);
				}
				else if (choice.equalsIgnoreCase("C"))
				{ // Go back to Game Menu
					return;
				}
				else if (!choice.equalsIgnoreCase("A") && !choice.equalsIgnoreCase("S") && !choice.equalsIgnoreCase("C"))
				{
					System.out.println("Invalid choice, returning to Menu...\n");
					return;
				}


			}
			else
			{
				Blackjackdb.inGameAddPlayer(pname, newPlayerBalance);
				player[numberOfPlayers] = new PlayersTest(pname, newPlayerBalance);
				System.out.println("\nWelcome " + pname + ", you have been added to the database!\nAs a welcome gift, your account has been topped up with " + newPlayerBalance + " credits!\n");

				System.out.println("[A]dd another player, [S]tart game or [C]ancel?");
				choice = s.next();
				s.nextLine(); // Need to consume this linebreak to be able to use nextLine() next loop iteration...
				if (choice.equalsIgnoreCase("S"))
				{
					System.out.println("\nThe game is on!\nTime to place your bets...\n");
					placeBets(player, numberOfPlayers);
				}
				else if (choice.equalsIgnoreCase("C"))
				{ // Go back to Game Menu
					return;
				}
				else if (!choice.equalsIgnoreCase("A") && !choice.equalsIgnoreCase("S") && !choice.equalsIgnoreCase("C"))
				{
					System.out.println("Invalid choice, returning to Menu...\n");
					return;
				}
			}

			Blackjackdb.inGameCloseConn();

		} //while loop

		s.close();

	} // menuStartGame method


	static void placeBets(PlayersTest[] player, int numberOfPlayers)
	{
		Blackjackdb.inGameOpenConn();
		for(int i=1; i<=numberOfPlayers; i++){
			int saldo = Blackjackdb.inGameGetSaldo(player[i].getPlayerName());
			System.out.println(player[i].getPlayerName() + ", your balance is " + saldo + " Credits. How much do you want to bet this round?\n(Min bet: " + minBet + ", Max bet: " + maxBet + ")");
			player[i].setCurrentBet(s.nextInt());
			Blackjackdb.inGameDecrBalance(player[i].getPlayerName(), player[i].getCurrentBet());
		}
		Blackjackdb.inGameCloseConn();
		numberOfPlayers++; //
		startGame(player, numberOfPlayers);
	}





	static void startGame(PlayersTest[] player, int numberOfPlayers)
	{
		// Dela ut två kort var: ////////////////////////////////////////////////////////////////////////
		for (int i=1;i<numberOfPlayers;i++)
		{
			player[i].cards[0] = Card.getCard();
			player[i].setDrawnCards(1);
			drawBoard(player, numberOfPlayers, false);
			try{Thread.sleep(1000);}
			catch(InterruptedException ex) {Thread.currentThread().interrupt();}
		}

		player[0].cards[0] = Card.getCard();
		player[0].setDrawnCards(1);
		drawBoard(player, numberOfPlayers, false);
		try{Thread.sleep(1000);}
		catch(InterruptedException ex) {Thread.currentThread().interrupt();}

		for (int i=1;i<numberOfPlayers;i++)
		{
			player[i].cards[1] = Card.getCard();
			player[i].setDrawnCards(2);
			drawBoard(player, numberOfPlayers, false);
			try{Thread.sleep(1000);}
			catch(InterruptedException ex) {Thread.currentThread().interrupt();}
		}

		player[0].cards[1] = Card.getCard();
		player[0].setDrawnCards(2);
		drawBoard(player, numberOfPlayers, false);
		try{Thread.sleep(1000);}
		catch(InterruptedException ex) {Thread.currentThread().interrupt();}

		for (int i=1;i<numberOfPlayers;i++)
		{
		countPlayerHand(player, i);
		}
	}


	static void countPlayerHand(PlayersTest[] player, int playerNumber)
	{
		int i = playerNumber;
		int currentCard = 0;
		player[i].setSumOfHand(0);
		player[i].setSumOfHandWithAces(0);

		for (int d=0; d < player[i].getDrawnCards(); d++)
		{
			currentCard = Integer.parseInt(player[i].cards[d][1]);

			if (currentCard == 1)
			{
				player[i].setSumOfHand(player[i].getSumOfHand() + 1);
				player[i].setSumOfHandWithAces(player[i].getSumOfHandWithAces() + 11);
			}
			else if (currentCard > 9)
			{
				player[i].setSumOfHand(player[i].getSumOfHand() + 10);
				player[i].setSumOfHandWithAces(player[i].getSumOfHandWithAces() + 10);

			}
			else
			{
				player[i].setSumOfHand(player[i].getSumOfHand() + currentCard);
				player[i].setSumOfHandWithAces(player[i].getSumOfHandWithAces() + currentCard);
			}
		}

		if (player[i].getSumOfHand() > 21)
		{
			System.out.print(space(28*(i-1)) + "  " + player[i].getSumOfHand() + " = BUST! [C]ontinue");
			s.next();
		}

		else if ((player[i].getSumOfHandWithAces() == 21) || (player[i].getSumOfHand() == 21))
		{
			System.out.print(space(28*(i-1)) + "  " + "BLACKJACK! [C]ontinue");
			s.next();
		}

		else
		{
			drawBoard(player, numberOfPlayers+1, false);
			System.out.print(space(28*(i-1)) + " " + player[i].getSumOfHand());
			if (player[i].getSumOfHandWithAces() > player[i].getSumOfHand())
			{
				System.out.print(" or " + player[i].getSumOfHandWithAces() + ". [H]it or [S]tay?");
			}
			else
			{
				System.out.print(". [H]it or [S]tay?");
			}
			hitOrStay = s.next();
			if (hitOrStay.equalsIgnoreCase("H"))
			{
				player[i].cards[player[i].getDrawnCards()] = Card.getCard();
				player[i].setDrawnCards(player[i].getDrawnCards()+1);
				drawBoard(player, numberOfPlayers+1, false);
				countPlayerHand(player, i);
			}
		}

	}





	static void drawBoard(PlayersTest[] player, int numberOfPlayers, boolean revealDealersSecond) {
		String boxTop = "╔═════════════════════════╗ ";
		String boxBtm = "╚═════════════════════════╝ ";
		String[] faceDown = {" ", " "};

		// Rita upp dealerns del av bordet:
		System.out.println();
		System.out.println(space(56)+boxTop+"\n"+space(56)+"║ Dealer"+space(18)+"║");
		//Första kortraden:
		for (int p=0;p<4;p++)
		{
			if(player[0].getDrawnCards() == 2)
			{
				System.out.print(space(56)+"║");
				System.out.print(buildCard(player[0].cards[0])[p]);
				if (revealDealersSecond == false){System.out.print(buildCard(faceDown)[p]);}
				else{System.out.print(buildCard(player[0].cards[1])[p]);}
				System.out.print(space(25-(player[0].getDrawnCards()*5)) + "║ ");
			}
			if ((player[0].getDrawnCards() < 6) && (player[0].getDrawnCards() != 2))
			{
				if (player[0].getDrawnCards() > 2) {revealDealersSecond = true;}
				System.out.print(space(56)+"║");
				for (int x=0;x<player[0].getDrawnCards();x++){System.out.print(buildCard(player[0].cards[x])[p]);}
				System.out.print(space(25-(player[0].getDrawnCards()*5)) + "║ ");
			}
			else if (player[0].getDrawnCards() != 2)
			{
				System.out.print(space(56)+"║");
				for (int x=0;x<5;x++){System.out.print(buildCard(player[0].cards[x])[p]);}
				System.out.print("║ ");
			}
			System.out.println();
		}
		//Andra kortraden:
		for (int p=0;p<4;p++)
		{
			if(player[0].getDrawnCards() > 5)
			{
				System.out.print(space(56)+"║");
				for (int x=5;x<player[0].getDrawnCards();x++){System.out.print(buildCard(player[0].cards[x])[p]);}
				System.out.print(space(50-(player[0].getDrawnCards()*5)) + "║ ");
			}
			else
			{
				System.out.print(space(56)+"║"+space(25)+"║ ");
			}
			System.out.println();
		}
		System.out.print(space(56)+boxBtm);
		System.out.println();

		// Rita upp spelarnas del av bordet:
		for (int i=1;i<6;i++){System.out.print(boxTop);}
		System.out.println();
		for (int i=1;i<numberOfPlayers;i++){System.out.print("║ " + player[i].getPlayerName() + space(24-(player[i].getPlayerName().length())) + "║ ");}
		for (int i=1;i<(6-numberOfPlayers+1);i++){System.out.print("║" + space(25) + "║ ");} //Fyller ut de tomma platserna...
		System.out.println();
		for (int i=1;i<numberOfPlayers;i++){System.out.print("║ Bet: " + player[i].getCurrentBet() + space(19-(String.valueOf(player[i].getCurrentBet()).length())) + "║ ");}
		for (int i=1;i<(6-numberOfPlayers+1);i++){System.out.print("║" + space(25) + "║ ");} //Fyller ut de tomma platserna...
		System.out.println();
		//Första kortraden:
		for (int p=0;p<4;p++)
		{
			for (int i=1;i<numberOfPlayers;i++)
			{
				if(player[i].getDrawnCards() < 6)
				{
					System.out.print("║");
					for (int x=0;x<player[i].getDrawnCards();x++){System.out.print(buildCard(player[i].cards[x])[p]);}
					System.out.print(space(25-(player[i].getDrawnCards()*5)) + "║ ");
				}
				else
				{
					System.out.print("║");
					for (int x=0;x<5;x++){System.out.print(buildCard(player[i].cards[x])[p]);}
					System.out.print("║ ");
				}
			}
			for (int x=1;x<(6-numberOfPlayers+1);x++){System.out.print("║" + space(25) + "║ ");} //Fyller ut de tomma platserna...
			System.out.println();
		}
		//Andra kortraden:
		for (int p=0;p<4;p++)
		{
			for (int i=1;i<numberOfPlayers;i++)
			{
				if(player[i].getDrawnCards() > 5)
				{
					System.out.print("║");
					for (int x=5;x<player[i].getDrawnCards();x++){System.out.print(buildCard(player[i].cards[x])[p]);}
					System.out.print(space(50-(player[i].getDrawnCards()*5)) + "║ ");
				}
				else
				{
					System.out.print("║"+space(25)+"║ ");
				}
			}
			for (int x=1;x<(6-numberOfPlayers+1);x++){System.out.print("║" + space(25) + "║ ");} //Fyller ut de tomma platserna...
			System.out.println();
		}
		for (int i=1;i<numberOfPlayers;i++)
		{
			if(player[i].getDrawnCards() < 6)
			{
				System.out.print(boxBtm);
			}
			else
			{
				System.out.print(boxBtm);
			}
		}
		for (int x=1;x<(6-numberOfPlayers+1);x++){System.out.print(boxBtm);} //Fyller ut de tomma platserna...

		System.out.println();
	}





	//		System.out.print("╔═════════════════════════╗\n");
	//		System.out.print("║ Hans-Olav     Chips:160 ║\n");
	//		System.out.print("║ Bet:40        Win: 0    ║\n");
	//		System.out.print("║╭───╮╭───╮╭───╮╭───╮╭───╮║\n");
	//		System.out.print("║│ ♡ ││ ♠ ││ ♡ ││ ♣ ││ ♢ │║\n");
	//		System.out.print("║│ 3 ││ K ││ 9 ││ 2 ││ A │║\n");
	//		System.out.print("║╰───╯╰───╯╰───╯╰───╯╰───╯║\n");
	//		System.out.print("║╭───╮╭───╮╭───╮╭───╮╭───╮║\n");
	//		System.out.print("║│ ♠ ││ ♠ ││ ♢ ││ ♡ ││ ♣ │║\n");
	//		System.out.print("║│ Q ││ J ││1 0││ 3 ││ 3 │║\n");
	//		System.out.print("║╰───╯╰───╯╰───╯╰───╯╰───╯║\n");
	//		System.out.print("╚═════════════════════════╝\n");
	//		System.out.print("  You have 9 or 19.\n");
	//		System.out.print("  Hit or Stay? [h/s]\n");




	static String[] buildCard(String[] card) {
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

	static String space(int number) {
		// Metod som matar ut blanksteg.
		String spaces = new String(new char[number]).replace("\0", " ");
		return spaces;
	}

}















