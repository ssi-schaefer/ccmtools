package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class UsesDefMultipleReceptacleDisconnectMethodAdapterFromCorbaTemplate
{
  protected static String nl;
  public static synchronized UsesDefMultipleReceptacleDisconnectMethodAdapterFromCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesDefMultipleReceptacleDisconnectMethodAdapterFromCorbaTemplate result = new UsesDefMultipleReceptacleDisconnectMethodAdapterFromCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "        else if(name.equals(\"";
  protected final String TEXT_2 = "\"))" + NL + "        {" + NL + "            // TODO: UsesDefMultipleReceptacleDisconnectMethodAdapterFromCorba" + NL + "        }";
  protected final String TEXT_3 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     UsesDef uses = (UsesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    return stringBuffer.toString();
  }
}
