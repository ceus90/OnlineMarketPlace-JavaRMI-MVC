Server IP - in-csci-rrpc01.cs.iupui.edu

Client IP - in-csci-rrpc02.cs.iupui.edu
			in-csci-rrpc03.cs.iupui.edu
			in-csci-rrpc04.cs.iupui.edu
			in-csci-rrpc05.cs.iupui.edu
			in-csci-rrpc06.cs.iupui.edu
			
RMI Registry Port - 1230 

1. Following are the files containing my code for Assignment-4
	
	policy
	MarketServer.java -- Backend server. Executed on the server window.
	MarketServerController.java -- Server controller is initialized by MarketServer.
	MarketModel.java -- Model file which is connected to MarketServer.
	MarketDB.java -- Database layer.
	MarketSession.java -- Session is created after the successful login.
	UserRoleCreate.java -- User role is created at the time of the login.
	RequiresRole.java -- Holds the user role at the runtime of the application.
	AuthorizationException.java -- Exception message for unauthorized access.
	AuthorizationInvocationHandler.java -- Checks if the called method has Java Annotation and proceed accordingly.
	MarketRmiInterface.java	-- Interface between controller and server.
	MarketController.java -- Link between view and the RMI Interface, which in turn connects server.
	MarketView.java	-- Client display functions are present here. Receiver for Command Pattern.
	makefile -- To compile all the java files and to clean up the classes once the user exits.
	MarketClient.java -- The application file which will be executed on the client window. 
	MarketAbstract.java -- Abstract Fatory of Abstract Factory Pattern. Login and Item Display views.
	MarketInvoker.java -- Invokes the concrete commands received from MarketController.
	CustView.java -- Concrete Factory of Abstract Factory Pattern.
	AdminView.java -- Concrete Factory of Abstract Factory Pattern.
	MarketAdminView.java -- Admin specific views.
	MarketCustView.java -- Customer specific views.

2. Server IP and port number for RMI registry are hard-coded in the client and model programs.

3. Open six terminals and navigate to the folder Scripts.

4. Make the terminal with IP- in-csci-rrpc01.cs.iupui.edu as your server, execute the makefile through the command "make". This is your server window.

5. Rest of the five terminals will be your clients.

6. Initiate the RMI registry through the command "rmiregistry 1230&" in the server window.

7. Initiate the server program through the command- "java -cp ".:mysql-connector.jar" -Djava.security.policy=policyarketServer" in server window.

8. In other five terminals, initiate client programs through the command- " java -cp ".:mysql-connector.jar" -Djava.security.policy=policy MarketClient".

9. Credentials for login function- admin/AdPassword and customer/CustPassword. This is hardcoded in MarketModel file.