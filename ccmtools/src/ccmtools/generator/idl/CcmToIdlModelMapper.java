package ccmtools.generator.idl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import ccmtools.CodeGenerator.NodeHandler;
import ccmtools.Metamodel.BaseIDL.MArrayDef;
import ccmtools.Metamodel.BaseIDL.MAttributeDef;
import ccmtools.Metamodel.BaseIDL.MConstantDef;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MEnumDef;
import ccmtools.Metamodel.BaseIDL.MExceptionDef;
import ccmtools.Metamodel.BaseIDL.MFieldDef;
import ccmtools.Metamodel.BaseIDL.MFixedDef;
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
import ccmtools.Metamodel.BaseIDL.MTypedefDef;
import ccmtools.Metamodel.BaseIDL.MWstringDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MFactoryDef;
import ccmtools.Metamodel.ComponentIDL.MFinderDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;
import ccmtools.Metamodel.ComponentIDL.MProvidesDef;
import ccmtools.Metamodel.ComponentIDL.MSupportsDef;
import ccmtools.Metamodel.ComponentIDL.MUsesDef;
import ccmtools.generator.idl.metamodel.AnyType;
import ccmtools.generator.idl.metamodel.ArrayDef;
import ccmtools.generator.idl.metamodel.AttributeDef;
import ccmtools.generator.idl.metamodel.BooleanType;
import ccmtools.generator.idl.metamodel.CharType;
import ccmtools.generator.idl.metamodel.ComponentDef;
import ccmtools.generator.idl.metamodel.ConstantDef;
import ccmtools.generator.idl.metamodel.DoubleType;
import ccmtools.generator.idl.metamodel.EnumDef;
import ccmtools.generator.idl.metamodel.ExceptionDef;
import ccmtools.generator.idl.metamodel.FacetDef;
import ccmtools.generator.idl.metamodel.FactoryMethodDef;
import ccmtools.generator.idl.metamodel.FieldDef;
import ccmtools.generator.idl.metamodel.FixedType;
import ccmtools.generator.idl.metamodel.FloatType;
import ccmtools.generator.idl.metamodel.HomeDef;
import ccmtools.generator.idl.metamodel.InterfaceDef;
import ccmtools.generator.idl.metamodel.LongDoubleType;
import ccmtools.generator.idl.metamodel.LongLongType;
import ccmtools.generator.idl.metamodel.LongType;
import ccmtools.generator.idl.metamodel.ModelElement;
import ccmtools.generator.idl.metamodel.ModelRepository;
import ccmtools.generator.idl.metamodel.ObjectType;
import ccmtools.generator.idl.metamodel.OctetType;
import ccmtools.generator.idl.metamodel.OperationDef;
import ccmtools.generator.idl.metamodel.ParameterDef;
import ccmtools.generator.idl.metamodel.PassingDirection;
import ccmtools.generator.idl.metamodel.ReceptacleDef;
import ccmtools.generator.idl.metamodel.SequenceDef;
import ccmtools.generator.idl.metamodel.ShortType;
import ccmtools.generator.idl.metamodel.StringType;
import ccmtools.generator.idl.metamodel.StructDef;
import ccmtools.generator.idl.metamodel.Type;
import ccmtools.generator.idl.metamodel.TypedefDef;
import ccmtools.generator.idl.metamodel.UnsignedLongLongType;
import ccmtools.generator.idl.metamodel.UnsignedLongType;
import ccmtools.generator.idl.metamodel.UnsignedShortType;
import ccmtools.generator.idl.metamodel.VoidType;
import ccmtools.generator.idl.metamodel.WCharType;
import ccmtools.generator.idl.metamodel.WStringType;
import ccmtools.utils.Code;

