package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class ComponentDefAdapterToCorbaTemplate
{
  protected static String nl;
  public static synchronized ComponentDefAdapterToCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ComponentDefAdapterToCorbaTemplate result = new ComponentDefAdapterToCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "" + NL + "import java.util.logging.Logger;" + NL + "import java.util.Map;" + NL + "import java.util.HashMap;" + NL + "" + NL + "import org.omg.PortableServer.POA;" + NL + "import org.omg.PortableServer.POAPackage.ServantNotActive;" + NL + "import org.omg.PortableServer.POAPackage.WrongPolicy;" + NL + "import org.omg.PortableServer.Servant;" + NL + "" + NL + "import Components.ccm.local.AlreadyConnected;" + NL + "import Components.ccm.local.CCMException;" + NL + "import Components.ccm.local.CCMExceptionReason;" + NL + "import Components.ccm.local.Cookie;" + NL + "import Components.ccm.local.CookieImpl;" + NL + "import Components.ccm.local.CookieRequired;" + NL + "import Components.ccm.local.ExceededConnectionLimit;" + NL + "import Components.ccm.local.CCMHome;" + NL + "import Components.ccm.local.InvalidName;" + NL + "import Components.ccm.local.InvalidConnection;" + NL + "import Components.ccm.local.InvalidConfiguration; " + NL + "import Components.ccm.local.NoConnection;" + NL + "import Components.ccm.local.RemoveFailure; " + NL + "" + NL + "import ccm.local.ServiceLocator;" + NL + "import ccm.local.ServiceLocatorException;" + NL;
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = "  " + NL + "                 " + NL + "" + NL + "public class ";
  protected final String TEXT_6 = "AdapterToCorba " + NL + "    implements ";
  protected final String TEXT_7 = NL + "{" + NL + "    private Logger logger = ServiceLocator.instance().getLogger();" + NL + "" + NL + "    /** CORBA reference to a remote component */" + NL + "    private ";
  protected final String TEXT_8 = " remoteInterface;" + NL + "" + NL + "    /** Java references to local facet adapters */";
  protected final String TEXT_9 = "    ";
  protected final String TEXT_10 = NL;
  protected final String TEXT_11 = NL + NL + "    /** Java references to local receptacle adapters */";
  protected final String TEXT_12 = "    ";
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = NL + NL + "    /** CORBA references */" + NL + "    private POA componentPoa;" + NL + "    " + NL + "    " + NL + "    public ";
  protected final String TEXT_15 = "AdapterToCorba(";
  protected final String TEXT_16 = " remoteComponent)" + NL + "        throws CCMException" + NL + "    {    " + NL + "        logger.fine(\"remoteComponent = \" + remoteComponent);" + NL + "        this.remoteInterface = remoteComponent;" + NL + "        try" + NL + "        {" + NL + "            componentPoa = ServiceLocator.instance().createSessionComponentPoa(\"";
  protected final String TEXT_17 = "Poa\");" + NL + "        }" + NL + "        catch(ServiceLocatorException e)" + NL + "        {" + NL + "            throw new CCMException(e.getMessage(), CCMExceptionReason.SYSTEM_ERROR);" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /* " + NL + "     * Supported interface methods " + NL + "     */" + NL + "    ";
  protected final String TEXT_18 = "    ";
  protected final String TEXT_19 = NL;
  protected final String TEXT_20 = "    " + NL + "    ";
  protected final String TEXT_21 = "    ";
  protected final String TEXT_22 = NL;
  protected final String TEXT_23 = NL + NL + "    " + NL + "    /** " + NL + "     * Equivalent interface methods " + NL + "     */" + NL;
  protected final String TEXT_24 = NL;
  protected final String TEXT_25 = "    " + NL + "            ";
  protected final String TEXT_26 = NL;
  protected final String TEXT_27 = NL;
  protected final String TEXT_28 = NL;
  protected final String TEXT_29 = NL + NL + "    " + NL + "    /** CCMObject interface methods */" + NL + "    " + NL + "    public void configuration_complete()" + NL + "        throws InvalidConfiguration" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        if(remoteInterface != null)" + NL + "        {" + NL + "            try" + NL + "            {" + NL + "                remoteInterface.configuration_complete();" + NL + "            }" + NL + "            catch(Components.InvalidConfiguration e)" + NL + "            {" + NL + "                e.printStackTrace();" + NL + "                throw new InvalidConfiguration();" + NL + "            }    " + NL + "        }" + NL + "    }" + NL + "    " + NL + "    public void remove()" + NL + "        throws RemoveFailure" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        try" + NL + "        {" + NL + "            if(remoteInterface == null)" + NL + "            {" + NL + "                throw new RemoveFailure(\"Can't remove component because its remote reference is null!\");           " + NL + "            " + NL + "            }" + NL + "            else" + NL + "            {" + NL + "                remoteInterface.remove();" + NL + "            }" + NL + "        }" + NL + "        catch(Components.RemoveFailure e)" + NL + "        {" + NL + "            throw new RemoveFailure();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    public CCMHome get_ccm_home()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        // TODO" + NL + "        throw new RuntimeException(\"Not implemented!\");" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Navigation interface methods */" + NL + "    " + NL + "    public Object provide_facet(String name)" + NL + "        throws InvalidName" + NL + "    {" + NL + "        logger.fine(\"name = \" + name);" + NL + "        if(name == null)" + NL + "        {" + NL + "            throw new InvalidName();" + NL + "        }";
  protected final String TEXT_30 = "        ";
  protected final String TEXT_31 = NL;
  protected final String TEXT_32 = NL + "        else" + NL + "        {" + NL + "            throw new InvalidName();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Receptacle interface methods */" + NL + "    " + NL + "    public Cookie connect(String name, Object localObject)" + NL + "        throws InvalidName, InvalidConnection," + NL + "               AlreadyConnected, ExceededConnectionLimit" + NL + "    {" + NL + "        logger.fine(\"name = \" + name + \", obj = \" + localObject);" + NL + "        if(name == null)" + NL + "        {" + NL + "            throw new InvalidName();" + NL + "        }";
  protected final String TEXT_33 = "     ";
  protected final String TEXT_34 = NL;
  protected final String TEXT_35 = NL + "        else" + NL + "        {" + NL + "            throw new InvalidName();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    public void disconnect(String name, Cookie ck)" + NL + "        throws InvalidName, InvalidConnection," + NL + "               CookieRequired, NoConnection" + NL + "    {" + NL + "        logger.fine(\"name = \" + name + \", ck = \" + ck);" + NL + "        if(name == null)" + NL + "        {" + NL + "            throw new InvalidName();" + NL + "        }";
  protected final String TEXT_36 = "     ";
  protected final String TEXT_37 = NL;
  protected final String TEXT_38 = NL + "        else" + NL + "        {" + NL + "            throw new InvalidName();" + NL + "        }" + NL + "    }" + NL + "}";
  protected final String TEXT_39 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     ComponentDef component = (ComponentDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(component.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(component.generateJavaNamespace());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(component.generateJavaImportStatements());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(component.generateAbsoluteIdlName());
    stringBuffer.append(TEXT_8);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();) 
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(provides.generateFacetAdapterDeclaration());
    
}

    stringBuffer.append(TEXT_11);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();) 
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(uses.generateReceptacleReferenceAdapterToCorba());
    
}

    stringBuffer.append(TEXT_14);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(component.generateAbsoluteIdlName());
    stringBuffer.append(TEXT_16);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_17);
    
