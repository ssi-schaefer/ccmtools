package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class AttributeDefDeclarationTemplate
{
  protected static String nl;
  public static synchronized AttributeDefDeclarationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AttributeDefDeclarationTemplate result = new AttributeDefDeclarationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "    ";
  protected final String TEXT_3 = " ";
  protected final String TEXT_4 = "()" + NL + "        throws CCMException;     " + NL + "" + NL + "    void ";
  protected final String TEXT_5 = "(";
  protected final String TEXT_6 = " value)" + NL + "        throws CCMException;     ";
  protected final String TEXT_7 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     AttributeDef attr = (AttributeDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(attr.getType().generateJavaMapping());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(attr.getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(attr.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(attr.getType().generateJavaMapping());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    return stringBuffer.toString();
  }
}
