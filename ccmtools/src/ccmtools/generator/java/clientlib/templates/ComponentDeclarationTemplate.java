package ccmtools.generator.java.clientlib.templates;

import java.util.Iterator;
import ccmtools.generator.java.clientlib.metamodel.*;

public class ComponentDeclarationTemplate
{
  protected static String nl;
  public static synchronized ComponentDeclarationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ComponentDeclarationTemplate result = new ComponentDeclarationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * ";
  protected final String TEXT_3 = NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_4 = ";" + NL + "                 " + NL + "public interface ";
  protected final String TEXT_5 = " " + NL + "    extends ccm.local.Components.CCMObject" + NL + "{" + NL + "    /** Attribute equivalent methods */";
  protected final String TEXT_6 = NL;
  protected final String TEXT_7 = "    " + NL + "" + NL + "    /** Facet equivalent methods */" + NL + "    ";
  protected final String TEXT_8 = NL;
  protected final String TEXT_9 = "    " + NL + "    " + NL + "    /** Receptacle equivalent methods */";
  protected final String TEXT_10 = NL;
  protected final String TEXT_11 = NL + NL + "}";

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
    
for(Iterator i = component.getAttributes().iterator(); i.hasNext();)
{
    AttributeDef attr = (AttributeDef)i.next();

    stringBuffer.append(TEXT_6);
    stringBuffer.append(attr.generateAttributeDeclaration());
    
}

    stringBuffer.append(TEXT_7);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();)
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_8);
    stringBuffer.append(provides.generateProvidesEquivalentMethodDeclaration());
    
}

    stringBuffer.append(TEXT_9);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();)
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_10);
    stringBuffer.append(uses.generateUsesEquivalentMethodDeclaration());
    
}

    stringBuffer.append(TEXT_11);
    return stringBuffer.toString();
  }
}
