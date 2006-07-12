package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class OperationDefApplicationImplementationTemplate
{
  protected static String nl;
  public static synchronized OperationDefApplicationImplementationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    OperationDefApplicationImplementationTemplate result = new OperationDefApplicationImplementationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "    public ";
  protected final String TEXT_2 = " ";
  protected final String TEXT_3 = "(";
  protected final String TEXT_4 = ")";
  protected final String TEXT_5 = NL + "        ";
  protected final String TEXT_6 = NL + "    {" + NL + "    \t// TODO: IMPLEMENT ME HERE !" + NL + "    \t";
  protected final String TEXT_7 = NL + "    }    ";
  protected final String TEXT_8 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     OperationDef op = (OperationDef) argument; 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(op.generateReturnType());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(op.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(op.generateParameterDeclarationList());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(op.generateThrowsStatementLocal());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(op.generateDefaultReturnStatement());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    return stringBuffer.toString();
  }
}
