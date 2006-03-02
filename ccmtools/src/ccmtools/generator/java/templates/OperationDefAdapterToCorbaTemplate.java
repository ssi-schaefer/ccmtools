package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class OperationDefAdapterToCorbaTemplate
{
  protected static String nl;
  public static synchronized OperationDefAdapterToCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    OperationDefAdapterToCorbaTemplate result = new OperationDefAdapterToCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public ";
  protected final String TEXT_2 = " ";
  protected final String TEXT_3 = "(";
  protected final String TEXT_4 = ") ";
  protected final String TEXT_5 = NL + "        ";
  protected final String TEXT_6 = " " + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        try" + NL + "        {";
  protected final String TEXT_7 = NL + "            ";
  protected final String TEXT_8 = " remoteInterface.";
  protected final String TEXT_9 = "(";
  protected final String TEXT_10 = ");" + NL + "        }";
  protected final String TEXT_11 = NL;
  protected final String TEXT_12 = NL + "        catch(java.lang.Exception e)" + NL + "        {" + NL + "    \t    throw new ccm.local.Components.CCMException(e.getMessage());" + NL + "        }" + NL + "    }";
  protected final String TEXT_13 = NL;

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
    stringBuffer.append(TEXT_7);
    stringBuffer.append(op.generateReturnStatement());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(op.getIdentifier());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(op.generateParameterList());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(op.generateCatchStatementAdapterToCorba());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    return stringBuffer.toString();
  }
}
