import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.UndeclaredThrowableException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.util.List;
import java.util.ArrayList;


public class MarketDB {

	// For Database Connection
	private String hostname = "localhost:3306";
	private String dbName = "shseshad_db";		
	private String url = "jdbc:mysql://" + hostname + "/" + dbName;
	private String db_username = "shseshad";
	private String db_password = "2000052166";	
	private Connection conn = null;
	private Statement stmt = null;
	
	public void startDB(){
		try{ //Database connection related code is taken from the in-class example
			System.out.println("Connecting database...");	
			conn = (Connection) DriverManager.getConnection(url, db_username, db_password);
			System.out.println("Database connected!");
		} catch (Exception e){
			System.out.println("Exception: MarketModel: startDB " + e.getMessage());
			e.printStackTrace();
			}
	}
	
	public List<String> getItems(){
		List<String> queryResult = new ArrayList<>();
		String itemData;
		int i = 0;
		if(conn != null) {
			stmt = null;
			ResultSet rs = null;
			try {
				stmt = (Statement) conn.createStatement();
				try { //Selecting everything from the market place and sending to the client as a list
					rs = stmt.executeQuery("select * from market");	
					while (rs.next())  {
						String name = rs.getString("Item_name");
						int price = rs.getInt("Item_Price");
						int stock = rs.getInt("Item_Stock");
						//Price and quantity are converted to STRING and then sent to client
						itemData = name+","+Integer.toString(price)+","+Integer.toString(stock);
						queryResult.add(i++,itemData);
					}
				    return queryResult;
				} catch (SQLException e) {
					System.err.println("Unable execute query!");
					e.printStackTrace();
					}		
			} catch (SQLException e1) {
				System.err.println("Unable to create SQL statement!");
				e1.printStackTrace();
			}
		}
		try{
			stmt.close();
		} catch (Exception e){
			System.out.println("Exception: MarketModel: stopDB " + e.getMessage());
			e.printStackTrace();
			}		
		return null;
	}
	
	public List<String> getCartItems(int connNum){
		List<String> queryResult = new ArrayList<>();
		String itemData;
		int i = 0;
		if(conn != null) {
			stmt = null;
			ResultSet rs = null;
			try {
				stmt = (Statement) conn.createStatement();
				try { //Selecting everything from the cart of this connection number and sending to the client as a list
					rs = stmt.executeQuery("select * from cart where Connection_Number ="+connNum+"");	
					while (rs.next())  {
						String name = rs.getString("Item_Name");
						int price = rs.getInt("Item_Price");
						int qty = rs.getInt("Purchase_Quantity");
				       
						itemData = name+","+Integer.toString(price)+","+Integer.toString(qty);
						queryResult.add(i++,itemData);
					}
				    return queryResult;
				} catch (SQLException e) {
					System.err.println("Unable execute query!");
					e.printStackTrace();
					}		
			} catch (SQLException e1) {
				System.err.println("Unable to create SQL statement!");
				e1.printStackTrace();
			}
		}
		try{
			stmt.close();
		} catch (Exception e){
			System.out.println("Exception: MarketModel: stopDB " + e.getMessage());
			e.printStackTrace();
			}	
		return null;
	}
	
	public void addToDB(String itemName, int itemPrice, int itemQty){
		if(conn != null) {
			stmt = null;
			try {
				stmt = (Statement) conn.createStatement();
				//Adding the details collected from client to the market table in the database
				try { 
					stmt.executeUpdate("insert into market (Item_name,Item_Price,Item_Stock) values ('"+itemName+"',"+itemPrice+","+itemQty+")");
				} catch (SQLException e) {
					System.err.println("Unable execute query!");
					e.printStackTrace();
				}				
			} catch (SQLException e1) {
				System.err.println("Unable to create SQL statement!");
				e1.printStackTrace();
			}
		}
		try{
			stmt.close();
		} catch (Exception e){
			System.out.println("Exception: MarketModel: stopDB " + e.getMessage());
			e.printStackTrace();
			}
	}

	public void updateDB(String itemXName, String itemName, int itemPrice, int itemQty){

		if(conn != null) {
			stmt = null;
			try {
				stmt = (Statement) conn.createStatement();
				synchronized(this){
					try { //Updating the details received from client. No two admins can do changes on the same item. The latest changes are displayed to the clients connected
						stmt.executeUpdate("update market set Item_name = '"+itemName+"',Item_Price = "+itemPrice+",Item_Stock = "+itemQty+" where Item_name = '"+itemXName+"'");
						wait(3000);
					} catch (SQLException e) {
						System.err.println("Unable execute query!");
						e.printStackTrace();
					}catch (InterruptedException ie){
						ie.printStackTrace();
						}
				notifyAll(); //Waking up all the threads
			}
		}catch (SQLException e1) {
				System.err.println("Unable to create SQL statement!");
				e1.printStackTrace();
		}
		try{
			stmt.close();
		} catch (Exception e){
			System.out.println("Exception: MarketModel: stopDB " + e.getMessage());
			e.printStackTrace();
			}
		}	
	}
	

