/**
 * Abstract class 
 */
 
import java.util.List;

public interface MarketAbstract{
	public void exDisplay(String uType); //Login display
	public void itemDisplay(List<String> itemDetails); //Items for sale display
	public String getUser(); //To retrieve user name
	public String getPass(); //To retrieve password
	public String getUserRole(); //To retrieve User role
	public void lAck(String ack); //Acknowledgment from sever on login
	public int welcomeDisplay(String uType); //Post login display
}
