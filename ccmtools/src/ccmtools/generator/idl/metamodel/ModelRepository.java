package ccmtools.generator.idl.metamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * A model root element provides entry points to the IDL implementation model
 * which can be used a startpoint for navigation.
 * 
 */
public class ModelRepository
	extends ModelElement
{
	private List<TypedefDef> typedefList = new ArrayList<TypedefDef>();
	private List<EnumDef> enumList = new ArrayList<EnumDef>();
	private List<StructDef> structList = new ArrayList<StructDef>();
	private List<ConstantDef> constantList = new ArrayList<ConstantDef>();
	private List<ExceptionDef> exceptionList = new ArrayList<ExceptionDef>();
	private List<InterfaceDef> interfaceList = new ArrayList<InterfaceDef>();
		
//	private List homeList = new ArrayList();
//	private List componentList = new ArrayList();
//	private List providesList = new ArrayList();
//	private List usesList = new ArrayList();
	
	public ModelRepository()
	{
	}

	public void addTypedef(TypedefDef value)
	{
		typedefList.add(value);
	}
	public List<TypedefDef> findAllTypedefs()
	{
		return typedefList;
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
	
	public void addGlobalConstant(ConstantDef value)
	{
		constantList.add(value);
	}
	public List<ConstantDef> findAllGlobalConstants()
	{
		return constantList;
	}
	
	public void addException(ExceptionDef value)
	{
		exceptionList.add(value);
	}
	public List<ExceptionDef> findAllExceptions()
	{
		return exceptionList;
	}

	public void addInterface(InterfaceDef iface)
	{
		interfaceList.add(iface);
	}
	public List<InterfaceDef> findAllInterfaces()
	{
		return interfaceList;
	}

	
//	public void addHome(HomeDef home)
//	{
//		homeList.add(home);
//	}
//	
//	public List findAllHomes()
//	{
//		return homeList;
//	}
//	
//	
//	public void addComponent(ComponentDef component)
//	{
//		componentList.add(component);
//	}
//
//	public List findAllComponents()
//	{
//		return componentList;
//	}
//	
//	
//		
//	
//	public void addProvides(ProvidesDef provides)
//	{
//		providesList.add(provides);
//	}
//	
//	public List findAllProvides()
//	{
//		return providesList;
//	}
//		
//	
//	public void addUses(UsesDef uses)
//	{
//		usesList.add(uses);
//	}
//	
//	public List findAllUses()
//	{
//		return usesList;
//	}
	
	
	
//	
//	
//	public void addSequence(SequenceDef value)
//	{
//		sequenceList.add(value);
//	}
//
//	public List findAllSequences()
//	{
//		return sequenceList;
//	}
//	
//	public void addArray(ArrayDef value)
//	{
//		arrayList.add(value);
//	}
//
//	public List findAllArrays()
//	{
//		return arrayList;
//	}
}
