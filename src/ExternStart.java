
public class ExternStart {
	
		public void init() {
//			Nedanstående behövs för att starta spelet	
			GameMain.player = new Player[6];
			GameMain.player[0] = new Player("Dealer", 0);
			GameMain.numberOfPlayers = 5; // Glöm inte denna!
			int bet = 100;
			GameMain.player[1] = new Player("Greta", 1000); 
			GameMain.player[2] = new Player("Yngvor", 1000); 
			GameMain.player[3] = new Player("Dagny", 1000); 
			GameMain.player[4] = new Player("Gunhild", 1000); 
			GameMain.player[5] = new Player("Inga-Lill", 1000); 

//			Ett gäng variabler för att ändra utseende och funktion om man vill:
			GameMain.numberOfDecks = 4;
			GameMain.minBet = 10;				
			GameMain.maxBet = 2000;				
			GameMain.littlePause = 1000;		// Pauser under dealerns tur & poängräkning
			//GameMain.tableLowerMargin = 18;
			GameMain.newPlayerBalance = 200;
			GameMain.minBet = 10;
			GameMain.maxBet = 2000; 
			GameMain.numberOfDecks = 4;
			GameMain.alphaNumericOnly = true; 	// Concerning username input.
			GameMain.text_ask_for_new_round = " Are ya'll keen for another round? [Y/N]";
			GameMain.text_dealer_hits = " %s. Dealer hits.";
			GameMain.text_dealer_stays = " Dealer stays at %s.";
			GameMain.text_hit_or_stay =  ". [H]it or [S]tay?";
			GameMain.text_user_bj =  "BLACKJACK! [C]ontinue";
			GameMain.text_user_bust = " = BUST! [C]ontinue";
			GameMain.text_dealer_bj = "Dealer got BLACKJACK!";
			GameMain.text_dealer_bust = "Dealer got BUSTED!";
			GameMain.text_how_much_bet = "\n %1$s, your balance is %2$s credits. How much do you want to bet this round?\n (Min bet: %3$s, Max bet: %4$s)\n";
			GameMain.text_place_bets =  "\n Please place your bets, while the dealer is shuffling the %1$s decks!\n";
			GameMain.text_table_full =  "\n The table is full.\n Please place your bets, while the dealer is shuffling the %1$s decks!\n";
			GameMain.text_added_player =  "\n Welcome %1$s, you have been added to the database!\n As a welcome gift, your account has been topped up with %2$d credits!\n";
			GameMain.text_welcome_back =  "\n Welcome back, %1$s, You have %2$d credits to your name.\n";
			GameMain.text_add_start_cancel =  "\n [A]dd another player, [S]tart game or [C]ancel?\n";
			GameMain.text_ask_for_name = "\n Player %1$d, please enter your name (a-z, A-Z, 0-9):\n";
			GameMain.text_game_on = "\n The game is on, time to place your bets!\n";
			GameMain.text_missed_key = "\n You're too drunk for gambling, honey. Just go home!\n";
			
//			Lägger till spelarna i DB:n:		
			for (int x=GameMain.numberOfPlayers; x>0; x--) {GameMain.Blackjackdb.inGameAddPlayer(GameMain.player[x].getPlayerName(), GameMain.player[x].getPlayerSaldo());}	
			
//			Spikar insatsen ovan för alla tanter och skriver dem till databasen:
			for (int x=GameMain.numberOfPlayers; x>0; x--) {
				GameMain.player[x].setCurrentBet(bet);
				GameMain.Blackjackdb.inGameDecrBalance(GameMain.player[x].getPlayerName(), GameMain.player[x].getCurrentBet());
			}
		
//			För att lägga bets var för sig::
			//GameMain.placeBets(GameMain.player, GameMain.numberOfPlayers);

//			För att komma rakt in i spelet:
			GameMain.startGame(GameMain.player, GameMain.numberOfPlayers+1);
			

		}
}

	
	
	
	