public class CcmToIdlModelMapper
	implements NodeHandler
{
	/** This map is used to cache source code artifacts like interfaces, exceptions, etc. */
	private Map<String, ModelElement> artifactCache;
		
    /** Java logging */
    private Logger logger;
    
    /** Root element of the IDL implementation model */
    private ModelRepository modelRepository;
    
    
	public CcmToIdlModelMapper()
	{
		logger = Logger.getLogger("ccm.generator.idl");
		logger.fine("");		
		artifactCache = new HashMap<String, ModelElement>();
		modelRepository = new ModelRepository();
	}
			
	public ModelRepository getIdlModel()
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
		logger.fine("");
		logger.finer("node = " + node + ", id = " + scopeId);
	}

	
    /**
	 * Handle possible root elements in the CCM metamodel. Each root element
	 * will be a starting point for the CCM metamodel to JavaImplementation
	 * metamodel transformation. Note that each transformed reoot element will
	 * be added to the ModelRepository from which source files can be generated.
	 */
    public void endNode(Object node, String scopeId)
	{
		logger.fine("");
		logger.finer("node = " + node + ", scopeId = " + scopeId);

		if (node == null)
		{
			// The current node is not valid!
			return;
		}
		else if (node instanceof MContained && !((MContained) node).getSourceFile().equals(""))
		{
			logger.finer("node = " + node + " has been defined in an included file ("
					+ ((MContained) node).getSourceFile() + ")");
			// The current node is defined in an included file
			// and should not be generated!
			return;
		}
		else if(node instanceof MContained && ((MContained)node).getDefinedIn() instanceof MInterfaceDef)
		{
			// Model elements defined within an interface will not generate a separate
			// file.
			MInterfaceDef iface = (MInterfaceDef)((MContained)node).getDefinedIn();
			logger.finer("  defined in  " + Code.getRepositoryId(iface));
			return;
		}
    		else if(node instanceof MEnumDef)
    		{
    			MEnumDef enumeration = (MEnumDef)node;
    			logger.finer("MEnumDef: " + Code.getRepositoryId(enumeration));
    			EnumDef idlEnum = transform(enumeration);
    			modelRepository.addEnum(idlEnum);
    		}
        	else if(node instanceof MStructDef)
        	{
        		MStructDef struct = (MStructDef)node;
        		logger.finer("MStructDef: " + Code.getRepositoryId(struct));
        		StructDef idlStruct = transform(struct);
        		modelRepository.addStruct(idlStruct);
        	}
        	else if(node instanceof MTypedefDef)
        	{
        		MTypedefDef typedef = (MTypedefDef)node;
        		logger.finer("MTypedefDef: " + Code.getRepositoryId(typedef));
        		TypedefDef idlTypedef = transform(typedef);
        		modelRepository.addTypedef(idlTypedef);
        	}
        	else if(node instanceof MConstantDef)
        	{
        		MConstantDef constant = (MConstantDef)node;
        		logger.finer("MTypedefDef: " + Code.getRepositoryId(constant));
        		ConstantDef idlConstant = transform(constant);
        		modelRepository.addGlobalConstant(idlConstant);
        	}
        	else if(node instanceof MExceptionDef)
        	{
        		MExceptionDef exception = (MExceptionDef)node;
        		logger.finer("MExceptionDef: " + Code.getRepositoryId(exception));
        		ExceptionDef idlException = transform(exception);	
        		modelRepository.addException(idlException);
        	}
        	else if(node instanceof MComponentDef)
        	{
        		MComponentDef component = (MComponentDef)node;
        		logger.finer("MComponentDef: " + Code.getRepositoryId(component));
        		ComponentDef idlComponent = transform(component);
        		modelRepository.addComponent(idlComponent);    		
        	}
        	else if(node instanceof MHomeDef)
        	{
        		MHomeDef home = (MHomeDef)node;
        		logger.finer("MHomeDef: " + Code.getRepositoryId(home));
        		HomeDef idlHome = transform(home);
        		modelRepository.addHome(idlHome);    		
        	}		
        	else if(node instanceof MInterfaceDef)
        	{
        		MInterfaceDef iface = (MInterfaceDef)node;
        		logger.finer("MInterfaceDef: " + Code.getRepositoryId(iface));
        		InterfaceDef idlInterface = transform(iface);
        		modelRepository.addInterface(idlInterface);    		
        	}
		//...
	}

	public void handleNodeData(String field_type, String field_id, Object value)
	{
	}
	
	
    /*************************************************************************
     * Model mapper methods
     *************************************************************************/ 
    	
	public Type transform(MIDLType in)
	{
		logger.fine("MIDLType: " + in);
		if(in instanceof MPrimitiveDef)
		{
			return transform((MPrimitiveDef)in);
		}
		else if(in instanceof MStringDef)
		{			
			return transform((MStringDef)in);
		}
		else if(in instanceof MWstringDef)
		{
			return transform((MWstringDef)in);
		}
		else if(in instanceof MFixedDef)
		{
			return transform((MFixedDef)in);
		}
		else if(in instanceof MStructDef)
		{			
			return transform((MStructDef)in);
		}
		else if(in instanceof MEnumDef)
		{			
			return transform((MEnumDef)in);
		}
		else if(in instanceof MTypedefDef)
		{
			return transform((MTypedefDef)in);
		}
		else if(in instanceof MArrayDef)
		{
			return transform((MArrayDef)in);
		}
		else if(in instanceof MSequenceDef)
		{
			return transform((MSequenceDef)in);
		}
		else if(in instanceof MInterfaceDef)
		{
			return transform((MInterfaceDef)in);
		}
		else
		{
			throw new RuntimeException("Unhandled idl type " + in);
		}
	}
	
	public Type transform(MPrimitiveDef primitive)	
	{
		logger.fine("MPrimitiveDef: " + primitive.getKind());
		if(primitive.getKind() == MPrimitiveKind.PK_VOID)
		{
			return new VoidType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_BOOLEAN)
		{
			return new BooleanType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_CHAR)
		{
			return new CharType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_WCHAR)
		{
			return new WCharType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_OCTET)
		{
			return new OctetType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_SHORT)
		{
			return new ShortType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_USHORT)
		{
			return new UnsignedShortType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_LONG)
		{
			return new LongType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_ULONG)
		{
			return new UnsignedLongType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_LONGLONG)
		{
			return new LongLongType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_ULONGLONG)
		{
			return new UnsignedLongLongType();			
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_FLOAT)
		{
			return new FloatType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_DOUBLE)
		{
			return new DoubleType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_LONGDOUBLE)
		{
			return new LongDoubleType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_ANY)
		{
			return new AnyType();
		}
		else if(primitive.getKind() == MPrimitiveKind.PK_OBJREF)
		{
			return new ObjectType();
		}
		else
		{
			throw new RuntimeException("Unhandled primitive type "	+ primitive.getKind());
		}
	}

	public StringType transform(MStringDef in)
	{
		StringType out = new StringType();
		logger.fine("MStringDef: " + in);
		out.setBound(in.getBound());
		return out;		
	}
	
	public WStringType transform(MWstringDef in)
	{
		WStringType out = new WStringType();
		logger.fine("MWstringDef: " + in);
		out.setBound(in.getBound());
		return out;
	}
	
	public FixedType transform(MFixedDef in)
	{
		FixedType out = new FixedType();
		logger.fine("MFixedDef: " + in);
		out.setDigits(in.getDigits());
		out.setScale(in.getScale());
		return out;
	}
	
	public SequenceDef transform(MSequenceDef in)
	{
		SequenceDef out = new SequenceDef();
		logger.fine("MSequenceDef: " + in);
		out.setBound(in.getBound());
		out.setElementType(transform(in.getIdlType()));
		return out;
	}
	
	public ArrayDef transform(MArrayDef in)
	{
		ArrayDef out = new ArrayDef();
		logger.fine("MArrayDef: " + in);
//		out.getBounds().addAll(in.getBounds());
		for(Iterator i=in.getBounds().iterator(); i.hasNext();)
		{
			out.getBounds().add((Long)i.next());
		}
		out.setElementType(transform(in.getIdlType()));		
		return out;
	}
	
	public EnumDef transform(MEnumDef in)
	{		
		EnumDef out;
		String repoId = Code.getRepositoryId(in);
		if (artifactCache.containsKey(repoId))
		{
			out = (EnumDef)artifactCache.get(repoId);
		}
		else 
		{			
			logger.fine("MEnumDef: " + repoId);
			out = new EnumDef(in.getIdentifier(), Code.getNamespaceList(in));
			for(Iterator i = in.getMembers().iterator(); i.hasNext();)
			{
				String member = (String)i.next();
				out.getMembers().add(member);
			}
			artifactCache.put(repoId, out);
		}
		return out;
	}
	
	public StructDef transform(MStructDef in)
	{		
		StructDef out;
		String repoId = Code.getRepositoryId(in);
		if (artifactCache.containsKey(repoId))
		{
			out = (StructDef)artifactCache.get(repoId);
		}
		else 
		{
			logger.fine("MStructDef: " + repoId);
			out = new StructDef(in.getIdentifier(), Code.getNamespaceList(in));
			for(Iterator i = in.getMembers().iterator(); i.hasNext();)
			{
				MFieldDef member = (MFieldDef)i.next();	
				MIDLType idlType = member.getIdlType();
				FieldDef field = new FieldDef();
				field.setIdentifier(member.getIdentifier());
				field.setType(transform(idlType));
				out.getFields().add(field);					
			}
			artifactCache.put(repoId, out);
		}
		return out;
	}
		
	public ExceptionDef transform(MExceptionDef in)
	{		
		ExceptionDef out;
		String repoId = Code.getRepositoryId(in);
		if (artifactCache.containsKey(repoId))
		{
			out = (ExceptionDef)artifactCache.get(repoId);
		}
		else 
		{
			logger.fine("MExceptionDef: " + repoId);
			out = new ExceptionDef(in.getIdentifier(), Code.getNamespaceList(in));
			for(Iterator i = in.getMembers().iterator(); i.hasNext();)
			{
				MFieldDef member = (MFieldDef)i.next();	
				MIDLType idlType = member.getIdlType();
				FieldDef field = new FieldDef();
				field.setIdentifier(member.getIdentifier());
				field.setType(transform(idlType));
				out.getFields().add(field);					
			}
			artifactCache.put(repoId, out);
		}
		return out;
	}	
	
	public TypedefDef transform(MTypedefDef in)
	{
		TypedefDef out;
		String repoId = Code.getRepositoryId(in);
		if (artifactCache.containsKey(repoId))
		{
			out = (TypedefDef)artifactCache.get(repoId);
		}
		else 
		{
			logger.fine("MTypedefDef: " + repoId);
			out = new TypedefDef(in.getIdentifier(), Code.getNamespaceList(in));
			out.setAlias(transform(((MTyped)in).getIdlType()));
			artifactCache.put(repoId, out);
		}
		return out;
	}	
	
	public ConstantDef transform(MConstantDef in)
	{
		ConstantDef out;
		String repoId = Code.getRepositoryId(in);
		if (artifactCache.containsKey(repoId))
		{
			out = (ConstantDef)artifactCache.get(repoId);
		}
		else 
		{
			logger.fine("MConstantDef: " + repoId);
			out = new ConstantDef(in.getIdentifier(), Code.getNamespaceList(in));
			out.setType(transform(((MTyped)in).getIdlType()));
			out.setConstValue(in.getConstValue());
		}
		return out;
	}
	
	
	public InterfaceDef transform(MInterfaceDef in)
	{		
		InterfaceDef out;
		String repoId = Code.getRepositoryId(in);
		if (artifactCache.containsKey(repoId))
		{
			out = (InterfaceDef)artifactCache.get(repoId);
		}
		else 
		{
			logger.fine("MInterfaceDef: " + repoId);
			out = new InterfaceDef(in.getIdentifier(), Code.getNamespaceList(in));
			
			for(Iterator i = in.getBases().iterator(); i.hasNext(); )
			{
				MInterfaceDef baseIface = (MInterfaceDef)i.next();
				out.getBaseInterfaces().add(transform(baseIface));
			}
			for(Iterator i = in.getContentss().iterator(); i.hasNext();)
			{
				MContained child = (MContained) i.next();
				if(child instanceof MConstantDef)
				{
					out.getConstants().add(transform((MConstantDef)child));
				}
				else if(child instanceof MEnumDef)
				{
					out.getEnumerations().add(transform((MEnumDef)child));
				}
				else if(child instanceof MStructDef)
				{
					out.getStructures().add(transform((MStructDef)child));
				}
				else if(child instanceof MTypedefDef)
				{
					out.getTypedefs().add(transform((MTypedefDef)child));
				}
				else if(child instanceof MExceptionDef)
				{
					out.getExceptions().add(transform((MExceptionDef)child));
				}
				else if(child instanceof MAttributeDef)
				{
					out.getAttributes().add(transform((MAttributeDef)child));
				}
				else if(child instanceof MOperationDef)
				{
					out.getOperations().add(transform((MOperationDef)child));
				}				
				// ...
			}
			artifactCache.put(repoId, out);
		}
		return out;
	}
	
	public AttributeDef transform(MAttributeDef in)
	{
		logger.fine("MAttributeDef: " + in.getIdentifier());
		AttributeDef out = new AttributeDef(in.getIdentifier());
		out.setType(transform(in.getIdlType()));
		out.setReadonly(in.isReadonly());
        for(Iterator i=in.getGetExceptions().iterator(); i.hasNext();)
        {
            MExceptionDef ex = (MExceptionDef)i.next();
            out.getGetterExceptions().add(transform(ex));
        }
        for(Iterator i=in.getSetExceptions().iterator(); i.hasNext();)
        {
            MExceptionDef ex = (MExceptionDef)i.next();
            out.getSetterExceptions().add(transform(ex));
        }
		return out;
	}
	
	public OperationDef transform(MOperationDef in)
	{
		logger.fine("MOperationDef: " + in.getIdentifier());
		OperationDef out = new OperationDef(in.getIdentifier());
		out.setType(transform(in.getIdlType()));
		out.setOneway(in.isOneway());
		out.setContext(in.getContexts());
		for(Iterator i = in.getParameters().iterator(); i.hasNext(); )
		{
			MParameterDef parameter = (MParameterDef)i.next();
			out.getParameters().add(transform(parameter));
		}
		for(Iterator i = in.getExceptionDefs().iterator(); i.hasNext(); )
		{
			MExceptionDef exc = (MExceptionDef)i.next();
			out.getExceptions().add(transform(exc));
		}
		return out;
	}
	
	public ParameterDef transform(MParameterDef in)
	{
		logger.fine("MParameterDef: " + in.getIdentifier());
		ParameterDef out = new ParameterDef(in.getIdentifier());
		out.setDirection(transform(in.getDirection()));
		out.setType(transform(in.getIdlType()));
		return out;
	}
	
	public PassingDirection transform(MParameterMode in)
	{
		logger.fine("MParameterMode: ");
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
			throw new RuntimeException("Unknown parameter mode " + in + "!");
		}
	}
	
	public ComponentDef transform(MComponentDef in)
	{		
		ComponentDef out;
		String repoId = Code.getRepositoryId(in);
		if (artifactCache.containsKey(repoId))
		{
			out = (ComponentDef)artifactCache.get(repoId);
		}
		else 
		{
			logger.fine("MComponentDef: " + repoId);
			out = new ComponentDef(in.getIdentifier(), Code.getNamespaceList(in));

			if(in.getBases() != null)
			{
				if(in.getBases().size() == 1) // single inheritynce only
				{
					MComponentDef base = (MComponentDef)in.getBases().get(0);
					out.setBase(transform(base));
				}
			}
			for(Iterator i = in.getSupportss().iterator(); i.hasNext();)
			{
				MInterfaceDef supportedInterface = ((MSupportsDef)i.next()).getSupports();
				out.getSupports().add(transform(supportedInterface));
			}
			for(Iterator i = in.getFacets().iterator(); i.hasNext();)
			{
				MProvidesDef provides = (MProvidesDef)i.next();
				FacetDef facet = transform(provides);
				facet.setComponent(out);
				out.getFacets().add(facet);
			}
			for(Iterator i = in.getReceptacles().iterator(); i.hasNext();)
			{
				MUsesDef uses = (MUsesDef)i.next();
				ReceptacleDef receptacle = transform(uses);
				receptacle.setComponent(out);
				receptacle.setMultiple(uses.isMultiple());
				out.getReceptacles().add(receptacle);
			}
			for(Iterator i = in.getContentss().iterator(); i.hasNext();)
			{
			    MContained child = (MContained) i.next();
			    if (child instanceof MAttributeDef)
			    {
			        out.getAttributes().add(transform((MAttributeDef)child));
			    }
			}
			artifactCache.put(repoId, out);
		}
		return out;
	}
	
	public FacetDef transform(MProvidesDef in)
	{
		FacetDef out;
		String repoId = Code.getRepositoryId(in);
		if(artifactCache.containsKey(repoId))
		{
			out = (FacetDef)artifactCache.get(repoId);	
		}
		else 
		{
			logger.finer("MProvidesDef: " + repoId);		
			out = new FacetDef(in.getIdentifier(), Code.getNamespaceList(in));
			out.setInterface(transform(in.getProvides()));
			artifactCache.put(repoId, out);
		}
		return out;
	}
	
	public ReceptacleDef transform(MUsesDef in)
	{
		ReceptacleDef out;
		String repoId = Code.getRepositoryId(in);
		if(artifactCache.containsKey(repoId))
		{
			out = (ReceptacleDef)artifactCache.get(repoId);	
		}
		else 
		{
			logger.finer("MUsesDef: " + repoId);		
			out = new ReceptacleDef(in.getIdentifier(), Code.getNamespaceList(in));
			out.setInterface(transform(in.getUses()));
			artifactCache.put(repoId, out);
		}
		return out;
	}
	
	public HomeDef transform(MHomeDef in)
	{		
		HomeDef out;
		String repoId = Code.getRepositoryId(in);
		if (artifactCache.containsKey(repoId))
		{
			out = (HomeDef)artifactCache.get(repoId);
		}
		else 
		{
			logger.fine("MHomeDef: " + repoId);
			out = new HomeDef(in.getIdentifier(), Code.getNamespaceList(in));
			ComponentDef component = transform(in.getComponent());
			component.getHomes().add(out);
			out.setComponent(component);
            if(in.getBases() != null)
            {
                if(in.getBases().size() == 1) // single inheritance only!
                {
                    MHomeDef base = (MHomeDef)in.getBases().get(0);
                    out.setBase(transform(base));
                }         
            }
            for(Iterator i = in.getSupportss().iterator(); i.hasNext();)
            {
                MInterfaceDef supportedInterface = ((MSupportsDef)i.next()).getSupports();
                out.getSupports().add(transform(supportedInterface));
            }
            for(Iterator i = in.getFactories().iterator(); i.hasNext();)
            {
                MFactoryDef factory = (MFactoryDef)i.next();
                out.getFactories().add(transform(factory));
            }
            // Check section
            for(Iterator i = in.getFinders().iterator(); i.hasNext();)
            {
                MFinderDef finder = (MFinderDef)i.next();
                throw new RuntimeException("Home's finder methods like '" + finder.getIdentifier() 
                        + "()' are not supported by CCM Tools!");                
            }
            for(Iterator i = in.getContentss().iterator(); i.hasNext();)
            {
                MContained child = (MContained) i.next();
                if (child instanceof MAttributeDef)
                {
                    out.getAttributes().add(transform((MAttributeDef)child));
                }
            }
            
			artifactCache.put(repoId, out);
		}
		return out;
	}
    
    public FactoryMethodDef transform(MFactoryDef in)
    {
        logger.fine("MFactoryDef: " + in.getIdentifier());
        FactoryMethodDef out = new FactoryMethodDef(in.getIdentifier());
        for(Iterator i = in.getParameters().iterator(); i.hasNext(); )
        {
            MParameterDef parameter = (MParameterDef)i.next();
            out.getParameters().add(transform(parameter));
        }
        for(Iterator i = in.getExceptionDefs().iterator(); i.hasNext(); )
        {
            MExceptionDef exc = (MExceptionDef)i.next();
            out.getExceptions().add(transform(exc));
        }
        return out;
    }    
}
