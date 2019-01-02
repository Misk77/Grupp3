
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/* 
 * INFO ABOUT Db file:
 * Admin Tool Methods:
 * First its ADMIN TOOL MENU METHODS, with open connection and close connection after every single methods/ for seperate methods for admin to use
 * Below this is the InGameMethods
 * InGameMethods:
 * This is with a method whats open connection and has the connection til the player exit the game and players info get updated
 * exempel:
 * InGameConnection - hold the connection open the whole game, then goes in i different methods and do its work, 
 * then players exit we call the update then close connection methods
 * 
 */
public class Db {
	// player
	private String playerName;
	private String dealer;
	private int saldo;
	private int highscore;
	// menu
	private int menu;
	// jdbc
	String sql;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null; // lägga in preparedStatement
	private ResultSet resultSet = null; // använda som result VAR
	// Scanner
	Scanner sc = new Scanner(System.in);

	// För att använda vår onlineDB, alltid ONLINE host Hostinger.eu

	private String pass = "mi235277sk";
	private String user = "u209758462_misk7";
	private String dburl = "jdbc:mysql://sql150.main-hosting.eu/u209758462_miskb?";
	private int Insertsaldo;

	// För att testa lokalt host: dusjälv.se
	/*
	 * private String dburl = "jdbc:mysql://localhost/feedback?"; private String
	 * user = "root"; private String pass = "root";
	 */
	// METHOD MENU
	public void menu() {
		while (true) {
			try {

				System.out.println("|============================|" + "\n|------  Database Menu -------|"
						+ "\n|---- BLackJack DATABASE ----|" + "\n|============================|"
						+ "\n[1]-->Create Database: " + "\n[2]-->Create TABLE:  " + "\n[3]-->Create players: "
						+ "\n[4]-->Describe BLackJack tabell: " + "\n[5]-->Select * FROM BLackJack: "
						+ "\n[6]-->Select Records: " + "\n[7]-->Update All Saldo To 200:  " + "\n[8]-->Delete Records: "
						+ "\n[9]-->Drop Table: " + "\n[10]->Player Info: " + "\n[11]->Search Player: "
						+ "\n[12]->InsertTestPlayers : " + "\n[13]-> Highscore Lista : "
						+ "\n[14]->PlayerUpdateTheSaldo : " + "\n[15]->inGameDecrBalance: "
						+ "\n[16]->inGameIncrBalance: " + "\n[17]->connectMethod: " + "\n[18]->inGameOpenConn: "
						+ "\n[19]->inGameCloseConn: " + "\n[20]->InGamePlayerUpdateSaldo: " + "\n[21]->getsaldoPlayer: "
						+ "\n[22]->inGameAddPlayer: " + "\n[0]->Go/Return to the GAME MENU "
						+ "\n|============================|" + "\n|----------------------------|"
						+ "\n|---------- Grupp3 ----------|" + "\n|----------------------------|"
						+ "\n|============================|");

				System.out.println("What you wanna do? ");

				// Valen i menu
				menu = Integer.parseInt(sc.next());
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
					selectFromtable();
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
					insertTestPlayers(playerName, highscore, saldo, connect);
					break;
				case 13:
					highscore();
					break;
				case 14:
					playerUpdateTheSaldo(saldo);
					break;
				case 15:
					inGameDecrBalance(playerName, saldo);
					break;
				case 16:
					inGameIncrBalance(playerName, saldo);
					break;
				case 17:
					connectMethod();
					break;
				case 18:
					inGameOpenConn();
					break;
				case 19:
					inGameCloseConn();
					break;
				case 20:
					InGamePlayerUpdateSaldo(saldo);
					break;
				case 21:
					getsaldoPlayer(dburl);
					break;
				case 22:
					inGameAddPlayer(playerName, saldo);
					break;
				case 0:
					System.out.println("Return to the GAME MENU");
					System.exit(0);
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

////////////////////////////// DATABASE STUFF INDELAT I METHODS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	//
	////// CREATE DATABASE
	void createDatabase() {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.cj.jdbc.Driver");// com.mysql.jdbc.Driver
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
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
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
					+ "playerName varchar(50),\r\n" + "highscore int ,\r\n" + "saldo int,\r\n" + "   primary key (id))";
			statement.executeUpdate(sql);

			System.out.println("Created table successfully ...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}

	// Insert
	void insertIntoTable(String playerName, int highscore, int saldo, Connection connect) {
		System.out.println("Connecting to a selected database...");
		try {
			preparedStatement = connect.prepareStatement("insert into BlackJack values (default,?,?,?)");

			preparedStatement.setString(1, playerName);
			preparedStatement.setInt(2, highscore);
			preparedStatement.setInt(3, saldo);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Hello " + getPlayerName() + "! Let's play!\n");
		System.out.println(getName() + " current balance: " + getSaldo());
	}

	/*
	 * // update saldo from input from player void insertUpdatedSaldo(int saldo) {
	 * System.out.println("Connecting to a selected database..."); try { // where
	 * playerName ='" + playerName + "'" statement = connect.createStatement();
	 * String query = "update BlackJack set Saldo ="; //count will give you how many
	 * records got updated statement.executeUpdate(query);
	 * System.out.println("Updated queries: ");
	 * 
	 * //String strUpdate=("update BlackJack set Saldo where playerName =?'" +
	 * playerName ); System.out.println("The SQL query is: " + statement); // Echo
	 * for debugging //statement.executeQuery(statement);
	 * 
	 * 
	 * 
	 * 
	 * }catch (SQLException e) { e.printStackTrace(); }
	 * 
	 * System.out.println(); System.out.println("Hello " + getPlayerName() +
	 * "! Let's play!\n"); System.out.println(getName() + " current balance: " +
	 * getSaldo());
	 * 
	 * }
	 */
	// Insert auto player for test
	// Insert player for testing or could be use for random opponents??
	void insertTestPlayers(String playerName, int highscore, int saldo, Connection connect) {
		connectMethod();
		System.out.println("Connecting to a selected database...");
		try {
			preparedStatement = connect.prepareStatement("insert into BlackJack values (default,?,?,?)");

			sql = "insert into BlackJack values (default, 'testplayer1',212,33333);";
			statement.executeUpdate(sql);
			sql = "insert into BlackJack values (default, 'testplayer2',313,44444);";
			statement.executeUpdate(sql);
			sql = "insert into BlackJack values (default,'testplayer3',414,55555)";
			statement.executeUpdate(sql);
			sql = "insert into BlackJack values (default, 'testplayer4',515,66666)";
			statement.executeUpdate(sql);

			System.out.println("Inserted TESTPLAYERS into the table...");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Hello TestPlayers/Random Players " + "! Let's play!\n");

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
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
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
			String strSelect = "select id, playerName,highscore,Saldo  from BlackJack";
			System.out.println("The SQL query is: " + strSelect);
			System.out.println();

			ResultSet resultSet = statement.executeQuery(strSelect);

			// For each row, retrieve the contents of the cells with getXxx(columnName).
			System.out.println("The records selected are:");

			int rowCount = 0;
			while (resultSet.next()) { // Move the cursor to the next row, return false if no more row
				System.out.println();
				System.out.printf("%1s %10s %15s %10s%n", "ID", "PLAYER", "HIGHSCORE", "SALDO");

				System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("playerName") + ",         "
						+ resultSet.getString("highscore") + ",       " + resultSet.getString("Saldo"));
				++rowCount;
			}
			System.out.println();
			System.out.println("Total number of records = " + rowCount);
			System.out.println();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
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
			System.out.printf("%1s %10s %15s %10s%n", "ID", "PLAYER", "HIGHSCORE", "SALDO");
			resultSet = statement.executeQuery(strSelect);
			while (resultSet.next()) { // Move the cursor to the next row
				System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("playerName") + ",         "
						+ resultSet.getString("highscore") + ",       " + resultSet.getString("Saldo"));
			}
			resultSet.close();
		} catch (SQLException e) {
			// Handle errors for JDBC
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}

	// // DROP table DROP TABLE BlackJack;
	void dropTable() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);
			System.out.println("Connected database successfully..." + dburl);
			String sql = "DROP TABLE  IF EXISTS BlackJack";
			statement.executeUpdate(sql);
			System.out.println("DROP TABLE successfully...");
		} catch (SQLException e) {
			// Handle errors for JDBC
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}

	// Update from simulator OBS!! Hårdkodat att man förlorar 1000, här ska bet in
	// från game , 2 methods First(inGameDecrBalance) LOOSE then
	// WIN(inGameIncrBalance)

// Update  current UpdateSaldoLooseInGame
	// This happen during the players play the game
	public boolean UpdateSaldoLooseInGame(int saldo) {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);
			System.out.println("Connected database successfully..." + dburl);
			int bet = 1000; // Players bet in here

			// LOOSE
			saldo = getSaldo() - bet;
			// update player in blacjjackgame - ("Select * from BlackJack where playerName
			// ='" + playerName + "'");
			// String strUpdate = ("update saldo from BlackJack where playerName ='" +
			// playerName + "'");
			setSaldo(saldo);
			System.out.println(getName() + "Saldo  is now: " + " " + getSaldo());
			System.out.println(getName() + " updated balance now: " + " " + getSaldo());
			insertIntoTable(getName(), getSaldo(), getHighscore(), connect);

		} catch (SQLException e) {
			// Handle errors for JDBC
			e.printStackTrace();
		} finally {

			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
		return true;
	}

	// Player update the saldo 29 dec
	// If plyaer wanna insert more money
	public boolean playerUpdateTheSaldo(int saldo) {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);
			System.out.println("Connected database successfully..." + dburl);
			// update player in blacjjackgame - ("Select * from BlackJack where playerName
			// ='" + playerName + "'");
			// String strUpdate = ("update saldo from BlackJack where playerName ='" +
			// playerName + "'");
			System.out.printf(playerName + ": current balance: ", getSaldo());
			System.out.println(" insert money:");
			saldo = sc.nextInt();
			setSaldo(saldo);
			System.out.println(playerName + " updated balance is now: " + " " + getSaldo());

			setSaldo(saldo);
			System.out.println(playerName + "Saldo  is now: " + " " + getSaldo());
			System.out.println(playerName + " updated balance now: " + " " + getSaldo());
			insertIntoTable(playerName, getSaldo(), getHighscore(), connect);

		} catch (SQLException e) {
			// Handle errors for JDBC
			e.printStackTrace();
		} finally {

			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
		return true;
	}

	/// UPDATE check if update went through
	// all player under 200 get 200 to play with
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
				System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("playerName") + ",         "
						+ resultSet.getString("highscore") + ",       " + resultSet.getString("Saldo"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
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
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}

	// addPLAYER
	void addplayers() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			try {
				connect = DriverManager.getConnection(dburl, user, pass);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				insertIntoTable(name, highscore, saldo, connect);
				System.out.println("Record is updated to BlackJack table!");
				System.out.println("Players are inserted into BlackJack successfully...");

			}
		} finally {

		}
	}

	///

	// Denna visar först i add player metoden vilka spelare som finns i DB(detta
	// verkar ej fungera i nuläget, tanken visa alla spelare i databasen
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
				System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("playerName") + ",         "
						+ resultSet.getString("highscore") + ",       " + resultSet.getString("Saldo"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}

	// searchPlayer Skriv in namnet på spelare och få ut: ID,
	// playerName,highscore,Saldo

	void searchPlayer() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			connect = DriverManager.getConnection(dburl, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Connected database successfully..." + dburl);
		System.out.println("Creating statement...");

		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Enter Player you looking for: ");
		String playerName = sc.next();
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			System.out.printf("%1s %10s %15s %10s%n", "ID", "PLAYER", "HIGHSCORE", "SALDO");
			resultSet = statement.executeQuery("Select * from BlackJack where playerName ='" + playerName + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (resultSet.next()) {
				System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("playerName") + ",         "
						+ resultSet.getString("highscore") + ",       " + resultSet.getString("Saldo"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}

	// Get saldo from a player , search specific player
	void getsaldoPlayer(String string) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			connect = DriverManager.getConnection(dburl, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Connected database successfully..." + dburl);
		System.out.println("Creating statement...");

		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			System.out.printf("%1s %n", "Saldo");
			resultSet = statement.executeQuery("Select saldo from BlackJack where playerName ='" + playerName + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (resultSet.next()) {
				System.out.println(resultSet.getString("Saldo"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}

	// Get the highscore ,NOW get all players highscore, maybe do one for search
	// specific??

	void highscore() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);

			System.out.println("Creating statement...");
			statement = connect.createStatement();

			// String sql = "SELECT id,playerName ,highscore,saldo FROM BlackJack";
			String sql = ("SELECT id, playerName, highscore ,Saldo FROM BlackJack \r\n" + "ORDER BY highscore DESC");
			System.out.println("The SQL query is: " + sql); // Echo For debugging
			System.out.printf("%1s %10s %15s %10s%n", "ID", "PLAYER", "HIGHSCORE", "SALDO");
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) { // Move the cursor to the next row
				System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("playerName") + ",         "
						+ resultSet.getString("highscore") + ",       " + resultSet.getString("Saldo"));
			}
			resultSet.close();
		} catch (SQLException e) {
			// Handle errors for JDBC
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}

	// Kolla ifall spelaren redan finns
	public boolean playerNameExists(String pname) {
		// Kolla playerName in db
		return false;
	}
	/*
	 * /////////////////////////////////////////////////////////// InGameMethods
	 * ///////////////////////////////////////////////////////////
	 * /////////////////////////////////////////////////////////// InGameMethods
	 * /////////////////////////////////////////////////////////// InGameMethods:
	 * For giving smoothness nad perforamce speed while using Db connection, so same
	 * connetion will be used for the player
	 * 
	 * This is with a method whts open connection and has the connection til player
	 * exit the game and players info get updated exempel: InGameConnection - hold
	 * the connection open the whole game, then goes in i different methods and do
	 * its work, then players exit we call the update then close connection methods
	 */

///////////////////////////////////////////////////////////  InGameMethods  /////////////////////////////////////////////////////////// 
///////////////////////////////////////////////////////////  InGameMethods  /////////////////////////////////////////////////////////// 

////// Firstv Different Connection/close Methods \\\\\\\\\\\\\\

	// Connection method 29 dec // Stänger inte connection, alltid öppen tills man
	// använder inGameCloseConn
	Connection connectMethod() {
		try {
			connect = DriverManager.getConnection(dburl, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect;
	}

	// Connect to BlackJackDb
	public Connection inGameOpenConn() {
		try {
			connect = DriverManager.getConnection(dburl, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect;
	}

	// Close db connection
	public void inGameCloseConn() {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}

	// Koll saldo hos spelaren
	public int inGameGetSaldo(String pname) {
		getSaldo();
		return saldo;
	}

	// lägga till spelare
	public void inGameAddPlayer(String pname, int newPlayerBalance) {
		// addPlayers();

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
			insertIntoTable(name, highscore, saldo, connect);
			System.out.println("Record is updated to BlackJack table!");
			System.out.println("Players are inserted into BlackJack successfully...");

		}

	}

	// Player LOOSE, DECREMENT MONEY/SALDO
	public void inGameDecrBalance(String playerName2, int currentBet) {

		try {
			// Hårdkodad test bet;
			int bet = 1000; // Players bet in here, DENNA SKA BORT när vi har så spelaren skriver in ett bet
			// INGAMEBET;
			// int bet = currentBet; //DEnna onödig??

			// LOOSE
			// Hårdkodad test bet från ovan LOOSE 1000 kr;
			saldo = getSaldo() - bet;
			// INGAME BET;
			// saldo = getSaldo() - currentBet; // Här kommer spelaren egna bet

			// update player in blacjjackgame - ("Select * from BlackJack where playerName
			// ='" + playerName + "'");
			// String strUpdate = ("update saldo from BlackJack where playerName ='" +
			// playerName + "'");
			setSaldo(saldo);
			insertIntoTable(getName(), getSaldo(), getHighscore(), connect);

		} finally {

		}

	}

	/// Increment WIN IN GAME INCREMENT MONEY/SALDO
	public void inGameIncrBalance(String playerName2, int currentBet) {

		try {
			// Hårdkodad test bet;
			int bet = 1000; // Players bet in here, DENNA SKA BORT när vi har så spelaren skriver in ett bet
			// INGAMEBET;
			// int bet = currentBet; //DEnna onödig??

			// LOOSE
			// Hårdkodad test bet från ovan WIN 1000 kr;
			saldo = getSaldo() + bet;
			// INGAME BET;
			// saldo = getSaldo() + currentBet; // Här kommer spelaren egna bet

			// update player in blacjjackgame - ("Select * from BlackJack where playerName
			// ='" + playerName + "'");
			// String strUpdate = ("update saldo from BlackJack where playerName ='" +
			// playerName + "'");
			setSaldo(saldo);

			InGameInsertIntoTable(getName(), getSaldo(), getHighscore(), connect);

		} finally {

		}
	}

	// If plyaer wanna insert more money during game mode
	public boolean InGamePlayerUpdateSaldo(int Updatedsaldo) {

		try {
			// update player in blacjjackgame - ("Select * from BlackJack where playerName
			// ='" + playerName + "'");
			// String strUpdate = ("update saldo from BlackJack where playerName ='" +
			// playerName + "'");
			System.out.println(playerName + " current balance: " + getSaldo());
			System.out.println(playerName + " insert money:");
			Insertsaldo = sc.nextInt();
			Updatedsaldo = (getSaldo() + Insertsaldo);
			setSaldo(Updatedsaldo);
			System.out.println(playerName + " updated balance is now: " + "  " + getSaldo());
			InGameInsertIntoTable(getName(), getSaldo(), getHighscore(), connect);
			// InGameInsertIntoTable(playerName, getSaldo(), getHighscore(), connect);

		} finally {

			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
		return true;
	}

	// Insert
	void InGameInsertIntoTable(String playerName, int highscore, int saldo, Connection connect) {

		try {
			preparedStatement = connect.prepareStatement("insert into BlackJack values (default,?,?,?)");

			preparedStatement.setString(1, playerName);
			preparedStatement.setInt(2, highscore);
			preparedStatement.setInt(3, saldo);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(getName() + " current balance: " + getSaldo());
	}

//////////////////////////////////////////////////////////  JUST A LOOK TO GRAB CODE
	///// ENkel connection och query, kan ändras eftersom, bra att ha och ta ifrån
	///// den kod man behöver
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
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			try {
				connect.close();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}

	////////// NEDAN setters getters and override

	// GET and Setter PlayerName
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
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

	public int setSaldo(int saldo) {
		this.saldo = saldo;
		return saldo;
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

	public int getHighscore() {
		return highscore;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}

	////////////////

	// END
}
