public class MarketServer{	
	public static void main(String args[]){
		String name = " ";
		try
		{
			//Instantiating the server controller
			MarketServerController MSC = new MarketServerController(name);			
			//Initiating the server
			MSC.startServer();		
		}catch(Exception e)
		{
			System.out.println("Exception:MarketServer "+e.getMessage());	
		}	
	}
}
