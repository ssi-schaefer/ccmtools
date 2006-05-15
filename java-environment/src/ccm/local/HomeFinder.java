package ccm.local;

import java.util.HashMap;
import java.util.Map;

import Components.ccm.local.CCMHome;
import Components.ccm.local.HomeNotFound;


public class HomeFinder
	implements Components.ccm.local.HomeFinder
{
	protected static HomeFinder instance = null;
	
	protected Map homeMap = new HashMap();
	
	public static HomeFinder instance()
	{
		if(instance == null) {
			instance = new HomeFinder();
		}
		return instance;
	}
	
	
	protected HomeFinder()
	{
	}

	public CCMHome find_home_by_component_type(String repid) 
		throws HomeNotFound
	{
		// TODO
		throw new RuntimeException("NotImplemented");
	}

	public CCMHome find_home_by_name(String name) 
		throws HomeNotFound
	{
		if(homeMap.containsKey(name)) 
		{
			return (CCMHome) homeMap.get(name);
		}
		else 
		{
			throw new HomeNotFound();
		}
	}

	public CCMHome find_home_by_type(String repid) 
		throws HomeNotFound
	{
		// TODO
	        throw new RuntimeException("NotImplemented");
	}

	public void register_home(CCMHome home, String name)
	{
		if(home != null) 
		{
			homeMap.put(name, home);
		}
	}

	public void unregister_home(CCMHome home)
	{
		// TODO: find key for value and call unregister_home(String name)
	        throw new RuntimeException("NotImplemented");
	}

	public void unregister_home(String name)
	{
		if(homeMap.containsKey(name)) 
		{
			homeMap.remove(name);
		}
	}
}
