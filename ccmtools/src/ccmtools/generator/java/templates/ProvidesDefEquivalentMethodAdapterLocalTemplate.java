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
  protected final String TEXT_3 = "()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        if(";
  protected final String TEXT_4 = "FacetAdapter == null)" + NL + "        {" + NL + "        \tif(delegator!=null)" + NL + "        \t{" + NL + "        \t\tObject o;" + NL + "        \t\ttry {" + NL + "        \t\t\to = delegator.provide(\"";
  protected final String TEXT_5 = "\");" + NL + "        \t\t} catch(Components.InvalidName e) {" + NL + "        \t\t\tthrow new RuntimeException(\"internal error: \"+e.getMessage());" + NL + "        \t\t}" + NL + "        \t\tif(o!=null)" + NL + "        \t\t{" + NL + "        \t\t\t";
  protected final String TEXT_6 = "FacetAdapter =" + NL + "        \t\t\t\t(";
  protected final String TEXT_7 = ")o;" + NL + "        \t\t\treturn ";
  protected final String TEXT_8 = "FacetAdapter;" + NL + "        \t\t}" + NL + "        \t}";
  protected final String TEXT_9 = NL + "            ";
  protected final String TEXT_10 = "FacetAdapter = " + NL + "                new ";
  protected final String TEXT_11 = "Adapter(localInterface.get_";
  protected final String TEXT_12 = "());" + NL + "        }" + NL + "        return ";
  protected final String TEXT_13 = "FacetAdapter;" + NL + "    }";
  protected final String TEXT_14 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     ProvidesDef provides = (ProvidesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(provides.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(provides.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(provides.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(TEXT_14);
    return stringBuffer.toString();
  }
}
