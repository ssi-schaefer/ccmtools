package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class ProvidesEquivalentMethodAdapterTemplate
{
  protected static String nl;
  public static synchronized ProvidesEquivalentMethodAdapterTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ProvidesEquivalentMethodAdapterTemplate result = new ProvidesEquivalentMethodAdapterTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public ";
  protected final String TEXT_2 = " provide_";
  protected final String TEXT_3 = "()" + NL + "    {" + NL + "        if(";
  protected final String TEXT_4 = "FacetAdapter == null)" + NL + "        {";
  protected final String TEXT_5 = NL + "            ";
  protected final String TEXT_6 = "FacetAdapter = " + NL + "                new ";
  protected final String TEXT_7 = "Adapter(localInterface.get_";
  protected final String TEXT_8 = "());" + NL + "        }" + NL + "        return ";
  protected final String TEXT_9 = "FacetAdapter;" + NL + "    }";
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
