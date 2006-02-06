package ccmtools.generator.java.metamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * A model root element provides entry points to the Java implementation model
 * which can be used a startpoint for navigation.
 * 
 */
public class ModelRepository
	extends ModelElement
{
	private List homeList = new ArrayList();
	private List componentList = new ArrayList();
	private List interfaceList = new ArrayList();
	private List constantList = new ArrayList();
	
	public ModelRepository()
	{
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
	
	public void addGlobalConstant(ConstantDef constant)
	{
		constantList.add(constant);
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
	
	public List findAllGlobalConstants()
	{
		return constantList;
	}
}
