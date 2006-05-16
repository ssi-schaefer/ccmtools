package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class ComponentDefAdapterFromCorbaTemplate
{
  protected static String nl;
  public static synchronized ComponentDefAdapterFromCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ComponentDefAdapterFromCorbaTemplate result = new ComponentDefAdapterFromCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "" + NL + "import java.util.logging.Logger;" + NL + "import java.util.Iterator;" + NL + "import java.util.Map;" + NL + "import java.util.HashMap;" + NL + "" + NL + "import Components.ccm.local.AlreadyConnected;" + NL + "import Components.ccm.local.Cookie;" + NL + "import Components.ccm.local.CookieImpl;" + NL + "import Components.ccm.local.CCMException;   " + NL + "import Components.ccm.local.ExceededConnectionLimit;" + NL + "import Components.ccm.local.InvalidConfiguration;" + NL + "import Components.ccm.local.InvalidConnection;" + NL + "import Components.ccm.local.NoConnection;" + NL + "import Components.ccm.local.RemoveFailure;" + NL + "" + NL + "import ccm.local.ServiceLocator;" + NL + "import ccm.remote.CCMSessionContainer;" + NL + "" + NL + "import org.omg.PortableServer.Servant;" + NL + "import org.omg.CORBA.BAD_OPERATION;" + NL + "          ";
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = NL + NL + "public class ";
  protected final String TEXT_6 = "AdapterFromCorba " + NL + "     extends ";
  protected final String TEXT_7 = "POA" + NL + "{" + NL + "    /** Default logger instance */" + NL + "    private Logger logger = ServiceLocator.instance().getLogger();" + NL + "  " + NL + "    /** CCM session container instance */  " + NL + "    private CCMSessionContainer container;" + NL + "" + NL + "    /** Local component instance */" + NL + "    private ";
  protected final String TEXT_8 = " localInterface;" + NL + "        " + NL + "    /** CORBA facet references */";
  protected final String TEXT_9 = "    ";
  protected final String TEXT_10 = NL;
  protected final String TEXT_11 = "   " + NL + "" + NL + "    /** CORBA receptacle references */";
  protected final String TEXT_12 = "    ";
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = "   " + NL + "        " + NL + "    public ";
  protected final String TEXT_15 = "AdapterFromCorba(CCMSessionContainer container, ";
  protected final String TEXT_16 = NL + "        ";
  protected final String TEXT_17 = " localComponent)" + NL + "    {    " + NL + "        logger.fine(\"localComponent = \" + localComponent);" + NL + "        this.container = container;" + NL + "        this.localInterface = localComponent;";
  protected final String TEXT_18 = "    ";
  protected final String TEXT_19 = NL;
  protected final String TEXT_20 = "              ";
  protected final String TEXT_21 = "    ";
  protected final String TEXT_22 = NL;
  protected final String TEXT_23 = "     " + NL + "    }" + NL + "    " + NL + "    " + NL + "    /* " + NL + "     * Supported interface methods " + NL + "     */" + NL + "" + NL + "    /** Supported interface attributes */";
  protected final String TEXT_24 = NL;
  protected final String TEXT_25 = "    " + NL + "    " + NL + "    /** Supported interface methods */";
  protected final String TEXT_26 = NL;
  protected final String TEXT_27 = "    " + NL + "    " + NL + "    " + NL + "    /** " + NL + "     * Equivalent interface methods " + NL + "     */" + NL + "     " + NL + "    /** Attribute equivalent methods */" + NL + "    ";
  protected final String TEXT_28 = NL;
  protected final String TEXT_29 = "    " + NL + "" + NL + "" + NL + "    /** Facet equivalent methods */    " + NL + "    ";
  protected final String TEXT_30 = NL;
  protected final String TEXT_31 = NL + "     " + NL + "    /** Receptacle equivalent methods */" + NL + "         ";
  protected final String TEXT_32 = NL;
  protected final String TEXT_33 = NL + "    " + NL + "    /** " + NL + "     * The following operations are defined in the Components::CCMObject" + NL + "     * interface." + NL + "     */" + NL + "    " + NL + "    public Components.CCMHome get_ccm_home()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        return container.getCorbaHome();" + NL + "    }" + NL + "" + NL + "    public void configuration_complete()" + NL + "        throws Components.InvalidConfiguration" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        try" + NL + "        {" + NL + "            localInterface.configuration_complete();" + NL + "        }" + NL + "        catch(InvalidConfiguration e)" + NL + "        {" + NL + "            e.printStackTrace();" + NL + "            throw new Components.InvalidConfiguration();" + NL + "        }" + NL + "    }" + NL + "" + NL + "    public void remove()" + NL + "        throws Components.RemoveFailure" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        try" + NL + "        {" + NL + "            if(localInterface == null)" + NL + "            {" + NL + "                throw new Components.RemoveFailure(\"Can't remove component because its \" " + NL + "                       + \"local reference is null!\");" + NL + "            }" + NL + "            else" + NL + "            {" + NL + "                localInterface.remove();" + NL + "            }" + NL + "        }" + NL + "        catch(RemoveFailure e)" + NL + "        {" + NL + "        \t    e.printStackTrace();" + NL + "            throw new Components.RemoveFailure(e.getMessage());" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** " + NL + "     * The following operations are defined in the Components::Navigation" + NL + "     * interface." + NL + "     */" + NL + "" + NL + "    public org.omg.CORBA.Object provide_facet(String name) " + NL + "        throws Components.InvalidName" + NL + "    {" + NL + "        logger.fine(\"\");\t" + NL + "        if(name == null)" + NL + "        {" + NL + "            throw new Components.InvalidName();" + NL + "        }";
  protected final String TEXT_34 = "        ";
  protected final String TEXT_35 = NL;
  protected final String TEXT_36 = NL + "        else" + NL + "        {" + NL + "            throw new Components.InvalidName();" + NL + "        }" + NL + "    }" + NL + "" + NL + "" + NL + "    /** " + NL + "     * The following operations are defined in the Components::Receptacles" + NL + "     * interface." + NL + "     */" + NL + "     " + NL + "    public Components.Cookie connect(String name, org.omg.CORBA.Object obj) " + NL + "        throws Components.InvalidName, " + NL + "        Components.InvalidConnection, " + NL + "        Components.AlreadyConnected, " + NL + "        Components.ExceededConnectionLimit" + NL + "    {" + NL + "        logger.fine(\"name = \" + name + \", obj = \" + obj);" + NL + "        if(name == null) " + NL + "        {" + NL + "            throw new Components.InvalidName(\"connection name is null!\");" + NL + "        }";
  protected final String TEXT_37 = "        ";
  protected final String TEXT_38 = NL;
  protected final String TEXT_39 = NL + "        else" + NL + "        {" + NL + "            throw new Components.InvalidName(name);" + NL + "        }\t" + NL + "    }" + NL + "" + NL + "    public void disconnect(String name, Components.Cookie ck) " + NL + "        throws Components.InvalidName, " + NL + "        Components.InvalidConnection, " + NL + "        Components.CookieRequired, " + NL + "        Components.NoConnection" + NL + "    {" + NL + "        logger.fine(\"name = \" + name + \", ck = \" + ck);" + NL + "        if(name == null)" + NL + "        {" + NL + "            throw new Components.InvalidName(\"connection name is null!\");" + NL + "        }";
  protected final String TEXT_40 = "        ";
  protected final String TEXT_41 = NL;
  protected final String TEXT_42 = NL + "        else" + NL + "        {" + NL + "            throw new Components.InvalidName(name);" + NL + "        }\t" + NL + "    }" + NL + "}";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     ComponentDef component = (ComponentDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(component.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(component.generateJavaRemoteNamespace());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(component.generateJavaImportStatements(component.generateJavaRemoteNamespace()));
    stringBuffer.append(TEXT_5);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(component.generateAbsoluteIdlName());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_8);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();) 
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(provides.generateCorbaFacetReferenceDeclaration());
    
}

    stringBuffer.append(TEXT_11);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();) 
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(uses.generateCorbaReceptacleReferenceDeclaration());
    
}

    stringBuffer.append(TEXT_14);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_17);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();) 
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(provides.generateCorbaFacetReferenceInit());
    
}

    stringBuffer.append(TEXT_20);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();) 
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_21);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(uses.generateCorbaReceptacleReferenceInit());
    
}

    stringBuffer.append(TEXT_23);
    
