package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class AttributeDefAdapterFromCorbaTemplate
{
  protected static String nl;
  public static synchronized AttributeDefAdapterFromCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AttributeDefAdapterFromCorbaTemplate result = new AttributeDefAdapterFromCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public ";
  protected final String TEXT_2 = " ";
  protected final String TEXT_3 = "()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        try" + NL + "        {";
  protected final String TEXT_4 = NL + "            ";
  protected final String TEXT_5 = " valueLocal;" + NL + "            valueLocal = localInterface.";
  protected final String TEXT_6 = "();";
  protected final String TEXT_7 = NL + "            ";
  protected final String TEXT_8 = " value;" + NL + "            value = ";
  protected final String TEXT_9 = "(valueLocal);" + NL + "            return value;" + NL + "        }" + NL + "        catch(java.lang.Exception e)" + NL + "        {" + NL + "    \t        throw new BAD_OPERATION(e.getMessage());" + NL + "        }" + NL + "    }" + NL + "     " + NL + "    public void ";
  protected final String TEXT_10 = "(";
  protected final String TEXT_11 = " value)" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        try" + NL + "        {";
  protected final String TEXT_12 = NL + "            ";
  protected final String TEXT_13 = " valueLocal;" + NL + "            valueLocal = ";
  protected final String TEXT_14 = "(value);" + NL + "            localInterface.";
  protected final String TEXT_15 = "(valueLocal);" + NL + "        }" + NL + "        catch(java.lang.Exception e)" + NL + "        {" + NL + "    \t        throw new BAD_OPERATION(e.getMessage());" + NL + "        }" + NL + "    } ";
  protected final String TEXT_16 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     AttributeDef attr = (AttributeDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(attr.getType().generateCorbaMapping());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(attr.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(attr.getType().generateJavaMapping());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(attr.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(attr.getType().generateCorbaMapping());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(attr.getType().generateCorbaConverterType());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(attr.getIdentifier());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(attr.getType().generateCorbaMapping());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(attr.getType().generateJavaMapping());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(attr.getType().generateCorbaConverterType());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(attr.getIdentifier());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(TEXT_16);
    return stringBuffer.toString();
  }
}
