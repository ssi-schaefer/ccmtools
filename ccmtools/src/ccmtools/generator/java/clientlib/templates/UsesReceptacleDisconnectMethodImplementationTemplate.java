package ccmtools.generator.java.clientlib.templates;

import ccmtools.generator.java.clientlib.metamodel.*;

public class UsesReceptacleDisconnectMethodImplementationTemplate
{
  protected static String nl;
  public static synchronized UsesReceptacleDisconnectMethodImplementationTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesReceptacleDisconnectMethodImplementationTemplate result = new UsesReceptacleDisconnectMethodImplementationTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "        else if(name.equals(\"";
  protected final String TEXT_2 = "\"))" + NL + "        {" + NL + "            disconnect_";
  protected final String TEXT_3 = "();" + NL + "        }";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     UsesDef uses = (UsesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_3);
    return stringBuffer.toString();
  }
}
