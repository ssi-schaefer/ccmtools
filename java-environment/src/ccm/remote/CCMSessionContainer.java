package ccm.remote;

import java.util.logging.Logger;

import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongAdapter;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import Components.CCMHome;
import Components.CCMHomeHelper;
import Components.CCMObject;
import Components.CCMObjectHelper;
import Components.ccm.local.CCMException;
import Components.ccm.local.CCMExceptionReason;
import ccm.local.ServiceLocator;
import ccm.local.ServiceLocatorException;

public class CCMSessionContainer
{
	private static long nextContainerNumber = 0;		

	private Logger logger = ServiceLocator.instance().getLogger();	
	private String containerId; 	
	private POA sessionPoa; 	
	private CCMHome home;
	
	
	public CCMSessionContainer() 
	{
		this("");
	}
	
	public CCMSessionContainer(String name) 
	{
		containerId = "CCMSessionComponentPOA." + name + ++nextContainerNumber;
		logger.fine("containerId = " + containerId);
	}
	
	
	public void load(Servant homeServant)
		throws CCMException 
	{
		logger.fine("home servant = " + homeServant);
		try
		{
			sessionPoa = ServiceLocator.instance().createSessionComponentPoa(containerId);
			// There is only one home instance per container instance.
			home = getCorbaHomeFromServant(homeServant);
		}
		catch (ServiceLocatorException e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
	}

	
	public void activate()
	{
		throw new RuntimeException("Not implemented!");
	}

	public void passivate()
	{
		throw new RuntimeException("Not implemented!");
	}
	
	public void remove()
	{
		throw new RuntimeException("Not implemented!");
	}
	
	
	
	public CCMHome getCorbaHome()
	{
		logger.fine("");
		return home;
	}

	public String getCorbaHomeIor()
	{
		logger.fine("");
		return ServiceLocator.instance().getCorbaOrb().object_to_string(getCorbaHome());
	}

	public void registerCorbaHome(String name)
		throws CCMException		
	{
		logger.fine("name = " + name);
		try
		{
			NamingContextExt ns = ServiceLocator.instance().getCorbaNameService();
			ns.rebind(ns.to_name(name), getCorbaHome());
		}
		catch (NotFound e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
		catch (CannotProceed e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
		catch (InvalidName e)
		{
			e.printStackTrace();			
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
		catch (ServiceLocatorException e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
	}
	
	public void unregisterCorbaHome(String name)
		throws CCMException	
	{
		logger.fine("name = " + name);
		try	
		{
			NamingContextExt ns = ServiceLocator.instance().getCorbaNameService();
			ns.unbind(ns.to_name(name));
		}
		catch (NotFound e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
		catch (CannotProceed e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
		catch (InvalidName e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
		catch (ServiceLocatorException e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
	}
	
	/**
	 * Establish a home CORBA object from the given servant instance.
	 * 
	 * @param servant
	 * @return
	 * @throws CCMException
	 */
	public CCMHome getCorbaHomeFromServant(Servant servant)
		throws CCMException	
	{
		logger.fine("servant = " + servant);
		try
		{
			home = CCMHomeHelper.narrow(sessionPoa.servant_to_reference(servant));
			return home;
		}
		catch (ServantNotActive e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);

		}
		catch (WrongPolicy e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
	}
	
	/**
	 * Establish a component CORBA object from the given servant instance.
	 * 
	 * @param servant
	 * @return
	 * @throws CCMException
	 */
	public CCMObject getCorbaComponentFromServant(Servant servant)
		throws CCMException
	{
		logger.fine("servant = " + servant);
		try
		{
			return CCMObjectHelper.narrow(sessionPoa.servant_to_reference(servant));
		}
		catch (ServantNotActive e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
		catch (WrongPolicy e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
	}
	
	/**
	 * Establish a CORBA object from the given servant instance.
	 * 
	 * @param servant
	 * @return
	 * @throws CCMException
	 */
	public org.omg.CORBA.Object getCorbaObjectFromServant(Servant servant) 
		throws CCMException
	{
		logger.fine("servant = " + servant);
		try
		{
			return sessionPoa.servant_to_reference(servant);
		}
		catch (ServantNotActive e)
		{		
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
		catch (WrongPolicy e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
	}
	
	/**
	 * Extracts the servant from a given CORBA object.
	 * 
	 * @param o
	 * @return
	 * @throws CCMException
	 */
	public Servant getServantFromCorbaObject(org.omg.CORBA.Object o)
		throws CCMException
	{
		logger.fine("object = " + o);
		try
		{
			return sessionPoa.reference_to_servant(o);
		}
		catch (ObjectNotActive e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
		catch (WrongPolicy e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
		catch (WrongAdapter e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);
		}
	}
}
