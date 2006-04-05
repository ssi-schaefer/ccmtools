package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class ProvidesDefApplicationClassTemplate
{
  protected static String nl;
  public static synchronized ProvidesDefApplicationClassTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ProvidesDefApplicationClassTemplate result = new ProvidesDefApplicationClassTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/**" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * " + NL + " * ";
  protected final String TEXT_3 = "Impl facet implementation." + NL + " *" + NL + " * // TODO: WRITE YOUR DESCRIPTION HERE !" + NL + " * " + NL + " * @author" + NL + " * @version" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_4 = ";" + NL + "                 ";
  protected final String TEXT_5 = NL;
  protected final String TEXT_6 = "   " + NL + " " + NL + "/** " + NL + " * This class implements a component facet's methods." + NL + " *" + NL + " * // TODO: WRITE YOUR DESCRIPTION HERE !" + NL + " *" + NL + " * @author" + NL + " * @version" + NL + " */" + NL + "public class ";
  protected final String TEXT_7 = "Impl " + NL + "    implements ";
  protected final String TEXT_8 = NL + "{" + NL + "    /** Reference to the facet's component implementation */" + NL + "    private ";
  protected final String TEXT_9 = "Impl component;" + NL + "" + NL + "    public ";
  protected final String TEXT_10 = "Impl(";
  protected final String TEXT_11 = "Impl component)" + NL + "    {" + NL + "        this.component = component;" + NL + "    }" + NL;
  protected final String TEXT_12 = "    ";
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = NL;
  protected final String TEXT_15 = " " + NL + "" + NL + "" + NL + "    /** Business logic implementations */" + NL;
  protected final String TEXT_16 = "    ";
  protected final String TEXT_17 = NL;
  protected final String TEXT_18 = NL + "  ";
  protected final String TEXT_19 = NL;
  protected final String TEXT_20 = " " + NL + "    " + NL + "    ";
  protected final String TEXT_21 = "    ";
  protected final String TEXT_22 = NL;
  protected final String TEXT_23 = " " + NL;
  protected final String TEXT_24 = NL;
  protected final String TEXT_25 = NL + "}";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     
ProvidesDef provides = (ProvidesDef) argument; 
InterfaceDef iface = provides.getInterface();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(iface.generateCcmtoolsVersion());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(iface.generateCcmIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(iface.generateJavaNamespace());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(iface.generateJavaImportStatements());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(provides.getComponent().getIdentifier());
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(iface.generateAbsoluteJavaCcmName());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(provides.getComponent().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(provides.getComponent().getIdentifier());
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(provides.getComponent().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_11);
    
for(Iterator i=iface.getBaseInterfaces().iterator(); i.hasNext();)
{
	InterfaceDef baseIface = (InterfaceDef)i.next();
	for(Iterator j=baseIface.getAttributes().iterator(); j.hasNext(); )
	{
        AttributeDef attr = (AttributeDef)j.next();

    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(attr.generateApplicationDeclaration());
    
    }
}

    
for(Iterator i=iface.getAttributes().iterator(); i.hasNext();)
{
    AttributeDef attr = (AttributeDef)i.next();

    stringBuffer.append(TEXT_14);
    stringBuffer.append(attr.generateApplicationDeclaration());
    
}

    stringBuffer.append(TEXT_15);
    
for(Iterator i=iface.getBaseInterfaces().iterator(); i.hasNext();)
{
	InterfaceDef baseIface = (InterfaceDef)i.next();
	for(Iterator j=baseIface.getAttributes().iterator(); j.hasNext(); )
	{
        AttributeDef attr = (AttributeDef)j.next();

    stringBuffer.append(TEXT_16);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(attr.generateApplicationImplementation());
    
    }
}

    stringBuffer.append(TEXT_18);
    
for(Iterator i=iface.getAttributes().iterator(); i.hasNext();)
{
    AttributeDef attr = (AttributeDef)i.next();

    stringBuffer.append(TEXT_19);
    stringBuffer.append(attr.generateApplicationImplementation());
    
}

    stringBuffer.append(TEXT_20);
    
for(Iterator i=iface.getBaseInterfaces().iterator(); i.hasNext();)
{
	InterfaceDef baseIface = (InterfaceDef)i.next();
	for(Iterator j=baseIface.getOperations().iterator(); j.hasNext(); )
	{
        OperationDef op = (OperationDef)j.next();

    stringBuffer.append(TEXT_21);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(op.generateApplicationImplementation());
    
    }
}    

    stringBuffer.append(TEXT_23);
    
for(Iterator i=iface.getOperations().iterator(); i.hasNext();)
{
    OperationDef op = (OperationDef)i.next();

    stringBuffer.append(TEXT_24);
    stringBuffer.append(op.generateApplicationImplementation());
    
}

    stringBuffer.append(TEXT_25);
    return stringBuffer.toString();
  }
}
