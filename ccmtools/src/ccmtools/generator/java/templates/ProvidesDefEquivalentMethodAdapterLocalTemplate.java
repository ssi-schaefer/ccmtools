package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class ProvidesDefEquivalentMethodAdapterLocalTemplate
{
  protected static String nl;
  public static synchronized ProvidesDefEquivalentMethodAdapterLocalTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ProvidesDefEquivalentMethodAdapterLocalTemplate result = new ProvidesDefEquivalentMethodAdapterLocalTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public ";
  protected final String TEXT_2 = " provide_";
  protected final String TEXT_3 = "()" + NL + "    {" + NL + "        System.out.println(\" ";
  protected final String TEXT_4 = "Adapter.provide_";
  protected final String TEXT_5 = "()\");" + NL + "        if(";
  protected final String TEXT_6 = "FacetAdapter == null)" + NL + "        {";
  protected final String TEXT_7 = NL + "            ";
  protected final String TEXT_8 = "FacetAdapter = " + NL + "                new ";
  protected final String TEXT_9 = "Adapter(localInterface.get_";
  protected final String TEXT_10 = "());" + NL + "        }" + NL + "        return ";
  protected final String TEXT_11 = "FacetAdapter;" + NL + "    }";
  protected final String TEXT_12 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     ProvidesDef provides = (ProvidesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(provides.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(provides.getComponent().getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(provides.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(TEXT_12);
    return stringBuffer.toString();
  }
}
