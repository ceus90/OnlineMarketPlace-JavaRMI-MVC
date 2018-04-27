import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MarketSession implements Serializable {

	// Creating the session with the received userRole
	private static final long serialVersionUID = 1L;
	private UserRoleCreate user;
	
	public MarketSession(String userRole)
	{
		//Assigning userRole to the variable "user"
		this.user = new UserRoleCreate(userRole);
	}
	
	public UserRoleCreate getUserDetails() {
		// Returning the userRole to the calling method
		return user;
	}
}