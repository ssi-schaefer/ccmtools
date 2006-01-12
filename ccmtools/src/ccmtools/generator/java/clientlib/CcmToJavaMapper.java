package ccmtools.generator.java.clientlib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ccmtools.Metamodel.BaseIDL.MAttributeDef;
import ccmtools.Metamodel.BaseIDL.MConstantDef;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MExceptionDef;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MOperationDef;
import ccmtools.Metamodel.BaseIDL.MParameterDef;
import ccmtools.Metamodel.BaseIDL.MParameterMode;
import ccmtools.Metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.Metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.Metamodel.BaseIDL.MStringDef;
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
import ccmtools.generator.java.clientlib.metamodel.FloatType;
import ccmtools.generator.java.clientlib.metamodel.HomeDef;
import ccmtools.generator.java.clientlib.metamodel.IntegerType;
import ccmtools.generator.java.clientlib.metamodel.InterfaceDef;
import ccmtools.generator.java.clientlib.metamodel.LongType;
import ccmtools.generator.java.clientlib.metamodel.OperationDef;
import ccmtools.generator.java.clientlib.metamodel.ParameterDef;
import ccmtools.generator.java.clientlib.metamodel.PassingDirection;
import ccmtools.generator.java.clientlib.metamodel.ProvidesDef;
import ccmtools.generator.java.clientlib.metamodel.ShortType;
import ccmtools.generator.java.clientlib.metamodel.StringType;
import ccmtools.generator.java.clientlib.metamodel.Type;
import ccmtools.generator.java.clientlib.metamodel.UsesDef;
import ccmtools.generator.java.clientlib.metamodel.VoidType;
import ccmtools.utils.Code;

public class CcmToJavaMapper
{
	/** This map is used to cache source code artifacts like interfaces, exceptions, etc. */
	private Map artifactMap = new HashMap();
	
	
	// Handle container elements ----------------------------------------------
	
	InterfaceDef transform(MInterfaceDef in)
	{
		InterfaceDef out;
		String repoId = Code.getRepositoryId(in);
//		System.out.println("MInterfaceDef: " + repoId);
		if (artifactMap.containsKey(repoId))
		{
			out = (InterfaceDef) artifactMap.get(repoId);
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
			artifactMap.put(repoId, out);
		}
		return out;
	}
	
	
	public HomeDef transform(MHomeDef in)
	{		
		HomeDef out;
		String repoId = Code.getRepositoryId(in);
//		System.out.println("MHomeDef: " + repoId);
		if (artifactMap.containsKey(repoId))
		{
			out = (HomeDef) artifactMap.get(repoId);
		}
		else
		{
			out = new HomeDef(in.getIdentifier(), Code.getNamespaceList(in));
			out.setComponent(transform(in.getComponent()));
			artifactMap.put(repoId, out);
		}
		return out;
	}
	
	
	public ComponentDef transform(MComponentDef in)
	{
		ComponentDef out;
		String repoId = Code.getRepositoryId(in);
//		System.out.println("MComponentDef: " + repoId);
		if (artifactMap.containsKey(repoId))
		{
			out = (ComponentDef) artifactMap.get(repoId);
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
			artifactMap.put(repoId, out);
		}
		return out;
	}
	
	
	// Handle Contained elements ----------------------------------------------

	public ExceptionDef transform(MExceptionDef in)
	{
		ExceptionDef out;
		String repoId = Code.getRepositoryId(in);	
		if (artifactMap.containsKey(repoId))
		{
			out = (ExceptionDef) artifactMap.get(repoId);
		}
		else 
		{
			out = new ExceptionDef(in.getIdentifier(), Code.getNamespaceList(in));
			artifactMap.put(repoId, out);
		}
		return out;
	}
	
	
	public ProvidesDef transform(MProvidesDef in)
	{
//		System.out.println("MProvidesDef: " + in.getIdentifier());		
		ProvidesDef out = new ProvidesDef(in.getIdentifier(), Code.getNamespaceList(in));
		out.setInterface(transform(in.getProvides()));
		return out;
	}
	
	
	public UsesDef transform(MUsesDef in)
	{
//		System.out.println("MUsesDef: " + in.getIdentifier());
		UsesDef out = new UsesDef(in.getIdentifier(), Code.getNamespaceList(in));
		out.setInterface(transform(in.getUses()));
		return out;
	}

	
	public OperationDef transform(MOperationDef in)
	{
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
		ParameterDef out = new ParameterDef(in.getIdentifier(), 
											transform(in.getDirection()), 
											transform(in.getIdlType()));		
		return out;
	}
		
	public PassingDirection transform(MParameterMode in)
	{
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

	public Type transform(MIDLType idlType)
	{
		if(idlType instanceof MPrimitiveDef)
		{
			return transform((MPrimitiveDef)idlType);
		}
		else if(idlType instanceof MStringDef)
		{
			return new StringType();
		}
		//....
		else
		{
			throw new RuntimeException("transform(MIDLType): unknown idl type " + idlType);
		}
	}

	public Type transform(MPrimitiveDef primitive)	
	{
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
//		else if(primitive.getKind() == MPrimitiveKind.PK_ANY)
//		{
//			return new AnyType();
//		}
		else
		{
			throw new RuntimeException("transform(MPrimitiveDef): unknown primitive type "
					+ primitive.getKind());
		}
	}
}
