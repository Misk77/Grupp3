
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Db {
	// player
	private String playerName;
	private String dealer;
	private int saldo;
	// menu
	private int menu;
	// jdbc
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null; // lägga in preparedStatement
	private ResultSet resultSet = null; // använda som result VAR
	private String sql;
	// Scanner
	Scanner sc = new Scanner(System.in);

	// För att använda vår onlineDB, alltid ONLINE host Hostinger.eu

	private String pass = "mi235277sk";
	private String user = "u209758462_misk7";
	private String dburl = "jdbc:mysql://sql150.main-hosting.eu/u209758462_miskb?";

	// För att testa lokalt host: dusjälv.se

	// private String dburl = "jdbc:mysql://localhost/feedback?";
//	private String dburl = "jdbc:mysql://localhost:8889/u209758462_miskb?"; 
//	private String user = "root";
//	private String pass = "root";

	// METHOD MENU
	public void menu() {
		while (true) {
			try {
				System.out.println("|============================|" + "\n|------  Database Menu -------|"
						+ "\n|---- BLackJack DATABASE ----|" + "\n|============================|"
						+ "\n[1]-->Create Database: " + "\n[2]-->Create TABLE:  " + "\n[3]-->Create players: "
						+ "\n[4]-->Describe BLackJack tabell: " + "\n[5]-->players records: "
						+ "\n[6]-->Select Records: " + "\n[7]-->Update All Saldo To 200:  " + "\n[8]-->Delete Records: "
						+ "\n[9]-->Drop Table: " + "\n[10]->Player Info: " + "\n[11]->Search Player: "
						+ "\n[12]->InsertTestPlayers : " + "\n[13]-> Highscore Lista : "
						+ "\n[14]->PlayerUpdateTheSaldo : " + "\n[15]->inGameDecrBalance: "
						+ "\n[16]->inGameIncrBalance: " + "\n[17]->connectMethod:LÄGGA IN NÅT ANNAT HÄR?? "
						+ "\n[18]->inGameOpenConn: " + "\n[19]->inGameCloseConn: " + "\n[20]->InGamePlayerUpdateSaldo: "
						+ "\n[21]->getsaldoPlayer: " + "\n[22]->inGameAddPlayer: " + "\n[23]->inGamePlayersRecord: "
						+ "\n[24]-> Login to Admin are: " + "\n[25]->highscorePlayerView: "
						+ "\n[0]->Go/Return to the GAME MENU " + "\n|============================|"
						+ "\n|----------------------------|" + "\n|---------- Grupp3 ----------|"
						+ "\n|----------------------------|" + "\n|============================|");
				System.out.println("What you wanna do? ");

				// Valen i menu
				menu = Integer.parseInt(sc.next());
				sc.nextLine();
				switch (menu) {
				case 1:
					createDatabase();
					break;
				case 2:
					createTable();
					break;
				case 3:
					addplayers();
					break;
				case 4:
					describeTable();
					break;
				case 5:
					playersRecord();
					break;
				case 6:
					selectRecords();
					break;
				case 7:
					UpdateAllSaldoTo200();
					break;
				case 8:
					deleteRecords();
					break;
				case 9:
					dropTable();
					break;
				case 10:
					playerInfo(playerName, connect);
					break;
				case 11:
					searchPlayer();
					break;
				case 12:
					insertTestPlayers(playerName,saldo);
					break;
				case 13:
					highscorewithid();
					break;
				case 14:
					playerUpdateTheSaldo(playerName, saldo);
					break;
				case 15:
					inGameDecrBalance(playerName, saldo);
					break;
				case 16:
					inGameIncrBalance(playerName, saldo);
					break;
				case 17: // denna kanske ej behövs
					// connectMethod();
					break;
				case 18:
					inGameOpenConn();
					break;
				case 19:
					inGameCloseConn();
					break;
				case 20:
					duringGameplayerUpdateTheSaldo(saldo);
					break;
				case 21:
					getsaldoFromPlayer(getName());
					break;
				case 22:
					inGameAddPlayer2(playerName, saldo);
					break;
				case 23:
					playersRecord();
					break;
				case 24:
					login();
					break;
				case 25:
					highscorePlayerView();
					break;
				case 0:
					System.out.println("Returning to the GAME");
					GameMain.theMenu.mainMenu(GameMain.s, GameMain.Blackjackdb);

					break;
				default:
					System.out.println("No such option in menu");
					System.out.println("\t try again........");
					break;

				}
			} catch (Exception e) {

			}
		}

	}

///////
	////// DATABASE STUFF INDELAT I METHODS
	void createDatabase() {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.cj.jdbc.Driver");// com.mysql.cj.jdbc.Driver
			// Setup the connection with the DB
			connect = DriverManager.getConnection(dburl, user, pass);
			System.out.println("DB connection successful to: " + dburl);
			System.out.println("Creating database...");
			statement = connect.createStatement();
			String sql = "CREATE DATABASE  IF NOT EXISTS u209758462_miskb ";
			statement.executeUpdate(sql);

			System.out.println("Database created successfully...");
			System.out.println("Databasen skapades hos: " + dburl);
		} catch (Exception e) {
			System.out.printf("Something went wrong: ", e);
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);

			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);			}
		}
	}

	// CREATE TABLE
	void createTable() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);

			System.out.println("Creating table in given database...");
			statement = connect.createStatement();

			sql = "create table IF NOT EXISTS BlackJack " + " (id INT NOT NULL AUTO_INCREMENT,\r\n"
					+ "playerName varchar(50),\r\n" + "saldo int ,\r\n" + "highscore int,\r\n" + "   primary key (id))";
			statement.executeUpdate(sql);

			System.out.println("Created table successfully ...");
		} catch (Exception e) {
			System.out.printf("Something went wrong: ", e);		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);			}
		}
	}

	// Insert
	void insertIntoTable(String playerName, int saldo, int highscore, Connection connect) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} // com.mysql.cj.jdbc.Driver
			// Setup the connection with the DB
		try {
			connect = DriverManager.getConnection(dburl, user, pass);
		} catch (SQLException e) {

			System.out.printf("Something went wrong: ", e);		}
		try {
			preparedStatement = connect.prepareStatement("insert into BlackJack values (default,?,?,?)");

			preparedStatement.setString(1, playerName);
			preparedStatement.setInt(2, saldo);
			preparedStatement.setInt(3, highscore);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);		}

		System.out.println();
		System.out.println("Hello " + getPlayerName() + "! Let's play!\n");
		System.out.println(getName() + " current balance: " + getSaldo());
	}

	// DESCRIBE
	void describeTable() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);

			statement = connect.createStatement();
			ResultSet resultSet = statement.executeQuery("DESCRIBE BlackJack");
			ResultSetMetaData md = resultSet.getMetaData();
			int col = md.getColumnCount();
			for (int i = 1; i <= col; i++) {
				String col_name = md.getColumnName(i);
				System.out.println(col_name);
			}
			DatabaseMetaData dbm = connect.getMetaData();
			ResultSet rs1 = dbm.getColumns(null, "%", "BlackJack", "%");
			while (rs1.next()) {
				String col_name = rs1.getString("COLUMN_NAME");
				String data_type = rs1.getString("TYPE_NAME");
				int data_size = rs1.getInt("COLUMN_SIZE");
				int nullable = rs1.getInt("NULLABLE");
				System.out.println(col_name + " " + data_type + "(" + data_size + ")");
				if (nullable == 1) {
					System.out.println("YES");
				} else {
					System.out.println("NO");

				}
			}
		} catch (SQLException se) {

		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
		}
	}

	// playersRecord
	// playersRecord
	void playersRecord() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);
			System.out.println("Creating statement...");
			statement = connect.createStatement();

			// is returned in a 'ResultSet' object.
			String strSelect = "select id, playerName,Saldo ,highscore from BlackJack";
			System.out.println("The SQL query is: " + strSelect);
			System.out.println();

			ResultSet resultSet = statement.executeQuery(strSelect);

			// For each row, retrieve the contents of the cells with getXxx(columnName).
			System.out.println("The records selected are:");

			int rowCount = 0;
			while (resultSet.next()) { // Move the cursor to the next row, return false if no more row
				System.out.println();
				System.out.printf("%1s %10s %10s %10s%n", "ID", "PLAYER", "SALDO", "HIGHSCORE");

				System.out.println(resultSet.getInt("id") + ",   " + resultSet.getString("playerName") + ",     "
						+ resultSet.getString("Saldo") + ",     " + resultSet.getString("highscore"));
				++rowCount;
			}
			System.out.println();
			System.out.println("Total number of records = " + rowCount);
			System.out.println();

		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);
		}

		try {
			statement.close();
		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);
		}
		try {
			connect.close();
		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);
		}
	}

	// SELECT * FROM BlackJack
	void selectFromtable() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);
			System.out.println("Creating statement...");
			statement = connect.createStatement();

			// is returned in a 'ResultSet' object.
			String strSelect = "select id, playerName ,Saldo  from BlackJack";
			System.out.println("The SQL query is: " + strSelect);
			System.out.println();

			ResultSet resultSet = statement.executeQuery(strSelect);

			// For each row, retrieve the contents of the cells with getXxx(columnName).
			System.out.println("The records selected are:");

			int rowCount = 0;
			while (resultSet.next()) { // Move the cursor to the next row, return false if no more row
				System.out.println();
				int id = resultSet.getInt("id");
				String playerName = resultSet.getString("playerName");
				int Saldo = resultSet.getInt("Saldo");
				System.out.printf("%2s %8s %5s%n", "id", "playerName", "Saldo");

				System.out.println(id + ", " + playerName + ",    " + Saldo);
				++rowCount;
			}
			System.out.println();
			System.out.println("Total number of records = " + rowCount);
			System.out.println();

		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
		}
	}
	// Close the resources - Done automatically by try-with-resources//bra att veta

	// SELECT RECORDS
	void selectRecords() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);

			System.out.println("Creating statement...");
			statement = connect.createStatement();

			String sql = "SELECT  id,playerName ,saldo FROM BlackJack";
			resultSet = statement.executeQuery(sql);
			// Extract data from result set
			String strSelect = "select * from BlackJack";
			System.out.println("The SQL query is: " + strSelect); // Echo For debugging
			System.out.printf("%2s %8s %5s%n", "id", "playerName", "Saldo");
			resultSet = statement.executeQuery(strSelect);
			while (resultSet.next()) { // Move the cursor to the next row
				System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("playerName") + ", "
						+ resultSet.getString("Saldo"));
			}
			resultSet.close();
		} catch (SQLException e) {
			// Handle errors for JDBC
			System.out.printf("Something went wrong: ", e);
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
		}
	}

	// // DROP table DROP TABLE BlackJack;
	void dropTable() {
		// This will load the MySQL driver, each DB has its own driver
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
					} catch (ClassNotFoundException e1) {
						
						System.out.printf("Something went wrong: ", e1);
					}
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);
			System.out.println("Connected database successfully..." + dburl);
			String sql = "DROP TABLE  IF EXISTS BlackJack";
			statement.executeUpdate(sql);
			System.out.println("DROP TABLE successfully...");
		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
		}
	}

	// Update from simulator OBS!! Hårdkodat att man förlorar 1000, här ska bet in
	// från game
	// Update current playerssaldo
	public boolean UpdateSaldo(int saldo) {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);
			System.out.println("Connected database successfully..." + dburl);
			int bet = 1000;

			saldo = getSaldo() - bet;
			// update player in blacjjackgame - ("Select * from BlackJack where playerName
			// ='" + playerName + "'");
			// String strUpdate = ("update saldo from BlackJack where playerName ='" +
			// playerName + "'");
			setSaldo(saldo);
			System.out.println(getName() + "Saldo  is now: " + " " + getSaldo());
			System.out.println(getName() + " updated balance now: " + " " + getSaldo());
			insertIntoTable(getName(), getSaldo(), inGameGetHighScore(playerName), connect);

		} catch (SQLException e) {
			// Handle errors for JDBC
			System.out.printf("Something went wrong: ", e);
		} finally {

			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
		}
		return true;
	}

	/// UPDATE check if update went through
	void UpdateAllSaldoTo200() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);
			System.out.println("Connected database successfully..." + dburl);
			System.out.println("Creating statement...");
			statement = connect.createStatement();

			// set saldo 200 alla som har mindre än 200
			String strUpdate = "update BlackJack set Saldo = 200 where Saldo <200";
			System.out.println("The SQL query is: " + strUpdate); // Echo for debugging
			int countUpdated = statement.executeUpdate(strUpdate);
			System.out.println(countUpdated + " records affected.");

			// SELECT to check the UPDATE.
			String strSelect = "select * from BlackJack ";
			System.out.println("The SQL query is: " + strSelect); // Echo for debugging
			ResultSet resultSet = statement.executeQuery(strSelect);
			while (resultSet.next()) { // Move the cursor to the next row
				System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("playerName") + ", "
						+ resultSet.getString("Saldo"));
			}
		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
		}
	}

	/// DELETE RECORDS
	void deleteRecords() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);
			System.out.println("Connected database successfully..." + dburl);
			System.out.println("Creating statement...");
			statement = connect.createStatement();

			// DELETE records with id>=0 and id<10
			String sqlDelete = "delete from BlackJack where id>0 and id<100";
			System.out.println("The SQL query is: " + sqlDelete);
			int countDeleted = statement.executeUpdate(sqlDelete);
			System.out.println(countDeleted + " records deleted.\n");
		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
		}
	}

	// addPLAYER
	void addplayers() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.printf("Something went wrong: ", e);
		}
		try {
			try {
				connect = DriverManager.getConnection(dburl, user, pass);
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			System.out.println("Players already exist: ");
			// playerInfo("Michel", connect);
			System.out.println();
			while (true) {

				System.out.println("*********************************\n*Type [ready] for start playing!*");
				System.out.println("*********************************");
				System.out.println("Enter player name:");
				String name = sc.next();
				if (name.equalsIgnoreCase("ready"))
					break;
				setPlayerName(name);

				System.out.printf("" + getName(), ": current balance: ", setSaldo(0));
				System.out.println(" money insert:");
				saldo = sc.nextInt();
				setSaldo(saldo);
				System.out.println(getName() + " updated balance now: " + " " + getSaldo());
				insertIntoTable(name, saldo, inGameGetHighScore(playerName), connect);
				System.out.println("Record is updated to BlackJack table!");
				System.out.println("Players are inserted into BlackJack successfully...");

			}
		} finally {

		}
	}

	///
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	// DEnna visar först i add player metoden vilka spelare som finns i DB
	// Nedanför är en metod för att hämta värden från en tabell
	private void playerInfo(String playerName, Connection connect) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connect = DriverManager.getConnection(dburl, user, pass);
			System.out.println("Players already exist: ");
			System.out.println();

			statement = connect.createStatement();
			resultSet = statement.executeQuery("Select * from BlackJack where playerName ='" + playerName + "'");
			if (resultSet.next()) {
				System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("playerName") + ", "
						+ resultSet.getString("Saldo"));
			}
		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
		}
	}

	// searchPlayer Skriv in namnet på spelare och få ut: ID, playerName,Saldo

	void searchPlayer() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.printf("Something went wrong: ", e);
		}

		try {
			connect = DriverManager.getConnection(dburl, user, pass);
		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);
		}

		System.out.println("Connected database successfully..." + dburl);
		System.out.println("Creating statement...");

		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);
		}
		System.out.println("Enter Player you looking for: ");
		String playerName = sc.next();
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {

			System.out.printf("Something went wrong: ", e);
		}
		try {
			System.out.printf("%1s %1s %6s%n", "id", "playerName", "Saldo");
			resultSet = statement.executeQuery("Select * from BlackJack where playerName ='" + playerName + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (resultSet.next()) {
				System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("playerName") + ",   "
						+ resultSet.getString("Saldo"));
			}

		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
		}
	}

	// Get saldo from a player
	//

	void getsaldoPlayer(String string) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.printf("Something went wrong: ", e);
		}

		try {
			connect = DriverManager.getConnection(dburl, user, pass);
		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);		}

		System.out.println("Connected database successfully..." + dburl);
		System.out.println("Creating statement...");

		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);		}
		try {
			System.out.printf("%1s %n", "Saldo");
			resultSet = statement.executeQuery("Select saldo from BlackJack where playerName ='" + playerName + "'");
		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);		}
		try {
			while (resultSet.next()) {
				System.out.println(resultSet.getString("Saldo"));
			}

		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
		}
	}

	///// ENkel connection och query, kan ändras eftersom
	public void selectFromSomeTable() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(dburl, user, pass);
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM BlackJack");
			while (resultSet.next()) {
				// Get values
				String someValue = resultSet.getString("playerName");
				System.out.println(someValue);
			}

		} catch (Exception e) {
			System.out.printf("Something went wrong: ", e);
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
		}
	}

	// Insert auto player for test
	// Insert player for testing or could be use for random opponents??
	void insertTestPlayers(String playerName, int saldo) {
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.printf("Somthing went wrong:(", e);
			}

			try {

				// Setup the connection with the DB
				connect = DriverManager.getConnection(dburl, user, pass);
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}

			try {
				preparedStatement = connect.prepareStatement("insert into BlackJack values (default,?,?,?)");
				sql = "insert into BlackJack values (default, 'testplayer1',5000,123);";
				statement.executeUpdate(sql);
				sql = "insert into BlackJack values (default, 'testplayer2',6000,124);";
				statement.executeUpdate(sql);
				sql = "insert into BlackJack values (default,'testplayer3',7000,125)";
				statement.executeUpdate(sql);
				sql = "insert into BlackJack values (default, 'testplayer4',8000,126)";

				System.out.println("Inserted TESTPLAYERS into the table...");
			} catch (SQLException e) {
				System.out.printf("Somthing went wrong:(", e);
			}

			System.out.println();
			System.out.println("Hello TestPlayers/Random Players " + "! Let's play!\n");

			try {

			} finally {
			}
		} finally {
		}

	}

	// Get the highscore ,NOW get all players highscore, maybe do one for search
	// specific??

	void highscorewithid() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);

			System.out.println("Creating statement...");
			statement = connect.createStatement();

			// String sql = "SELECT id,playerName ,highscore,saldo FROM BlackJack";
			String sql = ("SELECT id, playerName,highscore  FROM BlackJack \r\n" + "ORDER BY highscore DESC");
			System.out.println("The SQL query is: " + sql); // Echo For debugging
			System.out.printf("%1s %10s %15s%n", "ID", "PLAYER", "HIGHSCORE");
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) { // Move the cursor to the next row
				System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("playerName") + ",         "
						+ resultSet.getString("highscore"));
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);			}
		}
	}

	void highscorePlayerView() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);

			System.out.println("Creating statement...");
			statement = connect.createStatement();

			// String sql = "SELECT id,playerName ,highscore,saldo FROM BlackJack";
			String sql = ("SELECT playerName, highscore FROM BlackJack \r\n" + "ORDER BY highscore DESC");
			System.out.println("The SQL query is: " + sql); // Echo For debugging
			System.out.printf("%1s %20s%n", "PLAYER", " HIGHSCORE");
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) { // Move the cursor to the next row
				System.out.println(
						resultSet.getString("playerName") + ",            " + resultSet.getString("highscore"));
			}
			resultSet.close();
		} catch (SQLException e) {
			// Handle errors for JDBC
			System.out.printf("Something went wrong: ", e);		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);			}
		}
	}

	// Player update the saldo 29 dec
	// If plyaer wanna insert more money
	void playerUpdateTheSaldo(String pname, int saldo) {
		System.out.println("Connecting to a selected database...");
		try {
			try {
				connect = DriverManager.getConnection(dburl, user, pass);
			} catch (SQLException e) {

				System.out.printf("Somthing went wrong:(", e);
			}
			System.out.println("Connected database successfully..." + dburl);
			System.out.println(getPlayerName() + " Your current balance is: " + getSaldo());
			System.out.println(getPlayerName() + " Wanna insert money?:");
			saldo = sc.nextInt();
			saldo = (getSaldo() + saldo);
			setSaldo(saldo);
			try {
				preparedStatement = connect.prepareStatement("UPDATE BlackJack SET saldo=(?)");
			} catch (SQLException e) {

				System.out.printf("Something went wrong: ", e);			}

			try {
				preparedStatement.setInt(1, getSaldo());
			} catch (SQLException e) {

				System.out.printf("Something went wrong: ", e);			}

			try {
				preparedStatement.executeUpdate();
			} catch (SQLException e) {

				System.out.printf("Something went wrong: ", e);			}
			System.out.println(getName() + " updated balance is now: " + "  " + getSaldo());

		} finally {

			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Somthing went wrong:(", e);
			}
		}
		return;
	}

	////// InGameSETHIGHSCORE
	int inGameSetHighScore(String pname, int highscore) {
		inGameOpenConn();
		int oldHighscore = inGameGetHighScore(playerName); // Här ska higscore in
		int newHighscore = oldHighscore;// Ta ifrån? ;
		sql = "update BlackJack set highscore='" + Integer.toString((int) newHighscore) + "' where playername='" + pname
				+ "'";

		try (Connection conn = DriverManager.getConnection(dburl, user, pass);
				Statement stmt = conn.createStatement();) {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.printf("Something went wrong: ", e);
		}
		inGameCloseConn();
		return newHighscore;
	}

	// INGAME GETHIGHSCORE
	int inGameGetHighScore(String pname) {

		String plHighscoreStr = "";
		int plHighscoreInt = 0;

		try {
			inGameOpenConn();

			resultSet = statement.executeQuery("Select highscore from BlackJack where playerName ='" + pname + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			try {
				while (resultSet.next()) {
					try {
						plHighscoreStr = resultSet.getString("highscore");
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (plHighscoreStr != null) {
						// plHighscoreInt = Integer.parseInt(plHighscoreStr);
						try {
							plHighscoreInt = Integer.parseInt(plHighscoreStr);
						} catch (NumberFormatException nfe) {
							nfe.printStackTrace();
						}
					}
					{

					}
				}
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);
			}
		} finally {
		}
		inGameCloseConn();
		return plHighscoreInt;
	}

	// During the Game Update saldo
	public boolean duringGameplayerUpdateTheSaldo(int saldo) {
		System.out.println("Connecting to a selected database...");
		try {
			try {
				connect = DriverManager.getConnection(dburl, user, pass);
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);			}

			saldo = (getSaldo() - saldo);
			setSaldo(saldo);
			try {
				preparedStatement = connect.prepareStatement("UPDATE BlackJack SET saldo=(?)");
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);			}

			try {
				preparedStatement.setInt(1, getSaldo());
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);			}

			try {
				preparedStatement.executeUpdate();
			} catch (SQLException e) {

				System.out.printf("somthing went wrong about the executeUpdate", e);
			}
			System.out.println(getName() + " updated balance is now: " + "  " + getSaldo());

		} finally {

			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: ", e);			}
		}
		return true;
	}

	// Get saldo from a player , search specific player
	int getsaldoFromPlayer(String pname) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.printf("Something went wrong: \n", e);	
		}

		try {
			connect = DriverManager.getConnection(dburl, user, pass);
		} catch (SQLException e) {
			System.out.printf("Something went wrong: \n", e);		}

		System.out.println("Connected database successfully..." + dburl);
		System.out.println("Creating statement...");

		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			System.out.printf("Something went wrong: \n", e);		}
		try {

			System.out.printf("%1s %n", "Saldo");
			resultSet = statement.executeQuery("Select saldo from BlackJack where playerName ='" + playerName + "'");
		} catch (SQLException e) {
			System.out.printf("Something went wrong: \n", e);		
			}
		try {
			while (resultSet.next()) {
				System.out.println(resultSet.getString("saldo"));
			}

		} catch (SQLException e) {
			System.out.printf("Something went wrong: \n", e);	
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println(e);
				System.out.printf("Somthing went wrong:(", e);
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println(e);
				System.out.printf("Somthing went wrong:(", e);
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Somthing went wrong:(", e);
			}
		}
		return getSaldo();
	}

	///////////////////////// IN-GAME METODER ////////////////////////////////

	void inGameOpenConn() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.printf("Something went wrong: \n", e);	
		}

		try {
			connect = DriverManager.getConnection(dburl, user, pass);
		} catch (SQLException e) {
			System.out.printf("Something went wrong: \n", e);			}

		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			System.out.printf("Something went wrong: \n", e);			}
	}

	void inGameCloseConn() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: \n", e);	
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: \n", e);	
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.printf("Something went wrong: \n", e);	
			}
		}
	}

	// Connection must first be opened with inGameOpenConn(). Then close it when all
	// is done, with inGameCloseConn().
	int inGameGetSaldo(String pname) {
		inGameOpenConn();

		playerName = pname;
		String plSaldoStr = "";
		int plSaldoInt = 0;

		try {
			resultSet = statement.executeQuery("Select saldo from BlackJack where playerName ='" + playerName + "'");
		} catch (SQLException e) {
			System.out.printf("Something went wrong: \n", e);	
		}
		try {
			while (resultSet.next()) {
				plSaldoStr = resultSet.getString("Saldo");
			}

		} catch (SQLException e) {
			System.out.printf("Something went wrong: \n", e);	
		}

		try {
			if (plSaldoStr != null) {
				plSaldoInt = Integer.parseInt(plSaldoStr);
			} else {
				plSaldoInt = 0;
			}
		} catch (java.lang.NumberFormatException j) {
			System.out.printf("Something went wrong: \n", j);	
		}

		inGameCloseConn();
		return plSaldoInt;
	}

	// Connection must first be opened with inGameOpenConn(). Then close it when all
	// is done, with inGameCloseConn().
	boolean playerNameExists(String pname) {
		inGameOpenConn();
		playerName = pname;
		boolean playerExists = false;
		String theResult = "";

		try {
			resultSet = statement
					.executeQuery("Select playerName from BlackJack where playerName ='" + playerName + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (resultSet.next()) {
				theResult = resultSet.getString("playerName");
			}

		} catch (SQLException e) {
			System.out.printf("Something went wrong: \n", e);	
		}

		if (theResult.length() > 0) {
			playerExists = true;
		}
		inGameCloseConn();
		return playerExists;
	}

	void inGameAddPlayer(String pname, int credits) {
		try {
			inGameOpenConn();
			preparedStatement = connect.prepareStatement("insert into BlackJack values (default,?,?,?)");

			preparedStatement.setString(1, pname);
			preparedStatement.setInt(2, credits);
			preparedStatement.setInt(3, 0);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.printf("Something went wrong: \n", e);			}
		inGameCloseConn();
	}

	void inGameAddPlayer2(String pname, int credits) {
		try {
			inGameOpenConn();
			System.out.println("Players already exist: ");
			// playerInfo("Michel", connect);
			System.out.println();
			while (true) {

				System.out.println("*********************************\n*Type [ready] for start playing!*");
				System.out.println("*********************************");
				System.out.println("Enter player name:");
				String name = sc.next();
				if (name.equalsIgnoreCase("ready"))
					break;
				setPlayerName(name);

				System.out.printf("" + getName(), ": current balance: ", setSaldo(0));
				System.out.println(" money insert:");
				saldo = sc.nextInt();
				setSaldo(saldo);
				System.out.println(getName() + " updated balance now: " + " " + getSaldo());
				insertIntoTable(name, saldo, inGameGetHighScore(playerName), connect);
				System.out.println("Record is updated to BlackJack table!");
				System.out.println("Players are inserted into BlackJack successfully...");

			}
		} finally {

		}
	}

	void inGameIncrBalance(String pname, int amount) {
		inGameOpenConn();
		int oldSaldo = inGameGetSaldo(pname);
		int newSaldo = oldSaldo + amount;
		String sql = "update BlackJack set saldo='" + Integer.toString(newSaldo) + "' where playername='" + pname + "'";
		try (Connection conn = DriverManager.getConnection(dburl, user, pass);
				Statement stmt = conn.createStatement();) {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.printf("Something went wrong: \n", e);			}
		inGameCloseConn();
	}

	void inGameDecrBalance(String pname, int amount) {
		inGameOpenConn();
		int oldSaldo = inGameGetSaldo(pname);
		int newSaldo = oldSaldo - amount;
		String sql = "update BlackJack set saldo='" + Integer.toString(newSaldo) + "' where playername='" + pname + "'";
		try (Connection conn = DriverManager.getConnection(dburl, user, pass);
				Statement stmt = conn.createStatement();) {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		inGameCloseConn();
	}

	////////////////////// Klistrat från Michel på slacken 3/1 ///////////////////

	String Username = "admin";
	String Password = "password";

	void login() {

		System.out.println("Enter Username : ");
		String username = sc.next();

		System.out.println("Enter Password : ");
		String password = sc.next();
		if (username.equals("admin") && password.equals("password")) {
			System.out.println("Access Granted! Welcome!");
			menu();
		} else if (username.equals(Username)) {
			System.out.println("Invalid Password!");
			GameMain.theMenu.mainMenu(GameMain.s, GameMain.Blackjackdb);
		} else if (password.equals(Password)) {
			System.out.println("Invalid Username!");
			GameMain.theMenu.mainMenu(GameMain.s, GameMain.Blackjackdb);
		} else {
			System.out.println("Invalid Username & Password!");
			GameMain.theMenu.mainMenu(GameMain.s, GameMain.Blackjackdb);
		}
	}

	///////////////////////////////////////////////////////////////////////////////

	////////// NEDAN setters getters and override

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public String getName() {
		return playerName;
	}

	public void setName(String name) {
		this.playerName = name;
	}

	public int getSaldo() {
		return saldo;
	}

	public Object[] setSaldo(int saldo) {
		this.saldo = saldo;
		return null;
	}

	public int getMenu() {
		return menu;
	}

	public void setMenu(int menu) {
		this.menu = menu;
	}

	public Connection getConnect() {
		return connect;
	}

	public void setConnect(Connection connect) {
		this.connect = connect;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	public void setPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public Scanner getSc() {
		return sc;
	}

	public void setSc(Scanner sc) {
		this.sc = sc;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDburl() {
		return dburl;
	}

	public void setDburl(String dburl) {
		this.dburl = dburl;
	}

	@Override
	public String toString() {
		return "Db [playerName=" + playerName + ", dealer=" + dealer + ", saldo=" + saldo + ", menu=" + menu
				+ ", connect=" + connect + ", statement=" + statement + ", preparedStatement=" + preparedStatement
				+ ", resultSet=" + resultSet + ", sql=" + sql + ", sc=" + sc + ", dburl=" + dburl + ", user=" + user
				+ ", pass=" + pass + ", Username=" + Username + ", Password=" + Password + ", getPlayerName()="
				+ getPlayerName() + ", getDealer()=" + getDealer() + ", getName()=" + getName() + ", getSaldo()="
				+ getSaldo() + ", getMenu()=" + getMenu() + ", getConnect()=" + getConnect() + ", getStatement()="
				+ getStatement() + ", getPreparedStatement()=" + getPreparedStatement() + ", getResultSet()="
				+ getResultSet() + ", getSc()=" + getSc() + ", getPass()=" + getPass() + ", getUser()=" + getUser()
				+ ", getDburl()=" + getDburl() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	// END
}
