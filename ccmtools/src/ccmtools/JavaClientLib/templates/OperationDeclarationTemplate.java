package ccmtools.JavaClientLib.templates;

import ccmtools.JavaClientLib.metamodel.*;

public class OperationDeclarationTemplate
{
  protected static String nl;
  public static synchronized OperationDeclarationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    OperationDeclarationTemplate result = new OperationDeclarationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "    ";
  protected final String TEXT_2 = " ";
  protected final String TEXT_3 = " (";
  protected final String TEXT_4 = ")     " + NL + "        throws CCMException";
  protected final String TEXT_5 = ";    " + NL;
  protected final String TEXT_6 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     OperationDefinition op = (OperationDefinition) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(op.generateOperationReturnType());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(op.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(op.generateOperationParameterDeclarationList());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(op.generateOperationExceptionList());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    return stringBuffer.toString();
  }
}
