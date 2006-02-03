package ccmtools.generator.java.clientlib.templates;

import ccmtools.generator.java.clientlib.metamodel.*;

public class UsesMultipleEquivalentMethodDeclarationTemplate
{
  protected static String nl;
  public static synchronized UsesMultipleEquivalentMethodDeclarationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesMultipleEquivalentMethodDeclarationTemplate result = new UsesMultipleEquivalentMethodDeclarationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    ccm.local.Components.Cookie connect_";
  protected final String TEXT_2 = "(";
  protected final String TEXT_3 = " localObj)" + NL + "        throws ccm.local.Components.ExceededConnectionLimit, ccm.local.Components.InvalidConnection;" + NL;
  protected final String TEXT_4 = NL + "    ";
  protected final String TEXT_5 = " disconnect_";
  protected final String TEXT_6 = "(ccm.local.Components.Cookie ck)" + NL + "        throws ccm.local.Components.InvalidConnection;" + NL + "        ";
  protected final String TEXT_7 = NL + "    ";
  protected final String TEXT_8 = "[] get_connections_";
  protected final String TEXT_9 = "();";
  protected final String TEXT_10 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     UsesDef uses = (UsesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(uses.getInterface().getAbsoluteJavaName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(uses.getInterface().getAbsoluteJavaName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(uses.getInterface().getAbsoluteJavaName());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    return stringBuffer.toString();
  }
}