	public void addCartDB(int connNum, String itemName, int itemQty){
		if(conn != null) {
			stmt = null;
			try {
				stmt = (Statement) conn.createStatement();
				
				try { //Adding the item details to the cart table
					stmt.executeUpdate("insert into cart (Item_Name,Item_Price,Purchase_Quantity,Connection_Number) values ('"+itemName+"',(select Item_Price from market where Item_name =  '"+itemName+"'),"+itemQty+","+connNum+")");
				} catch (SQLException e) {
					System.err.println("Unable execute query!");
					e.printStackTrace();
				}				
			} catch (SQLException e1) {
				System.err.println("Unable to create SQL statement!");
				e1.printStackTrace();
			}
		}
		try{
			stmt.close();
		} catch (Exception e){
			System.out.println("Exception: MarketModel: stopDB " + e.getMessage());
			e.printStackTrace();
			}
	}	
	
	public void emptyCartDB(int connNum){
		if(conn != null) {
			stmt = null;
			try {
				stmt = (Statement) conn.createStatement();
				
				try { //Deleting everything from cart table, related to this connection number
					stmt.executeUpdate("delete from cart where Connection_Number = "+connNum+"" );
				} catch (SQLException e) {
					System.err.println("Unable execute query!");
					e.printStackTrace();
				}				
			} catch (SQLException e1) {
				System.err.println("Unable to create SQL statement!");
				e1.printStackTrace();
			}
		}
		try{
			stmt.close();
		} catch (Exception e){
			System.out.println("Exception: MarketModel: stopDB " + e.getMessage());
			e.printStackTrace();
			}
		System.out.println("Cart for the connection number-"+connNum+" is cleared.");
	}

	public String checkoutCartDB(int connNum){
		Statement stmt1 = null; //Customer does purchase
		Statement stmt2 = null;
		if(conn != null) {
			stmt = null;
			ResultSet rs1 = null;
			ResultSet rs2 = null;
			try {
				stmt = (Statement) conn.createStatement();
				try {
					rs1 = stmt.executeQuery("select * from cart where Connection_Number = "+connNum+"");	
					while (rs1.next())  { //Getting the details from cart table for this connection number
						int qty = rs1.getInt("Purchase_Quantity");
						String cName = rs1.getString("Item_Name");
						try {
							stmt1 = (Statement) conn.createStatement();
							try {
								rs2 = stmt1.executeQuery("select * from market where Item_Name = '"+cName+"'");	
								while (rs2.next()){
									int stock = rs2.getInt("Item_Stock");
									int temp = stock-qty; //Checking the stock of the item
									if(temp > -1){
										try {
											stmt2 = (Statement) conn.createStatement();
											synchronized(this){
												try { //Updating the cart details according to the purchase
													stmt2.executeUpdate("update market set Item_Stock = "+temp+" where Item_name = '"+cName+"'");
													System.out.println("Item is checked out");
													} catch (SQLException e) {
														System.err.println("Unable execute query!");
														e.printStackTrace();
														}
											notifyAll();
											}
										}catch (SQLException e) {
											System.err.println("Unable to create SQL statement5!");
											e.printStackTrace();
											}
									}
									else
										return "Stock not available. Come back tomorrow!";
								}
							} catch (SQLException e2) {
								System.err.println("Unable to create SQL statement1!");
								e2.printStackTrace();
								}
						}catch (SQLException e3) {
							System.err.println("Unable to create SQL statement2!");
							e3.printStackTrace();
							}
					}	
				} catch (SQLException e) {
					System.err.println("Unable to create SQL statement3!");
					e.printStackTrace();
					}
			}catch (SQLException e1) {
				System.err.println("Unable to create SQL statement4!");
				e1.printStackTrace();
				}
		
		}
		try{
			stmt.close();
			stmt1.close();
			stmt2.close();
		} catch (Exception e){
			System.out.println("Exception: MarketModel: checkoutCartDB " + e.getMessage());
			e.printStackTrace();
			}
		
		return "All the cart items are checked out.";
	}	
}