//Exception class for unauthorized UserRoles

public class AuthorizationException extends RuntimeException{
	public AuthorizationException(String methodName) {
	//This will be printed during runtime error for wrong user access
		super("Wrong user is trying to access the method "+methodName+"() -- Unauthorized access - request denied. Logging out the user.");
	}
}