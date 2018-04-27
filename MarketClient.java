import java.rmi.Naming;
import java.rmi.RemoteException;

public class MarketClient {

	private String name;
	
	public MarketClient(String s) throws RemoteException {
		super(); name = s; //For binding
	}
	// Ryan: We will improve on this design in the future - see Assignment #2.
	public static void main(String args[]){
		System.setSecurityManager(new SecurityManager());
		String ch = "";
		try{
			//Binding client with the server
			String name = "//in-csci-rrpc01.cs.iupui.edu:1230/MarketServer";
			//Change this server name if you are running server program in any machine other than in-csci-rrpc02.cs.iupui.edu
			MarketRmiInterface myMarket = (MarketRmiInterface) Naming.lookup(name);
			MarketView MV = new MarketView(); //View instance which will be passed to controller
			MarketController MC = new MarketController(myMarket,MV);
			while(true){ //Client program runs until the user decides to terminate
				System.out.println("\nWhich portal do you want to enter?\nExit Now\t-----> 0\nAdmin Portal\t-----> 1\nCustomer Portal\t-----> 2");
				System.out.print("Enter your choice here: ");
				ch = System.console().readLine();
				MC.startMarket(ch);//Entry point to the application
			}	
		}catch(Exception e){
			System.out.println("A5Client Exception: " +
			e.getMessage());
			e.printStackTrace();
			}
		
	System.exit(0);
	}
}
