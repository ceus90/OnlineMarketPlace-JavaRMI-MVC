import java.util.List;

public class MarketController {
	private MarketRmiInterface model;
	private MarketView view;
	
	private int ch;
	private int ch1;
	private int ch2;
	private boolean log = false;
	private boolean cart = true;
	private String ack;
	private String userType;
	private MarketSession MS;
	private UserRoleCreate uType;
	private int id;
	private List<String> itemDetails;


	// Model-View Glue
	public MarketController(MarketRmiInterface model, MarketView view) {
		this.model = model;
		this.view = view;
	}
	
	public void startMarket(String chee){
		if(chee.isEmpty()) //Making sure user enters something
			System.out.println("Please enter something.");
		else if((chee.matches("\\d+"))){ //Making sure user enters integers only
			ch = Integer.parseInt(chee);
			if (ch == 1 || ch == 2){
				try{
					MarketInvoker MI = new MarketInvoker();
					MarketAbstract adDisplay = new AdminView(view); //To be used later based on the user input
					MarketAbstract cuDisplay = new CustView(view); //To be used later based on the user input
					MarketAdminView AV = new MarketAdminView(); //To be used later based on the user input
					MarketCustView CV = new MarketCustView(); //To be used later based on the user input
					
					if(ch == 1){
						userType = "Admin";		
						//MarketSession MS = new MarketSession(userType);						
						MI.invokeMCo(adDisplay); //Dispatcher
						MI.display(userType); //command
						ack = model.checkLogin(MI.getUser(), MI.getPass()); //Sending the user details to the server when a user logs in.
						MI.lAck(ack); //Reply from the server is sent to view for customer display
						if(ack.equals("Login successful.")){
							log = true;
							MS = model.startSession(userType); //Object from MarketSession
							uType = MS.getUserDetails(); //Object from UserRoleCreate
							id = model.get_id();
							System.out.println("\nConnection Number: " + id);
						}
						while(log){
							ch1 = MI.welcomeDisplay(userType);	 //Command
							if (ch1 == 999){ //Admin decides to access the shopping cart
							//Illustrating Authorization pattern through RBAC
								if(model.reqCustAccess(MS)){
									List<String> itemDetails;
									itemDetails = model.getCartDetails(MS,id);
									System.out.println("\nDisplaying the items for sale on this market.");
									CV.cartDisplay(itemDetails);
								}
							}
							else if (ch1 == 1){ //Admin decides to view existing items
								System.out.println("\nDisplaying the items for sale on this market.");
								itemDetails = model.getItemDetails(MS);
								MI.itemDisplay(itemDetails);
							}
							else if (ch1 == 2){ //Admin decides to add new items
								if(model.reqAdminAccess(MS)){
									
									itemDetails = model.getItemDetails(MS);
									AV.itemStockDisplay(itemDetails);  //Dispatcher
									
									AV.getItem("add"); //Calling the method in MarketAdminView
									ack = model.addToMarket(MS,id, AV.str, AV.price, AV.qty);
									System.out.println(ack);
									
									itemDetails = model.getItemDetails(MS);
									AV.itemStockDisplay(itemDetails); //Dispatcher
								}
							}
							else if (ch1 == 3){ //Admin decides to edit the existing items' information
								if(model.reqAdminAccess(MS)){
									
									itemDetails = model.getItemDetails(MS);
									AV.itemStockDisplay(itemDetails);  //Dispatcher
									
									AV.getItem("update"); //Calling the method in MarketAdminView
									ack = model.updateMarket(MS,id, AV.xStr, AV.str, AV.price, AV.qty);
									System.out.println(ack);
									
									itemDetails = model.getItemDetails(MS);
									AV.itemStockDisplay(itemDetails); //Dispatcher
								}
							}

							else if (ch1 == 4){ //Admin decides to logout
								log = false;
								model.getId();
							}
							else
								System.out.println("Please choose the correct option.");			
						}
					}
					else{
						userType = "Customer";	
						MarketSession MS = new MarketSession(userType);							
						MI.invokeMCo(cuDisplay); //Dispatcher
						MI.display(userType); //command
						ack = model.checkLogin(MI.getUser(), MI.getPass()); //Sending the user details to the server when a user logs in.
						MI.lAck(ack); //Reply from the server is sent to view for customer display
						if(ack.equals("Login successful.")){
							log = true;
							MS = model.startSession(userType); //Object from MarketSession
							uType = MS.getUserDetails(); //Object from UserRoleCreate
							id = model.get_id();
							System.out.println("\nConnection Number: " + id);
						}
						while(log){
							
							ch1 = MI.welcomeDisplay(userType);	 //Command
							if (ch1 == 999){ //Customer decides to add new items to the market place
							//Illustrating Authorization pattern through RBAC
								if(model.reqAdminAccess(MS)){
									itemDetails = model.getItemDetails(MS);
									AV.itemStockDisplay(itemDetails);  //Dispatcher
									
									AV.getItem("add"); //Calling the method in MarketAdminView
									ack = model.addToMarket(MS,id, AV.str, AV.price, AV.qty);
									System.out.println(ack);
									
									itemDetails = model.getItemDetails(MS);
									AV.itemStockDisplay(itemDetails); //Dispatcher
								}
							}
							else if (ch1 == 1){  //Customer decides to view the existing items for sale
								System.out.println("\nDisplaying the items for sale on this market.");
								itemDetails = model.getItemDetails(MS);
								MI.itemDisplay(itemDetails);
								}
							else if (ch1 == 2){ //Customer decides to add existing items to the cart
								if(model.reqCustAccess(MS)){
									List<String> itemDetails;
									itemDetails = model.getItemDetails(MS);
									System.out.println("\nDisplaying the items for sale on this market.");
									MI.itemDisplay(itemDetails);  //Dispatcher
									CV.cartItem();
									ack = model.addToCart(MS, id, CV.str, CV.qty);
									System.out.println(ack);
									itemDetails = model.getCartDetails(MS,id);
									System.out.println("\nDisplaying the items for sale on this market.");
									CV.cartDisplay(itemDetails); //Calling the method in MarketCustomerView
								}
							}
							else if (ch1 == 3){  //Customer decides to view his/her cart and proceed to checkout
								if(model.reqCustAccess(MS)){
									List<String> itemDetails;
									itemDetails = model.getCartDetails(MS,id);
									System.out.println("\nDisplaying the items in the cart for connection number- "+id+".");
									CV.cartDisplay(itemDetails); //Calling the method in MarketCustomerView		
									cart = true;									
									while(cart){
										ch2 = CV.buyDisplay();
										if (ch2 == 1){ //Customer decides to purchase
											if(model.reqCustAccess(MS)){	
												ack = model.checkoutCart(MS,id);
												System.out.println(ack);
												cart = false;
											}
										}
										else{
											System.out.println("\t\t\tGoing to the previous screen.");
											cart = false;
										}
									}
								}
							}
							else if (ch1 == 4){ //Customer decides to logout
								log = false;
								model.getId();
							}
							else //Customer enters wrong input
								System.out.println("Please choose the correct option.");	
						}
					} 				
				}catch(Exception e){
					System.out.println("Exception::MarketController "+e.getMessage());
					e.printStackTrace();
					}	
			}
			else if (ch == 0) //User decides not to do anything
				System.exit(0);
			else
				//User inputs wrong digits
				System.out.println("Wrong input. Try again");
		}
		else //User inputs letters
			System.out.println("Enter numbers only.");
	}	
}
