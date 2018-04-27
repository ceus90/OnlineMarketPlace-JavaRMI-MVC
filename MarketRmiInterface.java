import java.util.List;

public interface MarketRmiInterface extends java.rmi.Remote {
	//Calls the remote method on server
	String checkLogin(String username, String password) throws java.rmi.RemoteException;
	
	int get_id() throws java.rmi.RemoteException; //Increases the connection count for new log in
	void getId() throws java.rmi.RemoteException; //Decreases the connection count for user log out
	
	public MarketSession startSession(String uRole) throws java.rmi.RemoteException;

	@RequiresRole("Admin") //Checking for admin role function calls
	boolean reqAdminAccess(MarketSession MS) throws java.rmi.RemoteException; 
	
	@RequiresRole("Customer") //Checking for customer role function calls
	boolean reqCustAccess(MarketSession MS) throws java.rmi.RemoteException; 
	
	List<String> getItemDetails(MarketSession MS) throws java.rmi.RemoteException; //Market display. Common for both admin and customers
	
	@RequiresRole("Admin") //Admin decides to add items to market
	String addToMarket(MarketSession MS, int connNum, String str, int price, int qty) throws java.rmi.RemoteException;
	
	@RequiresRole("Admin") //Admin decides to edit the information of items present in market
	String updateMarket(MarketSession MS, int connNum, String xStr, String str, int price, int qty) throws java.rmi.RemoteException; 

	@RequiresRole("Customer") //Customer decides to add items to his/her cart
	String addToCart(MarketSession MS, int connNum, String str, int qty) throws java.rmi.RemoteException; 
	
	@RequiresRole("Customer") //Customer decides to view his/her cart
	List<String> getCartDetails(MarketSession MS, int connNum) throws java.rmi.RemoteException;
	
	@RequiresRole("Customer") //Customer decides to do purchase on the cart items
	String checkoutCart(MarketSession MS, int connNum) throws java.rmi.RemoteException;
}
