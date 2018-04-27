//Receiver
import java.util.List;

public class MarketView{
	// Ryan: Should these really be publically visible?
	//shseshad: Made all the variables and methods protected
	
	protected String userName;
	protected String password;
	protected String userRole;
	protected String path;
	
	protected void logDisplay(String uType) { //Login display
		System.out.println("\nYou are in the "+uType+" portal.");
		System.out.print("User name: ");
		userName = System.console().readLine();
		System.out.print("Password: ");
		password = System.console().readLine();
	}
	
	protected int wDisplay(String uType){ //Post login display
		if(uType.equalsIgnoreCase("Admin")){	
			userRole = "Admin";
			System.out.print("\nAdmin portal");
			System.out.println("\n\tView Cart (customer specific view) --> 999\n\tView Items --> 1\n\tAdd Items --> 2\n\tEdit Existing Items --> 3\n\tLogout --> 4");
			System.out.print("\n\tEnter your choice here: ");
		}
		else{
			userRole = "Customer";
			System.out.print("\nCustomer portal");	
			System.out.println("\n\tAdd Items to online market (admin specific view) --> 999\n\tBrowse Items --> 1\n\tAdd Items To Cart --> 2\n\tView Cart --> 3\n\tLogout --> 4");
			System.out.print("\n\tEnter your choice here: ");	
		} //Returning the user input to MarketController
		return Integer.parseInt(System.console().readLine());
	}
	
	protected void iDisplay(List<String> itemDetails){ 
		String[] itemData;
		System.out.println("\n\tItem Name\tItem Price\n=======================================");    
		for(int i = 0; i < itemDetails.size(); i++)
		{ //Items for sale display
			itemData = itemDetails.get(i).split(",");
			System.out.print("\t   "+itemData[0]+"\t\t  "+itemData[1]+"\n");
			itemData = null;
		}    
	}

	protected void loginAck(String ack){ //Getting acknowledgment from the server after user login is successful
		System.out.println(ack);
	}
	
}
