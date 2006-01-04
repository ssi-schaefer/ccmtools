package ccmtools.JavaClientLib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.JavaClientLib.metamodel.ComponentDef;
import ccmtools.JavaClientLib.metamodel.HomeDef;
import ccmtools.JavaClientLib.metamodel.IntegerType;
import ccmtools.JavaClientLib.metamodel.InterfaceDef;
import ccmtools.JavaClientLib.metamodel.OperationDef;
import ccmtools.JavaClientLib.metamodel.ParameterDef;
import ccmtools.JavaClientLib.metamodel.PassingDirection;
import ccmtools.JavaClientLib.metamodel.SourceFile;
import ccmtools.JavaClientLib.metamodel.StringType;

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
		
		InterfaceDef iface = new InterfaceDef("BasicTypeInterface", ns);
		iface.getOperation().add(f2);
		iface.getOperation().add(f8);

		ComponentDef component = new ComponentDef("Test", ns);
		
		HomeDef home = new HomeDef("TestHome", ns);
		home.setComponent(component);
		
		
		
		// Write Source Files -------------------------------------------------
		
		File outDir = new File(System.getProperty("user.dir"),
				"test/JavaClientLib/simple/xxx/JavaClientLib/src");

		List sourceFileList = new ArrayList();
		sourceFileList.addAll(iface.generateSourceFiles());
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
