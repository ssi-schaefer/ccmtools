package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class OperationDefAdapterLocalTemplate
{
  protected static String nl;
  public static synchronized OperationDefAdapterLocalTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    OperationDefAdapterLocalTemplate result = new OperationDefAdapterLocalTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    /** Business delegate method */" + NL + "    public ";
  protected final String TEXT_2 = " ";
  protected final String TEXT_3 = "(";
  protected final String TEXT_4 = ") ";
  protected final String TEXT_5 = NL + "        ";
  protected final String TEXT_6 = " " + NL + "    {" + NL + "        System.out.println(\"  ";
  protected final String TEXT_7 = "() Adapter\");";
  protected final String TEXT_8 = NL + "        ";
  protected final String TEXT_9 = " localInterface.";
  protected final String TEXT_10 = "(";
  protected final String TEXT_11 = ");" + NL + "    }";
  protected final String TEXT_12 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
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
    stringBuffer.append(op.getIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(op.generateReturnStatement());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(op.getIdentifier());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(op.generateParameterList());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(TEXT_12);
    return stringBuffer.toString();
  }
}
