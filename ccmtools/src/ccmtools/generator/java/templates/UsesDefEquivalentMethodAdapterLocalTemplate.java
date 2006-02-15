package ccmtools.generator.java.templates;

import ccmtools.generator.java.metamodel.*;

public class UsesDefEquivalentMethodAdapterLocalTemplate
{
  protected static String nl;
  public static synchronized UsesDefEquivalentMethodAdapterLocalTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    UsesDefEquivalentMethodAdapterLocalTemplate result = new UsesDefEquivalentMethodAdapterLocalTemplate();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "    public void connect_";
  protected final String TEXT_2 = "(";
  protected final String TEXT_3 = " localObj)" + NL + "        throws ccm.local.Components.AlreadyConnected, " + NL + "               ccm.local.Components.InvalidConnection" + NL + "    {" + NL + "        System.out.println(\" ";
  protected final String TEXT_4 = "Adapter.connect_";
  protected final String TEXT_5 = "()\");" + NL + "        if(";
  protected final String TEXT_6 = "Receptacle != null)" + NL + "        {" + NL + "            throw new ccm.local.Components.AlreadyConnected();" + NL + "        }\t" + NL + "        else" + NL + "        {";
  protected final String TEXT_7 = NL + "            ";
  protected final String TEXT_8 = "Receptacle = localObj;" + NL + "        }" + NL + "    }" + NL + "" + NL + "    public ";
  protected final String TEXT_9 = " disconnect_";
  protected final String TEXT_10 = "()" + NL + "        throws ccm.local.Components.NoConnection" + NL + "    {" + NL + "        System.out.println(\" ";
  protected final String TEXT_11 = "Adapter.disconnect_";
  protected final String TEXT_12 = "()\");" + NL + "        if(";
  protected final String TEXT_13 = "Receptacle == null)" + NL + "        {" + NL + "            throw new ccm.local.Components.NoConnection();" + NL + "        }" + NL + "        else" + NL + "        {";
  protected final String TEXT_14 = NL + "            ";
  protected final String TEXT_15 = " f = ";
  protected final String TEXT_16 = "Receptacle;";
  protected final String TEXT_17 = NL + "            ";
  protected final String TEXT_18 = "Receptacle = null;" + NL + "            return f;" + NL + "        }" + NL + "    }" + NL + "        " + NL + "    public ";
  protected final String TEXT_19 = " get_connection_";
  protected final String TEXT_20 = "()" + NL + "    {" + NL + "        System.out.println(\" ";
  protected final String TEXT_21 = "Adapter. get_connection_";
  protected final String TEXT_22 = "()\");" + NL + "        return ";
  protected final String TEXT_23 = "Receptacle;" + NL + "    }";
  protected final String TEXT_24 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
     UsesDef uses = (UsesDef) argument;  
    stringBuffer.append(TEXT_1);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(uses.getComponent().getIdentifier());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(uses.getComponent().getIdentifier());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_16);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_18);
    stringBuffer.append(uses.getInterface().generateAbsoluteJavaName());
    stringBuffer.append(TEXT_19);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_20);
    stringBuffer.append(uses.getComponent().getIdentifier());
    stringBuffer.append(TEXT_21);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_22);
    stringBuffer.append(uses.getIdentifier());
    stringBuffer.append(TEXT_23);
    stringBuffer.append(TEXT_24);
    return stringBuffer.toString();
  }
}
