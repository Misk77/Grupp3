

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
	private String playerName;
	private String dealer;
	private int saldo;
	private int menu;

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null; // l�gga in preparedStatement
	private ResultSet resultSet = null; // kanske on�dig???
	private String books;
	// private String tableName = books; // gl�mde vad jag skulle ha den till hmm?

	// Scanner
	Scanner sc = new Scanner(System.in);

	/*
	 * f�rinst�llda anslutningar s� man inte beh�ver �ndra �verallt b�sta �r att ha
	 * ett dokument som h�nvisas till // get db properties Properties props = new
	 * Properties(); props.load(new FileInputStream("demo.properties"));
	 * 
	 * String user = props.getProperty("user"); String password =
	 * props.getProperty("password"); String dburl = props.getProperty("dburl");
	 * 
	 * // connect to database myConn = DriverManager.getConnection(dburl, user,
	 * password);
	 * 
	 * System.out.println("DB connection successful to: " + dburl);
	 * 
	 * 
	 * private String password = "mi235277sk"; private String
	 * user="u209758462_misk7"; private String dburl =
	 * "jdbc:mysql://sql150.main-hosting.eu/u209758462_miskb?";
	 */

	// Ta bort denna och testar local
	
	// alltid online
	  private String pass = "mi235277sk"; 
	 private String user = "u209758462_misk7";
	  private String dburl =  "jdbc:mysql://sql150.main-hosting.eu/u209758462_miskb?"; 
	  // var ?
	 private player p2;
	  private player p1;
	 

	// testar loclas
	/*
	private String dburl = "jdbc:mysql://localhost/feedback?";
	private String user = "root";
	private String pass = "root";
	
*/
	//// MENU STUFF manuallyInsert()
	// METHOD MENU
	public void menu() {
		while (true) {
			try {

				System.out.println("|===============================|" + "\n|------  Database Menu -------|"
						+ "\n|----------  BLackJack DATABASE Michel -----------|" + "\n|=============================|"
						+ "\n[1]-Create Database: " + "\n[2]-CREATE TABLE:  " + "\n[3]-Add players   "
						+ "\n[4]-DESCRIBE BLackJack: " + "\n[5]-SELECT * FROM BLackJack: " + "\n[6]-Select Records "
						+ "\n[7]-Check Update:  " + "\n[8]-Delete Records: " + "\n[9]-Drop Table: "
						+ "\n[10]-playerInfo: " + "\n[11]- ???"
						+ "\n[12]-????: "
						+ "\n[13]-????" + "\n[14]-????" + "\n[15]-????" + "\n[0]-EXIT "
						+ "\n|===================================|" + "\n|-----------------------------------|"
						+ "\n|--------- Michel ----------|" + "\n|---- -------------------- ---------|"
						+ "\n|===================================|");

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
					checkUpdate();
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

					/// TA BORT DENNA FÖR ATT KOLLA HUR DET GÅR MED ATT LÄGGA IN SPELARE MANUELLT
					// insertIntoTable();
				case 12:
					// ??
					break;
				case 13:
					// ??
					break;
				case 14:
					// ??
					break;
				case 15:
					// manuallyInsert();
					break;
				case 0:
					System.out.println("\t\tBye....");
					System.exit(0);
					break;
				default:
					System.out.println("No such option in menu");
					System.out.println("\t try again........");
					break;

				}
			} catch (Exception e) {
				System.out.println("Invalid input try again\nMust be a Integer!");

			}
		}

	}

	////// DATABASE STUFF INDELAT I METHODS
	/*
	 * //Verkar inte anv�ndas i jdbc USE DATABASE; useDatabase();
	 * 
	 * 
	 */

	void createDatabase() {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.cj.jdbc.Driver");// com.mysql.jdbc.Driver
			// Setup the connection with the DB

			// connect to database

			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("DB connection successful to: " + dburl);

			// Execute a query - create database FUNKAR
			System.out.println("Creating database...");
			statement = connect.createStatement();
			String sql = "CREATE DATABASE  IF NOT EXISTS u209758462_miskb ";
			statement.executeUpdate(sql);

			System.out.println("Database created successfully...");
			System.out.println("Databasen skapades hos: " + dburl);
		} catch (SQLException se) {
			// Handle errors for JDBC
			System.out.println("Database creation failed");
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (connect != null)
					connect.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");

	}

	// CREATE TABLE
	// connection
	void createTable() {
		System.out.println("Connecting to a selected database...");
		try {
			// connect to database
			// connect = DriverManager.getConnection(dburl, user, password);
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);

			System.out.println("Creating table in given database...");
			statement = connect.createStatement();

			String sql = "create table IF NOT EXISTS BlackJack " + " (id INT NOT NULL AUTO_INCREMENT,\r\n"
					+ "playerName varchar(50),\r\n" + "saldo int,\r\n" + "   primary key (id))";
			statement.executeUpdate(sql);

			System.out.println("Created table successfully ...");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (statement != null)
					connect.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (connect != null)
					connect.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");

	}
