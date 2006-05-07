package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class UsesDefMultipleEquivalentMethodAdapterFromCorbaTemplate
{
  protected static String nl;
  public static synchronized UsesDefMultipleEquivalentMethodAdapterFromCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesDefMultipleEquivalentMethodAdapterFromCorbaTemplate result = new UsesDefMultipleEquivalentMethodAdapterFromCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public Components.Cookie connect_";
  protected final String TEXT_2 = "(";
  protected final String TEXT_3 = " obj)" + NL + "        throws Components.ExceededConnectionLimit, Components.InvalidConnection" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        // TODO: " + NL + "        return null;" + NL + "    }" + NL + "" + NL + "    public ";
  protected final String TEXT_4 = " disconnect_";
  protected final String TEXT_5 = "(Components.Cookie ck)" + NL + "        throws Components.InvalidConnection" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        // TODO:" + NL + "        return null;" + NL + "    }" + NL + "" + NL + "    public ";
  protected final String TEXT_6 = "Package.";
  protected final String TEXT_7 = "Connection[] get_connections_";
  protected final String TEXT_8 = "()" + NL + "    {" + NL + "        logger.fine(\"\");" + NL + "        // TODO:" + NL + "        return null;" + NL + "    }";
  protected final String TEXT_9 = NL + "     ";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     UsesDef uses = (UsesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(uses.getInterface().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(uses.getInterface().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(uses.getComponent().generateAbsoluteIdlName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    return stringBuffer.toString();
  }
}
