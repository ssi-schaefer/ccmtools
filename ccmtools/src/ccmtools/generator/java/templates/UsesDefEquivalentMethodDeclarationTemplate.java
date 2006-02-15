package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class UsesDefEquivalentMethodDeclarationTemplate
{
  protected static String nl;
  public static synchronized UsesDefEquivalentMethodDeclarationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesDefEquivalentMethodDeclarationTemplate result = new UsesDefEquivalentMethodDeclarationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    void connect_";
  protected final String TEXT_2 = "(";
  protected final String TEXT_3 = " localObj)" + NL + "        throws ccm.local.Components.AlreadyConnected, ccm.local.Components.InvalidConnection;" + NL;
  protected final String TEXT_4 = NL + "    ";
  protected final String TEXT_5 = " disconnect_";
  protected final String TEXT_6 = "()" + NL + "        throws ccm.local.Components.NoConnection;" + NL + "        ";
  protected final String TEXT_7 = NL + "    ";
  protected final String TEXT_8 = " get_connection_";
  protected final String TEXT_9 = "();";
  protected final String TEXT_10 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     UsesDef uses = (UsesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    return stringBuffer.toString();
  }
}
