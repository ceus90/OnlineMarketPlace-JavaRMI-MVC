//Concrete Commands sent to receiver
import java.util.List;

public class AdminView implements MarketAbstract {
 //reference to the MarketView or receiver
  private MarketView MV;
  
  public AdminView(MarketView MV){
    this.MV = MV;
  }
  public void exDisplay(String uType){
	MV.logDisplay(uType);
  }
  
  public int welcomeDisplay(String uType){
	return MV.wDisplay(uType);
  }  
  
  public void itemDisplay(List<String> itemDetails){
	MV.iDisplay(itemDetails);
  }
  
  public String getUser(){
	return MV.userName;
  }

  public String getUserRole(){
	return MV.userRole;
  }
    
  public String getPass(){
	return MV.password;
  }
  
  public void lAck(String ack){
	MV.loginAck(ack);
  }
}
