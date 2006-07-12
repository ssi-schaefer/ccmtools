package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class UsesDefReceptacleConnectMethodAdapterToCorbaTemplate
{
  protected static String nl;
  public static synchronized UsesDefReceptacleConnectMethodAdapterToCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesDefReceptacleConnectMethodAdapterToCorbaTemplate result = new UsesDefReceptacleConnectMethodAdapterToCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "        else if(name.equals(\"";
  protected final String TEXT_2 = "\"))" + NL + "        {" + NL + "            connect_";
  protected final String TEXT_3 = "((";
  protected final String TEXT_4 = ") localObject);" + NL + "            return new CookieImpl();" + NL + "        }";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     UsesDef uses = (UsesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}
