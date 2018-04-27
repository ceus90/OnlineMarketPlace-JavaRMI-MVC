import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;

public class MarketModel {
	
	private String name;
	private MarketDB mDB = new MarketDB();
	
	public MarketModel(String username, String password) throws RemoteException {
		/*Calling the super class class*/
		super();
		this.name=name;
		// TODO Auto-generated constructor stub
	}
	
	public String checkLog(String username, String password){
		String model_str = "Login successful.";
		String model_Nstr = "Wrong username & password";
		System.out.println("Credentials received from the client\nUsername: "+username+"\nPassword:"+password);
		if((username.equalsIgnoreCase("Admin") || username.equalsIgnoreCase("Customer")) && (password.equals("AdPassword") || password.equals("CustPassword")))
			return model_str; //Receiving the user details from controller when admin/customer logs in and sending back the acknowledgment for client display
		else
			return model_Nstr;
	}

	public boolean reqAdmin(MarketSession MS) { 
			System.out.println("User role- "+MS.getUserDetails().getUserRole());
			if(MS.getUserDetails().getUserRole().equals("Admin"))
				return true; 
			else return false;
	}
	
	public boolean reqCust(MarketSession MS) { 
			System.out.println("User role- "+MS.getUserDetails().getUserRole());
			if(MS.getUserDetails().getUserRole().equals("Customer"))
				return true;
			else return false;
	}
	
	public void startDatabase(){
		mDB.startDB(); //Sending the command received from ServerController to DB layer
	}
	
	public List<String> getItems(){
		List<String> itemDetails;
		itemDetails = mDB.getItems(); //Sending the command received from ServerController to DB layer, to get the items on sale
		return itemDetails;
	}
	
	public List<String> getCartItems(int connNum){
		List<String> itemDetails; //Sending the command received from ServerController to DB layer, to get the items in the cart for this customer
		itemDetails = mDB.getCartItems(connNum);
		return itemDetails;
	}
	
	public void addToMarketDatabase(String itemName, int itemPrice, int itemQty){
		mDB.addToDB(itemName, itemPrice, itemQty); //Sending the command received from ServerController, to add new items to the market
	}

	public void updateMarketDatabase(String itemXName, String itemName, int itemPrice, int itemQty){
		mDB.updateDB(itemXName, itemName, itemPrice, itemQty); //Sending the command received from ServerController, to update existing items in the market
	}	

	public void addCartDatabase(int connNum, String itemName, int itemQty){
		mDB.addCartDB(connNum, itemName, itemQty); //Sending the command received from ServerController, to add items to the cart for this customer
	}	
	
	public void emptyCart(int connNum){
		mDB.emptyCartDB(connNum); //When the customer logs out, his/her cart will be cleared
		System.out.println("Cart for the connection number-"+connNum+" is cleared.");
	}

	public String checkoutCartDatabase(int connNum){
		String ack = mDB.checkoutCartDB(connNum); //Sending details to DB layer when customer decides to do purchase
		return ack;
	}	
}