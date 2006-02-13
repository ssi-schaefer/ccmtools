package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class CcmComponentAdapterTemplate
{
  protected static String nl;
  public static synchronized CcmComponentAdapterTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    CcmComponentAdapterTemplate result = new CcmComponentAdapterTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * ";
  protected final String TEXT_3 = NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_4 = ";" + NL + "                 " + NL + "public class ";
  protected final String TEXT_5 = "Adapter " + NL + "    implements ";
  protected final String TEXT_6 = NL + "{" + NL + "    private ";
  protected final String TEXT_7 = " localInterface;" + NL + "    private ";
  protected final String TEXT_8 = "_Context ctx;" + NL + "" + NL + "    /** Facet adapter references */";
  protected final String TEXT_9 = NL;
  protected final String TEXT_10 = "  " + NL + "\t" + NL + "\t" + NL + "    protected ";
  protected final String TEXT_11 = "Adapter()" + NL + "    {" + NL + "    }" + NL + "\t" + NL + "    public ";
  protected final String TEXT_12 = "Adapter(";
  protected final String TEXT_13 = " localInterface)" + NL + "    {" + NL + "        this.localInterface = localInterface;" + NL + "    }" + NL + "\t" + NL + "\t" + NL + "    /** Supported interface methods */ " + NL + "\t" + NL + "\t" + NL + "\t" + NL + "    /** " + NL + "     * Equivalent interface methods " + NL + "     */" + NL + "\t" + NL + "    /** Attribute equivalent methods */";
  protected final String TEXT_14 = NL;
  protected final String TEXT_15 = "    " + NL + "" + NL + "    /** Facet equivalent methods */" + NL + "    ";
  protected final String TEXT_16 = NL;
  protected final String TEXT_17 = "    " + NL + "    " + NL + "    /** Receptacle equivalent methods */";
  protected final String TEXT_18 = NL;
  protected final String TEXT_19 = NL + "\t" + NL + "    /** CCMObject interface methods */" + NL + "\t" + NL + "    public void configuration_complete()" + NL + "        throws ccm.local.Components.InvalidConfiguration" + NL + "    {" + NL + "        ctx = new ";
  protected final String TEXT_20 = "_ContextImpl(this); " + NL + "        try" + NL + "        {" + NL + "            localInterface.set_session_context(ctx);" + NL + "            localInterface.ccm_activate();" + NL + "        }" + NL + "        catch (ccm.local.Components.CCMException e)" + NL + "        {" + NL + "            throw new ccm.local.Components.InvalidConfiguration();" + NL + "        }" + NL + "    }" + NL + "" + NL + "    public void remove()" + NL + "        throws ccm.local.Components.RemoveFailure" + NL + "    { " + NL + "        try " + NL + "        {" + NL + "            localInterface.ccm_remove();" + NL + "        }" + NL + "        catch(ccm.local.Components.CCMException e)" + NL + "        {" + NL + "            throw new ccm.local.Components.RemoveFailure();" + NL + "        }" + NL + "    }" + NL + "    " + NL + "    public ccm.local.Components.HomeExecutorBase get_ccm_home()" + NL + "    {" + NL + "        throw new RuntimeException(\"Not implemented!\");" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Navigation interface methods */" + NL + "    " + NL + "    public Object provide_facet(String name)" + NL + "        throws ccm.local.Components.InvalidName" + NL + "    {" + NL + "        if(name == null)" + NL + "        {" + NL + "            throw new ccm.local.Components.InvalidName();" + NL + "        }";
  protected final String TEXT_21 = NL;
  protected final String TEXT_22 = "   " + NL + "        throw new ccm.local.Components.InvalidName();" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /** Receptacle methods */" + NL + "    " + NL + "    public ccm.local.Components.Cookie connect(String name, Object connection)" + NL + "        throws ccm.local.Components.InvalidName, " + NL + "               ccm.local.Components.InvalidConnection," + NL + "               ccm.local.Components.AlreadyConnected, " + NL + "               ccm.local.Components.ExceededConnectionLimit" + NL + "    {" + NL + "        throw new ccm.local.Components.InvalidName();" + NL + "    }" + NL + "    " + NL + "    public void disconnect(String name, ccm.local.Components.Cookie ck)" + NL + "        throws ccm.local.Components.InvalidName, " + NL + "               ccm.local.Components.InvalidConnection," + NL + "               ccm.local.Components.CookieRequired, " + NL + "               ccm.local.Components.NoConnection" + NL + "    {" + NL + "        throw new ccm.local.Components.InvalidName();" + NL + "    }    " + NL + "}";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     ComponentDef component = (ComponentDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(component.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(component.generateTimestamp());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(component.getJavaNamespace());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(component.getAbsoluteJavaName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(component.getAbsoluteJavaCcmName());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(component.getAbsoluteJavaCcmName());
    stringBuffer.append(TEXT_8);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();)
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_9);
    stringBuffer.append(provides.generateFacetAdapterReference());
    
}

    stringBuffer.append(TEXT_10);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(component.getAbsoluteJavaCcmName());
    stringBuffer.append(TEXT_13);
    
for(Iterator i = component.getAttributes().iterator(); i.hasNext();)
{
    AttributeDef attr = (AttributeDef)i.next();

    stringBuffer.append(TEXT_14);
    stringBuffer.append(attr.generateAttributeAdapter());
    
}

    stringBuffer.append(TEXT_15);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();)
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_16);
    stringBuffer.append(provides.generateProvidesEquivalentMethodAdapter());
    
}

    stringBuffer.append(TEXT_17);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();)
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_18);
    stringBuffer.append(uses.generateUsesEquivalentMethodAdapter());
    
}

    stringBuffer.append(TEXT_19);
    stringBuffer.append(component.getAbsoluteJavaCcmName());
    stringBuffer.append(TEXT_20);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();)
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_21);
    stringBuffer.append(provides.generateProvidesAdapterNavigationMethodImplementation());
    
}

    stringBuffer.append(TEXT_22);
    return stringBuffer.toString();
  }
}
