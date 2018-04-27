import java.util.Scanner;
import java.util.List;

public class MarketAdminView {
	
	
	protected String str;
	protected int price;
	protected int qty;
	protected String xStr;
	
	
	protected void itemStockDisplay(List<String> itemDetails){ 

		String[] itemData;

		System.out.println("\nDisplaying the stock of the items on sale in this market.");
		System.out.println("\nItem Name\tItem Price\tItem Stock\n====================================================");    
		
		for(int i = 0; i < itemDetails.size(); i++)
		{ //Items for sale display, including the stock
			itemData = itemDetails.get(i).split(",");
			System.out.println("   "+itemData[0]+"\t\t  "+itemData[1]+"\t\t  "+itemData[2]);
			itemData = null;
		}    
	}
	
	
	protected void getItem(String cmd){//Display options for admin while adding a new item or editing the information of the items or deleting an item present in the market place
		if(cmd.equals("update")) {
			System.out.print("Please enter the Item Name you want to "+cmd+"- ");
			Scanner sc = new Scanner(System.in);
			while (!sc.hasNext("[A-Za-z]+")) {
				System.out.print("Enter alphabets only: ");
				sc.next();
			}
			xStr = sc.next();
		}
			
		System.out.println("\nPlease enter the new details below");
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\nEnter the name: ");
		while (!sc.hasNext("[A-Za-z]+")) {
			System.out.print("Enter alphabets only: ");
			sc.next();
		}
		str = sc.next();
		
		System.out.print("Enter the price: ");
		while (!sc.hasNextInt()) {
			System.out.print("Enter the digits only: ");
			sc.next();
		}
		price = Integer.parseInt(sc.next());
	
		System.out.print("Enter the quantity: ");
		sc = new Scanner(System.in);
		while (!sc.hasNextInt()) {
			System.out.print("Enter the digits only: ");
			sc.next();
		}

		qty = Integer.parseInt(sc.next());
	}
}