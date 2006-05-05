package ccm.local;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Policy;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.ImplicitActivationPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import Components.CCMHome;
import Components.CCMHomeHelper;

public class ServiceLocator
{
	/** There can be only one... */
	private static ServiceLocator instance ;
	
	/** Use the java Logger as default logging mechanism */
	private Logger logger = Logger.getLogger("ccm");
	
	/** Store CORBA specific references */
	private ORB orb = null;
	
	/** Map of created POAs */
	private Map poaMap = new HashMap();
		
	/** Thread which calls orb.run() */
	private Thread orbRunThread = null;
	
	
	
	/** Get an instance of this component proxy (singleton) */
	public static ServiceLocator instance()
	{
		if (instance == null)
		{
			instance = new ServiceLocator();
		}
		return instance;
	}

	/**
	 * Interrupt threads, close connections and free resources.
	 *
	 */
	public void destroy()
	{
		orb = null;
		instance = null;
		logger = null;
		poaMap.clear();
	}
	
	/** Don't let clients use this constructor */
	protected ServiceLocator()
	{		
	}

	
	/*************************************************************************
	 * Default Logger Settings and Helper Methods
	 * 
	 *************************************************************************/
	
	public Logger getLogger()
	{
		return logger;
	}
	
	public void setLogger(Logger logger)
	{
		this.logger = logger;
	}
	
	public void setLogLevel(Level level)
	{
		logger.setLevel(level);
	}
	
	
	/*************************************************************************
	 * CORBA Settings and Helper Methods 
	 * 
	 *************************************************************************/
	
	public void setCorbaOrb(ORB orb)
	{
		this.orb = orb;
		
		// Start a thread that executes orb.run()
		orbRunThread = new OrbRunThread(orb);
		
		// Deamon threads terminate when the application is finished 
		orbRunThread.setDaemon(true);
		orbRunThread.start();
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
			//e.printStackTrace();
			throw new ServiceLocatorException(e.getMessage());
		}
	}

	
	/**
	 * Create a POA instance that supports all policies used for session
	 * CORBA components.
	 * Note that POA instance names has to be unique for a given ORB, thus
	 * we use a map to store all created POAs.
	 * 
	 * @param name
	 * @return
	 * @throws ServiceLocatorException
	 */
	public POA createSessionComponentPoa(String name)
		throws ServiceLocatorException
	{
		try
		{
			if (poaMap.containsKey(name))
			{
				return (POA) poaMap.get(name);
			}
			else
			{
				POA rootPoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
				rootPoa.the_POAManager().activate();
				Policy[] policies = new Policy[1];
				policies[0] = rootPoa.create_implicit_activation_policy(
						ImplicitActivationPolicyValue.IMPLICIT_ACTIVATION);
				POA sessionPoa = rootPoa.create_POA(name, rootPoa.the_POAManager(), policies);
				sessionPoa.the_POAManager().activate();
				poaMap.put(name, sessionPoa);
				return sessionPoa;
			}
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			throw new ServiceLocatorException(e.getMessage());
		}
	}
}
