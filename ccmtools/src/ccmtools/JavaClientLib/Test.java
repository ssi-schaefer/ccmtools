package ccmtools.JavaClientLib;

import java.util.ArrayList;
import java.util.List;

import ccmtools.JavaClientLib.metamodel.ExceptionDefinition;
import ccmtools.JavaClientLib.metamodel.InterfaceDefinition;
import ccmtools.JavaClientLib.metamodel.LongType;
import ccmtools.JavaClientLib.metamodel.OperationDefinition;
import ccmtools.JavaClientLib.metamodel.ParameterDefinition;
import ccmtools.JavaClientLib.metamodel.PassingDirection;
import ccmtools.JavaClientLib.metamodel.StringType;

public class Test
{
	public static void main(String[] args)
	{
		StringType stringType = new StringType();
		LongType longType = new LongType();
		
		List ns = new ArrayList();
		ns.add("wamas");
		
		OperationDefinition f2 = new OperationDefinition("f2", longType);
		{
			ParameterDefinition p1 = new ParameterDefinition("p1", PassingDirection.IN, longType);
			ParameterDefinition p2 = new ParameterDefinition("p2", PassingDirection.INOUT, longType);
			ParameterDefinition p3 = new ParameterDefinition("p3", PassingDirection.OUT, longType);		
			f2.getParameter().add(p1);
			f2.getParameter().add(p2);
			f2.getParameter().add(p3);
			f2.getException().add(new ExceptionDefinition("XXXException"));
		}
		
		OperationDefinition f8 = new OperationDefinition("f8", stringType);
		{
			f8.getParameter().add(new ParameterDefinition("p1", PassingDirection.IN, stringType));
			f8.getParameter().add(new ParameterDefinition("p2", PassingDirection.INOUT, stringType));
			f8.getParameter().add(new ParameterDefinition("p3", PassingDirection.OUT, stringType));
		}
		
		InterfaceDefinition iface = new InterfaceDefinition("BasicTypeInterface", ns);
		iface.getOperation().add(f2);
		iface.getOperation().add(f8);
		
		String code;
		code = iface.generateInterfaceDeclaration();
		System.out.println(code);
		
		code = iface.generateInterfaceAdapterToCorba();
		System.out.println(code);
		
		code = iface.generateInterfaceAdapterFromCorba();
		System.out.println(code);
	}

}
