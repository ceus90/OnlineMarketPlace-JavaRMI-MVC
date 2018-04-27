import java.lang.reflect.Proxy;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.IOException;
import java.util.List;

public class MarketServerController extends UnicastRemoteObject implements MarketRmiInterface {
	private String name;
	private String server_str;
	private String username;
	private String password;
	private boolean ch;
	private static int id = 0;
	
	private MarketModel MM = new MarketModel(username, password);
	
	protected MarketServerController(String s) throws RemoteException{
		super(); name = s; //For binding
	}
	 
	public synchronized int get_id() throws RemoteException{
		id++; // To get the number of clients connected to the server
		System.out.println("\nConnection number " +id+" established.");
		MM.startDatabase(); //Initializing the DB connection
		return id;
	}
	
	public synchronized void getId() throws RemoteException{
		System.out.println("\nUser on connection number " +id+" logged out.");
		MM.emptyCart(id--); //Subtracting the connection number and emptying the cart details
	}	
	 
	public String checkLogin(String username, String password){
		server_str = MM.checkLog(username, password);
		System.out.println("Login info received from client controller and sent to model.");
		return server_str; //Receiving the user details from controller when admin/customer logs in and sending back the acknowledgment for client display
	}
	
	public MarketSession startSession(String uRole) throws RemoteException {
		MarketSession MS = new MarketSession(uRole); //Initializing the session
		System.out.println("User role "+uRole+" received from client controller and sent to MarketSession.");
		return MS;
	}
		
	public boolean reqAdminAccess(MarketSession MS) { 
		ch = MM.reqAdmin(MS); //Checking for admin access
		System.out.println("User role "+MS.getUserDetails().getUserRole()+" received from client controller and sent to model for RBAC.");
		return ch;
	}
	
	public boolean reqCustAccess(MarketSession MS) {
		ch = MM.reqCust(MS); //Checking for customer access
		System.out.println("User role "+MS.getUserDetails().getUserRole()+" received from client controller and sent to model for RBAC.");
		return ch;
	}
	
	public List<String> getItemDetails(MarketSession MS) {
		List<String> itemDetails; //Getting the details for items on sale
		itemDetails = MM.getItems();
		return itemDetails;
	}
	
	public List<String> getCartDetails(MarketSession MS, int connNum) {
		List<String> itemDetails; //Getting the details of items in the respective customer cart
		itemDetails = MM.getCartItems(connNum);
		return itemDetails;
	}
	
	public String addToMarket(MarketSession MS, int connNum, String str, int price, int qty) {
		System.out.println("Adding new items to the market place. Request received from connection number- "+connNum);
		MM.addToMarketDatabase(str, price, qty); //Details of new item to be added, sent by admin
		return "Item details are added.";
	}
	
	public String updateMarket(MarketSession MS, int connNum, String xStr, String str, int price, int qty) {
		System.out.println("Updating existing item- "+xStr+" in the market place. Request received from connection number- "+connNum);
		MM.updateMarketDatabase(xStr, str, price, qty); //Details of new item details to be updated, sent by admin
		return "Item details are added.";
	}
	
	public String addToCart(MarketSession MS, int connNum, String str, int qty) {
		System.out.println("Adding items to cart. Request received from connection number- "+connNum);
		MM.addCartDatabase(connNum, str, qty); //Details of new item to be added into the cart, sent by customer
		return "Item details are added to the cart.";
	}
	
	public String checkoutCart(MarketSession MS, int connNum) {
		System.out.println("Checking out the cart. Request received from connection number- "+connNum);
		String ack = MM.checkoutCartDatabase(connNum); //Customer decides to do purchase
		return ack;
	}
	
	public void startServer(){			
		System.setSecurityManager(new SecurityManager());
		try{
			System.out.println("Creating a market server!");
			String name = "//in-csci-rrpc01.cs.iupui.edu:1230/MarketServer";		
			//Calling the proxy method MarketServerController(name), using reflection on MarketRmiInterface class
			//Proxy.newProxyInstance(classLoader, interfaces, invocationHandler)
			MarketRmiInterface mRMI = (MarketRmiInterface)
										Proxy.newProxyInstance(MarketRmiInterface.class.getClassLoader(),
										new Class<?>[]{MarketRmiInterface.class},
										new AuthorizationInvocationHandler(new MarketServerController(name)));
										
			
			System.out.println("MarketServer: binding it to name: " +name);
			Naming.rebind(name, mRMI);
			System.out.println("Market Server Ready!");			
		} catch (Exception e){
			System.out.println("Exception: MarketServerController " + e.getMessage());
			e.printStackTrace();
		} 
	}
}
