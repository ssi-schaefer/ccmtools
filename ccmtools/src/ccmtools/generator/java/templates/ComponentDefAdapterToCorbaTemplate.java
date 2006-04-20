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
  protected final String TEXT_3 = ";" + NL + "" + NL + "import java.util.logging.Logger;" + NL + "import ccm.local.Components.AlreadyConnected;" + NL + "import ccm.local.Components.CCMException;" + NL + "import ccm.local.Components.Cookie;" + NL + "import ccm.local.Components.CookieRequired;" + NL + "import ccm.local.Components.ExceededConnectionLimit;" + NL + "import ccm.local.Components.HomeExecutorBase;" + NL + "import ccm.local.Components.InvalidName;" + NL + "import ccm.local.Components.InvalidConnection;" + NL + "import ccm.local.Components.NoConnection;" + NL + "import ccm.local.ServiceLocator;" + NL + "import ccm.local.ServiceLocatorException;" + NL;
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = "  " + NL + "                 " + NL + "import org.omg.PortableServer.POA;" + NL + "" + NL + "public class ";
  protected final String TEXT_6 = "AdapterToCorba " + NL + "    implements ";
  protected final String TEXT_7 = NL + "{" + NL + "    private Logger logger = ServiceLocator.instance().getLogger();" + NL + "    " + NL + "    public static final String COMPONENT_REPOSITORY_ID = " + NL + "        \"";
  protected final String TEXT_8 = "\";" + NL + "" + NL + "    /** CORBA reference to a remote component */" + NL + "    private ";
  protected final String TEXT_9 = " remoteInterface;" + NL + "" + NL + "    /** Java references to local facet adapters */";
  protected final String TEXT_10 = "    ";
  protected final String TEXT_11 = NL;
  protected final String TEXT_12 = NL + NL + "    /** Java references to local receptacle adapters */";
  protected final String TEXT_13 = "    ";
  protected final String TEXT_14 = NL;
  protected final String TEXT_15 = NL + NL + "    /** CORBA references */" + NL + "    private POA componentPoa;" + NL + "    " + NL + "    " + NL + "    public ";
  protected final String TEXT_16 = "AdapterToCorba(";
  protected final String TEXT_17 = " remoteComponent)" + NL + "        throws CCMException" + NL + "    {    " + NL + "        logger.fine(\"remoteComponent = \" + remoteComponent);" + NL + "        this.remoteInterface = remoteComponent;" + NL + "        try" + NL + "        {" + NL + "            componentPoa = ServiceLocator.instance().createSessionComponentPoa(\"";
  protected final String TEXT_18 = "Poa\");" + NL + "        }" + NL + "        catch(ServiceLocatorException e)" + NL + "        {" + NL + "            throw new CCMException(e.getMessage());" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /* " + NL + "     * Supported interface methods " + NL + "     */" + NL + "    ";
  protected final String TEXT_19 = "    ";
  protected final String TEXT_20 = NL;
  protected final String TEXT_21 = "    " + NL + "    ";
  protected final String TEXT_22 = "    ";
  protected final String TEXT_23 = NL;
  protected final String TEXT_24 = NL + NL + "    " + NL + "    /** " + NL + "     * Equivalent interface methods " + NL + "     */" + NL;
  protected final String TEXT_25 = NL;
  protected final String TEXT_26 = "    " + NL + "            ";
  protected final String TEXT_27 = NL;
  protected final String TEXT_28 = NL;
  protected final String TEXT_29 = NL;
  protected final String TEXT_30 = NL + NL + "    " + NL + "    /** CCMObject interface methods */" + NL + "    " + NL + "    public void configuration_complete()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        if(remoteInterface != null)" + NL + "        {" + NL + "            remoteInterface.configuration_complete();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    public void remove()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        if(remoteInterface != null)" + NL + "        {" + NL + "            remoteInterface.remove();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    public HomeExecutorBase get_ccm_home()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        // TODO" + NL + "        throw new RuntimeException(\"Not implemented!\");" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Navigation interface methods */" + NL + "    " + NL + "    public Object provide_facet(String name)" + NL + "        throws InvalidName" + NL + "    {" + NL + "        logger.fine(\"name = \" + name);" + NL + "        if(name == null)" + NL + "        {" + NL + "            throw new InvalidName();" + NL + "        }";
  protected final String TEXT_31 = "        ";
  protected final String TEXT_32 = NL;
  protected final String TEXT_33 = NL + "        else" + NL + "        {" + NL + "            throw new InvalidName();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Receptacle interface methods */" + NL + "    " + NL + "    public Cookie connect(String name, Object localObject)" + NL + "        throws InvalidName, InvalidConnection," + NL + "               AlreadyConnected, ExceededConnectionLimit" + NL + "    {" + NL + "        logger.fine(\"name = \" + name + \", obj = \" + localObject);" + NL + "        if(name == null)" + NL + "        {" + NL + "            throw new InvalidName();" + NL + "        }";
  protected final String TEXT_34 = "     ";
  protected final String TEXT_35 = NL;
  protected final String TEXT_36 = NL + "        else" + NL + "        {" + NL + "            throw new InvalidName();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    public void disconnect(String name, Cookie ck)" + NL + "        throws InvalidName, InvalidConnection," + NL + "               CookieRequired, NoConnection" + NL + "    {" + NL + "        logger.fine(\"name = \" + name + \", ck = \" + ck);" + NL + "        if(name == null)" + NL + "        {" + NL + "            throw new InvalidName();" + NL + "        }";
  protected final String TEXT_37 = "     ";
  protected final String TEXT_38 = NL;
  protected final String TEXT_39 = NL + "        else" + NL + "        {" + NL + "            throw new InvalidName();" + NL + "        }" + NL + "    }" + NL + "}";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
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
    stringBuffer.append(component.generateRepositoryId());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(component.generateAbsoluteIdlName());
    stringBuffer.append(TEXT_9);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();) 
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_10);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(provides.generateFacetAdapterDeclaration());
    
}

    stringBuffer.append(TEXT_12);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();) 
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_13);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(uses.generateReceptacleReferenceAdapterToCorba());
    
}

    stringBuffer.append(TEXT_15);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_16);
    stringBuffer.append(component.generateAbsoluteIdlName());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_18);
    
