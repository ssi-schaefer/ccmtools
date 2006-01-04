package ccmtools.JavaClientLib.templates;

import ccmtools.JavaClientLib.metamodel.*;

public class OperationAdapterFromCorbaTemplate
{
  protected static String nl;
  public static synchronized OperationAdapterFromCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    OperationAdapterFromCorbaTemplate result = new OperationAdapterFromCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    /** Business delegate method */" + NL + "    public ";
  protected final String TEXT_2 = " ";
  protected final String TEXT_3 = " (";
  protected final String TEXT_4 = ") " + NL + "    {" + NL + "        try" + NL + "        {" + NL + "            return localInterface.";
  protected final String TEXT_5 = "(";
  protected final String TEXT_6 = ");" + NL + "        }" + NL + "        catch(java.lang.Exception e)" + NL + "        {" + NL + "    \t    throw new BAD_OPERATION(e.getMessage());" + NL + "        }" + NL + "    }";
  protected final String TEXT_7 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     OperationDef op = (OperationDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(op.generateOperationReturnType());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(op.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(op.generateOperationParameterDeclarationList());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(op.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(op.generateOperationParameterList());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    return stringBuffer.toString();
  }
}
