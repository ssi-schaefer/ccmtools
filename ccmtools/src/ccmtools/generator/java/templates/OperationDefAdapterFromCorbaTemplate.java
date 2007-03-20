package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class OperationDefAdapterFromCorbaTemplate
{
  protected static String nl;
  public static synchronized OperationDefAdapterFromCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    OperationDefAdapterFromCorbaTemplate result = new OperationDefAdapterFromCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    /** Business delegate method */" + NL + "    public ";
  protected final String TEXT_2 = " ";
  protected final String TEXT_3 = "(";
  protected final String TEXT_4 = ") ";
  protected final String TEXT_5 = NL + "        ";
  protected final String TEXT_6 = " " + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        " + NL + "        try" + NL + "        {" + NL + "            // Convert CORBA parameters to local Java parameters ";
  protected final String TEXT_7 = NL;
  protected final String TEXT_8 = NL + NL + "            // Define local Java result type ";
  protected final String TEXT_9 = NL;
  protected final String TEXT_10 = NL;
  protected final String TEXT_11 = NL + "            ";
  protected final String TEXT_12 = " " + NL + "        " + NL + "            // Convert local Java parameters to CORBA parameters";
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = "            " + NL + "" + NL + "            // Convert local Java result to a CORBA result                 ";
  protected final String TEXT_15 = NL;
  protected final String TEXT_16 = NL + "        }";
  protected final String TEXT_17 = NL;
  protected final String TEXT_18 = NL + "        catch(java.lang.Exception e)" + NL + "        {" + NL + "    \t        throw new org.omg.CORBA.BAD_OPERATION(e.getMessage());" + NL + "        }" + NL + "    }";
  protected final String TEXT_19 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     OperationDef op = (OperationDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(op.generateCorbaReturnType());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(op.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(op.generateCorbaParameterDeclarationList());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(op.generateThrowsStatementFromCorba());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(op.generateInParameterConvertersFromCorba());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(op.generateResultDeclaration());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(op.generateMethodConverterFromCorba());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(op.generateOutParameterConvertersToCorba());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(op.generateResultConverterToCorba());
    stringBuffer.append(TEXT_16);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(op.generateCatchStatementConverterFromCorba());
    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    return stringBuffer.toString();
  }
}
