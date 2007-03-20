package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class ComponentDefAdapterLocalTemplate
{
  protected static String nl;
  public static synchronized ComponentDefAdapterLocalTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ComponentDefAdapterLocalTemplate result = new ComponentDefAdapterLocalTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_3 = ";" + NL;
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = "  " + NL + "                 " + NL + "public class ";
  protected final String TEXT_6 = "Adapter " + NL + "    implements ";
  protected final String TEXT_7 = NL + "{" + NL + "    private java.util.logging.Logger logger = ccmtools.local.ServiceLocator.instance().getLogger();" + NL + "    " + NL + "    private ";
  protected final String TEXT_8 = " localInterface;" + NL + "    private ";
  protected final String TEXT_9 = "_Context ctx;" + NL + "    private Components.Assembly assembly;" + NL + "" + NL + "    /** Facet adapter references */";
  protected final String TEXT_10 = NL;
  protected final String TEXT_11 = "  " + NL + "\t" + NL + "    /** Receptacle references */";
  protected final String TEXT_12 = NL;
  protected final String TEXT_13 = "  " + NL + "\t\t" + NL + "\t\t" + NL + "    protected ";
  protected final String TEXT_14 = "Adapter()" + NL + "    {" + NL + "        this(null, null);" + NL + "    }" + NL + "\t" + NL + "    public ";
  protected final String TEXT_15 = "Adapter(";
  protected final String TEXT_16 = " localInterface)" + NL + "    {" + NL + "        this(localInterface, null);" + NL + "    }" + NL + "\t" + NL + "    public ";
  protected final String TEXT_17 = "Adapter(";
  protected final String TEXT_18 = " localInterface, Components.Assembly assembly)" + NL + "    {" + NL + "        logger.fine(\"localInterface = \" + localInterface + \", \" + assembly);" + NL + "        this.localInterface = localInterface;" + NL + "        this.assembly = assembly;" + NL + "    }" + NL + "\t" + NL + "\t" + NL + "    /* " + NL + "     * Supported interface methods " + NL + "     */" + NL + "" + NL + "    /** Supported interface attributes */";
  protected final String TEXT_19 = NL;
  protected final String TEXT_20 = "    " + NL + "    " + NL + "    " + NL + "    /** Supported interface methods */";
  protected final String TEXT_21 = NL;
  protected final String TEXT_22 = "    " + NL + "" + NL + "\t" + NL + "    /* " + NL + "     * Equivalent interface methods " + NL + "     */" + NL + "\t" + NL + "    /** Attribute equivalent methods */";
  protected final String TEXT_23 = NL;
  protected final String TEXT_24 = "    " + NL + "" + NL + "    /** Facet equivalent methods */    ";
  protected final String TEXT_25 = NL;
  protected final String TEXT_26 = "    " + NL + "    " + NL + "    /** Receptacle equivalent methods */";
  protected final String TEXT_27 = NL;
  protected final String TEXT_28 = NL + "\t" + NL + "    /** CCMObject interface methods */" + NL + "\t" + NL + "    public void configuration_complete()" + NL + "        throws Components.InvalidConfiguration" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        ctx = new ";
  protected final String TEXT_29 = "_ContextImpl(this); " + NL + "        try" + NL + "        {" + NL + "            if(assembly != null) " + NL + "            { " + NL + "                assembly.configuration_complete(); " + NL + "            }" + NL + "            localInterface.set_session_context(ctx);" + NL + "            localInterface.ccm_activate();" + NL + "        }" + NL + "        catch(Components.CCMException e)" + NL + "        {" + NL + "            throw new Components.InvalidConfiguration();" + NL + "        }" + NL + "    }" + NL + "" + NL + "    public void remove()" + NL + "        throws Components.RemoveFailure" + NL + "    { " + NL + "        logger.fine(\"\");" + NL + "        try " + NL + "        {" + NL + "            localInterface.ccm_remove();" + NL + "            if(assembly != null) " + NL + "            { " + NL + "                assembly.tear_down(); " + NL + "                assembly = null;" + NL + "            }" + NL + "        }" + NL + "        catch(Components.CCMException e)" + NL + "        {" + NL + "            throw new Components.RemoveFailure();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    public Components.CCMHome get_ccm_home()" + NL + "    {" + NL + "        throw new RuntimeException(\"Not implemented!\");" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Navigation interface methods */" + NL + "    " + NL + "    public Object provide_facet(String name)" + NL + "        throws Components.InvalidName" + NL + "    {" + NL + "        logger.fine(\"name = \" + name);" + NL + "        if(name == null)" + NL + "        {" + NL + "            throw new Components.InvalidName();" + NL + "        }";
  protected final String TEXT_30 = NL;
  protected final String TEXT_31 = "   " + NL + "        throw new Components.InvalidName();" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Receptacle methods */" + NL + "    " + NL + "    public Components.Cookie connect(String name, Object obj)" + NL + "        throws Components.InvalidName, Components.InvalidConnection, " + NL + "        Components.AlreadyConnected, Components.ExceededConnectionLimit" + NL + "    {" + NL + "        logger.fine(\"name = \" + name + \", obj = \" + obj);" + NL + "        if(name == null)" + NL + "        {" + NL + "            throw new Components.InvalidName();" + NL + "        }" + NL + "        if(obj == null)" + NL + "        {" + NL + "            throw new Components.InvalidConnection();" + NL + "        }    ";
  protected final String TEXT_32 = "     ";
  protected final String TEXT_33 = NL;
  protected final String TEXT_34 = "     " + NL + "        else" + NL + "        {" + NL + "            throw new Components.InvalidName();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    public void disconnect(String name, Components.Cookie ck)" + NL + "        throws Components.InvalidName, Components.InvalidConnection, " + NL + "        Components.CookieRequired, Components.NoConnection" + NL + "    {" + NL + "        logger.fine(\"name = \" + name + \", ck = \" + ck );" + NL + "        if(name == null)" + NL + "        {" + NL + "            throw new Components.InvalidName();" + NL + "        }" + NL + "        if(ck == null)" + NL + "        {" + NL + "            throw new Components.CookieRequired();" + NL + "        } ";
  protected final String TEXT_35 = "     ";
  protected final String TEXT_36 = NL;
  protected final String TEXT_37 = "     " + NL + "        else" + NL + "        {" + NL + "            throw new Components.InvalidName();" + NL + "        }" + NL + "    }    " + NL + "}";
  protected final String TEXT_38 = NL;

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
    stringBuffer.append(component.generateCcmIdentifier());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(component.generateCcmIdentifier());
    stringBuffer.append(TEXT_9);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();)
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_10);
    stringBuffer.append(provides.generateFacetAdapterReference());
    
}

    stringBuffer.append(TEXT_11);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();)
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_12);
    stringBuffer.append(uses.generateReceptacleAdapterReference());
    
}

    stringBuffer.append(TEXT_13);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(component.generateCcmIdentifier());
    stringBuffer.append(TEXT_16);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(component.generateCcmIdentifier());
    stringBuffer.append(TEXT_18);
    
