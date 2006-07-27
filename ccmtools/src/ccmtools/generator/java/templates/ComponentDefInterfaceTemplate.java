package ccmtools.generator.java.templates;

import java.util.Iterator;
import ccmtools.generator.java.metamodel.*;

public class ComponentDefInterfaceTemplate
{
  protected static String nl;
  public static synchronized ComponentDefInterfaceTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ComponentDefInterfaceTemplate result = new ComponentDefInterfaceTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * This file was automatically generated by ";
  protected final String TEXT_2 = NL + " * <http://ccmtools.sourceforge.net>" + NL + " * DO NOT EDIT!" + NL + " */" + NL + "" + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "           " + NL + "import Components.ccm.local.AlreadyConnected;" + NL + "import Components.ccm.local.CCMObject;" + NL + "import Components.ccm.local.CCMException;" + NL + "import Components.ccm.local.Cookie;             " + NL + "import Components.ccm.local.ExceededConnectionLimit;" + NL + "import Components.ccm.local.InvalidConnection;" + NL + "import Components.ccm.local.NoConnection;" + NL;
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = "  " + NL + "                 " + NL + "public interface ";
  protected final String TEXT_6 = " " + NL + "    extends CCMObject";
  protected final String TEXT_7 = NL + "{" + NL + "    /** Attribute equivalent methods */";
  protected final String TEXT_8 = NL;
  protected final String TEXT_9 = "    " + NL + "" + NL + "    /** Facet equivalent methods */" + NL + "    ";
  protected final String TEXT_10 = NL;
  protected final String TEXT_11 = "    " + NL + "    " + NL + "    /** Receptacle equivalent methods */";
  protected final String TEXT_12 = NL;
  protected final String TEXT_13 = "    " + NL + "}";
  protected final String TEXT_14 = NL;

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
    stringBuffer.append(component.generateSupportsDeclarations());
    stringBuffer.append(TEXT_7);
    
for(Iterator i = component.getAttributes().iterator(); i.hasNext();)
{
    AttributeDef attr = (AttributeDef)i.next();

    stringBuffer.append(TEXT_8);
    stringBuffer.append(attr.generateDeclaration());
    
}

    stringBuffer.append(TEXT_9);
    
for(Iterator i = component.getFacet().iterator(); i.hasNext();)
{
    ProvidesDef provides = (ProvidesDef)i.next();

    stringBuffer.append(TEXT_10);
    stringBuffer.append(provides.generateEquivalentMethodDeclaration());
    
}

    stringBuffer.append(TEXT_11);
    
for(Iterator i = component.getReceptacle().iterator(); i.hasNext();)
{
    UsesDef uses = (UsesDef)i.next();

    stringBuffer.append(TEXT_12);
    stringBuffer.append(uses.generateEquivalentMethodDeclaration());
    
}

    stringBuffer.append(TEXT_13);
    stringBuffer.append(TEXT_14);
    return stringBuffer.toString();
  }
}
