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
	private List providesList = new ArrayList();
	private List usesList = new ArrayList();
	private List constantList = new ArrayList();
	
	private List structList = new ArrayList();
	private List sequenceList = new  ArrayList();
	
	
	public ModelRepository()
	{
	}

	public void addHome(HomeDef home)
	{
		homeList.add(home);
	}
	
	public List findAllHomes()
	{
		return homeList;
	}
	
	
	public void addComponent(ComponentDef component)
	{
		componentList.add(component);
	}

	public List findAllComponents()
	{
		return componentList;
	}
	
	
	public void addInterface(InterfaceDef iface)
	{
		interfaceList.add(iface);
	}

	public List findAllInterfaces()
	{
		return interfaceList;
	}
		
	
	public void addProvides(ProvidesDef provides)
	{
		providesList.add(provides);
	}
	
	public List findAllProvides()
	{
		return providesList;
	}
		
	
	public void addUses(UsesDef uses)
	{
		usesList.add(uses);
	}
	
	public List findAllUses()
	{
		return usesList;
	}
	
		
	public void addGlobalConstant(ConstantDef value)
	{
		constantList.add(value);
	}

	public List findAllGlobalConstants()
	{
		return constantList;
	}
	
	public void addStruct(StructDef value)
	{
		structList.add(value);
	}

	public List findAllStructs()
	{
		return structList;
	}
	
	
	public void addSequence(SequenceDef value)
	{
		sequenceList.add(value);
	}

	public List findAllSequences()
	{
		return sequenceList;
	}
	
}