for(Iterator i=component.getSupports().iterator(); i.hasNext();)
{
	SupportsDef supports = (SupportsDef)i.next();
	InterfaceDef iface = supports.getInterface();
	for(Iterator j=iface.getAllAttributes().iterator(); j.hasNext(); )
	{
        AttributeDef attr = (AttributeDef)j.next();

    stringBuffer.append(TEXT_19);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(attr.generateAdapterToCorba());
    
    }
}

    stringBuffer.append(TEXT_21);
    
for(Iterator i=component.getSupports().iterator(); i.hasNext();)
{
	SupportsDef supports = (SupportsDef)i.next();
	InterfaceDef iface = supports.getInterface();
	for(Iterator j=iface.getAllOperations().iterator(); j.hasNext(); )
	{
        OperationDef op = (OperationDef)j.next();

    stringBuffer.append(TEXT_22);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(op.generateAdapterToCorba());
    
    }
} 

    stringBuffer.append(TEXT_24);
    
for(Iterator i = component.getAttributes().iterator(); i.hasNext();)
{
    AttributeDef attr = (AttributeDef)i.next();

    stringBuffer.append(TEXT_25);
    stringBuffer.append(attr.generateAdapterToCorba());
    
}

    stringBuffer.append(TEXT_26);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();)
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_27);
    stringBuffer.append(provides.generateEquivalentMethodAdapterToCorba());
    
}

    stringBuffer.append(TEXT_28);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();)
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_29);
    stringBuffer.append(uses.generateEquivalentMethodAdapterToCorba());
    
}

    stringBuffer.append(TEXT_30);
    		
for(Iterator i = component.getFacet().iterator(); i.hasNext();) 
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_31);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(provides.generateNavigationMethodAdapterToCorba());
    
}

    stringBuffer.append(TEXT_33);
    		
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();) 
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_34);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(uses.generateReceptacleConnectMethodAdapterToCorba());
    
}

    stringBuffer.append(TEXT_36);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();) 
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_37);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(uses.generateReceptacleDisconnectMethodAdapterToCorba());
    
}

    stringBuffer.append(TEXT_39);
    return stringBuffer.toString();
  }
}
