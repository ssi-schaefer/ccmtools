package ccmtools.generator.java.clientlib.templates;

import ccmtools.generator.java.clientlib.metamodel.*;

public class UsesReceptacleConnectMethodImplementationTemplate
{
  protected static String nl;
  public static synchronized UsesReceptacleConnectMethodImplementationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesReceptacleConnectMethodImplementationTemplate result = new UsesReceptacleConnectMethodImplementationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "        else if(name.equals(\"";
  protected final String TEXT_2 = "\"))" + NL + "        {" + NL + "            connect_";
  protected final String TEXT_3 = "((";
  protected final String TEXT_4 = ") localObject);" + NL + "            return new ccm.local.Components.Cookie();" + NL + "        }";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     UsesDef uses = (UsesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(uses.getInterface().getAbsoluteJavaName());
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}
