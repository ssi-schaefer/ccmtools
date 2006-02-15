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
  protected final String TEXT_3 = ";" + NL + "                 " + NL + "public class ";
  protected final String TEXT_4 = "Adapter " + NL + "    implements ";
  protected final String TEXT_5 = NL + "{" + NL + "    private ";
  protected final String TEXT_6 = " localInterface;" + NL + "    private ";
  protected final String TEXT_7 = "_Context ctx;" + NL + "" + NL + "    /** Facet adapter references */";
  protected final String TEXT_8 = NL;
  protected final String TEXT_9 = "  " + NL + "\t" + NL + "    /** Receptacle references */";
  protected final String TEXT_10 = NL;
  protected final String TEXT_11 = "  " + NL + "\t\t" + NL + "    protected ";
  protected final String TEXT_12 = "Adapter()" + NL + "    {" + NL + "    }" + NL + "\t" + NL + "    public ";
  protected final String TEXT_13 = "Adapter(";
  protected final String TEXT_14 = " localInterface)" + NL + "    {" + NL + "        System.out.println(\"+";
  protected final String TEXT_15 = "Adapter.";
  protected final String TEXT_16 = "Adapter()\");" + NL + "        this.localInterface = localInterface;" + NL + "    }" + NL + "\t" + NL + "\t" + NL + "    /** Supported interface methods */ " + NL + "\t" + NL + "\t" + NL + "\t" + NL + "    /** " + NL + "     * Equivalent interface methods " + NL + "     */" + NL + "\t" + NL + "    /** Attribute equivalent methods */";
  protected final String TEXT_17 = NL;
  protected final String TEXT_18 = "    " + NL + "" + NL + "    /** Facet equivalent methods */    ";
  protected final String TEXT_19 = NL;
  protected final String TEXT_20 = "    " + NL + "    " + NL + "    /** Receptacle equivalent methods */";
  protected final String TEXT_21 = NL;
  protected final String TEXT_22 = NL + "\t" + NL + "    /** CCMObject interface methods */" + NL + "\t" + NL + "    public void configuration_complete()" + NL + "        throws ccm.local.Components.InvalidConfiguration" + NL + "    {" + NL + "        System.out.println(\" ";
  protected final String TEXT_23 = "Adapter.configuration_complete()\");" + NL + "        ctx = new ";
  protected final String TEXT_24 = "_ContextImpl(this); " + NL + "        try" + NL + "        {" + NL + "            localInterface.set_session_context(ctx);" + NL + "            localInterface.ccm_activate();" + NL + "        }" + NL + "        catch (ccm.local.Components.CCMException e)" + NL + "        {" + NL + "            throw new ccm.local.Components.InvalidConfiguration();" + NL + "        }" + NL + "    }" + NL + "" + NL + "    public void remove()" + NL + "        throws ccm.local.Components.RemoveFailure" + NL + "    { " + NL + "        System.out.println(\" ";
  protected final String TEXT_25 = "Adapter.remove()\");" + NL + "        try " + NL + "        {" + NL + "            localInterface.ccm_remove();" + NL + "        }" + NL + "        catch(ccm.local.Components.CCMException e)" + NL + "        {" + NL + "            throw new ccm.local.Components.RemoveFailure();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    public ccm.local.Components.HomeExecutorBase get_ccm_home()" + NL + "    {" + NL + "        throw new RuntimeException(\"Not implemented!\");" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Navigation interface methods */" + NL + "    " + NL + "    public Object provide_facet(String name)" + NL + "        throws ccm.local.Components.InvalidName" + NL + "    {" + NL + "        System.out.println(\" ";
  protected final String TEXT_26 = "Adapter.provide_facet()\");" + NL + "        if(name == null)" + NL + "        {" + NL + "            throw new ccm.local.Components.InvalidName();" + NL + "        }";
  protected final String TEXT_27 = NL;
  protected final String TEXT_28 = "   " + NL + "        throw new ccm.local.Components.InvalidName();" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Receptacle methods */" + NL + "    " + NL + "    public ccm.local.Components.Cookie connect(String name, Object connection)" + NL + "        throws ccm.local.Components.InvalidName, " + NL + "               ccm.local.Components.InvalidConnection," + NL + "               ccm.local.Components.AlreadyConnected, " + NL + "               ccm.local.Components.ExceededConnectionLimit" + NL + "    {" + NL + "        System.out.println(\" ";
  protected final String TEXT_29 = "Adapter.connect()\");" + NL + "        throw new ccm.local.Components.InvalidName();" + NL + "    }" + NL + "    " + NL + "    public void disconnect(String name, ccm.local.Components.Cookie ck)" + NL + "        throws ccm.local.Components.InvalidName, " + NL + "               ccm.local.Components.InvalidConnection," + NL + "               ccm.local.Components.CookieRequired, " + NL + "               ccm.local.Components.NoConnection" + NL + "    {" + NL + "        System.out.println(\" ";
  protected final String TEXT_30 = "Adapter.disconnect()\");" + NL + "        throw new ccm.local.Components.InvalidName();" + NL + "    }    " + NL + "}";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     ComponentDef component = (ComponentDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(component.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(component.generateJavaNamespace());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(component.generateAbsoluteJavaName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(component.generateAbsoluteJavaCcmName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(component.generateAbsoluteJavaCcmName());
    stringBuffer.append(TEXT_7);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();)
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_8);
    stringBuffer.append(provides.generateFacetAdapterReference());
    
}

    stringBuffer.append(TEXT_9);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();)
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_10);
    stringBuffer.append(uses.generateReceptacleAdapterReference());
    
}

    stringBuffer.append(TEXT_11);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(component.generateAbsoluteJavaCcmName());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_16);
    
for(Iterator i = component.getAttributes().iterator(); i.hasNext();)
{
    AttributeDef attr = (AttributeDef)i.next();

    stringBuffer.append(TEXT_17);
    stringBuffer.append(attr.generateAttributeAdapter());
    
}

    stringBuffer.append(TEXT_18);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();)
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_19);
    stringBuffer.append(provides.generateProvidesDefEquivalentMethodAdapterLocal());
    
}

    stringBuffer.append(TEXT_20);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();)
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_21);
    stringBuffer.append(uses.generateUsesDefEquivalentMethodAdapterLocal());
    
}

    stringBuffer.append(TEXT_22);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_23);
    stringBuffer.append(component.generateAbsoluteJavaCcmName());
    stringBuffer.append(TEXT_24);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_25);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_26);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();)
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_27);
    stringBuffer.append(provides.generateProvidesDefNavigationMethodAdapterLocal());
    
}

    stringBuffer.append(TEXT_28);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_29);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_30);
    return stringBuffer.toString();
  }
}