///   TA BORT DENNA FÖR ATT KOLLA HUR DET GÅR MED ATT LÄGGA IN SPELARE MANUELLT
// INSERT INTO table H�rdkodat   TA BORT DENNA FÖR ATT KOLLA HUR DET GÅR MED ATT LÄGGA IN SPELARE MANUELLT
	// Conection
	// TA BORT DENNA FÖR ATT KOLLA HUR DET GÅR MED ATT LÄGGA IN SPELARE MANUELLT

	void insertIntoTable(String playerName, int saldo, Connection connect) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			try {
				connect = DriverManager.getConnection(dburl, user, pass);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		System.out.println("Connecting to a selected database...");
		try {
			PreparedStatement s = connect.prepareStatement("insert into BlackJack values (default,?,?)");
			s.setString(1, playerName);
			s.setInt(2, saldo);
			s.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		} finally {
			// finally block used to close resources
		}
		
		System.out.println();
		System.out.println("Hello " + getPlayerName() + "! Let's play!\n");
		System.out.println(getName() + " current balance: " + getSaldo());
	}
	
	// DESCRIBE ;
	// connection
	void describeTable() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);

			Statement st = connect.createStatement();
			ResultSet resultSet = st.executeQuery("DESCRIBE BlackJack");
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
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
		}

		System.out.println("Goodbye!");

	}

	// SELECT * FROM BlackJack
	// Now get some metadata from the database
	// Result set get the result of the SQL query
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
				int id = resultSet.getInt("id");
				String playerName = resultSet.getString("playerName");
				int Saldo = resultSet.getInt("Saldo");

				System.out.println(id + ", " + playerName + ", " + Saldo);
				++rowCount;
			}
			System.out.println("Total number of records = " + rowCount);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		// Close the resources - Done automatically by try-with-resources//bra att veta
	}

	// SELECT RECORDS
	void selectRecords() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);

			System.out.println("Creating statement...");
			statement = connect.createStatement();

			String sql = "SELECT  id, playerName ,saldo FROM BlackJack";
			ResultSet rs = statement.executeQuery(sql);
			// Extract data from result set
			String strSelect = "select * from BlackJack";
			System.out.println("The SQL query is: " + strSelect); // Echo For debugging
			System.out.printf("%2s %8s %5s%n", "id", "playerName", "Saldo");
			ResultSet rset = statement.executeQuery(strSelect);
			while (rset.next()) { // Move the cursor to the next row
				System.out.println(
						rset.getInt("id") + ", " + rset.getString("playerName") + ", " + rset.getString("Saldo"));
			}
			rs.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (statement != null)
					connect.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (connect != null)
					connect.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}

	// DROP
	// drop table DROP TABLE BlackJack;
	void dropTable() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);
			String sql = "DROP TABLE  IF EXISTS BlackJack";
			statement.executeUpdate(sql);

			System.out.println("DROP TABLE successfully...");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (statement != null)
					connect.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (connect != null)
					connect.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");

	}

	/// UPDATE check if update went through
	void checkUpdate() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);

			System.out.println("Creating statement...");
			statement = connect.createStatement();

			// h�jer saldo och kollar att player finns, och id 11
			String strUpdate = "update BlackJack set Saldo = 200 where Saldo <200";
			System.out.println("The SQL query is: " + strUpdate); // Echo for debugging
			int countUpdated = statement.executeUpdate(strUpdate);
			System.out.println(countUpdated + " records affected.");

			// SELECT to check the UPDATE.
			String strSelect = "select * from BlackJack where id = 1";
			System.out.println("The SQL query is: " + strSelect); // Echo for debugging
			ResultSet resultSet = statement.executeQuery(strSelect);
			while (resultSet.next()) { // Move the cursor to the next row
				System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("playerName") + ", "
						+ resultSet.getString("Saldo"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
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

			// DELETE records with id>=3000 and id<4000
			String sqlDelete = "delete from BlackJack where id<0 and id<4";
			System.out.println("The SQL query is: " + sqlDelete); // Echo for debugging
			int countDeleted = statement.executeUpdate(sqlDelete);
			System.out.println(countDeleted + " records deleted.\n");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	//

	//
	//
	// PLAYER
	void addplayers() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			connect = DriverManager.getConnection(dburl, user, pass);
			System.out.println("Players already exists: ");
			playerInfo("johannes", connect);//helst kunna skriva in ett namn
			while (true) {
				System.out.println("Wanna add player?or reod to play?[redo] ");
				System.out.println("Player: set your name: ");
				String name = sc.next();
				setPlayerName(name);
				if (name.equalsIgnoreCase("redo"))
					break;
				System.out.println(getName() + " current balance: " + getSaldo());
				System.out.println(" money insert:");
				int saldo = sc.nextInt();
				setSaldo(saldo);
				System.out.println(getName() + " updated balance now: " + " " + getSaldo());
				System.out.println();
				System.out.println(" Type [redo] for start the game!");
				insertIntoTable(name, saldo, connect);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Record is updated to BlackJack table!");
		System.out.println("Players are inserted into BlackJack successfully...");

		

	}

	///

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	
	//  Troligen denna som du kan använda för hämta  spelar namn i spelet.
	// Nedanför är en metod för att hämta värden från en tabell
	private void playerInfo(String playerName, Connection connect) {
		
		
		try {
			Statement s = connect.createStatement();
			ResultSet result = s.executeQuery("Select * from BlackJack where playerName ='" + playerName + "'");
			
			if (result.next()) {
				System.out.println(result.getString("playerName") + " saldo " + result.getString("saldo"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
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

	public void setSaldo(int saldo) {
		this.saldo = saldo;
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

	public String getBooks() {
		return books;
	}

	public void setBooks(String books) {
		this.books = books;
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

	public player getP1() {
		return p1;
	}

	public void setP1(player p1) {
		this.p1 = p1;
	}

	public player getP2() {
		return p2;
	}

	public void setP2(player p2) {
		this.p2 = p2;
	}

	@Override
	public String toString() {
		return "DblocaltestMenu [playerName=" + playerName + ", dealer=" + dealer + ", saldo=" + saldo + ", menu="
				+ menu + ", connect=" + connect + ", statement=" + statement + ", preparedStatement="
				+ preparedStatement + ", resultSet=" + resultSet + ", books=" + books + ", sc=" + sc + ", pass=" + pass
				+ ", user=" + user + ", dburl=" + dburl + ", p2=" + p2 + ", p1=" + p1 + ", getPlayerName()="
				+ getPlayerName() + ", getDealer()=" + getDealer() + ", getName()=" + getName() + ", getSaldo()="
				+ getSaldo() + ", getMenu()=" + getMenu() + ", getConnect()=" + getConnect() + ", getStatement()="
				+ getStatement() + ", getPreparedStatement()=" + getPreparedStatement() + ", getResultSet()="
				+ getResultSet() + ", getBooks()=" + getBooks() + ", getSc()=" + getSc() + ", getPass()=" + getPass()
				+ ", getUser()=" + getUser() + ", getDburl()=" + getDburl() + ", getP1()=" + getP1() + ", getP2()="
				+ getP2() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	// END
	// END

}
