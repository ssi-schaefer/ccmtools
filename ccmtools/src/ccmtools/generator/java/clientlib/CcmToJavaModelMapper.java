package ccmtools.generator.java.clientlib;

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
import ccmtools.Metamodel.ComponentIDL.MUsesDef;
import ccmtools.generator.java.clientlib.metamodel.BooleanType;
import ccmtools.generator.java.clientlib.metamodel.ByteType;
import ccmtools.generator.java.clientlib.metamodel.CharType;
import ccmtools.generator.java.clientlib.metamodel.ComponentDef;
import ccmtools.generator.java.clientlib.metamodel.DoubleType;
import ccmtools.generator.java.clientlib.metamodel.ExceptionDef;
import ccmtools.generator.java.clientlib.metamodel.FixedType;
import ccmtools.generator.java.clientlib.metamodel.FloatType;
import ccmtools.generator.java.clientlib.metamodel.HomeDef;
import ccmtools.generator.java.clientlib.metamodel.IntegerType;
import ccmtools.generator.java.clientlib.metamodel.InterfaceDef;
import ccmtools.generator.java.clientlib.metamodel.LongType;
import ccmtools.generator.java.clientlib.metamodel.ModelRoot;
import ccmtools.generator.java.clientlib.metamodel.OperationDef;
import ccmtools.generator.java.clientlib.metamodel.ParameterDef;
import ccmtools.generator.java.clientlib.metamodel.PassingDirection;
import ccmtools.generator.java.clientlib.metamodel.ProvidesDef;
import ccmtools.generator.java.clientlib.metamodel.SequenceDef;
import ccmtools.generator.java.clientlib.metamodel.ShortType;
import ccmtools.generator.java.clientlib.metamodel.StringType;
import ccmtools.generator.java.clientlib.metamodel.StructDef;
import ccmtools.generator.java.clientlib.metamodel.Type;
import ccmtools.generator.java.clientlib.metamodel.UsesDef;
import ccmtools.generator.java.clientlib.metamodel.VoidType;
import ccmtools.utils.Code;


public class CcmToJavaModelMapper
    implements NodeHandler
{
	/** This map is used to cache source code artifacts like interfaces, exceptions, etc. */
	private Map artifactCache;
		
    /** Java logging */
    private Logger logger;
    
    /** Root element of the Java Implementation Model */
    private ModelRoot model;
    
    
	CcmToJavaModelMapper()
	{
		logger = Logger.getLogger("ccm.generator.java.clientlib");
		logger.fine("CcmModelNodeHandler()");
		
		artifactCache = new HashMap();
		model = new ModelRoot();
	}
			
	public ModelRoot getJavaModel()
	{
		return model;
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
    		return;
    	}
    	else if(node instanceof MHomeDef) 
    	{
    		MHomeDef home = (MHomeDef)node;
    		logger.finer("MHomeDef: " + Code.getRepositoryId(home));
    		HomeDef javaHome = transform(home);
    		model.addHome(javaHome);
    	}
    	else if(node instanceof MComponentDef) 
    	{
    		MComponentDef component = (MComponentDef)node;
    		logger.finer("MComponentDef: " + Code.getRepositoryId(component));
    		ComponentDef javaComponent = transform(component);
    		model.addComponent(javaComponent);
    	}
    	else if(node instanceof MInterfaceDef)
    	{
    		MInterfaceDef iface = (MInterfaceDef)node;
    		logger.finer("MInterfaceDef: " + Code.getRepositoryId(iface));
    		InterfaceDef javaIface = transform(iface);   		
    		model.addInterface(javaIface);
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
			for (Iterator i = in.getContentss().iterator(); i.hasNext();)
			{
				MContained child = (MContained) i.next();
				if (child instanceof MConstantDef)
				{

				}
				else if (child instanceof MAttributeDef)
				{

				}
				else if (child instanceof MOperationDef)
				{
					out.getOperation().add(transform((MOperationDef) child));
				}
			}
			artifactCache.put(repoId, out);
		}
		return out;
	}
		
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
			for(Iterator i = in.getFacets().iterator(); i.hasNext(); )
			{
				out.getFacet().add(transform((MProvidesDef)i.next())); 
			}
			for(Iterator i = in.getReceptacles().iterator(); i.hasNext(); )
			{
				out.getReceptacle().add(transform((MUsesDef)i.next())); 
			}
			artifactCache.put(repoId, out);
		}
		return out;
	}
	
	
	// Handle Contained elements ----------------------------------------------

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
	
	
	public ProvidesDef transform(MProvidesDef in)
	{
		logger.finer("MProvidesDef: " + in.getIdentifier());		
		ProvidesDef out = new ProvidesDef(in.getIdentifier(), Code.getNamespaceList(in));
		out.setInterface(transform(in.getProvides()));
		return out;
	}
	
	
	public UsesDef transform(MUsesDef in)
	{
		logger.finer("MUsesDef: " + in.getIdentifier());
		UsesDef out = new UsesDef(in.getIdentifier(), Code.getNamespaceList(in));
		out.setInterface(transform(in.getUses()));
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
			if(innerIdlType instanceof MSequenceDef)
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
		else
		{
			throw new RuntimeException("transform(MPrimitiveDef): unknown primitive type "
					+ primitive.getKind());
		}
	}
}
