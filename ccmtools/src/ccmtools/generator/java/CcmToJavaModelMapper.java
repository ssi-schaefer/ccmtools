package ccmtools.generator.java;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import ccmtools.CodeGenerator.NodeHandler;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MAttributeDef;
import ccmtools.Metamodel.BaseIDL.MConstantDef;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MEnumDef;
import ccmtools.Metamodel.BaseIDL.MExceptionDef;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MOperationDef;
import ccmtools.Metamodel.BaseIDL.MParameterDef;
import ccmtools.Metamodel.BaseIDL.MParameterMode;
import ccmtools.Metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.Metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.Metamodel.BaseIDL.MSequenceDef;
import ccmtools.Metamodel.BaseIDL.MStringDef;
import ccmtools.Metamodel.BaseIDL.MStructDef;
import ccmtools.Metamodel.BaseIDL.MTyped;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;
import ccmtools.Metamodel.ComponentIDL.MProvidesDef;
import ccmtools.Metamodel.ComponentIDL.MSupportsDef;
import ccmtools.Metamodel.ComponentIDL.MUsesDef;
import ccmtools.generator.java.metamodel.AnyType;
import ccmtools.generator.java.metamodel.AttributeDef;
import ccmtools.generator.java.metamodel.BooleanType;
import ccmtools.generator.java.metamodel.ByteType;
import ccmtools.generator.java.metamodel.CharType;
import ccmtools.generator.java.metamodel.ComponentDef;
import ccmtools.generator.java.metamodel.ConstantDef;
import ccmtools.generator.java.metamodel.DoubleType;
import ccmtools.generator.java.metamodel.EnumDef;
import ccmtools.generator.java.metamodel.ExceptionDef;
import ccmtools.generator.java.metamodel.FixedType;
import ccmtools.generator.java.metamodel.FloatType;
import ccmtools.generator.java.metamodel.HomeDef;
import ccmtools.generator.java.metamodel.IntegerType;
import ccmtools.generator.java.metamodel.InterfaceDef;
import ccmtools.generator.java.metamodel.LongType;
import ccmtools.generator.java.metamodel.ModelRepository;
import ccmtools.generator.java.metamodel.OperationDef;
import ccmtools.generator.java.metamodel.ParameterDef;
import ccmtools.generator.java.metamodel.PassingDirection;
import ccmtools.generator.java.metamodel.ProvidesDef;
import ccmtools.generator.java.metamodel.SequenceDef;
import ccmtools.generator.java.metamodel.ShortType;
import ccmtools.generator.java.metamodel.StringType;
import ccmtools.generator.java.metamodel.StructDef;
import ccmtools.generator.java.metamodel.SupportsDef;
import ccmtools.generator.java.metamodel.Type;
import ccmtools.generator.java.metamodel.UsesDef;
import ccmtools.generator.java.metamodel.VoidType;
import ccmtools.utils.Code;


