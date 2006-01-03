package ccm.local;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import Components.CCMHome;
import Components.CCMHomeHelper;

public class ServiceLocator
{
	private static ServiceLocator instance ;
	
	/** Store CORBA specific references */
	private ORB orb = null;
	
	/** Get an instance of this component proxy (singleton) */
	public static ServiceLocator instance()
	{
		if (instance == null)
		{
			instance = new ServiceLocator();
		}
		return instance;
	}

	/** Don't let clients use this constructor */
	protected ServiceLocator()
	{
	}

	public void setCorbaOrb(ORB orb)
	{
		this.orb = orb;
		
		// Start a thread that executes orb.run()
		Thread thread = new OrbRunThread(orb);
		thread.start();
	}
	
	public ORB getCorbaOrb()
	{
		return orb;
	}
	
	
	public NamingContextExt getCorbaNameService() 
		throws ServiceLocatorException
	{
		// Connect to the NameService
		org.omg.CORBA.Object nameObj;
		try
		{
			nameObj = orb.resolve_initial_references("NameService");
			NamingContextExt namingContext = NamingContextExtHelper.narrow(nameObj);
			return namingContext;
		}
		catch (InvalidName e)
		{
			e.printStackTrace();
			throw new ServiceLocatorException(e.getMessage());
		}
	}
	
	
	public CCMHome findCCMHome(String name) 
		throws ServiceLocatorException
	{
		try
		{
			org.omg.CORBA.Object homeObj = getCorbaNameService().resolve_str(name);
			return CCMHomeHelper.narrow(homeObj);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new ServiceLocatorException(e.getMessage());
		}
	}
	
	public org.omg.CORBA.Object findCorbaObject(String name) 
		throws ServiceLocatorException
	{
		try
		{
			org.omg.CORBA.Object obj = getCorbaNameService().resolve_str(name);
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new ServiceLocatorException(e.getMessage());
		}
}
}
