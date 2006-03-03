package ccmtools.generator.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;
import ccmtools.CodeGenerator.CcmGraphTraverser;
import ccmtools.CodeGenerator.GraphTraverser;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.UI.Driver;
import ccmtools.generator.java.metamodel.ComponentDef;
import ccmtools.generator.java.metamodel.HomeDef;
import ccmtools.generator.java.metamodel.InterfaceDef;
import ccmtools.generator.java.metamodel.ModelRepository;
import ccmtools.generator.java.ui.CommandLineParameters;
import ccmtools.utils.CcmModelHelper;
import ccmtools.utils.Code;

public class JavaLocalComponentGenerator
{
	/** UI driver for generator messages */
	protected Driver uiDriver;
	
	/** Command line parameters */
	protected CommandLineParameters parameters;
	
	/** Java standard logger object */
	protected Logger logger;
	
	
	public JavaLocalComponentGenerator(CommandLineParameters parameters, Driver uiDriver)
	{
		this.uiDriver = uiDriver;
		this.parameters = (CommandLineParameters)parameters;		                           
        logger = Logger.getLogger("ccm.generator.java.local");
        logger.fine("JavaLocalComponentGenerator()");
        printVersion();
	}
	
	
	public void generate()
		throws CcmtoolsException
	{
		logger.fine("enter generate()");
		try
		{
			for (Iterator i = parameters.getIdlFiles().iterator(); i.hasNext();)
			{
				String idlFile = (String) i.next();
				MContainer ccmModel = 
					CcmModelHelper.loadCcmModel(uiDriver, idlFile, parameters.getIncludePaths());

				// Transform CCM Model to Java Implementation Model
				GraphTraverser traverser = new CcmGraphTraverser();
				CcmToJavaModelMapper nodeHandler = new CcmToJavaModelMapper();
				traverser.addHandler(nodeHandler);
				traverser.traverseGraph(ccmModel);

				// Query the Java Implementation Model and generate all source
				// file objects for the Java Client Library
				ModelRepository javaModel = nodeHandler.getJavaModel();
				List sourceFileList = new ArrayList();
				for (Iterator j = javaModel.findAllInterfaces().iterator(); j.hasNext();)
				{
					InterfaceDef javaIface = (InterfaceDef) j.next();
					sourceFileList.addAll(javaIface.generateLocalInterfaceSourceFiles());
					sourceFileList.addAll(javaIface.generateLocalComponentSourceFiles());
				}
				for (Iterator j = javaModel.findAllComponents().iterator(); j.hasNext();)
				{
					ComponentDef javaComponent = (ComponentDef) j.next();
					sourceFileList.addAll(javaComponent.generateLocalInterfaceSourceFiles());
					sourceFileList.addAll(javaComponent.generateLocalComponentSourceFiles());
				}
				for (Iterator j = javaModel.findAllHomes().iterator(); j.hasNext();)
				{
					HomeDef javaHome = (HomeDef) j.next();
					sourceFileList.addAll(javaHome.generateLocalInterfaceSourceFiles());
					sourceFileList.addAll(javaHome.generateLocalComponentSourceFiles());
				}
				// Save all source file objects
				Code.writeSourceCodeFiles(uiDriver, parameters.getOutDir(), sourceFileList);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CcmtoolsException("Error in JavaLocalComponentGenerator: " + e.getMessage());
		}
		logger.fine("leave generate()");
	}
	
    private void printVersion()
    {
        uiDriver.println("Java Local Component Generator, " + 
                         Constants.VERSION_TEXT + "\n");
    }
}
