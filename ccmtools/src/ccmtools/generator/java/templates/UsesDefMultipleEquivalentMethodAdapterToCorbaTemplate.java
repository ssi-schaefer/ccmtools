package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class UsesDefMultipleEquivalentMethodAdapterToCorbaTemplate
{
  protected static String nl;
  public static synchronized UsesDefMultipleEquivalentMethodAdapterToCorbaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesDefMultipleEquivalentMethodAdapterToCorbaTemplate result = new UsesDefMultipleEquivalentMethodAdapterToCorbaTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public ccm.local.Components.Cookie connect_";
  protected final String TEXT_2 = "(";
  protected final String TEXT_3 = " localObject)" + NL + "        throws ccm.local.Components.ExceededConnectionLimit, " + NL + "               ccm.local.Components.InvalidConnection" + NL + "    {" + NL + "        // TODO: implement template! " + NL + "        throw new ccm.local.Components.InvalidConnection();     " + NL + "    }" + NL + "" + NL + "    public ";
  protected final String TEXT_4 = " disconnect_";
  protected final String TEXT_5 = "(ccm.local.Components.Cookie ck)" + NL + "        throws ccm.local.Components.InvalidConnection" + NL + "    {" + NL + "        // TODO: implement template! " + NL + "        throw new ccm.local.Components.InvalidConnection();" + NL + "    }" + NL + "" + NL + "    public ";
  protected final String TEXT_6 = "[] get_connections_";
  protected final String TEXT_7 = "()" + NL + "    {" + NL + "        // TODO: implement template! " + NL + "        return null;" + NL + "    }";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     UsesDef uses = (UsesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_7);
    return stringBuffer.toString();
  }
}
