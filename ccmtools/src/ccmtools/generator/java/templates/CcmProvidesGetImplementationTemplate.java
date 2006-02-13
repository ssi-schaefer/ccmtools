package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class CcmProvidesGetImplementationTemplate
{
  protected static String nl;
  public static synchronized CcmProvidesGetImplementationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    CcmProvidesGetImplementationTemplate result = new CcmProvidesGetImplementationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public ";
  protected final String TEXT_2 = " get_";
  protected final String TEXT_3 = "()" + NL + "    {" + NL + "        return new ";
  protected final String TEXT_4 = "Impl(this);" + NL + "    }";
  protected final String TEXT_5 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     ProvidesDef provides = (ProvidesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(provides.getInterface().getAbsoluteJavaCcmName());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(provides.getAbsoluteJavaName());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    return stringBuffer.toString();
  }
}