for(Iterator i=component.getSupports().iterator(); i.hasNext();)
{
	SupportsDef supports = (SupportsDef)i.next();
	InterfaceDef iface = supports.getInterface();
	for(Iterator j=iface.getAllAttributes().iterator(); j.hasNext(); )
	{
        AttributeDef attr = (AttributeDef)j.next();

    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(attr.generateAdapterToCorba());
    
    }
}

    stringBuffer.append(TEXT_20);
    
for(Iterator i=component.getSupports().iterator(); i.hasNext();)
{
	SupportsDef supports = (SupportsDef)i.next();
	InterfaceDef iface = supports.getInterface();
	for(Iterator j=iface.getAllOperations().iterator(); j.hasNext(); )
	{
        OperationDef op = (OperationDef)j.next();

    stringBuffer.append(TEXT_21);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(op.generateAdapterToCorba());
    
    }
} 

    stringBuffer.append(TEXT_23);
    
for(Iterator i = component.getAttributes().iterator(); i.hasNext();)
{
    AttributeDef attr = (AttributeDef)i.next();

    stringBuffer.append(TEXT_24);
    stringBuffer.append(attr.generateAdapterToCorba());
    
}

    stringBuffer.append(TEXT_25);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();)
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_26);
    stringBuffer.append(provides.generateEquivalentMethodAdapterToCorba());
    
}

    stringBuffer.append(TEXT_27);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();)
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_28);
    stringBuffer.append(uses.generateEquivalentMethodAdapterToCorba());
    
}

    stringBuffer.append(TEXT_29);
    		
for(Iterator i = component.getFacet().iterator(); i.hasNext();) 
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_30);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(provides.generateNavigationMethodAdapterToCorba());
    
}

    stringBuffer.append(TEXT_32);
    		
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();) 
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_33);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(uses.generateReceptacleConnectMethodAdapterToCorba());
    
}

    stringBuffer.append(TEXT_35);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();) 
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_36);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(uses.generateReceptacleDisconnectMethodAdapterToCorba());
    
}

    stringBuffer.append(TEXT_38);
    stringBuffer.append(TEXT_39);
    return stringBuffer.toString();
  }
}
