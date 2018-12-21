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
	// Scanner
	Scanner sc = new Scanner(System.in);
	// using blacjjack class object
	// BlackjackSimulation game= new BlackjackSimulation(); för simulering

	// För att använda vår onlineDB, alltid ONLINE host Hostinger.eu

	private String pass = "mi235277sk";
	private String user = "u209758462_misk7";
	private String dburl = "jdbc:mysql://sql150.main-hosting.eu/u209758462_miskb?";

	// För att testa lokalt host: dusjälv.se
	/*
	 * private String dburl = "jdbc:mysql://localhost/feedback?"; private String
	 * user = "root"; private String pass = "root";
	 */
	// METHOD MENU
	public void menu() {
		while (true) {
			try {

				System.out.println("|============================|" + "\n|------ Database Menu -------|"
						+ "\n|---- BLackJack DATABASE ----|" + "\n|============================|"
						+ "\n[1]-->Create Database: " + "\n[2]-->Create TABLE:  " + "\n[3]-->Create players: "
						+ "\n[4]-->Describe BLackJack tabell: " + "\n[5]-->Select * FROM BLackJack: "
						+ "\n[6]-->Select Records: " + "\n[7]-->Update All Saldo To 200:  " + "\n[8]-->Delete Records: "
						+ "\n[9]-->Drop Table: " + "\n[10]->Player Info: " + "\n[11]->Search Player: "
						+ "\n[12]->???? : " + "\n[13]->???? : " + "\n[14]->???? : " + "\n[15]->??: "
						+ "\n[0]->Go to GAME " + "\n|============================|" + "\n|----------------------------|"
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
					// searchPlayer();
					break;
				case 12:
					// Go to GAME
					break;
				case 13:
					// ??
					break;
				case 14:
					// ??
					break;
				case 15:
					// ??
					break;
				case 0:
					System.out.println("Go to GAME");
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

	////// DATABASE STUFF INDELAT I METHODS
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

			String sql = "create table IF NOT EXISTS BlackJack " + " (id INT NOT NULL AUTO_INCREMENT,\r\n"
					+ "playerName varchar(50),\r\n" + "saldo int,\r\n" + "   primary key (id))";
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
	void insertIntoTable(String playerName, int saldo, Connection connect) {
		System.out.println("Connecting to a selected database...");
		try {
			preparedStatement = connect.prepareStatement("insert into BlackJack values (default,?,?)");

			preparedStatement.setString(1, playerName);
			preparedStatement.setInt(2, saldo);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

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
			System.out.printf("%2s %8s %5s%n", "id", "playerName", "Saldo");
			resultSet = statement.executeQuery(strSelect);
			while (resultSet.next()) { // Move the cursor to the next row
				System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("playerName") + ", "
						+ resultSet.getString("Saldo"));
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

// Update plyerssaldo
	public void UpdateSaldo(int saldo) {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);
			System.out.println("Connected database successfully..." + dburl);

			// update player in blacjjackgame - ("Select * from BlackJack where playerName
			// ='" + playerName + "'");
			// String strUpdate = ("update saldo from BlackJack where playerName ='" +
			// playerName + "'");
			String strUpdate = "update BlackJack set Saldo  where playerName ='" + playerName + "'";
			System.out.println("The SQL query is: " + strUpdate); // Echo for debugging
			int countUpdated = statement.executeUpdate(strUpdate);
			System.out.println(countUpdated + " records affected.");

		} catch (SQLException e) {
			// Handle errors for JDBC
			e.printStackTrace();
		}
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
			String sqlDelete = "delete from BlackJack where id>0 and id<10";
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
				insertIntoTable(name, saldo, connect);
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

	// searchPlayer Skriv in namnet på spelare och få ut: ID, playerName,Saldo

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

	// Get saldo from a player
	//

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
		return "DblocaltestMenu [playerName=" + playerName + ", dealer=" + dealer + ", saldo=" + saldo + ", menu="
				+ menu + ", connect=" + connect + ", statement=" + statement + ", preparedStatement="
				+ preparedStatement + ", resultSet=" + resultSet + ", sc=" + sc + ", pass=" + pass + ", user=" + user
				+ ", dburl=" + dburl + ", getPlayerName()=" + getPlayerName() + ", getDealer()=" + getDealer()
				+ ", getName()=" + getName() + ", getSaldo()=" + getSaldo() + ", getMenu()=" + getMenu()
				+ ", getConnect()=" + getConnect() + ", getStatement()=" + getStatement() + ", getPreparedStatement()="
				+ getPreparedStatement() + ", getResultSet()=" + getResultSet() + ", getSc()=" + getSc()
				+ ", getPass()=" + getPass() + ", getUser()=" + getUser() + ", getDburl()=" + getDburl()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	// END
}
