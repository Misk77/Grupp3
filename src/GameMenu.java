import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class GameMenu {

	public void mainMenu(Scanner scanner, Db Blackjackdb) {
		while (true) {
			int saldo = 100;

			System.out.println("							  ╔═════════════════════════╗\n"
					+ "							  ║  Please enter your name ║\n"
					+ "							  ╚═════════════════════════╝\n");
			String pname = scanner.next();
			scanner.nextLine();

			if (pname.equals("thomas")) {
				//menulogo();

				System.out.println("\n"
						+ "                                   ╔═══════════════════════════════════════════════════════════════════════════════╗\n"
						+ "                                   ║          Welcome back " + pname + "! " + "You have "
						+ saldo + " credits on your account.           ║\n"
						+ "                                   ╚═══════════════════════════════════════════════════════════════════════════════╝");
			}

			else {
				menulogo();

				System.out.println("\n"
						+ "                                   ╔═══════════════════════════════════════════════════════════════════════════════╗\n"
						+ "                                                                  Welcome " + pname + "! \n"
						+ "				║ 		       You're account has been created						║ \n"
						+ "			   	║ 	    You have recived 100 credits as a gift too your account.				║ \n"
						+ "                                   ╚═══════════════════════════════════════════════════════════════════════════════╝\n");

			}

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
				GameMain.menuStartGame();
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
				Blackjackdb.login();
				// Blackjackdb.menu();
			} else if (!choice.equalsIgnoreCase("A") && !choice.equalsIgnoreCase("S")
					&& !choice.equalsIgnoreCase("C")) {
				System.out.println("Invalid choice, returning to Menu...\n");
				return;
			}
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
