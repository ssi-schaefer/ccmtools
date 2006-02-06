package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class ConstantDeclarationTemplate
{
  protected static String nl;
  public static synchronized ConstantDeclarationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ConstantDeclarationTemplate result = new ConstantDeclarationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "    final public static ";
  protected final String TEXT_2 = " ";
  protected final String TEXT_3 = " = (";
  protected final String TEXT_4 = ") ";
  protected final String TEXT_5 = ";";
  protected final String TEXT_6 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     ConstantDef constant = (ConstantDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(constant.getType().generateJavaMapping());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(constant.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(constant.getType().generateJavaMapping());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(constant.generateConstantValue());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    return stringBuffer.toString();
  }
}
