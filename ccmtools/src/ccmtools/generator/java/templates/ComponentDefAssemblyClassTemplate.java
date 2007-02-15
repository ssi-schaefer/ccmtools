package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class ComponentDefAssemblyClassTemplate
{
  protected static String nl;
  public static synchronized ComponentDefAssemblyClassTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ComponentDefAssemblyClassTemplate result = new ComponentDefAssemblyClassTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/**" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * " + NL + " * ";
  protected final String TEXT_3 = " component business logic." + NL + " *" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_4 = ";" + NL + "   " + NL + "import Components.CCMException;     " + NL + "import Components.CCMExceptionReason;" + NL + "import Components.SessionContext; " + NL;
  protected final String TEXT_5 = NL;
  protected final String TEXT_6 = "  " + NL + "   " + NL + "/**" + NL + " * This class implements component equivalent and supported interfaces" + NL + " * as well as component attributes." + NL + " * Additionally, session component callback methods must be implemented." + NL + " *" + NL + " */   " + NL + "public class ";
  protected final String TEXT_7 = "Impl " + NL + "    implements ";
  protected final String TEXT_8 = NL + "{" + NL + "    /** Supported interface attribute variables */" + NL;
  protected final String TEXT_9 = NL;
  protected final String TEXT_10 = "    " + NL + "    " + NL + "" + NL + "    /** Component attribute variables */" + NL + "    ";
  protected final String TEXT_11 = NL;
  protected final String TEXT_12 = " " + NL + "    ";
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = " " + NL + "" + NL + "    public ";
  protected final String TEXT_15 = "_Context ctx;" + NL + "    " + NL + "    " + NL + "    public ";
  protected final String TEXT_16 = "Impl()" + NL + "    {" + NL + "        // OPTIONAL: IMPLEMENT ME HERE !" + NL + "    }" + NL + "" + NL + "" + NL + "    /* " + NL + "     * Supported interface methods " + NL + "     */" + NL + "" + NL + "    /** Supported interface attributes */" + NL;
  protected final String TEXT_17 = NL;
  protected final String TEXT_18 = "    " + NL + "    " + NL + "    " + NL + "    /** Supported interface methods */" + NL + "    ";
  protected final String TEXT_19 = NL;
  protected final String TEXT_20 = "    " + NL + "" + NL + "" + NL + "    /** Component attribute accessor methods */" + NL;
  protected final String TEXT_21 = NL;
  protected final String TEXT_22 = "    " + NL + "" + NL + "" + NL + "    /** Facet implementation factory methods */" + NL + "    ";
  protected final String TEXT_23 = NL;
  protected final String TEXT_24 = "    " + NL + "" + NL + "    /** Component callback methods */" + NL + "    " + NL + "    public void set_session_context(SessionContext ctx) " + NL + "        throws CCMException" + NL + "    {" + NL + "        this.ctx = (";
  protected final String TEXT_25 = "_Context)ctx; " + NL + "    }" + NL + "" + NL + "    public void ccm_activate() " + NL + "        throws CCMException" + NL + "    {" + NL + "        // OPTIONAL: IMPLEMENT ME HERE !" + NL + "    }" + NL + "" + NL + "    public void ccm_passivate() " + NL + "        throws CCMException" + NL + "    {" + NL + "        // OPTIONAL: IMPLEMENT ME HERE !" + NL + "    }" + NL + "" + NL + "    public void ccm_remove() " + NL + "        throws CCMException" + NL + "    {" + NL + "        // OPTIONAL: IMPLEMENT ME HERE !" + NL + "    }" + NL + "}";
  protected final String TEXT_26 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     ComponentDef component = (ComponentDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(component.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(component.generateCcmIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(component.generateJavaNamespace());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(component.generateJavaImportStatements());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(component.generateCcmIdentifier());
    stringBuffer.append(TEXT_8);
    
for(Iterator i = component.getSupports().iterator(); i.hasNext();)
{
    SupportsDef supports = (SupportsDef)i.next();
    for(Iterator j = supports.getInterface().getAllAttributes().iterator(); j.hasNext();)
    {
    	AttributeDef attr = (AttributeDef)j.next();

    stringBuffer.append(TEXT_9);
    stringBuffer.append(attr.generateApplicationDeclaration());
    
	}
}

    stringBuffer.append(TEXT_10);
    
for(Iterator i=component.getAttributes().iterator(); i.hasNext();)
{
    AttributeDef attr = (AttributeDef)i.next();

    stringBuffer.append(TEXT_11);
    stringBuffer.append(attr.generateApplicationDeclaration());
    
}

    stringBuffer.append(TEXT_12);
    
for(Iterator i=component.getAssemblyAttributeDeclarations(); i.hasNext();)
{

    stringBuffer.append(TEXT_13);
    stringBuffer.append(i.next().toString());
    
}

    stringBuffer.append(TEXT_14);
    stringBuffer.append(component.generateCcmIdentifier());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(component.getIdentifier());
    stringBuffer.append(TEXT_16);
    
for(Iterator i = component.getSupports().iterator(); i.hasNext();)
{
    SupportsDef supports = (SupportsDef)i.next();
    for(Iterator j = supports.getInterface().getAllAttributes().iterator(); j.hasNext();)
    {
    	AttributeDef attr = (AttributeDef)j.next();

    stringBuffer.append(TEXT_17);
    stringBuffer.append(attr.generateApplicationImplementation());
    
	}
}

    stringBuffer.append(TEXT_18);
    
for(Iterator i = component.getSupports().iterator(); i.hasNext();)
{
    SupportsDef supports = (SupportsDef)i.next();
    for(Iterator j = supports.getInterface().getAllOperations().iterator(); j.hasNext();)
    {
    	OperationDef op = (OperationDef)j.next();

    stringBuffer.append(TEXT_19);
    stringBuffer.append(op.generateApplicationImplementation());
    
	}
}

    stringBuffer.append(TEXT_20);
    
for(Iterator i = component.getAttributes().iterator(); i.hasNext();)
{
    AttributeDef attr = (AttributeDef)i.next();

    stringBuffer.append(TEXT_21);
    stringBuffer.append(attr.generateApplicationImplementation());
    
}

    stringBuffer.append(TEXT_22);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();)
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_23);
    stringBuffer.append(provides.generateGetMethodImplementation());
    
}

    stringBuffer.append(TEXT_24);
    stringBuffer.append(component.generateCcmIdentifier());
    stringBuffer.append(TEXT_25);
    stringBuffer.append(TEXT_26);
    return stringBuffer.toString();
  }
}
