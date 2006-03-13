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
  protected final String TEXT_6 = " " + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "                " + NL + "        // Convert local Java parameters to CORBA parameters ";
  protected final String TEXT_7 = NL;
  protected final String TEXT_8 = NL + NL + "        // Define CORBA result type ";
  protected final String TEXT_9 = NL;
  protected final String TEXT_10 = NL + "            " + NL + "        try" + NL + "        {";
  protected final String TEXT_11 = NL + "            ";
  protected final String TEXT_12 = "        " + NL + "        }";
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = NL + "        catch(java.lang.Exception e)" + NL + "        {" + NL + "    \t    throw new ccm.local.Components.CCMException(e.getMessage());" + NL + "        }" + NL + "        " + NL + "        // Convert CORBA parameters to local Java parameters";
  protected final String TEXT_15 = NL;
  protected final String TEXT_16 = "            " + NL + "" + NL + "        // Convert CORBA result to a local Java result                 ";
  protected final String TEXT_17 = NL;
  protected final String TEXT_18 = NL + "    }";
  protected final String TEXT_19 = NL;

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
    stringBuffer.append(op.generateInParameterConvertersToCorba());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(op.generateCorbaResultDeclaration());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(op.generateMethodConverterToCorba());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(op.generateCatchStatementConverterToCorba());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(op.generateOutParameterConvertersFromCorba());
    stringBuffer.append(TEXT_16);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(op.generateResultConverterFromCorba());
    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    return stringBuffer.toString();
  }
}
