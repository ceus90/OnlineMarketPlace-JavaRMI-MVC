import java.util.Scanner;
import java.util.List;

public class MarketCustView {
	protected String str;
	protected int qty;
	
	protected void cartItem(){
			//Display options when customer wants to add items to his/her cart
		System.out.println("\nPlease enter the item details below, which you want to add to your cart");
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\nEnter the name: ");
		while (!sc.hasNext("[A-Za-z]+")) {
			System.out.print("Enter alphabets only: ");
			sc.next();
		}
		str = sc.next();
	
		System.out.print("Enter the quantity: ");
		sc = new Scanner(System.in);
		while (!sc.hasNextInt()) {
			System.out.print("Enter the digits only: ");
			sc.next();
		}
		qty = Integer.parseInt(sc.next());
	}
	
	protected void cartDisplay(List<String> itemDetails){
			//Displaying the items in cart for this customer
		String[] itemData;
		System.out.println("\n\tItem Name\tItem Price\tPurchase Quantity\n=====================================================");    
		for(int i = 0; i < itemDetails.size(); i++)
		{
			itemData = itemDetails.get(i).split(",");
			System.out.print("\t   "+itemData[0]+"\t\t  "+itemData[1]+"\t\t  "+itemData[2]+"\n");
			itemData = null;
		} 
	}
	
	public int buyDisplay(){//Display options for customer when he decides to purchase items
		System.out.println("\tProceed to purchase --> 1\n\tEnter any other number to go to the previous screen...");
		System.out.print("\n\tEnter your choice here: ");
		return Integer.parseInt(System.console().readLine());
	}
}
