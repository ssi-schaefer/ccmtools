package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class AttributeAdapterToCorbaTemplate
{
  protected static String nl;
  public static synchronized AttributeAdapterToCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AttributeAdapterToCorbaTemplate result = new AttributeAdapterToCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    /** Business delegate attribute */" + NL + "    public ";
  protected final String TEXT_2 = " ";
  protected final String TEXT_3 = "()" + NL + "        throws ccm.local.Components.CCMException" + NL + "    {" + NL + "        try" + NL + "        {" + NL + "            return remoteInterface.";
  protected final String TEXT_4 = "();" + NL + "        }" + NL + "        catch(java.lang.Exception e)" + NL + "        {" + NL + "    \t    throw new ccm.local.Components.CCMException(e.getMessage());" + NL + "        }" + NL + "    }" + NL + "     " + NL + "    public void ";
  protected final String TEXT_5 = "(";
  protected final String TEXT_6 = " value)" + NL + "        throws ccm.local.Components.CCMException" + NL + "    {" + NL + "        try" + NL + "        {" + NL + "            remoteInterface.";
  protected final String TEXT_7 = "(value);" + NL + "        }" + NL + "        catch(java.lang.Exception e)" + NL + "        {" + NL + "    \t    throw new ccm.local.Components.CCMException(e.getMessage());" + NL + "        }" + NL + "    } ";
  protected final String TEXT_8 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     AttributeDef attr = (AttributeDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(attr.getType().generateJavaMapping());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(attr.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(attr.getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(attr.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(attr.getType().generateJavaMapping());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(attr.getIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    return stringBuffer.toString();
  }
}
