package ccmtools.generator.java.clientlib.metamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * A model root element provides entry points to the Java implementation model
 * which can be used a startpoint for navigation.
 * 
 */
public class ModelRoot
	extends ModelElement
{
	private List homeList;
	private List componentList;
	private List interfaceList;
	
	public ModelRoot()
	{
		homeList = new ArrayList();
		componentList = new ArrayList();
		interfaceList = new ArrayList();
	}

	public void addHome(HomeDef home)
	{
		homeList.add(home);
	}

	public void addComponent(ComponentDef component)
	{
		componentList.add(component);
	}

	public void addInterface(InterfaceDef iface)
	{
		interfaceList.add(iface);
	}
	
	
	public List findAllHomes()
	{
		return homeList;
	}
	
	public List findAllComponents()
	{
		return componentList;
	}
	
	public List findAllInterfaces()
	{
		return interfaceList;
	}
}
