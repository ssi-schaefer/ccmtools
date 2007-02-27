package Components;

import java.util.HashMap;
import java.util.Map;

/***
 * Clients can use the HomeFinder interface to obtain homes for particular
 * component types, of particularly homes, or homes that are bound to
 * specific names in a naming service.
 * CCM Spec. 1-42
 ***/
public class HomeFinder
	implements HomeRegistration
{
    protected static HomeFinder instance = null;
    
    protected Map<String, CCMHome> homeMap = new HashMap<String, CCMHome>();
    
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
        throw new RuntimeException("NotImplemented");
    }
    
    public CCMHome find_home_by_name(String name) 
        throws HomeNotFound
    {
        if(homeMap.containsKey(name)) 
        {
            return homeMap.get(name);
        }
        else 
        {
            throw new HomeNotFound(name);
        }
    }
    
    public CCMHome find_home_by_type(String repid) 
        throws HomeNotFound
    {
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