for(Iterator i = component.getSupports().iterator(); i.hasNext();)
{
    SupportsDef supports = (SupportsDef)i.next();
    for(Iterator j = supports.getInterface().getAllAttributes().iterator(); j.hasNext();)
    {
    	AttributeDef attr = (AttributeDef)j.next();

    stringBuffer.append(TEXT_19);
    stringBuffer.append(attr.generateAdapterLocal());
    
	}
}

    stringBuffer.append(TEXT_20);
    
for(Iterator i = component.getSupports().iterator(); i.hasNext();)
{
    SupportsDef supports = (SupportsDef)i.next();
    for(Iterator j = supports.getInterface().getAllOperations().iterator(); j.hasNext();)
    {
    	OperationDef op = (OperationDef)j.next();

    stringBuffer.append(TEXT_21);
    stringBuffer.append(op.generateAdapterLocal());
    
	}
}

    stringBuffer.append(TEXT_22);
    
for(Iterator i = component.getAttributes().iterator(); i.hasNext();)
{
    AttributeDef attr = (AttributeDef)i.next();

    stringBuffer.append(TEXT_23);
    stringBuffer.append(attr.generateAdapterLocal());
    
}

    stringBuffer.append(TEXT_24);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();)
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_25);
    stringBuffer.append(provides.generateEquivalentMethodAdapterLocal());
    
}

    stringBuffer.append(TEXT_26);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();)
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_27);
    stringBuffer.append(uses.generateEquivalentMethodAdapterLocal());
    
}

    stringBuffer.append(TEXT_28);
    stringBuffer.append(component.generateCcmIdentifier());
    stringBuffer.append(TEXT_29);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();)
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_30);
    stringBuffer.append(provides.generateNavigationMethodAdapterLocal());
    
}

    stringBuffer.append(TEXT_31);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();) 
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_32);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(uses.generateReceptacleConnectMethodAdapterLocal());
    
}

    stringBuffer.append(TEXT_34);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();) 
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_35);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(uses.generateReceptacleDisconnectMethodAdapterLocal());
    
}

    stringBuffer.append(TEXT_37);
    stringBuffer.append(TEXT_38);
    return stringBuffer.toString();
  }
}
