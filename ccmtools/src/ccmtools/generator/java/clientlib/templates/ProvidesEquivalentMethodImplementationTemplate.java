package ccmtools.generator.java.clientlib.templates;

import ccmtools.generator.java.clientlib.metamodel.*;

public class ProvidesEquivalentMethodImplementationTemplate
{
  protected static String nl;
  public static synchronized ProvidesEquivalentMethodImplementationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ProvidesEquivalentMethodImplementationTemplate result = new ProvidesEquivalentMethodImplementationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public ";
  protected final String TEXT_2 = " provide_";
  protected final String TEXT_3 = "()" + NL + "    {" + NL + "        if(";
  protected final String TEXT_4 = " == null)" + NL + "        {";
  protected final String TEXT_5 = NL + "            ";
  protected final String TEXT_6 = " = " + NL + "                new ";
  protected final String TEXT_7 = "AdapterToCorba(remoteComponent.provide_";
  protected final String TEXT_8 = "());" + NL + "        }" + NL + "        return ";
  protected final String TEXT_9 = ";" + NL + "    }";
  protected final String TEXT_10 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     ProvidesDef provides = (ProvidesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(provides.getInterface().getAbsoluteJavaName());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(provides.getInterface().getAbsoluteJavaName());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    return stringBuffer.toString();
  }
}
