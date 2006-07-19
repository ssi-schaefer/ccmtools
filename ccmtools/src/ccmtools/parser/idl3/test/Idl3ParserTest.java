package ccmtools.parser.idl3.test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.Metamodel.BaseIDL.MFieldDef;
import ccmtools.Metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.Metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.Metamodel.BaseIDL.MStringDef;
import ccmtools.Metamodel.BaseIDL.MStructDef;
import ccmtools.UI.Driver;
import ccmtools.test.CcmtoolsTestCase;
import ccmtools.utils.CcmModelHelper;

public class Idl3ParserTest 
	extends CcmtoolsTestCase
{
	private String ccmtoolsDir;
	private String testDir;
	
    /** Driver that handles user output */
    private static Driver uiDriver;
    
    private List includePaths = new ArrayList();
    
	public Idl3ParserTest(String name) throws FileNotFoundException
	{
		super(name);
		// get current working directory (this is where build.xml is executed)
		ccmtoolsDir = System.getProperty("user.dir");
		testDir = ccmtoolsDir + "/test/IDL3Parser";
		includePaths.add("testDir");
		uiDriver = new ccmtools.UI.ConsoleDriver(Driver.M_NONE);
	}
	
    public static Test suite()
    {
    		return new TestSuite(Idl3ParserTest.class);
    }

    
    // ------------------------------------------------------------------------
    // IDL3 parser test cases
    // ------------------------------------------------------------------------
    
	public void testStruct()
	{		
		MContainer ccmModel = null;
		try
		{
			String idlFile = testDir + "/struct/Person.idl";
			ccmModel = CcmModelHelper.loadCcmModel(uiDriver, idlFile, includePaths);
			System.out.println(includePaths);		
		}
		catch (CcmtoolsException e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}			

		System.out.println(ccmModel);
		List modelElements = ccmModel.getContentss();
			
		MStructDef struct = (MStructDef)modelElements.get(0);
		assertEquals("Person", struct.getIdentifier());
				
		List members = struct.getMembers();
				
		MFieldDef id = (MFieldDef)members.get(0);
		assertEquals("id", id.getIdentifier());
		if(id.getIdlType() instanceof MPrimitiveDef)
		{
			MPrimitiveDef primitive = (MPrimitiveDef)id.getIdlType();
			if(primitive.getKind() != MPrimitiveKind.PK_LONG)
				fail("id is not of type long !!");
		}
		else
		{
			fail("id is not a PrimitiveDef !!");
		}
		MFieldDef name = (MFieldDef)members.get(1);
			
		assertEquals("name", name.getIdentifier());
		if(!(name.getIdlType() instanceof MStringDef))
		{
			fail("name is not a StringDef !!");
		}				
	}
	
	public void testInterface()
	{		
		MContainer ccmModel = null;
		try
		{
			String idlFile = testDir + "/interface/InterfaceBasicTypeOperation.idl";
			ccmModel = CcmModelHelper.loadCcmModel(uiDriver, idlFile, includePaths);
			System.out.println(includePaths);		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}			

		System.out.println(ccmModel);
	}
}
