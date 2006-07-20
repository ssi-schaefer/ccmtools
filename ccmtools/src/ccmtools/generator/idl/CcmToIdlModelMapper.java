package ccmtools.generator.idl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import ccmtools.CodeGenerator.NodeHandler;
import ccmtools.Metamodel.BaseIDL.MArrayDef;
import ccmtools.Metamodel.BaseIDL.MConstantDef;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MEnumDef;
import ccmtools.Metamodel.BaseIDL.MFieldDef;
import ccmtools.Metamodel.BaseIDL.MFixedDef;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.Metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.Metamodel.BaseIDL.MSequenceDef;
import ccmtools.Metamodel.BaseIDL.MStringDef;
import ccmtools.Metamodel.BaseIDL.MStructDef;
import ccmtools.Metamodel.BaseIDL.MTyped;
import ccmtools.Metamodel.BaseIDL.MTypedefDef;
import ccmtools.Metamodel.BaseIDL.MWstringDef;
import ccmtools.generator.idl.metamodel.AnyType;
import ccmtools.generator.idl.metamodel.ArrayDef;
import ccmtools.generator.idl.metamodel.BooleanType;
import ccmtools.generator.idl.metamodel.CharType;
import ccmtools.generator.idl.metamodel.ConstantDef;
import ccmtools.generator.idl.metamodel.DoubleType;
import ccmtools.generator.idl.metamodel.EnumDef;
import ccmtools.generator.idl.metamodel.FieldDef;
import ccmtools.generator.idl.metamodel.FixedType;
import ccmtools.generator.idl.metamodel.FloatType;
import ccmtools.generator.idl.metamodel.LongDoubleType;
import ccmtools.generator.idl.metamodel.LongLongType;
import ccmtools.generator.idl.metamodel.LongType;
import ccmtools.generator.idl.metamodel.ModelElement;
import ccmtools.generator.idl.metamodel.ModelRepository;
import ccmtools.generator.idl.metamodel.ObjectType;
import ccmtools.generator.idl.metamodel.OctetType;
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
		logger.finer("MIDLType: " + in);
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
		else
		{
			throw new RuntimeException("Unhandled idl type " + in);
		}
	}
	
	public Type transform(MPrimitiveDef primitive)	
	{
		logger.finer("MPrimitiveDef: " + primitive.getKind());
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
		logger.finer("MStringDef: " + in);
		out.setBound(in.getBound());
		return out;		
	}
	
	public WStringType transform(MWstringDef in)
	{
		WStringType out = new WStringType();
		logger.finer("MWstringDef: " + in);
		out.setBound(in.getBound());
		return out;
	}
	
	public FixedType transform(MFixedDef in)
	{
		FixedType out = new FixedType();
		logger.finer("MFixedDef: " + in);
		out.setDigits(in.getDigits());
		out.setScale(in.getScale());
		return out;
	}
	
	public SequenceDef transform(MSequenceDef in)
	{
		SequenceDef out = new SequenceDef();
		logger.finer("MSequenceDef: " + in);
		out.setBound(in.getBound());
		out.setElementType(transform(in.getIdlType()));
		return out;
	}
	
	public ArrayDef transform(MArrayDef in)
	{
		ArrayDef out = new ArrayDef();
		logger.finer("MArrayDef: " + in);
		out.getBounds().addAll(in.getBounds());
		out.setElementType(transform(in.getIdlType()));		
		return out;
	}
	
	public EnumDef transform(MEnumDef in)
	{		
		EnumDef out;
		String repoId = Code.getRepositoryId(in);
		logger.finer("MEnumDef: " + repoId);
		if (artifactCache.containsKey(repoId))
		{
			out = (EnumDef)artifactCache.get(repoId);
		}
		else 
		{			
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
		logger.finer("MStructDef: " + repoId);
		if (artifactCache.containsKey(repoId))
		{
			out = (StructDef)artifactCache.get(repoId);
		}
		else 
		{
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
	
	public TypedefDef transform(MTypedefDef in)
	{
		TypedefDef out;
		String repoId = Code.getRepositoryId(in);
		logger.finer("MTypedefDef: " + repoId);
		if (artifactCache.containsKey(repoId))
		{
			out = (TypedefDef)artifactCache.get(repoId);
		}
		else 
		{
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
		logger.finer("MConstantDef: " + repoId);
		if (artifactCache.containsKey(repoId))
		{
			out = (ConstantDef)artifactCache.get(repoId);
		}
		else 
		{
			out = new ConstantDef(in.getIdentifier(), Code.getNamespaceList(in));
			out.setType(transform(((MTyped)in).getIdlType()));
			out.setConstValue(in.getConstValue());
		}
		return out;
	}
	
}
