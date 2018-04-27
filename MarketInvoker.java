/**
 * Invoker Objects which is user by command
 */
import java.util.List;

public class MarketInvoker{
	private MarketAbstract MA;
	
	//Constructor
	public void MarketInvoker(MarketAbstract MA){
		this.MA = MA;
	}
	
	public void invokeMCo(MarketAbstract MA){
		this.MA = MA;
	}
	
	public void display(String uType){
		MA.exDisplay(uType);
	}
	
	public int welcomeDisplay(String uType){
		return MA.welcomeDisplay(uType);
	}	
	
	public void itemDisplay(List<String> toDisplay){
		MA.itemDisplay(toDisplay);
	}
	
	
	public String getUser(){
		return MA.getUser();
	}
	
	public String getUserRole(){
		return MA.getUserRole();
	}
	
	public String getPass(){
		return MA.getPass();
	}
	
	public void lAck(String ack){
		MA.lAck(ack);
	}
}
