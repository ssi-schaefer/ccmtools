package ccmtools.generator.idl.metamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * A model root element provides entry points to the IDL implementation model
 * which can be used a startpoint for navigation.
 * 
 */
public class ModelRepository
//	extends ModelElement
{
	private List<TypedefDef> typedefList = new ArrayList<TypedefDef>();
	private List<EnumDef> enumList = new ArrayList<EnumDef>();
	private List<StructDef> structList = new ArrayList<StructDef>();
	private List<ConstantDef> constantList = new ArrayList<ConstantDef>();
	private List<ExceptionDef> exceptionList = new ArrayList<ExceptionDef>();
	private List<InterfaceDef> interfaceList = new ArrayList<InterfaceDef>();
	private List<ComponentDef> componentList = new ArrayList<ComponentDef>();
	private List<HomeDef> homeList = new ArrayList<HomeDef>();

	public void addTypedef(TypedefDef value)
	{
        if(!typedefList.contains(value))
            typedefList.add(value);
	}
	public List<TypedefDef> findAllTypedefs()
	{
		return typedefList;
	}
		
	public void addEnum(EnumDef value)
	{
        if(!enumList.contains(value))
            enumList.add(value);
	}
	public List<EnumDef> findAllEnums()
	{
		return enumList;
	}
		
	public void addStruct(StructDef value)
	{
        if(!structList.contains(value))
            structList.add(value);
	}
	public List<StructDef> findAllStructs()
	{
		return structList;
	}
	
	public void addGlobalConstant(ConstantDef value)
	{
        if(!constantList.contains(value))
            constantList.add(value);
	}
	public List<ConstantDef> findAllGlobalConstants()
	{
		return constantList;
	}
	
	public void addException(ExceptionDef value)
	{
        if(!exceptionList.contains(value))
            exceptionList.add(value);
	}
	public List<ExceptionDef> findAllExceptions()
	{
		return exceptionList;
	}

	public void addInterface(InterfaceDef value)
	{
        if(!interfaceList.contains(value)) 
            interfaceList.add(value);
	}
	public List<InterfaceDef> findAllInterfaces()
	{
		return interfaceList;
	}

	public void addHome(HomeDef value)
	{
        if(!homeList.contains(value))
            homeList.add(value);   
	}
	public List<HomeDef> findAllHomes()
	{
		return homeList;
	}
		
	public void addComponent(ComponentDef value)
	{
        if(!componentList.contains(value))
            componentList.add(value);
	}
		public List<ComponentDef> findAllComponents()
	{
		return componentList;
	}
}
