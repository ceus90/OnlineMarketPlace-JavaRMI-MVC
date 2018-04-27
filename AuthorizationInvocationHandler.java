//Proxy & Reflection pattern implementation
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AuthorizationInvocationHandler implements InvocationHandler, Serializable {
	private static final long serialVersionUID = 1L;
	private Object implementedObject;
 
	public AuthorizationInvocationHandler(Object imp) {
	   this.implementedObject = imp;
	}
	 
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		if (method.isAnnotationPresent(RequiresRole.class)) {
		//Reflection pattern - Checking if annotation is present for the method being called.
			RequiresRole test = method.getAnnotation(RequiresRole.class);
			
			//This user should access only the methods which is available to this userType in this session
			//If the user tries to access methods out of his access, the user will be logged out
			MarketSession MS = (MarketSession) args[0];
			
			//the role type for the method and the session is compared
			if (MS.getUserDetails().getUserRole().equals(test.value()))
				 return method.invoke(implementedObject, args);
			else
            	throw new AuthorizationException(method.getName()); //AuthorizationException is invoked here
		}
		else //Calling the method with no annotation
			return method.invoke(implementedObject, args);
	}
}
