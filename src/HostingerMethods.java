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
 * "jdbc:mysql:// sql150.main-hosting.eu/u209758462_miskb?
 * "user=u209758462_misk7 password=mi235277sk");
 * 
 * user=u209758462_misk7 password=mi235277sk
 * dburl=sql150.main-hosting.eu/u209758462_miskb?
 
* __author__ = 'Michel Skoglund'
* 
* 
*/

public class HostingerMethods {
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
					showDatabase();
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
					selectRecords();
					break;
				case 8:
					checkUpdate();
					break;

				case 9:
					deleteRecords();
					break;
				case 10:
					dropTable();
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
					manuallyInsert();
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

			String sql = "create table IF NOT EXISTS books " + "   (id INT NOT NULL AUTO_INCREMENT,\r\n" +"category varchar(50),\r\n"+ "title varchar(50),\r\n"
					+ "author varchar(50),\r\n" + "pages int,\r\n" + "price float,\r\n" + "quantity  int,\r\n"
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

	// VERKAR INTE ANVÄNDAS I JDBC ????? eller...hmmm
	// USE u209758462_miskb;
	// Conection
	void useDatabase() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);

			String sql = "USE books;)";
			statement.executeUpdate(sql);

			System.out.println("USE books successfully...");
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

			String sql = "insert into books values (default,'Programming', 'Java for dummies', 'Astrid Lindgren',345, 11.11, 11);";
			statement.executeUpdate(sql);
			sql = "insert into books values (default,'Thriller', 'More Java for dummies', 'Astrid Lindgren2',678, 22.22, 22);";
			statement.executeUpdate(sql);
			sql = "insert into books values (default,'Drama' ,'More Java for more dummies', 'Stephen King',980, 33.33, 33)";
			statement.executeUpdate(sql);
			sql = "insert into books values (default,'Comedy', 'A Cup of Java', 'G.W persson',123, 44.44, 44)";
			statement.executeUpdate(sql);
			sql = "insert into books values (default,'Based on true story', 'A Teaspoon of Java', 'fÖRFATTARE  Jane doe',453, 55.55, 55)";
			statement.executeUpdate(sql);
			System.out.println("Inserted records into the table...");

			System.out.println("INSERT INTO books successfully...");
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

//  SHOW DATABASES  show databases;
	// connection
	void showDatabase() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);

			DatabaseMetaData meta = connect.getMetaData();
			ResultSet resultSet = meta.getCatalogs();
			System.out.println("List of databases: ");
			while (resultSet.next()) {
				System.out.println("   " + resultSet.getString("TABLE_CAT"));
			}
			resultSet.close();

			connect.close();
			System.out.println("show databases successfully...");
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
			ResultSet resultSet = st.executeQuery("DESCRIBE books");
			ResultSetMetaData md = resultSet.getMetaData();
			int col = md.getColumnCount();
			for (int i = 1; i <= col; i++) {
				String col_name = md.getColumnName(i);
				System.out.println(col_name);
			}
			DatabaseMetaData dbm = connect.getMetaData();
			ResultSet rs1 = dbm.getColumns(null, "%", "books", "%");
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
			String strSelect = "select id, category,title,author,pages, price, quantity  from books";
			System.out.println("The SQL query is: " + strSelect);
			System.out.println();

			ResultSet resultSet = statement.executeQuery(strSelect);

			// For each row, retrieve the contents of the cells with getXxx(columnName).
			System.out.println("The records selected are:");
			int rowCount = 0;
			while (resultSet.next()) { // Move the cursor to the next row, return false if no more row
				int id  = resultSet.getInt("id");
				String category = resultSet.getString("category");
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				int pages = resultSet.getInt("pages");
				double price = resultSet.getDouble("price");
				int quantity  = resultSet.getInt("quantity");
				System.out.println(id+", "+category+", "+title + ", " + author + ", " + pages + ", " + price + ", " + quantity);
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

			String sql = "SELECT  id,category,title,author,pages, price, quantity FROM books";
			ResultSet rs = statement.executeQuery(sql);
			// Extract data from result set
			String strSelect = "select * from books";
			System.out.println("The SQL query is: " + strSelect); // Echo For debugging
			System.out.printf("%2s %5s %15s %15s %11s %3s %3s%n", "id","category" , "title", "author", "pages", "price", "quantity");
			ResultSet rset = statement.executeQuery(strSelect);
			while (rset.next()) { // Move the cursor to the next row
				System.out.println(rset.getInt("id")+ ", " + rset.getString("category")  + ", " + rset.getString("title") + ", " + rset.getString("author")
						+ ", " + rset.getInt("pages") + ", " + rset.getDouble("price") + ", " + rset.getInt("quantity"));
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
//drop table   DROP TABLE profiles;
	void dropTable() {
		System.out.println("Connecting to a selected database...");
		try {
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);
			String sql = "DROP TABLE books;";
			statement.executeUpdate(sql);

			System.out.println("DROP TABLE  successfully...");
		} catch (SQLException se) {
//Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
//Handle errors for Class.forName
			e.printStackTrace();
		} finally {
//finally block used to close resources
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

			// höjer momsen och kollar att boken finns, och id 1001
			String strUpdate = "update books set price = price*0.7, quantity  = quantity+1 where id = 1001";
			System.out.println("The SQL query is: " + strUpdate); // Echo for debugging
			int countUpdated = statement.executeUpdate(strUpdate);
			System.out.println(countUpdated + " records affected.");

			// SELECT to check the UPDATE.
			String strSelect = "select * from books where id = 1001";
			System.out.println("The SQL query is: " + strSelect); // Echo for debugging
			ResultSet resultSet = statement.executeQuery(strSelect);
			while (resultSet.next()) { // Move the cursor to the next row
				System.out.println(resultSet.getInt("id")+ ", " + resultSet.getString("category") + ", " + resultSet.getString("title") + ", "
						+ resultSet.getString("author") + ", " + resultSet.getInt("pages") + ", "
						+ resultSet.getDouble("price") + ", " + resultSet.getInt("quantity "));
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
			String sqlDelete = "delete from books where id>=3000 and id<4000";
			System.out.println("The SQL query is: " + sqlDelete); // Echo for debugging
			int countDeleted = statement.executeUpdate(sqlDelete);
			System.out.println(countDeleted + " records deleted.\n");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// Method for manual insert.  funkar ej , KOLLA UPP
	void manuallyInsert(){
	System.out.println("Please Enter Category name: ");
	String insertString = sc.nextLine();
	

	try{
		System.out.println("Connecting to a selected database...");
		
			connect = DriverManager.getConnection(dburl, user, pass);

			System.out.println("Connected database successfully..." + dburl);
	    String sql="insert into Books(category,title,author,pages, price, quantity) values(default, ?)";
	    preparedStatement = connect.prepareStatement(sql);
	    preparedStatement.setString( 0, insertString);
	    preparedStatement.execute();
	} catch (SQLException se){
	    System.out.println(se.getMessage());
	} finally {
		try {
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


// END
}
