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
	private List<HomeDef> homeList = new ArrayList<HomeDef>();
	private List<ComponentDef> componentList = new ArrayList<ComponentDef>();
	private List<InterfaceDef> interfaceList = new ArrayList<InterfaceDef>();
	private List<ProvidesDef> providesList = new ArrayList<ProvidesDef>();
	private List<UsesDef> usesList = new ArrayList<UsesDef>();
	private List<ConstantDef> constantList = new ArrayList<ConstantDef>();
	
	private List<EnumDef> enumList = new ArrayList<EnumDef>();
	private List<StructDef> structList = new ArrayList<StructDef>();
	private List<ExceptionDef> exceptionList = new ArrayList<ExceptionDef>();
	private List<SequenceDef> sequenceList = new  ArrayList<SequenceDef>();
	private List<ArrayDef> arrayList = new  ArrayList<ArrayDef>();
	
	
	public void addHome(HomeDef home)
	{
		homeList.add(home);
	}
	
	public List<HomeDef> findAllHomes()
	{
		return homeList;
	}
	
	
	public void addComponent(ComponentDef component)
	{
		componentList.add(component);
	}

	public List<ComponentDef> findAllComponents()
	{
		return componentList;
	}
	
	
	public void addInterface(InterfaceDef iface)
	{
		interfaceList.add(iface);
	}

	public List<InterfaceDef> findAllInterfaces()
	{
		return interfaceList;
	}
		
	
	public void addProvides(ProvidesDef provides)
	{
		providesList.add(provides);
	}
	
	public List<ProvidesDef> findAllProvides()
	{
		return providesList;
	}
		
	
	public void addUses(UsesDef uses)
	{
		usesList.add(uses);
	}
	
	public List<UsesDef> findAllUses()
	{
		return usesList;
	}
	
		
	public void addGlobalConstant(ConstantDef value)
	{
		constantList.add(value);
	}

	public List<ConstantDef> findAllGlobalConstants()
	{
		return constantList;
	}
	
	
	public void addEnum(EnumDef value)
	{
		enumList.add(value);
	}

	public List<EnumDef> findAllEnums()
	{
		return enumList;
	}
	
	
	public void addStruct(StructDef value)
	{
		structList.add(value);
	}

	public List<StructDef> findAllStructs()
	{
		return structList;
	}
	
	
	public void addException(ExceptionDef value)
	{
		exceptionList.add(value);
	}

	public List<ExceptionDef> findAllExceptions()
	{
		return exceptionList;
	}
	
	
	public void addSequence(SequenceDef value)
	{
		sequenceList.add(value);
	}

	public List<SequenceDef> findAllSequences()
	{
		return sequenceList;
	}
	
	public void addArray(ArrayDef value)
	{
		arrayList.add(value);
	}

	public List<ArrayDef> findAllArrays()
	{
		return arrayList;
	}
}
