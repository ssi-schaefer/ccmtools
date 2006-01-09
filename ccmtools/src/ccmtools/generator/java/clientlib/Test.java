package ccmtools.generator.java.clientlib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.generator.java.clientlib.metamodel.ComponentDef;
import ccmtools.generator.java.clientlib.metamodel.HomeDef;
import ccmtools.generator.java.clientlib.metamodel.IntegerType;
import ccmtools.generator.java.clientlib.metamodel.InterfaceDef;
import ccmtools.generator.java.clientlib.metamodel.OperationDef;
import ccmtools.generator.java.clientlib.metamodel.ParameterDef;
import ccmtools.generator.java.clientlib.metamodel.PassingDirection;
import ccmtools.generator.java.clientlib.metamodel.ProvidesDef;
import ccmtools.generator.java.clientlib.metamodel.SourceFile;
import ccmtools.generator.java.clientlib.metamodel.StringType;
import ccmtools.generator.java.clientlib.metamodel.UsesDef;

public class Test
{
	public static void main(String[] args)
	{
		StringType stringType = new StringType();
		IntegerType longType = new IntegerType();
		
		List ns = new ArrayList();
		ns.add("wamas");

		OperationDef f2 = new OperationDef("f2", longType);
		f2.getParameter().add(new ParameterDef("p1", PassingDirection.IN, longType));
		f2.getParameter().add(new ParameterDef("p2", PassingDirection.INOUT, longType));
		f2.getParameter().add(new ParameterDef("p3", PassingDirection.OUT, longType));

		OperationDef f8 = new OperationDef("f8", stringType);
		f8.getParameter().add(new ParameterDef("p1", PassingDirection.IN, stringType));
		f8.getParameter().add(new ParameterDef("p2", PassingDirection.INOUT, stringType));
		f8.getParameter().add(new ParameterDef("p3", PassingDirection.OUT, stringType));
		
		InterfaceDef basicTypeInterface = new InterfaceDef("BasicTypeInterface", ns);
		basicTypeInterface.getOperation().add(f2);
		basicTypeInterface.getOperation().add(f8);

		ProvidesDef basicTypeIn = new ProvidesDef("basicTypeIn", ns);
		basicTypeIn.setInterface(basicTypeInterface);
		
		UsesDef basicTypeOut = new UsesDef("basicTypeOut", ns);
		basicTypeOut.setInterface(basicTypeInterface);
		
		ComponentDef component = new ComponentDef("Test", ns);
		component.getFacet().add(basicTypeIn);
		component.getReceptacle().add(basicTypeOut);
		
		HomeDef home = new HomeDef("TestHome", ns);
		home.setComponent(component);
		
		
		
		// Write Source Files -------------------------------------------------
		
		File outDir = new File(System.getProperty("user.dir"),
				"test/JavaClientLib/simple/xxx/JavaClientLib/src");

		List sourceFileList = new ArrayList();
		sourceFileList.addAll(basicTypeInterface.generateSourceFiles());
		sourceFileList.addAll(component.generateSourceFiles());
		sourceFileList.addAll(home.generateSourceFiles());
		
		for(Iterator i=sourceFileList.iterator(); i.hasNext();)
		{
			SourceFile source = (SourceFile)i.next();
			writeCode(outDir, source);
		}
	}

	
	public static void writeCode(File outDir, SourceFile source)
	{
		File location = new File(outDir, source.getPackageName());
		File file = new File(location, source.getClassName());		
		System.out.println("> write " + file);
		try
		{
			if(!location.isDirectory()) 
			{
				location.mkdirs();
			}			
			FileWriter writer = new FileWriter(file);
			writer.write(source.getCode(), 0, source.getCode().length());
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
