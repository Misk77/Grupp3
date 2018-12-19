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
	 
	
	
		private int menu;
		private Connection connect = null;
		private Statement statement = null;
		private PreparedStatement preparedStatement = null; // lägga in preparedStatement
		private ResultSet resultSet = null; // kanske onödig???
		private String books;
		private String tableName = books; // glömde vad jag skulle ha den till hmm?

		// Scanner
		Scanner sc = new Scanner(System.in);

		/*
		 * förinställda anslutningar så man inte behöver ändra överallt bästa är att ha
		 * ett dokument som hänvisas till // get db properties Properties props = new
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
		private String pass = "mi235277sk";
		private String user = "u209758462_misk7";
		private String dburl = "jdbc:mysql://sql150.main-hosting.eu/u209758462_miskb?";
	

		//// MENU STUFF   manuallyInsert()
		// METHOD MENU
		void menu() {
			while (true) {
				try {

					System.out.println("|===============================|" + "\n|------  Database Menu -------|"
							+ "\n|----------  books -----------|" + "\n|=============================|"
							+ "\n[1]-Create Database: " + "\n[2]-SHOW DATABASES:  " + "\n[3]-CREATE TABLE: "
							+ "\n[4]-INSERT INTO table " + "\n[5]-DESCRIBE books: " + "\n[6]-SELECT * FROM books: "
							+ "\n[7]-Select Records: " + "\n[8]-Check Update: " + "\n[9]-Delete Records: " + "\n[10]-Drop Table:"
							+ "\n[11]-????" + "\n[12]-????" + "\n[13]-????" + "\n[14]-????" + "\n[15]-Funkar ej !!! manually Insert" +"\n[0]-EXIT "
							+ "\n|===================================|" + "\n|-----------------------------------|"
							+ "\n|--------- GRUPP3 PROJEKT ----------|" + "\n|---- -------------------- ---------|"
							+ "\n|===================================|");

					System.out.println("What you wanna do? ");

					// Valen i menu
					menu = Integer.parseInt(sc.next());

					switch (menu) {

					case 1:
						createDatabase();
						break;
					case 2:
						//showDatabase();
						break;
					case 3:
					createTable();
						break;
					case 4:
						insertIntoTable();
						break;
					case 5:
						describeTable();
						break;

					case 6:
						selectFromtable();
						break;
					case 7:
						//selectRecords();
						break;
					case 8:
						//checkUpdate();
						break;

					case 9:
					//	deleteRecords();
						break;
					case 10:
						//dropTable();
						break;
					case 11:
						// ??
						break;
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
					//	manuallyInsert();
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
		 * //Verkar inte användas i jdbc USE DATABASE; useDatabase();
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
				System.out.println("Databasen skapades hos hosting.eu.");
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

	//  CREATE TABLE
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
				+"Player varchar(50),\r\n"+
						 "saldo int,\r\n"
						+ "   primary key (id))";

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
	

// INSERT INTO table Hårdkodat
		// Conection
		void insertIntoTable() {
			System.out.println("Connecting to a selected database...");
			try {
				connect = DriverManager.getConnection(dburl, user, pass);
				System.out.println("Connected database successfully..." + dburl);
				// create table
				System.out.println("Creating table in given database...");
				statement = connect.createStatement();

				String sql = "insert into BlackJack values (default, 'Player1', 212);";
				statement.executeUpdate(sql);
				sql = "insert into BlackJack values (default, 'Astrid Lindgren2',313);";
				statement.executeUpdate(sql);
				sql = "insert into BlackJack values (default,'Stephen King',414)";
				statement.executeUpdate(sql);
				sql = "insert into BlackJack values (default, 'G.W persson',515)";
				statement.executeUpdate(sql);
				sql = "insert into BlackJack values (default,'Johannes',616)";
				statement.executeUpdate(sql);
				System.out.println("Inserted records into the table...");

				System.out.println("INSERT INTO BlackJack successfully...");
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
	
		
		// SELECT * FROM books
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
				String strSelect = "select id, Player ,Saldo  from BlackJack";
				System.out.println("The SQL query is: " + strSelect);
				System.out.println();

				ResultSet resultSet = statement.executeQuery(strSelect);

				// For each row, retrieve the contents of the cells with getXxx(columnName).
				System.out.println("The records selected are:");
				int rowCount = 0;
				while (resultSet.next()) { // Move the cursor to the next row, return false if no more row
					int id  = resultSet.getInt("id");
					String Player = resultSet.getString("Player");
					int Saldo = resultSet.getInt("Saldo");
					
					System.out.println(id+", "+Player+", "+Saldo );
					++rowCount;
				}
				System.out.println("Total number of records = " + rowCount);

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			// Close the resources - Done automatically by try-with-resources//bra att veta
		}
	
	
		
	
	
	
	//  END

}
