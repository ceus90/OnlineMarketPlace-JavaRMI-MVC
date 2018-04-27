import java.io.Serializable;

public class UserRoleCreate implements Serializable {

	// Assigning the userRole for one session
	private static final long serialVersionUID = 1L;
	private String userRole;
	
	//This is the constructor of UserRoleCreate class
	public UserRoleCreate(String userRole)
	{
		//Assigning the value of userRole to the private variable
		this.userRole = userRole;
	}
	
	public String getUserRole(){
		//Returning the userRole requested - either admin or customer
		return userRole;
	}
}
