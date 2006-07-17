package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class ProvidesDefNavigationMethodAdapterToCorbaTemplate
{
  protected static String nl;
  public static synchronized ProvidesDefNavigationMethodAdapterToCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ProvidesDefNavigationMethodAdapterToCorbaTemplate result = new ProvidesDefNavigationMethodAdapterToCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "        else if(name.equals(\"";
  protected final String TEXT_2 = "\"))" + NL + "        {" + NL + "            return provide_";
  protected final String TEXT_3 = "();" + NL + "        }";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     ProvidesDef provides = (ProvidesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(provides.getIdentifier());
    stringBuffer.append(TEXT_3);
    return stringBuffer.toString();
  }
}
