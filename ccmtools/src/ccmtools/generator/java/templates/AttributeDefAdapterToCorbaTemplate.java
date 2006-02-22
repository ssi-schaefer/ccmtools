package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class AttributeDefAdapterToCorbaTemplate
{
  protected static String nl;
  public static synchronized AttributeDefAdapterToCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AttributeDefAdapterToCorbaTemplate result = new AttributeDefAdapterToCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    /** Business delegate attribute */" + NL + "    public ";
  protected final String TEXT_2 = " ";
  protected final String TEXT_3 = "()" + NL + "        throws ccm.local.Components.CCMException" + NL + "    {" + NL + "        try" + NL + "        {";
  protected final String TEXT_4 = NL + "            ";
  protected final String TEXT_5 = " result = remoteInterface.";
  protected final String TEXT_6 = "();" + NL + "            logger.fine(\"result = \" + result);" + NL + "            return result;" + NL + "        }" + NL + "        catch(java.lang.Exception e)" + NL + "        {" + NL + "    \t    throw new ccm.local.Components.CCMException(e.getMessage());" + NL + "        }" + NL + "    }" + NL + "     " + NL + "    public void ";
  protected final String TEXT_7 = "(";
  protected final String TEXT_8 = " value)" + NL + "        throws ccm.local.Components.CCMException" + NL + "    {" + NL + "        logger.fine(\"value = \" + value);" + NL + "        try" + NL + "        {" + NL + "            remoteInterface.";
  protected final String TEXT_9 = "(value);" + NL + "        }" + NL + "        catch(java.lang.Exception e)" + NL + "        {" + NL + "    \t    throw new ccm.local.Components.CCMException(e.getMessage());" + NL + "        }" + NL + "    } ";
  protected final String TEXT_10 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     AttributeDef attr = (AttributeDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(attr.getType().generateJavaMapping());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(attr.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(attr.getType().generateJavaMapping());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(attr.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(attr.getIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(attr.getType().generateJavaMapping());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(attr.getIdentifier());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    return stringBuffer.toString();
  }
}
