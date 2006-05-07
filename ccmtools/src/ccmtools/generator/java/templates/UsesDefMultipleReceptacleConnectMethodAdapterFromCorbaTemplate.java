package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class UsesDefMultipleReceptacleConnectMethodAdapterFromCorbaTemplate
{
  protected static String nl;
  public static synchronized UsesDefMultipleReceptacleConnectMethodAdapterFromCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesDefMultipleReceptacleConnectMethodAdapterFromCorbaTemplate result = new UsesDefMultipleReceptacleConnectMethodAdapterFromCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "        else if(name.equals(\"";
  protected final String TEXT_2 = "\"))" + NL + "        {" + NL + "            connect_";
  protected final String TEXT_3 = "(";
  protected final String TEXT_4 = "Helper.narrow(obj));" + NL + "            return new Components.CookieImpl();" + NL + "        }";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     UsesDef uses = (UsesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(uses.getInterface().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}
