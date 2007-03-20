package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class AttributeDefApplicationImplementationTemplate
{
  protected static String nl;
  public static synchronized AttributeDefApplicationImplementationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AttributeDefApplicationImplementationTemplate result = new AttributeDefApplicationImplementationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public ";
  protected final String TEXT_2 = " ";
  protected final String TEXT_3 = "()" + NL + "        throws Components.CCMException" + NL + "    {" + NL + "        return this.";
  protected final String TEXT_4 = "_;" + NL + "    }     " + NL + "" + NL + "    public void ";
  protected final String TEXT_5 = "(";
  protected final String TEXT_6 = " value)" + NL + "        throws Components.CCMException" + NL + "    {" + NL + "        this.";
  protected final String TEXT_7 = "_ = value;" + NL + "    }";
  protected final String TEXT_8 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
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