for(Iterator i = component.getSupports().iterator(); i.hasNext();)
{
    SupportsDef supports = (SupportsDef)i.next();
    for(Iterator j = supports.getInterface().getAllAttributes().iterator(); j.hasNext();)
    {
    	AttributeDef attr = (AttributeDef)j.next();

    stringBuffer.append(TEXT_24);
    stringBuffer.append(attr.generateAdapterFromCorba());
    
	}
}

    stringBuffer.append(TEXT_25);
    
for(Iterator i = component.getSupports().iterator(); i.hasNext();)
{
    SupportsDef supports = (SupportsDef)i.next();
    for(Iterator j = supports.getInterface().getAllOperations().iterator(); j.hasNext();)
    {
    	OperationDef op = (OperationDef)j.next();

    stringBuffer.append(TEXT_26);
    stringBuffer.append(op.generateAdapterFromCorba());
    
	}
}

    stringBuffer.append(TEXT_27);
    
for(Iterator i = component.getAttributes().iterator(); i.hasNext();)
{
    AttributeDef attr = (AttributeDef)i.next();

    stringBuffer.append(TEXT_28);
    stringBuffer.append(attr.generateAdapterFromCorba());
    
}

    stringBuffer.append(TEXT_29);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();)
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_30);
    stringBuffer.append(provides.generateEquivalentMethodAdapterFromCorba());
    
}

    stringBuffer.append(TEXT_31);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();)
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_32);
    stringBuffer.append(uses.generateEquivalentMethodAdapterFromCorba());
    
}

    stringBuffer.append(TEXT_33);
    		
for(Iterator i = component.getFacet().iterator(); i.hasNext();) 
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_34);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(provides.generateNavigationMethodAdapterFromCorba());
    
}

    stringBuffer.append(TEXT_36);
    		
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();) 
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_37);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(uses.generateReceptacleConnectMethodAdapterFromCorba());
    
}

    stringBuffer.append(TEXT_39);
    		
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();) 
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_40);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(uses.generateReceptacleDisconnectMethodAdapterFromCorba());
    
}

    stringBuffer.append(TEXT_42);
    return stringBuffer.toString();
  }
}