public class CcmToJavaModelMapper
    implements NodeHandler
{
	/** This map is used to cache source code artifacts like interfaces, exceptions, etc. */
	private Map artifactCache;
		
    /** Java logging */
    private Logger logger;
    
    /** Root element of the Java Implementation Model */
    private ModelRepository modelRepository;
    
    private AnyPluginManager anyPluginManager;
    
    
	CcmToJavaModelMapper()
	{
		logger = Logger.getLogger("ccm.generator.java.clientlib");
		logger.fine("CcmModelNodeHandler()");
		
		artifactCache = new HashMap();
		modelRepository = new ModelRepository();
		anyPluginManager = new AnyPluginManager();
	}
			
	public ModelRepository getJavaModel()
	{
		return modelRepository;
	}
	
	
    /*
     * Callback methods for the CCMGraphTraverser 
     */ 
    
    public void startGraph()
    {
    	logger.fine("startGraph()");
    }

    public void endGraph()
    {
    	logger.fine("endGraph()");
    }

    public void startNode(Object node, String scopeId)
    {
    	logger.fine("startNode(" + node +")");
    }

    public void endNode(Object node, String scopeId)
    {                
    	logger.fine("endNode(" + node + ")");
    	if(node == null)
    	{
    		// The current node is not valid!
    		return;
    	}
    	else if(node instanceof MContained 
    			&& !((MContained) node).getSourceFile().equals(""))
    	{
    		// The current node is defined in an included file
    		// and should not be generated!
    		return;
    	}
    	else if (node instanceof MHomeDef)
		{
			MHomeDef home = (MHomeDef) node;
			logger.finer("MHomeDef: " + Code.getRepositoryId(home));
			HomeDef javaHome = transform(home);
			modelRepository.addHome(javaHome);
		}
    	else if(node instanceof MComponentDef) 
    	{
    		MComponentDef component = (MComponentDef)node;
    		logger.finer("MComponentDef: " + Code.getRepositoryId(component));
    		ComponentDef javaComponent = transform(component);
    		modelRepository.addComponent(javaComponent);
    	}
    	else if(node instanceof MInterfaceDef)
    	{
    		MInterfaceDef iface = (MInterfaceDef)node;
    		logger.finer("MInterfaceDef: " + Code.getRepositoryId(iface));
    		InterfaceDef javaIface = transform(iface);   		
    		modelRepository.addInterface(javaIface);
    	}   
    	else if(node instanceof MProvidesDef)
    	{
    		MProvidesDef provides = (MProvidesDef)node;
    		logger.finer("MProvidesDef: " + Code.getRepositoryId(provides));
    		ProvidesDef javaProvides = transform(provides);   		
    		modelRepository.addProvides(javaProvides);
    	}
    	else if(node instanceof MUsesDef)
    	{
    		MUsesDef uses = (MUsesDef)node;
    		logger.finer("MUsesDef: " + Code.getRepositoryId(uses));
    		UsesDef javaUses = transform(uses);
    		modelRepository.addUses(javaUses);
    	}
    }

    public void handleNodeData(String fieldType, String fieldId, Object value)
    {
        logger.finest("handleNodeData(" + fieldType + ", " + fieldId + ")");        
    }
    
    
    /*
     * Model mapper methods
     */ 
    
    //  Handle container elements ----------------------------------------------
    		
	public HomeDef transform(MHomeDef in)
	{		
		HomeDef out;
		String repoId = Code.getRepositoryId(in);
		logger.finer("MHomeDef: " + repoId);
		if (artifactCache.containsKey(repoId))
		{
			out = (HomeDef) artifactCache.get(repoId);
		}
		else
		{
			out = new HomeDef(in.getIdentifier(), Code.getNamespaceList(in));
			out.setComponent(transform(in.getComponent()));
			artifactCache.put(repoId, out);
		}
		return out;
	}
	
	
	public ComponentDef transform(MComponentDef in)
	{
		ComponentDef out;
		String repoId = Code.getRepositoryId(in);
		logger.finer("MComponentDef: " + repoId);
		if (artifactCache.containsKey(repoId))
		{
			out = (ComponentDef) artifactCache.get(repoId);
		}
		else
		{
			out = new ComponentDef(in.getIdentifier(), Code.getNamespaceList(in));
			for (Iterator i = in.getContentss().iterator(); i.hasNext();)
			{
				MContained child = (MContained) i.next();
				if (child instanceof MAttributeDef)
				{
					out.getAttributes().add(transform((MAttributeDef)child));
				}
			}
			// Transform supported interfaces
			for(Iterator i = in.getSupportss().iterator(); i.hasNext(); )
			{
				out.getSupports().add(transform((MSupportsDef)i.next())); 
			}
			artifactCache.put(repoId, out);
		}
		return out;
	}
	
	
	InterfaceDef transform(MInterfaceDef in)
	{
		InterfaceDef out;
		String repoId = Code.getRepositoryId(in);
		logger.finer("MInterfaceDef: " + repoId);
		if (artifactCache.containsKey(repoId))
		{
			out = (InterfaceDef) artifactCache.get(repoId);
		}
		else
		{
			out = new InterfaceDef(in.getIdentifier(), Code.getNamespaceList(in));
	
			// Transform base interface types
			for(Iterator i = in.getBases().iterator(); i.hasNext(); )
			{
				MInterfaceDef baseIface = (MInterfaceDef)i.next();
				out.getBaseInterfaces().add(transform(baseIface));
			}
			
			// Transform interface sub elements like constants, attributes and operations
			for (Iterator i = in.getContentss().iterator(); i.hasNext();)
			{
				MContained child = (MContained) i.next();
				if (child instanceof MConstantDef)
				{
					out.getConstants().add(transform((MConstantDef)child));
				}
				else if (child instanceof MAttributeDef)
				{
					out.getAttributes().add(transform((MAttributeDef)child));
				}
				else if (child instanceof MOperationDef)
				{
					out.getOperations().add(transform((MOperationDef)child));
				}
			}
			artifactCache.put(repoId, out);
		}
		return out;
	}
	
	
	// Handle Contained elements ----------------------------------------------
	
	public ProvidesDef transform(MProvidesDef in)
	{
		ProvidesDef out;
		String repoId = Code.getRepositoryId(in);
		logger.finer("MProvidesDef: " + repoId);		
		if(artifactCache.containsKey(repoId))
		{
			out = (ProvidesDef)artifactCache.get(repoId);	
		}
		else 
		{
			out = new ProvidesDef(in.getIdentifier(), Code.getNamespaceList(in));
			out.setInterface(transform(in.getProvides()));
			// Establish a bidirectional connection between ComponentDef and ProvidesDef
			ComponentDef component = transform(in.getComponent());
			component.getFacet().add(out);
			out.setComponent(component); 			
			artifactCache.put(repoId, out);
		}
		return out;
	}
	
	
	public UsesDef transform(MUsesDef in)
	{
		UsesDef out;
		String repoId = Code.getRepositoryId(in);
		logger.finer("MUsesDef: " + repoId);
		if(artifactCache.containsKey(repoId))
		{
			out = (UsesDef)artifactCache.get(repoId);
		}
		else
		{
			out = new UsesDef(in.getIdentifier(), Code.getNamespaceList(in));
			out.setMultiple(in.isMultiple());
			out.setInterface(transform(in.getUses()));
			// Establish a bidirectional connection between ComponentDef and UsesDef
			ComponentDef component = transform(in.getComponent());
			component.getReceptacle().add(out);
			out.setComponent(component);
			artifactCache.put(repoId, out);
		}
		return out;
	}
	
	
	public SupportsDef transform(MSupportsDef in)
	{
		logger.finer("MSupportsDef: " + in.getIdentifier());	
		SupportsDef out = new SupportsDef(in.getIdentifier(), Code.getNamespaceList(in));
		out.setInterface(transform(in.getSupports()));
		return out;
	}

	
	public OperationDef transform(MOperationDef in)
	{
		logger.finer("MOperationDef: " + in.getIdentifier());
		OperationDef out = new OperationDef(in.getIdentifier(), transform(in.getIdlType()));
		for(Iterator i = in.getParameters().iterator(); i.hasNext(); )
		{
			MParameterDef parameter = (MParameterDef)i.next();
			out.getParameter().add(transform(parameter));
		}
		for(Iterator i = in.getExceptionDefs().iterator(); i.hasNext(); )
		{
			MExceptionDef exc = (MExceptionDef)i.next();
			out.getException().add(transform(exc));
		}
		return out;
	}

	
	public ExceptionDef transform(MExceptionDef in)
	{
		ExceptionDef out;
		String repoId = Code.getRepositoryId(in);
		logger.finer("MExceptionDef: " + repoId);
		if (artifactCache.containsKey(repoId))
		{
			out = (ExceptionDef) artifactCache.get(repoId);
		}
		else 
		{
			out = new ExceptionDef(in.getIdentifier(), Code.getNamespaceList(in));
			artifactCache.put(repoId, out);
		}
		return out;
	}


	public AttributeDef transform(MAttributeDef in)
	{
		logger.finer("MAttributeDef: " + in.getIdentifier());
		AttributeDef out = new AttributeDef(in.getIdentifier(), 
											transform(in.getIdlType()),
											in.isReadonly());
		return out;
	}
	
	
	public ParameterDef transform(MParameterDef in)
	{
		logger.finer("MParameterDef: " + in.getIdentifier());
		ParameterDef out = new ParameterDef(in.getIdentifier(), 
											transform(in.getDirection()), 
											transform(in.getIdlType()));		
		return out;
	}
		
	public PassingDirection transform(MParameterMode in)
	{
		logger.finer("MParameterMode: ");
		if(in == MParameterMode.PARAM_IN)
		{
			return PassingDirection.IN;
		}
		else if(in == MParameterMode.PARAM_INOUT)
		{
			return PassingDirection.INOUT;
		}
		else if(in == MParameterMode.PARAM_OUT)
		{
			return PassingDirection.OUT;
		}
		else
		{
			throw new RuntimeException("transform(MParameterMode): unknown mode!");
		}
	}

	
	public StructDef transform(MStructDef in)
	{		
		StructDef out = new StructDef(in.getIdentifier(), Code.getNamespaceList(in));
		// As long as we don't map parameters from CORBA to Java, we don't 
		// have to set a structure's members.
		return out;
	}
	
	public EnumDef transform(MEnumDef in)
	{		
		EnumDef out = new EnumDef(in.getIdentifier(), Code.getNamespaceList(in));
		// As long as we don't map parameters from CORBA to Java, we don't 
		// have to set a enum's members.
		return out;
	}
	
	public ConstantDef transform(MConstantDef in)
	{
		MTyped type = (MTyped)in;
		MIDLType idlType = type.getIdlType();
		ConstantDef out = new ConstantDef(in.getIdentifier(), transform(idlType), in.getConstValue());
		return out;
	}

	
	public Type transform(MIDLType in)
	{
		logger.finer("MIDLType: ");
		if(in instanceof MPrimitiveDef)
		{
			return transform((MPrimitiveDef)in);
		}
		else if(in instanceof MStringDef)
		{
			return new StringType();
		}
		else if(in instanceof MStructDef)
		{			
			return transform((MStructDef)in);
		}
		else if(in instanceof MEnumDef)
		{			
			return transform((MEnumDef)in);
		}
		else if(in instanceof MAliasDef)
		{
			MAliasDef alias = (MAliasDef)in;
			MTyped typed = (MTyped)alias;
			MIDLType innerIdlType = typed.getIdlType();			
			if(innerIdlType instanceof MPrimitiveDef)
			{
				MPrimitiveDef primitive = (MPrimitiveDef)innerIdlType;
				if(primitive.getKind() == MPrimitiveKind.PK_ANY)
				{
					return anyPluginManager.load(alias.getIdentifier());
				}
				else
				{
					return transform(primitive);
				}
			}
			else if(innerIdlType instanceof MSequenceDef)
			{
				MSequenceDef seq = (MSequenceDef)innerIdlType;
				MTyped seqType = (MTyped)seq;
				MIDLType seqIdl = seqType.getIdlType();						
				return new SequenceDef(alias.getIdentifier(), Code.getNamespaceList(alias), transform(seqIdl));
			}			
			// TODO: Handle other alias types
			else
			{
				throw new RuntimeException("transform(MIDLType): unknown alias type " + in);
			}
		}
		else
		{
			throw new RuntimeException("transform(MIDLType): unknown idl type " + in);
		}
	}
	

	public Type transform(MPrimitiveDef primitive)	
	{
		logger.finer("MPrimitiveDef: ");
		if(primitive.getKind() == MPrimitiveKind.PK_VOID)
		{
			return new VoidType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_BOOLEAN)
		{
			return new BooleanType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_CHAR
				|| primitive.getKind() == MPrimitiveKind.PK_WCHAR)
		{
			return new CharType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_STRING
				|| primitive.getKind() == MPrimitiveKind.PK_WSTRING)
		{
			return new StringType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_OCTET)
		{
			return new ByteType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_SHORT
				|| primitive.getKind() == MPrimitiveKind.PK_USHORT)
		{
			return new ShortType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_LONG
				|| primitive.getKind() == MPrimitiveKind.PK_ULONG)
		{
			return new IntegerType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_LONGLONG
				|| primitive.getKind() == MPrimitiveKind.PK_ULONGLONG)
		{
			return new LongType();			
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_FLOAT)
		{
			return new FloatType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_DOUBLE)
		{
			return new DoubleType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_FIXED)
		{
			return new FixedType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_ANY)
		{
			return new AnyType();
		}
		else
		{
			throw new RuntimeException("transform(MPrimitiveDef): unknown primitive type "
					+ primitive.getKind());
		}
	}
}
