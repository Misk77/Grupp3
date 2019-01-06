import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class GameMenu {
	static boolean firstOpen = true;


	public void mainMenu(Scanner scanner, Db Blackjackdb) {
		if (firstOpen) {
			firstOpen = false;
			GameMain.player[0] = new Player("Dealer", 0); 
			GameMain.numberOfPlayers = 0;
			addPlayer(scanner, Blackjackdb);
		}
		while (true) {
			menulogo();
			System.out.println(

					"							  ╔═════════════════════════╗\n"
							+ "							  ║        GAME MENU        ║\n"
							+ "							  ╚═════════════════════════╝\n"
							+ "							  ╔═════════════════════════╗\n"
							+ "							  ║                         ║\n"
							+ "							  ║       [S]tart Game      ║\n"
							+ "							  ║                         ║\n"
							+ "							  ║       [A]dd player      ║\n"
							+ "							  ║                         ║\n"
							+ "							  ║       [H]ighscore       ║\n"
							+ "							  ║                         ║\n"
							+ "							  ║       [D]Admin area     ║\n"
							+ "							  ║                         ║\n"
							+ "							  ║         [E]xit          ║\n"
							+ "							  ║                         ║\n"
							+ "							  ╚═════════════════════════╝\n"

					);
			String choice = scanner.next();
			scanner.nextLine();

			if (choice.equalsIgnoreCase("S")) { // START GAME
				System.out.println("START GAME");
				GameMain.placeBets(GameMain.player, GameMain.numberOfPlayers);
			} else if (choice.equalsIgnoreCase("E")) { // EXIT
				System.out.println("Have a nice day!");
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					System.out.printf("Somthing went wrong:(", e);
					e.printStackTrace();
				}
				System.out.println("\tBye....");
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					System.out.printf("Somthing went wrong:(", e);
					e.printStackTrace();
				}
				System.out.println("\t\tBye....");
				System.exit(0);
			} else if (choice.equalsIgnoreCase("D")) { // Go into Db Tool Menu
				Blackjackdb.login();}
				else if (choice.equalsIgnoreCase("H")) { // Go into Db Tool Menu
					Blackjackdb.highscorePlayerView();
			} else if (choice.equalsIgnoreCase("A")) {
				addPlayer(scanner, Blackjackdb);
			} else if (!choice.equalsIgnoreCase("A") && !choice.equalsIgnoreCase("S")
					&& !choice.equalsIgnoreCase("C")) {
				System.out.println("Invalid choice, returning to Menu...\n");
				return;
			}


			
		}
	}



	void addPlayer(Scanner scanner, Db Blackjackdb) {

		for (int w=0; w<20; w++) 
		{
			System.out.print("\n\n\n");
			try{Thread.sleep(20);}
			catch(InterruptedException ex)
			{Thread.currentThread().interrupt();}
		}
//		
		
		int saldo = 0;

		System.out.println("							  ╔═════════════════════════╗\n"
				+ "							  ║  Please enter your name ║\n"
				+ "							  ╚═════════════════════════╝\n");

		String pname = scanner.next();
		scanner.nextLine();
		
		
		String boxSpace1 = GameMain.space( 19 - ( (int) ((pname +(Integer.toString(saldo))) ).length()) );
		String boxSpace2 = GameMain.space( 37 - ( (int) (pname).length()) );

		if (GameMain.Blackjackdb.playerNameExists(pname)){
			GameMain.numberOfPlayers++;
			saldo = GameMain.Blackjackdb.inGameGetSaldo(pname);
			GameMain.player[GameMain.numberOfPlayers] = new Player(pname, saldo);
			System.out.println("\n"
					+ "                                  ╔═══════════════════════════════════════════════════════════════════════════════╗\n"
					+ "                                  ║          Welcome back " + pname + "! " + "You have "
					+ saldo + " credits on your account."+ boxSpace1 + "║\n"
					+ "                                  ╚═══════════════════════════════════════════════════════════════════════════════╝");
			System.out.println("                                  Your Highscore: " + Blackjackdb.inGameGetHighScore(pname));
			
			
		}else{
			GameMain.numberOfPlayers++;
			GameMain.Blackjackdb.inGameAddPlayer(pname, GameMain.newPlayerBalance);
			GameMain.player[GameMain.numberOfPlayers] = new Player(pname, GameMain.newPlayerBalance);

			System.out.println("\n"
					+ "                                   ╔══════════════════════════════════════════════════════════════════════════╗\n"
					+ "                                   ║                            Welcome " + pname + "!" + boxSpace2  +"║\n"                         
					+ "                                   ║                    You're account has been created.                      ║ \n"
					+ "                                   ║ 	    You have recived " + GameMain.newPlayerBalance + " credits as a gift too your account.          ║ \n"
					+ "                                   ╚══════════════════════════════════════════════════════════════════════════╝\n");
		}


	}


	@Override
	public String toString() {
		return "GameMenu [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
		+ "]";
	}

	void menulogo() {
		System.out.println("\n\n\n\n");

		int width = 150;
		int height = 30;

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setFont(new Font("SansSerif", Font.BOLD, 24));

		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.drawString("BlackJack", 15, 20);

		for (int y = 0; y < height; y++) {
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < width; x++) {

				sb.append(image.getRGB(x, y) == -16777216 ? " " : "$");

			}

			if (sb.toString().trim().isEmpty()) {
				continue;
			}

			System.out.println(sb);
		}
	}
}
